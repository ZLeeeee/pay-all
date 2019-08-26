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
import java.util.*;

@Slf4j
public class RongYiFuChannel extends AbstractChannel {

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "{\"success\":\"1\"}";

    public RongYiFuChannel(TChannel channel, TChannelAccount account) {
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
        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = RequestUtil.getRequestJsonObject(request);
        }catch (Exception e){

        }
        log.info("通道:"+getChannel().getChannelName()+"接收回调开始验证["+requestJsonObject.toJSONString()+"]");
        return true;
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
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        SortedMap<String,Object> content = new TreeMap<>();
        content.put("orderNo",order.getOrderNo());
        content.put("orderAmount",order.getAmount().setScale(2).toString());
        content.put("getway","HXT");
        content.put("payment","ZFB_H5");
        content.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        content.put("body","话费");
        content.put("goodsName","话费");
        sortedMap.put("version","1.0");
        sortedMap.put("merchantNo",getAccount().getAccount());
        sortedMap.put("channel","ORDER_PAY");
        String encodedText = encoder.encodeToString(JSON.toJSONString(content).getBytes(Charset.forName("UTF-8")));
        sortedMap.put("content",encodedText);
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("payType", 2);
        sortedMap.put("codeType",2);
        String sign1 = createSign(sortedMap,getAccount().getSignKey());
        sortedMap.put("sign",sign1);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),sortedMap,null);
        Map<String, Object> resMap = paySender.send();

        if(StringUtils.equalsIgnoreCase(resMap.get("status").toString(),"SUCCESS") ){
            String content1 = new String(decoder.decode(resMap.get("content").toString().getBytes(Charset.forName("UTF-8"))), Charset.forName("UTF-8"));
            Map<String,Object> map = JSONObject.parseObject(content1, Map.class);
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",map.get("payUrl"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",map.get("TradeOrderNo"));
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
    private  String createSign(SortedMap<String, Object> parameters, String key) {

        String str = "version=1.0&merchantNo="+getAccount().getAccount()+"&channel=ORDER_PAY&content="+parameters.get("content")+"&appsecret="+getAccount().getSignKey();
        String sign = DigestUtils.md5Hex(str);
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
