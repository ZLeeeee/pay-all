package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DateUtil;
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
import java.util.*;

@Slf4j
public class XiAnTaoBaChannel extends AbstractChannel {


    private static final String successStr = "success";
    private static final Random random = new Random();

    public XiAnTaoBaChannel(TChannel channel, TChannelAccount account) {
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
        Map<String,String> map = JSONObject.parseObject(RequestUtil.getBody(request),Map.class);
            String sign = map.get("sign");
            String str  = map.get("user_order_no")
                + map.get("orderno")
                + map.get("tradeno")
                + map.get("price")
                + map.get("realprice")
                + getAccount().getSignKey();
            String sign1 = DigestUtils.md5Hex(str);
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+str);
        if (StringUtils.equals(sign,sign1))
                return true;

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
        Map<String,String> content = new TreeMap<>();
        content.put("user_order_no",order.getOrderNo());
        content.put("price",order.getAmount().setScale(2).toString());
        content.put("uid",getAccount().getAccount());
        content.put("tm", DateUtil.getDateTime(order.getCreateTime()));
        content.put("paytype", "106");
        content.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        content.put("return_url","https://www.baidu.com");
        //content.put("pay_amount",order.getAmount().toString());
        String sign1 =
                content.get("uid")
                + content.get("price")
                + content.get("paytype")
                + content.get("notify_url")
                + content.get("return_url")
                + content.get("user_order_no")
                + getAccount().getSignKey();
        content.put("sign",DigestUtils.md5Hex(sign1));
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),content,null);
        Map<String, Object> resp = paySender.send();
        SortedMap<String,Object> result = new TreeMap<>();
        Object payurl = resp.get("payurl");
        if(payurl!=null) {
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", payurl.toString());
            result.put("success", "1");
            result.put("underOrderNo", order.getUnderOrderNo());
            result.put("orderNo", order.getOrderNo());
            result.put("upperOrderNo", "1");
            String resultSign = DigestUtil.createSignBySortMap(result, order.getUserKey()).toUpperCase();
            result.put("sign", resultSign);
            return result;
        }
        log.info("下单失败！失败信息:["+JSONObject.toJSONString(resp)+"]");
        return null;

    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
    private  String createSign(SortedMap<String, Object> parameters, String key) {


        return "sign"; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
