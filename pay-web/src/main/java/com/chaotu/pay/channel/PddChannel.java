package com.chaotu.pay.channel;

import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PddChannel extends AbstractChannel {

    public PddChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        SortedMap<Object,Object> sortedMap = new TreeMap<>(order);
        return DigestUtil.createSign(sortedMap, getAccount().getSignKey());
    }

    @Override
    Map<String, Object> createSignMap(OrderVo order) {
        Map<String,Object> sortedMap = new HashMap<>();
        sortedMap.put("userId",order.getUserId());
        sortedMap.put("amount",order.getAmount());
        sortedMap.put("underOrderNo",order.getUnderOrderNo());
        sortedMap.put("notifyUrl",order.getNotifyUrl());
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();
        sortedMap.put("userId",order.getUserId());
        sortedMap.put("amount",order.getAmount());
        sortedMap.put("userOrderNo",order.getUnderOrderNo());
        sortedMap.put("notifyUrl",order.getNotifyUrl());
        sortedMap.put("sign",sign);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),sortedMap,null);
        Map<String, Object> resMap = paySender.send();
        String upperSign = resMap.remove("sign").toString();
        SortedMap<Object,Object> params = new TreeMap<>(resMap);
        if(checkSign(params,upperSign))
            return null;
        return resMap;
    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
