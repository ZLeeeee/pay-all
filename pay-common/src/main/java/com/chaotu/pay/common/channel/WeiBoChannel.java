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
public class WeiBoChannel extends AbstractChannel {


    private static final String successStr = "success";

    public WeiBoChannel(TChannel channel, TChannelAccount account) {
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
        String fxstatus = request.getParameter("fxstatus");
        String fxsign = request.getParameter("fxsign");
        String fxid = request.getParameter("fxid");
        String fxddh = request.getParameter("fxddh");
        String fxfee = request.getParameter("fxfee");
        log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+fxsign);
        if ("1".equals(fxstatus)&&StringUtils.equals(fxsign,DigestUtils.md5Hex(fxstatus+fxid+fxddh+fxfee+getAccount().getSignKey())))
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
        String    fxid=getAccount().getAccount();//商户id
        String    fxddh=order.getOrderNo();//20位订单号 时间戳+6位随机字符串组成
        String    fxnotifyurl=getChannel().getNotifyUrl() + getChannel().getId() + "/" + fxddh;//通知地址
        String    fxbackurl="https://www.baidu.com";//回调地址
        String    fxfee=order.getAmount().toString();
        String    fxdesc="充值";
        String    fxpay=getChannel().getChannelCode();
        SortedMap<Object,Object> map = new TreeMap<>();
        map.put("fxid",fxid);
        map.put("fxddh",fxddh);
        map.put("fxip","127.0.0.1");
        map.put("fxnotifyurl",fxnotifyurl);
        map.put("fxbackurl",fxbackurl);
        map.put("fxfee",fxfee);
        map.put("fxpay",fxpay);
        map.put("fxsign",DigestUtils.md5Hex(fxid+fxddh+fxfee+fxnotifyurl+getAccount().getSignKey()));
        map.put("fxdesc",fxdesc);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(getChannel().getRequestUrl(),RequestUtil.createPostParamStr(map),null);
        Map<String, Object> resMap = paySender.send();

        if(StringUtils.equalsIgnoreCase(resMap.get("status").toString(),"1") ) {
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
