package com.chaotu.pay.common.channel;


import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.StringResultSender;
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
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class SuDaHtmlChannel extends AbstractChannel {

    //private final BigDecimal bigDecimal100 = new BigDecimal(100);
    private static final String successStr = "OK";
    private static final String pay_bankcode = "924";

    public SuDaHtmlChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String, Object> order) {
        String    pay_memberid=getAccount().getAccount();//商户id
        String    pay_orderid=order.get("orderNo").toString();//20位订单号 时间戳+6位随机字符串组成
        String    pay_applydate=order.get("createTime").toString();//yyyy-MM-dd HH:mm:ss
        String    pay_notifyurl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + pay_orderid;//通知地址
        String    pay_callbackurl="https://www.baidu.com";//回调地址
        String    pay_amount=order.get("amount").toString();
        String    pay_productname="话费充值";

        String stringSignTemp="pay_amount="+pay_amount+"&pay_applydate="+pay_applydate+"&pay_bankcode="+pay_bankcode+"&pay_callbackurl="+pay_callbackurl+"&pay_memberid="+pay_memberid+"&pay_notifyurl="+pay_notifyurl+"&pay_orderid="+pay_orderid+"&key="+getAccount().getSignKey()+"";
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String memberid=request.getParameter("memberid");
        String orderid=request.getParameter("orderid");
        String amount=request.getParameter("amount");
        String datetime=request.getParameter("datetime");
        String returncode=request.getParameter("returncode");
        String transaction_id = request.getParameter("transaction_id");
        String keyValue=getAccount().getSignKey();
        String SignTemp="amount="+amount+"&datetime="+datetime+"&memberid="+memberid+"&orderid="+orderid+"&returncode="+returncode+"&transaction_id="+transaction_id+"&key="+keyValue+"";
        String md5sign=DigestUtils.md5Hex(SignTemp).toUpperCase();//MD5加密
        log.info("回调签名中，签名参数:"+SignTemp+",sign:"+request.getParameter("sign"));
        return md5sign;
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        if(StringUtils.equals(createNotifySign(signParam, request), request.getParameter("sign"))){
            if(StringUtils.equals(request.getParameter("returncode"),"00")){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    Map<String, Object> createSignMap(OrderVo order) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo",order.getOrderNo());
        map.put("createTime",DateUtil.getDateTime(order.getCreateTime()));
        map.put("amount",order.getAmount().toString());
        return map;
    }

    @Override
    public String requestUpper(OrderVo order, String sign) {
        String    pay_memberid=getAccount().getAccount();//商户id
        String    pay_orderid=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        String    pay_applydate=DateUtil.getDateTime(order.getCreateTime());//yyyy-MM-dd HH:mm:ss
        String    pay_notifyurl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + pay_orderid;//通知地址
        String    pay_callbackurl="https://www.baidu.com";//回调地址
        String    pay_amount=order.getAmount().toString();
        String    pay_productname="话费充值";
        Map<Object,Object> map = new HashMap<>();
        map.put("pay_memberid",pay_memberid);
        map.put("pay_orderid",pay_orderid);
        map.put("pay_applydate",pay_applydate);
        map.put("pay_notifyurl",pay_notifyurl);
        map.put("pay_callbackurl",pay_callbackurl);
        map.put("pay_amount",pay_amount);
        map.put("pay_productname",pay_productname);
        map.put("pay_bankcode",pay_bankcode);
        map.put("pay_md5sign",sign);
        String url=getChannel().getRequestUrl();
        StringResultSender paySender = new StringResultSender(url, RequestUtil.createPostParamStr(map), null);
        String resMap = paySender.send();
        return resMap;
    }

    private boolean checkSign(SortedMap<Object, Object> params, String sign) {
        return StringUtils.equals(DigestUtil.createSign(params, getAccount().getSignKey()), sign);
    }
}
