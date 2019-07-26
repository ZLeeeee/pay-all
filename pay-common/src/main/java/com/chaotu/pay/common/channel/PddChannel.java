package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PddChannel extends AbstractChannel {

    private final BigDecimal bigDecimal100 = new BigDecimal(100);

    public PddChannel(TChannel channel, TChannelAccount account) {
        super(channel, account);
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return DigestUtils.md5Hex("cp_order_no="+order.get("cp_order_no")+"&mch_id="+order.get("mch_id")
                +"&notify_url="+order.get("notify_url")+"&order_amount="
                +order.get("order_amount")+order.get("mch_key"));
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {
        String cpOrderNo = request.getParameter("cp_order_no");
        String orderUid = request.getParameter("order_uid");
        String orderAmount = request.getParameter("order_amount");

        String payAmount = request.getParameter("pay_amount");
        String goodsId = request.getParameter("goods_id");
        TChannelAccount account = getAccount();
        String str = "cp_order_no="+cpOrderNo+"&goods_id="+(StringUtils.isNotBlank(goodsId)?goodsId:"")+"&order_amount="+orderAmount+"&order_uid="+orderUid+"&pay_amount="+payAmount+account.getSignKey();
        System.out.println("待签名字符串:"+str);
        return DigestUtils.md5Hex(str);
    }

    @Override
    public boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request) {
        return StringUtils.equals(createNotifySign(signParam,request),request.getParameter("sign"));
    }

    @Override
    Map<String, Object> createSignMap(OrderVo order) {
        Map<String,Object> sortedMap = new HashMap<>();
        sortedMap.put("cp_order_no",order.getOrderNo());
        sortedMap.put("mch_id",getAccount().getAccount());
        sortedMap.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("order_amount",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("mch_key",getAccount().getSignKey());
        return sortedMap;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        Map<Object,Object> sortedMap = new HashMap<>();

        sortedMap.put("mch_id",getAccount().getAccount());
        sortedMap.put("order_amount",order.getAmount().multiply(bigDecimal100).intValue());
        sortedMap.put("cp_order_no",order.getOrderNo());
        sortedMap.put("order_uid","1");
        sortedMap.put("trade_type",2);
        sortedMap.put("ip","1.80.92.154");
        sortedMap.put("notify_url", getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        sortedMap.put("sign",sign);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),sortedMap,null);
        Map<String, Object> resMap = paySender.send();
        if((int)resMap.get("retcode") == 0){
            JSONObject jsonObject = (JSONObject) resMap.get("data");
            Map map = jsonObject.toJavaObject(Map.class);
            SortedMap<String,Object> result = new TreeMap<>();
            result.put("userId",order.getUserId());
            result.put("amount",order.getAmount());
            result.put("qrCode",map.get("qrcode"));
            result.put("success","1");
            result.put("underOrderNo",order.getUnderOrderNo());
            result.put("orderNo",order.getOrderNo());
            result.put("upperOrderNo","1");
            String resultSign = DigestUtil.createSignBySortMap(result,order.getUserKey()).toUpperCase();
            result.put("sign",resultSign);
            return result;
        }
        return resMap;
    }
    private boolean checkSign(SortedMap<Object,Object> params,String sign){
        return StringUtils.equals(DigestUtil.createSign(params,getAccount().getSignKey()),sign);
    }
}
