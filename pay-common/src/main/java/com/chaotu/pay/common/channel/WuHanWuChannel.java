package com.chaotu.pay.common.channel;

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
import java.util.*;

@Slf4j
public class WuHanWuChannel extends AbstractChannel {


    private static final String successStr = "success";
    private static final BigDecimal oneH = new BigDecimal(100);

    public WuHanWuChannel(TChannel channel, TChannelAccount account) {
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
        SortedMap<String,Object> map = new TreeMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()){
            String s = parameterNames.nextElement();
            map.put(s,request.getParameter(s));
        }

        String sign = (String) map.remove("sign");
        //map.remove("attach");
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+JSONObject.toJSONString(map));
        if ("200".equals(map.get("status"))&&StringUtils.equals(sign,createSign(map,getAccount().getSignKey())))
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
        String    userid=getAccount().getAccount();//商户id
        String    pay_number=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        //String    pay_applydate= DateUtil.getDateTime(order.getCreateTime());//yyyy-MM-dd HH:mm:ss
        String    notifyUrl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + pay_number;//通知地址
        //String    pageNotifyUrl="https://www.baidu.com";//回调地址
        String    amount=order.getAmount().setScale(2).toString();
        //String    subject="话费充值";
        SortedMap<String,Object> map = new TreeMap<>();
        map.put("tenantNo",userid);
        //map.put("pay_applydate",pay_applydate);
        map.put("notifyUrl",notifyUrl);
        //map.put("returnUrl",pageNotifyUrl);
        map.put("amount",amount);
        //map.put("subject",subject);
        map.put("payType",getChannel().getChannelCode());
        map.put("tenantOrderNo",pay_number);
        map.put("sign",createSign(map,getAccount().getSignKey()));
        String postParamStr = RequestUtil.createPostParamStr2(map);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        Map<String, Object> resMap = paySender.send();
        if(StringUtils.equals(resMap.get("status").toString(),"200") ) {
            SortedMap<String, Object> result = new TreeMap<>();
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", resMap.get("url"));
            result.put("success", "1");
            result.put("underOrderNo", order.getUnderOrderNo());
            result.put("orderNo", order.getOrderNo());
            result.put("upperOrderNo", "1");
            String resultSign = DigestUtil.createSignBySortMap(result, order.getUserKey()).toUpperCase();
            result.put("sign", resultSign);
            return result;
        }
        log.info("下单失败！失败信息:["+ JSONObject.toJSONString(resMap)+"]");
        return null;

    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
    private  String createSign(SortedMap<String, Object> parameters, String key) {

        return DigestUtil.createSignBySortMap(parameters,key).toUpperCase(); // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
