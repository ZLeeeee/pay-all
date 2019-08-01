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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
@Slf4j
public class MachiPayChannel extends AbstractChannel {

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "success";

    public MachiPayChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return DigestUtil.createSignBySortMapForMachi((SortedMap<String, Object>) order,getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        String secpVer = request.getParameter("secpVer");
        //String mac = request.getParameter("mac");
        String currencyCode = request.getParameter("currencyCode");
        String extInfo = request.getParameter("extInfo");
        String txnStatusDesc = request.getParameter("txnStatusDesc");
        String orderTime = request.getParameter("orderTime");
        String txnAmt = request.getParameter("txnAmt");
        String txnStatus = request.getParameter("txnStatus");
        String macKeyId = request.getParameter("macKeyId");
        String timeStamp = request.getParameter("timeStamp");
        String secpMode = request.getParameter("secpMode");
        String respMsg = request.getParameter("respMsg");
        String merId = request.getParameter("merId");
        String orderDate = request.getParameter("orderDate");
        String respCode = request.getParameter("respCode");
        String txnId = request.getParameter("txnId");
        SortedMap<String , Object> map = new TreeMap<>();
        map.put("orderId",orderId);
        map.put("secpVer",secpVer);
        //map.put("mac",mac);
        map.put("currencyCode",currencyCode);
        map.put("extInfo",extInfo);
        map.put("txnStatusDesc",txnStatusDesc);
        map.put("orderTime",orderTime);
        map.put("txnAmt",txnAmt);
        map.put("txnStatus",txnStatus);
        map.put("macKeyId",macKeyId);
        map.put("timeStamp",timeStamp);
        map.put("secpMode",secpMode);
        map.put("respMsg",respMsg);
        map.put("merId",merId);
        map.put("orderDate",orderDate);
        map.put("respCode",respCode);
        map.put("txnId",txnId);
        System.out.println(JSONObject.toJSONString(map));
        return DigestUtil.createSignBySortMapForMachi(map,getAccount().getSignKey());
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return StringUtils.equals(createNotifySign(signParam,request),request.getParameter("mac"));
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    SortedMap<String, Object> createSignMap(OrderVo order) {
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        sortedMap.put("clientIp","180.76.99.215");
        sortedMap.put("currencyCode","156");
        sortedMap.put("macKeyId",getAccount().getAccount());
        sortedMap.put("merId",getAccount().getAccount());
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("orderDate", DateUtil.format(DateUtil.MACHI_PAY_DATE_FORMAT,order.getCreateTime()));
        sortedMap.put("orderId",order.getOrderNo());
        sortedMap.put("orderTime",DateUtil.format(DateUtil.MACHI_PAY_TIME_FORMAT,order.getCreateTime()));
        sortedMap.put("pageReturnUrl","http://www.baidu.com");
        sortedMap.put("productTitle","sp");
        sortedMap.put("sceneBizType","WAP");
        sortedMap.put("secpMode","perm");
        sortedMap.put("secpVer","icp3-1.1");
        sortedMap.put("timeStamp",DateUtil.format(DateUtil.MACHI_PAY_MINUTE_FORMAT,order.getCreateTime()));
        sortedMap.put("txnAmt",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("txnSubType","41");
        sortedMap.put("txnType","01");
        sortedMap.put("wapName","test");
        sortedMap.put("wapUrl","http://www.baidu.com");
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();
        sortedMap.put("clientIp","180.76.99.215");
        sortedMap.put("currencyCode","156");
        sortedMap.put("macKeyId",getAccount().getAccount());
        sortedMap.put("merId",getAccount().getAccount());
        sortedMap.put("notifyUrl",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("orderDate", DateUtil.format(DateUtil.MACHI_PAY_DATE_FORMAT,order.getCreateTime()));
        sortedMap.put("orderId",order.getOrderNo());
        sortedMap.put("orderTime",DateUtil.format(DateUtil.MACHI_PAY_TIME_FORMAT,order.getCreateTime()));
        sortedMap.put("pageReturnUrl","http://www.baidu.com");
        sortedMap.put("productTitle","sp");
        sortedMap.put("sceneBizType","WAP");
        sortedMap.put("secpMode","perm");
        sortedMap.put("secpVer","icp3-1.1");
        sortedMap.put("timeStamp",DateUtil.format(DateUtil.MACHI_PAY_MINUTE_FORMAT,order.getCreateTime()));
        sortedMap.put("txnAmt",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("txnSubType","41");
        sortedMap.put("txnType","01");
        sortedMap.put("wapName","test");
        sortedMap.put("wapUrl","http://www.baidu.com");
        sortedMap.put("mac",sign);
        String postParamStr = RequestUtil.createPostParamStr(sortedMap);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        Map<String, Object> resMap = paySender.send();
        if(StringUtils.equals((String)resMap.get("respMsg"),"成功") ){
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",resMap.get("codePageUrl"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",resMap.get("txnId"));
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
}
