package com.chaotu.pay.common.channel;


import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.StringResultSender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Ali2BankHtmlChannel extends AbstractChannel {

    //private final BigDecimal bigDecimal100 = new BigDecimal(100);
    private static final String successStr = "success";

    public Ali2BankHtmlChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        SortedMap<String,Object> map = new TreeMap<>(order);
        return DigestUtil.createSignBySortMap(map,getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String merchant = request.getParameter("merchant");
        String amount = request.getParameter("amount");
        String sys_order_no = request.getParameter("sys_order_no");

        String out_order_no = request.getParameter("out_order_no");
        String order_time = request.getParameter("order_time");
        SortedMap<String , Object> sortedMap = new TreeMap<>();
        sortedMap.put("merchant",merchant);
        sortedMap.put("amount",amount);
        sortedMap.put("sys_order_no",sys_order_no);
        sortedMap.put("out_order_no",out_order_no);
        sortedMap.put("order_time",order_time);
        return DigestUtil.createSignBySortMap(sortedMap,getAccount().getSignKey());
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return StringUtils.equals(createNotifySign(signParam,request),request.getParameter("sign"));
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    Map<String, Object> createSignMap(OrderVo order) {
        Map<String,Object> sortedMap = new HashMap<>();
        sortedMap.put("merchant",getAccount().getAccount());
        sortedMap.put("amount",order.getAmount().toString());
        sortedMap.put("order_no",order.getOrderNo());
        sortedMap.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("return_url","https://www.baidu.com");
        sortedMap.put("order_time", DateUtil.getDateTime(order.getCreateTime()));
        sortedMap.put("pay_code", "alipay_bank2");
        System.out.println(JSONObject.toJSONString(sortedMap));
        return sortedMap;
    }

    @Override
    public String requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();
        sortedMap.put("merchant",getAccount().getAccount());
        sortedMap.put("amount",order.getAmount().toString());
        sortedMap.put("order_no",order.getOrderNo());
        sortedMap.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("return_url","https://www.baidu.com");
        sortedMap.put("order_time", DateUtil.getDateTime(order.getCreateTime()));
        sortedMap.put("pay_code", "alipay_bank2");
        sortedMap.put("sign",sign);
        System.out.println(JSONObject.toJSONString(sortedMap));
        StringResultSender paySender = new StringResultSender(getChannel().getRequestUrl(),sortedMap,null);
        return paySender.send();
    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
