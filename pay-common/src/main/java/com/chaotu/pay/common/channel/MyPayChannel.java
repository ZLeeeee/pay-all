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
import java.util.*;

@Slf4j
public class MyPayChannel extends AbstractChannel {


    private static final String successStr = "SUCCESS";

    public MyPayChannel(TChannel channel, TChannelAccount account) {
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
        Map<String,String> map = JSONObject.parseObject(body,Map.class);
        SortedMap<String,Object> sortedMap = new TreeMap<>(map);
        String sign = map.remove("sign");
        String sign1 = createSign(sortedMap,getAccount().getSignKey());
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+body);
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
        SortedMap<String,Object> content = new TreeMap<>();
        content.put("outOrderId",order.getOrderNo());
        content.put("amount",order.getAmount().setScale(2).toString());
        content.put("partner",getAccount().getAccount());
        content.put("payType", "ALIPAY");
        content.put("apiCode", "YL-PAY");
        content.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        content.put("signType","MD5");
        content.put("inputCharset","UTF-8");

        String sign1 = createSign(content, getAccount().getSignKey());

        content.put("sign",sign1);
       PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),content,null);
        Map<String, Object> resp = paySender.send();
        SortedMap<String,Object> result = new TreeMap<>();
        String responseCode = resp.get("responseCode").toString();
        if(StringUtils.equals(responseCode,"0000")) {
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", resp.get("qrCodeUrl").toString());
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
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(key); //KEY是商户秘钥
        String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
