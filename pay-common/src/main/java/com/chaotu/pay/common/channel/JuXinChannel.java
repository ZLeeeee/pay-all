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
import java.util.*;

@Slf4j
public class JuXinChannel extends AbstractChannel {


    private static final String successStr = "OK";

    public JuXinChannel(TChannel channel, TChannelAccount account) {
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
        String amount = request.getParameter("amount");
        String orderid = request.getParameter("orderid");
        String status = request.getParameter("status");
        String shkey = request.getParameter("shkey");
        String sign = DigestUtils.md5Hex(DigestUtils.md5Hex(getAccount().getSignKey())+DigestUtils.md5Hex(getAccount().getAccount()+amount+getChannel().getChannelCode()+orderid));
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+orderid+" sign:"+shkey+" status:"+status+" orderId:"+orderid+" mySign:"+sign);
        if(StringUtils.equals(status,"1")) {
            //String sign = DigestUtils.md5Hex(DigestUtils.md5Hex(getAccount().getSignKey()) + DigestUtils.md5Hex(getAccount().getAccount() + amount + getChannel().getChannelCode() + orderid));
            if(StringUtils.equals(shkey,sign))
                return true;
        }
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
        String    shid=getAccount().getAccount();//商户id
        String    orderid=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        //String    pay_applydate= DateUtil.getDateTime(order.getCreateTime());//yyyy-MM-dd HH:mm:ss
        String    notifyurl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + orderid;//通知地址
        String    url="https://www.baidu.com";//回调地址
        String    amount=order.getAmount().setScale(2).toString();
        SortedMap<String,Object> map = new TreeMap<>();
        map.put("shid",shid);
        map.put("notifyurl",notifyurl);
        map.put("url",url);
        map.put("amount",amount);
        map.put("sign",DigestUtils.md5Hex(DigestUtils.md5Hex(getAccount().getSignKey())+DigestUtils.md5Hex(getAccount().getAccount()+amount+getChannel().getChannelCode()+orderid)));
        map.put("pay",getChannel().getChannelCode());
        map.put("orderid",orderid);
        String postParamStr = RequestUtil.createPostParamStr2(map);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        Map<String, Object> resMap = paySender.send();
        if(StringUtils.equals(resMap.get("code").toString(),"200") ) {
            SortedMap<String, Object> result = new TreeMap<>();
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", resMap.get("payurl"));
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
