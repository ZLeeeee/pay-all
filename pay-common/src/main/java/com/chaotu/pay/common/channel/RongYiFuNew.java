package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class RongYiFuNew extends AbstractChannel {



    private static final String successStr = "success";

    public RongYiFuNew(TChannel channel, TChannelAccount account) {
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
        String body = RequestUtil.getBody(request);
        log.info("通道:"+getChannel().getChannelName()+"接收回调开始验证["+body+"]");
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (StringUtils.equals(jsonObject.getString("status"),"SUCCESS")) {
            String sign = jsonObject.getString("sign");
            String merchant_no = jsonObject.getString("merchant_no");
            String order_no = jsonObject.getString("order_no");
            String system_no = jsonObject.getString("system_no");
            String price = jsonObject.getString("price");
            request.setAttribute("amount",price);
            String status = jsonObject.getString("status");
            String s = merchant_no + order_no + system_no + price + status + getAccount().getSignKey();
            if(DigestUtils.md5Hex(s).equals(sign))
                return true;
        }
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
        sortedMap.put("order_no",order.getOrderNo());
        sortedMap.put("price",order.getAmount().setScale(2).toString());
        sortedMap.put("merch_no",getAccount().getAccount());
        sortedMap.put("paytype",getChannel().getChannelCode());
        sortedMap.put("time", DateUtil.getDateTime(order.getCreateTime()));
        sortedMap.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("sign",DigestUtils.md5Hex(getAccount().getAccount()+sortedMap.get("price")+sortedMap.get("paytype")+sortedMap.get("notify_url")+order.getOrderNo()+sortedMap.get("time")+getAccount().getSignKey()));
        sortedMap.put("url",getChannel().getRequestUrl());
        String postParamStr = RequestUtil.createPostParamStr(sortedMap);
        String url= CommonConstant.REDIRECT_URL+postParamStr;

            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",url);
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo","1");
            String resultSign = DigestUtil.createSignBySortMap(result,order.getUserKey()).toUpperCase();
            result.put("sign",resultSign);
            return result;

    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
