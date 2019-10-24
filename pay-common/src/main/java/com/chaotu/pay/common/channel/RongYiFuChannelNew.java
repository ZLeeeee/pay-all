package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class RongYiFuChannelNew extends AbstractChannel {



    private static final String successStr = "success";

    public RongYiFuChannelNew(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return null;
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {

        return "";
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        String state = request.getParameter("state");
        String sign = null;
        if (StringUtils.equals("1",state)) {
            sign = state+getAccount().getAccount()+request.getParameter("orderNo")+request.getParameter("amount")+getAccount().getSignKey();
            request.setAttribute("amount",request.getParameter("actualPayAmount"));
            if (StringUtils.equals(request.getParameter("sign"),DigestUtils.md5Hex(sign))) {
                return true;
            }
        }
        log.info("通道:"+getChannel().getChannelName()+"接收回调开始验证["+sign+"]");
        return false;
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    SortedMap<String, Object> createSignMap(OrderVo order) {


        return null;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        sortedMap.put("orderNo",order.getOrderNo());
        sortedMap.put("amount",order.getAmount().setScale(2).toString());
        sortedMap.put("merchantNum",getAccount().getAccount());
        sortedMap.put("payType",getChannel().getChannelCode());
        sortedMap.put("returnUrl","http://www.baidu.com");
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("sign",DigestUtils.md5Hex(getAccount().getAccount()+order.getOrderNo()+sortedMap.get("amount")+sortedMap.get("notifyUrl")+getAccount().getSignKey()));
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),RequestUtil.createPostParamStr(sortedMap),null);
        Map<String, Object> resMap = paySender.send();

        if(StringUtils.equalsIgnoreCase(resMap.get("code").toString(),"200") ){
            JSONObject data = (JSONObject) resMap.get("data");
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",data.getString("payUrl"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo","1");
            String resultSign = DigestUtil.createSignBySortMap(result,order.getUserKey()).toUpperCase();
            result.put("sign",resultSign);
            return result;
        }
        log.info("下单失败！失败信息:["+JSONObject.toJSONString(resMap)+"]");
        return null;
    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
