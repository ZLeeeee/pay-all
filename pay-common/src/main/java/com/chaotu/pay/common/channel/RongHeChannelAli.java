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
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
@Slf4j
public class RongHeChannelAli extends AbstractChannel {

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    private static final String successStr = "{\"status\":200,\"statusDes\":\"SUCCESS\"}";

    public RongHeChannelAli(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return DigestUtils.md5Hex("cpOrderId="+order.get("cpOrderId")
                +"|cpId="+order.get("cpId")
                +"|price="+order.get("price")
                +"|key="+getAccount().getSignKey());
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String cpOrderId = request.getParameter("cpOrderId");
        String cpId = request.getParameter("cpId");
        String price = request.getParameter("price");
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String statusDes = request.getParameter("statusDes");
        String str = "cpOrderId="+cpOrderId
                +"|cpId="+cpId
                +"|price=" +price
                +"|orderId="+orderId
                +"|status="+status
                +"|statusDes="+statusDes
                +"|key="+getAccount().getSignKey();
        System.out.println(str);
        return DigestUtils.md5Hex(str);
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return StringUtils.equals(createNotifySign(signParam,request),request.getParameter("sign"));
    }

    @Override
    public String getSuccessNotifyStr() {
        return successStr;
    }

    @Override
    SortedMap<String, Object> createSignMap(OrderVo order) {
        SortedMap<String,Object> sortedMap = new TreeMap<>();
        sortedMap.put("cpOrderId",order.getOrderNo());
        sortedMap.put("cpId",getAccount().getAccount());
        sortedMap.put("price",order.getAmount().multiply(bigDecimal100).intValue());
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();
        sortedMap.put("cpOrderId",order.getOrderNo());
        sortedMap.put("cpId",getAccount().getAccount());
        sortedMap.put("price",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("notifyUrl","http://47.75.146.15:8080/order/ronghe/notify");
        sortedMap.put("payType", 1);
        sortedMap.put("sign",sign);
        String postParamStr = RequestUtil.createPostParamStr(sortedMap);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),postParamStr,null);
        Map<String, Object> resMap = paySender.send();
        if(StringUtils.equals(resMap.get("status").toString(),"200") ){
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",resMap.get("payUrl"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo",resMap.get("orderId"));
            String resultSign = DigestUtil.createSignBySortMap(result,order.getUserKey()).toUpperCase();
            result.put("sign",resultSign);
            return result;
        }
        log.info("下单失败！失败信息:["+ JSONObject.toJSONString(resMap)+"]");
        return null;
    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
