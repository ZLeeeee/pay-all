package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class ChaoRenChannel extends AbstractChannel {


    private static final String successStr = "success";
    private static final BigDecimal oneH = new BigDecimal(100);

    public ChaoRenChannel(TChannel channel, TChannelAccount account) {
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
        request.setAttribute("amount",request.getParameter("amount_true"));
        String sign = (String) map.remove("sign");
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+JSONObject.toJSONString(map));
        if ("CODE_SUCCESS".equals(map.get("callbacks"))&&StringUtils.equals(sign,createSign(map,getAccount().getSignKey())))
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
        String    appid=getAccount().getAccount();//商户id
        String    pay_number=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        //String    pay_applydate= DateUtil.getDateTime(order.getCreateTime());//yyyy-MM-dd HH:mm:ss
        String    callback_url=getChannel().getNotifyUrl() + "/" + pay_number;//通知地址
        String    success_url="https://www.baidu.com";//回调地址
        String    amount=order.getAmount().setScale(2).toString();
        //String    subject="话费充值";
        SortedMap<String,Object> map = new TreeMap<>();
        map.put("appid",appid);
        //map.put("pay_applydate",pay_applydate);
        map.put("callback_url",callback_url);
        map.put("error_url",success_url);
        map.put("success_url",success_url);
        map.put("amount",amount);
        map.put("version","v1.1");
        map.put("pay_type",getChannel().getChannelCode());
        map.put("out_trade_no",pay_number);
        map.put("out_uid","6666");
        map.put("sign",createSign(map,getAccount().getSignKey()));
        map.put("url",getChannel().getRequestUrl());
        String postParamStr = RequestUtil.createPostParamStr2(map);
        //PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        //Map<String, Object> resMap = paySender.send();
        String url= CommonConstant.REDIRECT_URL+postParamStr;
        //if(StringUtils.equals(resMap.get("code").toString(),"200") ) {
            SortedMap<String, Object> result = new TreeMap<>();
            //JSONObject data = (JSONObject) resMap.get("data");
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", url);
            result.put("success", "1");
            result.put("underOrderNo", order.getUnderOrderNo());
            result.put("orderNo", order.getOrderNo());
            result.put("upperOrderNo", "1");
            String resultSign = DigestUtil.createSignBySortMap(result, order.getUserKey()).toUpperCase();
            result.put("sign", resultSign);
            return result;
       // }
        //log.info("下单失败！失败信息:["+ JSONObject.toJSONString(resMap)+"]");
        //return null;

    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
    private  String createSign(SortedMap<String, Object> parameters, String key) {

        return DigestUtil.createSignBySortMap(parameters,key).toUpperCase(); // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
