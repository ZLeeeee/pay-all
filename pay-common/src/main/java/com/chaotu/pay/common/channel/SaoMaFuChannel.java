package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
public class SaoMaFuChannel extends AbstractChannel {

    private static final String successStr = "SUCCESS";

    public SaoMaFuChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return createSignBySortMap((SortedMap<String, Object>) order,getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        SortedMap<String,Object> map = new TreeMap<>(request.getParameterMap());
        map.remove("sign");
        log.info(JSONObject.toJSONString(map));
        return createSignBySortMap(map,getAccount().getSignKey());
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
        String id = getAccount().getAccount();
        int pay_type = 2;
        String price = order.getAmount().toString();
        String out_trade_no = order.getOrderNo();
        String notify_url = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String pay_user_id = "666";
        sortedMap.put("id",id);
        sortedMap.put("pay_type",pay_type);
        sortedMap.put("price",price);
        sortedMap.put("out_trade_no",out_trade_no);
        sortedMap.put("pay_user_id",pay_user_id);
        sortedMap.put("notify_url",notify_url);
        sortedMap.put("pay_time",order.getCreateTime().getTime());
        sortedMap.put("extend","");
        sortedMap.put("return_url","");
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        String id = getAccount().getAccount();
        int pay_type = 2;
        String price = order.getAmount().toString();
        String out_trade_no = order.getOrderNo();
        String notify_url = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String pay_user_id = "666";
        sortedMap.put("id",id);
        sortedMap.put("pay_type",pay_type);
        sortedMap.put("price",price);
        sortedMap.put("out_trade_no",out_trade_no);
        sortedMap.put("pay_user_id",pay_user_id);
        sortedMap.put("notify_url",notify_url);
        sortedMap.put("pay_time",order.getCreateTime().getTime());
        sortedMap.put("extend","");
        sortedMap.put("return_url","");
        sortedMap.put("sign",sign);
        String url = getChannel().getRequestUrl()
                +"?"+RequestUtil.createPostParamStr(sortedMap);
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
    private static String createSignBySortMap(SortedMap<String, Object> parameters, String key) {

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
                sb.append(k + "=" + v==null?"":v + "&");
            }
        }
        sb.append(key); //KEY是商户秘钥
        String sign = DigestUtils.md5Hex(sb.toString());
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
