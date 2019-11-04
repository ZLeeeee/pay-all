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
public class PowerChannel extends AbstractChannel {



    private static final String successStr = "OK";

    public PowerChannel(TChannel channel, TChannelAccount account) {
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
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+JSONObject.toJSONString(map));
        request.setAttribute("amount",request.getParameter("amount"));
        if ("00".equals(request.getParameter("returncode"))&&StringUtils.equals(sign,createSign(map,getAccount().getSignKey())))
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
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        sortedMap.put("pay_orderid",order.getOrderNo());
        sortedMap.put("price",order.getAmount().setScale(2).toString());
        sortedMap.put("pay_memberid",getAccount().getAccount());
        sortedMap.put("class",getChannel().getChannelCode());
        sortedMap.put("addtime",String.valueOf(System.currentTimeMillis()/ 1000L));
        sortedMap.put("pay_callbackurl","http://www.baidu.com");
        sortedMap.put("pay_notifyurl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("md5sign",createSign(sortedMap,getAccount().getSignKey()));
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),RequestUtil.createPostParamStr2(sortedMap),null);
        Map<String, Object> resMap = paySender.send();

        if(StringUtils.equalsIgnoreCase(resMap.get("status").toString(),"success") ){
            JSONObject data = (JSONObject) resMap.get("data");
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",data.getString("pay_url"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",data.getString("transaction"));
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
        //sb.deleteCharAt(sb.length()-1);
        sb.append("key="+key); //KEY是商户秘钥
        String sign = DigestUtils.md5Hex(sb.toString());
        return sign.toUpperCase(); // D3A5D13E7838E1D453F4F2EA526C4766
    }
}
