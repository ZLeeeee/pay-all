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
public class USDTChannel extends AbstractChannel {

    private static final String successStr = "success";

    public USDTChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return order.get("sign").toString();
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String outOrderId = request.getParameter("outOrderId");
        String customerAmountCny = request.getParameter("customerAmountCny");
        String customerAmount = request.getParameter("customerAmount");
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String str = customerAmount==null?"":customerAmount + customerAmountCny + outOrderId + orderId + "MD5" + status + getAccount().getSignKey();
        log.info(str);
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
        String outOrderId = order.getOrderNo();
        String pickupUrl = "https://www.baidu.com";
        String receiveUrl = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String customerAmountCny = order.getAmount().toString();
        String signType = "MD5";

        String md5Key = getAccount().getSignKey();
        String sign = DigestUtils.md5Hex(outOrderId + pickupUrl + receiveUrl + customerAmountCny + signType + md5Key);
        sortedMap.put("sign",sign);
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        String APPKey = getAccount().getAccount();
        String outOrderId = order.getOrderNo();
        String pickupUrl = "https://www.baidu.com";
        String receiveUrl = getChannel().getNotifyUrl() + order.getChannelId() + "/" + order.getOrderNo();
        String customerAmountCny = order.getAmount().toString();
        String signType = "MD5";
        String url = getChannel().getRequestUrl()
                +"?outOrderId="+outOrderId+"&customerAmount=&customerAmountCny="+customerAmountCny+"&APPKey="+APPKey
                +"&pickupUrl="+pickupUrl+"&receiveUrl="+receiveUrl+"&signType="+signType+"&sign="+sign;
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
}
