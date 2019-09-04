package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSON;
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
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class YouKuaiChannel extends AbstractChannel {

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "OK";

    public YouKuaiChannel(TChannel channel, TChannelAccount account) {
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
            SortedMap<Object,Object> map = new TreeMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String s = parameterNames.nextElement();
                map.put(s,request.getParameter(s));
            }
            String sign = (String) map.remove("sign");
            map.remove("attach");
            log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+JSONObject.toJSONString(map));
            if ("00".equals(request.getParameter("returncode"))&&StringUtils.equals(sign,DigestUtil.createSign(map,getAccount().getSignKey())))
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
        SortedMap<Object,Object> content = new TreeMap<>();
        content.put("pay_orderid",order.getOrderNo());
        content.put("pay_amount",order.getAmount().setScale(2).toString());
        content.put("pay_memberid",getAccount().getAccount());
        content.put("pay_applydate", DateUtil.getDateTime(order.getCreateTime()));
        content.put("pay_bankcode", "936");
        content.put("pay_notifyurl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        content.put("pay_callbackurl","https://www.baidu.com");
        //content.put("pay_amount",order.getAmount().toString());
        String sign1 = DigestUtil.createSign(content,getAccount().getSignKey());
        content.put("pay_md5sign",sign1);
        content.put("pay_productname","花费");
        content.put("url",getChannel().getRequestUrl());
        String url="http://47.75.146.15:8080/order/redirect?"+RequestUtil.createPostParamStr(content);

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
    private  String createSign(SortedMap<String, Object> parameters, String key) {


        return "sign"; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
