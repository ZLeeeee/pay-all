package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import com.gopay.Sdk;
import com.gopay.StringExtension;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class HiPayChannel extends AbstractChannel {

    private final Sdk sdk;

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "ok";

    public HiPayChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
        sdk = new Sdk(channel.getRequestUrl(),account.getSignKey());
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
        boolean passed = false;
        if (request.getContentType().toLowerCase().equals("application/x-www-form-urlencoded")) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
            Enumeration<String> parameterNames = request.getParameterNames();

            while(parameterNames.hasMoreElements() == true) {
                String parameterName = parameterNames.nextElement();
                parameters.put(parameterName, request.getParameter(parameterName));
            }

            passed = sdk.validate(parameters, request.getParameter("_signature"));
        } else {
            passed = sdk.validate(RequestUtil.getBody(request), request.getHeader("X-GOPAY-SIGNATURE"));
        }
        log.info("通道:"+getChannel().getChannelName()+"接收回调开始验证[]");
        return passed;
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
        // 交易建立
        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
        data.put("amount", order.getAmount().toString());
        data.put("out_trade_no", order.getOrderNo());
        data.put("notify_url", getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        String resp = null;
        try {
             resp = StringExtension.unicodeToUtf8(sdk.send(data));
        }catch (Exception e){

        }
        Map<String,Object> resMap = JSONObject.parseObject(resp, Map.class);

        if(StringUtils.equalsIgnoreCase(resMap.get("out_trade_no").toString(),order.getOrderNo()) ){
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",resMap.get("uri"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",resMap.get("trade_no"));
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
