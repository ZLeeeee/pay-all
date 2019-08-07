package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class YzChannel extends AbstractChannel {

    private static final String successStr = "{\"success\":\"1\"}";

    public YzChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return order.get("sign").toString();
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        try {
            JSONObject jsonObject = RequestUtil.getRequestJsonObject(request);
            Map<Object,Object> map = jsonObject.toJavaObject(Map.class);
            SortedMap<Object,Object> signMap = new TreeMap<>(map);
            String sign = signMap.remove("sign").toString();
            String sign1 = DigestUtil.createSign(signMap, getAccount().getSignKey());
            return Boolean.toString(StringUtils.equals(sign1,sign));
        } catch (IOException e) {
            log.error("接受回调异常!");
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return Boolean.valueOf(createNotifySign(signParam,request));
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    SortedMap<String, Object> createSignMap(OrderVo order) {
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        String userOrderNo = order.getOrderNo();
        String notifyUrl = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String amount = order.getAmount().toString();
        String userId = getAccount().getAccount();
        String key = getAccount().getSignKey();
        sortedMap.put("userOrderNo",userOrderNo);
        sortedMap.put("notifyUrl",notifyUrl);
        sortedMap.put("amount",amount);
        sortedMap.put("userId",userId);
        String sign = DigestUtil.createSignBySortMap(sortedMap, key).toUpperCase();
        sortedMap.put("sign",sign);
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        String userOrderNo = order.getOrderNo();
        String notifyUrl = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String amount = order.getAmount().toString();
        String userId = getAccount().getAccount();
        String url = getChannel().getRequestUrl();
        Map<String,Object> map = new HashMap<>();
        map.put("userOrderNo",userOrderNo);
        map.put("notifyUrl",notifyUrl);
        map.put("amount",amount);
        map.put("userId",userId);
        map.put("sign",sign);
        PddSender<Map<String,Object>> sender = new PddSender<>(url,map,null);
        Map<String, Object> resMap = sender.send();
        Object success = resMap.get("success");
        if(StringUtils.equals(success==null?"":success.toString(),"1")){
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",resMap.get("qrCode").toString());
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",resMap.get("orderNo"));
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
