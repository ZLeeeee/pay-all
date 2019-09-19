package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
public class NewRongYiFuChannel extends AbstractChannel {


    private static final String successStr = "SUCCESS";

    public NewRongYiFuChannel(TChannel channel, TChannelAccount account) {
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
        String    pay_memberid=getAccount().getAccount();//商户id
        String    pay_orderid=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        String    pay_applydate= DateUtil.getDateTime(order.getCreateTime());//yyyy-MM-dd HH:mm:ss
        String    pay_notifyurl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + pay_orderid;//通知地址
        String    pay_callbackurl="https://www.baidu.com";//回调地址
        String    pay_amount=order.getAmount().toString();
        String    pay_productname="话费充值";
        SortedMap<Object,Object> map = new TreeMap<>();
        map.put("pay_memberid",pay_memberid);
        map.put("pay_orderid",pay_orderid);
        map.put("pay_applydate",pay_applydate);
        map.put("pay_notifyurl",pay_notifyurl);
        map.put("pay_callbackurl",pay_callbackurl);
        map.put("pay_amount",pay_amount);
        map.put("pay_bankcode","915");
        map.put("pay_md5sign",DigestUtil.createSign(map,getAccount().getSignKey()));
        map.put("pay_productname",pay_productname);
        map.put("url",getChannel().getRequestUrl());
        String url= CommonConstant.REDIRECT_URL +RequestUtil.createPostParamStr(map);
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
