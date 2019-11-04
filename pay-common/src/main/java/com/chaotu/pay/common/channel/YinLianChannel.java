package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.RoundChoser;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.sender.StringResultSender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.dao.TYinlianAccountMapper;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.po.TYinlianAccount;
import com.chaotu.pay.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class YinLianChannel extends AbstractChannel {

    private static final String successStr = "OK";
    private final RoundChoser<TYinlianAccount> roundChoser;
    private final TYinlianAccountMapper accountMapper;
    private final ThreadLocal<String> accountThreadLocal = new ThreadLocal<>();
    private final BigDecimal B_100 = new BigDecimal(100);
    public YinLianChannel(TChannel channel, TYinlianAccountMapper yinlianAccountMapper) {
        super(channel, null);
        TYinlianAccount account = new TYinlianAccount();
        account.setStatus("1");
        roundChoser = new RoundChoser<TYinlianAccount>(yinlianAccountMapper.select(account));
        accountMapper = yinlianAccountMapper;
    }

    @Override
    public String createSign(Map<String,Object> order) {
        return null;
    }

    @Override
    public String createNotifySign(Map<String, Object> signParam, HttpServletRequest request) {

        return "";
    }
    private TYinlianAccount chooseAccount(){
        if(roundChoser.size()<=0)
            return null;
        TYinlianAccount account = roundChoser.chose();
        account = accountMapper.selectByPrimaryKey(account.getId());
        if(account.getLimitAmount().compareTo(account.getTodayAmount())<=0){
            TYinlianAccount yinlianAccount = new TYinlianAccount();
            yinlianAccount.setStatus("0");
            yinlianAccount.setId(account.getId());
            accountMapper.updateByPrimaryKeySelective(yinlianAccount);
            update();
            return chooseAccount();
        }
        return roundChoser.chose();
    }
    public void update(){
        TYinlianAccount yinlianAccount = new TYinlianAccount();
        yinlianAccount.setStatus("0");
        roundChoser.update(accountMapper.select(yinlianAccount));
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
        TYinlianAccount account = chooseAccount();
        if (account == null) {
            return null;
        }
        accountThreadLocal.set(account.getAccount());
        SortedMap<String,Object> map = new TreeMap<>();
        map.put("service", "unified.trade.native");
        map.put("sign_type", "MD5");
        map.put("mch_id", account.getAccount());
        map.put("nonce_str", order.getOrderNo());
        map.put("mch_create_ip", "127.0.0.1");
        map.put("total_fee", order.getAmount().multiply(B_100).intValue());
        map.put("body", "话费充值");
        map.put("out_trade_no", order.getOrderNo());
        map.put("notify_url",getChannel().getNotifyUrl()+order.getChannelId()+"/"+order.getOrderNo());
        map.put("sign", createSign(map,account.getSignKey()));
        Sender<String> sender = new StringResultSender(getChannel().getRequestUrl(),generateXml(map),null);
        String resp = sender.send();
        SortedMap<String,Object> result = new TreeMap<>();
        Map<String, String> resMap = RequestUtil.xmlToMap(resp);
        if(StringUtils.equals("0",resMap.get("status"))&&StringUtils.equals("0",resMap.get("result_code"))) {
            result.put("userId", order.getUserId());
            result.put("amount", order.getAmount());
            result.put("qrCode", CommonConstant.REDIRECT_IMG_URL+"amount="+order.getAmount()+"&orderNo="+order.getOrderNo()+"&imgUrl="+resMap.get("code_img_url"));
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
    private String generateXml(Map<String,Object> map){
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");

        Iterator it=map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String value = map.get(key).toString();

            root.addElement(key).addText(value);
        }

        String reqBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + doc.getRootElement().asXML();

        log.info("==============================待编码字符串==============================\r\n" + reqBody);

        try {
            reqBody = URLEncoder.encode(reqBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("==============================编码后字符串==============================\r\n" + reqBody);
        return reqBody;
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
        sb.append("key="+key); //KEY是商户秘钥
        String sign = DigestUtils.md5Hex(sb.toString());
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
    }
    @Override
    public String getAccountId(){
        return accountThreadLocal.get();
    }
}
