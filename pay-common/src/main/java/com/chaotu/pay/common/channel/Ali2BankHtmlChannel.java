package com.chaotu.pay.common.channel;


import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.StringResultSender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class Ali2BankHtmlChannel extends AbstractChannel {

    //private final BigDecimal bigDecimal100 = new BigDecimal(100);
    private static final String successStr = "success";

    public Ali2BankHtmlChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String, Object> order) {
        SortedMap<String, Object> map = new TreeMap<>(order);
        return DigestUtil.createSignBySortMap(map, getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String merchant = request.getParameter("merchant");
        String amount = request.getParameter("amount");
        String sys_order_no = request.getParameter("sys_order_no");

        String out_order_no = request.getParameter("out_order_no");
        String order_time = request.getParameter("order_time");
        String realPrice = request.getParameter("realPrice");
        String cuid = request.getParameter("cuid");
        String attach = request.getParameter("attach");
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        if(StringUtils.isNotBlank(realPrice)){
            sortedMap.put("realPrice", realPrice);
        }
        if(StringUtils.isNotBlank(cuid)){
            sortedMap.put("cuid", cuid);
        }
        if(StringUtils.isNotBlank(attach)){
            sortedMap.put("attach", attach);
        }
        sortedMap.put("merchant", merchant);
        sortedMap.put("amount", amount);
        sortedMap.put("sys_order_no", sys_order_no);
        sortedMap.put("out_order_no", out_order_no);
        sortedMap.put("order_time", order_time);
        log.info(JSONObject.toJSONString(sortedMap));
        return DigestUtil.createSignBySortMap(sortedMap, getAccount().getSignKey());
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return StringUtils.equals(createNotifySign(signParam, request), request.getParameter("sign"));
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    Map<String, Object> createSignMap(OrderVo order) {
        Map<String, Object> sortedMap = new HashMap<>();
        sortedMap.put("merchant", getAccount().getAccount());
        sortedMap.put("amount", order.getAmount().toString());
        sortedMap.put("order_no", order.getOrderNo());
        sortedMap.put("notify_url", getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo());
        sortedMap.put("return_url", "https://www.baidu.com");
        sortedMap.put("order_time", DateUtil.getDateTime(order.getCreateTime()));
        sortedMap.put("pay_code", getChannel().getChannelCode());
        System.out.println(JSONObject.toJSONString(sortedMap));
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object, Object> sortedMap = new HashMap<>();
        sortedMap.put("merchant", getAccount().getAccount());
        sortedMap.put("amount", order.getAmount().toString());
        sortedMap.put("order_no", order.getOrderNo());
        sortedMap.put("notify_url", getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo());
        sortedMap.put("return_url", "https://www.baidu.com");
        sortedMap.put("order_time", DateUtil.getDateTime(order.getCreateTime()));
        sortedMap.put("pay_code", getChannel().getChannelCode());
        sortedMap.put("json", "json");
        sortedMap.put("sign", sign);
        PddMerchantSender<Map<String, Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(), sortedMap, null);
        Map<String, Object> resMap = paySender.send();
        if (resMap.get("QRCodeLink") != null) {
            String QRCodeLink = (String) resMap.get("QRCodeLink");
            SortedMap<String, Object> result = new TreeMap<>();
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", QRCodeLink);
            result.put("success", "1");
            result.put("underOrderNo", order.getUnderOrderNo());
            result.put("orderNo", order.getOrderNo());
            result.put("upperOrderNo", "1");
            String resultSign = DigestUtil.createSignBySortMap(result, order.getUserKey()).toUpperCase();
            result.put("sign", resultSign);
            return result;
        }
        log.info("下单失败！失败信息:[" + JSONObject.toJSONString(resMap) + "]");
        return null;
    }

    private boolean checkSign(SortedMap<Object, Object> params, String sign) {
        return StringUtils.equals(DigestUtil.createSign(params, getAccount().getSignKey()), sign);
    }
}
