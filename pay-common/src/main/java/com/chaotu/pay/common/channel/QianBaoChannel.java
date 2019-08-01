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
public class QianBaoChannel extends AbstractChannel {

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "success";

    public QianBaoChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return createSign((SortedMap<String, Object>) order,getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String order_sn = request.getParameter("order_sn");
        String out_order_sn = request.getParameter("out_order_sn");
        String paymoney = request.getParameter("paymoney");
        String pay_time = request.getParameter("pay_time");
        String status = request.getParameter("status");
        SortedMap<String , Object> map = new TreeMap<>();
        map.put("order_sn",order_sn);
        map.put("out_order_sn",out_order_sn);
        map.put("paymoney",paymoney);
        map.put("pay_time",pay_time);
        map.put("status",status);
        System.out.println(JSONObject.toJSONString(map));
        return createSign(map,getAccount().getSignKey());
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
    SortedMap<String, Object> createSignMap(OrderVo order) {
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        sortedMap.put("out_uid",order.getUnderOrderNo());
        sortedMap.put("out_order_sn",order.getOrderNo());
        sortedMap.put("tradeMoney",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("payType", 2);
        sortedMap.put("codeType",2);
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();
        sortedMap.put("business_code",getAccount().getAccount());
        sortedMap.put("out_uid",order.getUnderOrderNo());
        sortedMap.put("out_order_sn",order.getOrderNo());
        sortedMap.put("tradeMoney",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("payType", 2);
        sortedMap.put("codeType",2);
        sortedMap.put("sign",sign);
        String postParamStr = RequestUtil.createPostParamStr(sortedMap);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        Map<String, Object> resMap = paySender.send();
        if(StringUtils.equalsIgnoreCase(resMap.get("data").toString(),"OK") ){
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",resMap.get("info"));
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
    private static String createSign(SortedMap<String, Object> parameters, String key) {

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (!"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("accessKey=" + key); //KEY是商户秘钥
        String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
