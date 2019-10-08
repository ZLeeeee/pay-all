package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.channel.po.*;
import com.chaotu.pay.common.channel.util.AESUtil;
import com.chaotu.pay.common.channel.util.HttpReqUtil;
import com.chaotu.pay.common.channel.util.Md5Util;
import com.chaotu.pay.common.channel.util.RecordNoUtils;
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
public class RenRenChannel extends AbstractChannel {


    private static final String successStr = "SUCCESS";
    

    public RenRenChannel(TChannel channel, TChannelAccount account) {
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
        String body = RequestUtil.getBody(request);
        CallBackReqVO callBackReqVO = JSONObject.parseObject(body, CallBackReqVO.class);
        log.info("rrp callback req param:" + body);

        if (!callBackReqVO.merchantAccount.isEmpty() && !callBackReqVO.sign.isEmpty() && !callBackReqVO.result.isEmpty()) {

            String reqResultData = "";
            try {
                //解密result
                reqResultData = AESUtil.decrypt(getAccount().getPublicKey(), callBackReqVO.result);
            } catch (Exception e) {
                System.out.println(e);
            }

            /*JSONObject reqJson = JSONObject.parseObject(reqResultData);

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("MerchantOrderNo",reqJson.getString("MerchantOrderNo"));
            paramMap.put("OrderState",reqJson.getString("OrderState"));
            paramMap.put("OrderPrice",reqJson.getString("OrderPrice"));
            paramMap.put("OrderRealPrice",reqJson.getString("OrderRealPrice"));
            paramMap.put("Code",reqJson.getInteger("Code"));
            paramMap.put("Time",reqJson.getString("Time"));
            paramMap.put("Message",reqJson.getString("Message"));
            paramMap.put("OrderType",reqJson.getInteger("OrderType"));
            paramMap.put("MerchantFee",reqJson.getString("MerchantFee"));
            paramMap.put("CompleteTime",reqJson.getString("CompleteTime"));
            paramMap.put("DealTime",reqJson.getString("DealTime"));

            String encryptData=getReqEncryptData(paramMap);*/
            log.info("通道:"+getChannel().getChannelName()+"接收回调验证："+reqResultData);
            //获取sign
            String sign = Md5Util.md5Sign("MerchantAccount="+getAccount().getAccount()+"&Result="+callBackReqVO.result+"&Key="+getAccount().getSignKey());
            if (sign.equals(callBackReqVO.sign)){
                System.out.println("callBack success.");
                //处理业务逻辑
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
    SortedMap<String, Object> createSignMap(OrderVo order) {


        return null;
    }

    @Override
    public Map<String, Object> requestUpper(OrderVo order, String sign) {
        PublicReqVO req = new PublicReqVO();

        req.action = "deposit";
        req.merchantAccount = getAccount().getAccount();

        //--------------

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("MerchantOrderNo", order.getOrderNo());
        paramMap.put("Channel", 2);
        paramMap.put("OrderPrice", order.getAmount().toString());
        paramMap.put("CallBackUrl", getChannel().getNotifyUrl() + getChannel().getId() + "/" + order.getOrderNo());

        req.data = getReqEncryptData(paramMap);
        req.sign = getReqSign(req);

        JSONObject resJson = sendPost(req);
        JSONObject res = JSONObject.parseObject(getRes(resJson));
        SortedMap<String,Object> result = new TreeMap<>();
        result.put("userId",order.getUserId());
        result.put("amount",order.getAmount());
        result.put("qrCode",res.getString("URL"));
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



    /**
     * 接收回调
     * 商户收到回调后, 如果没有问题（回调带来的参数值经过md5加密后计算出的sign与回调带来的sign相同，则认为没有问题，sign计算见下面备注）, 需要回传SUCCESS
     *
     * @param
     * @return
     */
    public String callBack(CallBackReqVO callBackReqVO) {

        System.out.println("rrp callback req param:" + callBackReqVO);

        if (!callBackReqVO.merchantAccount.isEmpty() && !callBackReqVO.sign.isEmpty() && !callBackReqVO.result.isEmpty()) {

            String reqResultData = "";
            try {
                //解密result
                reqResultData = AESUtil.decrypt(getAccount().getPublicKey(), callBackReqVO.result);
            } catch (Exception e) {
                System.out.println(e);
            }

            JSONObject reqJson = JSONObject.parseObject(reqResultData);

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("MerchantOrderNo",reqJson.getString("MerchantOrderNo"));
            paramMap.put("OrderState",reqJson.getString("OrderState"));
            paramMap.put("OrderPrice",reqJson.getString("OrderPrice"));
            paramMap.put("OrderRealPrice",reqJson.getString("OrderRealPrice"));
            paramMap.put("Code",reqJson.getInteger("Code"));
            paramMap.put("Time",reqJson.getString("Time"));
            paramMap.put("Message",reqJson.getString("Message"));
            paramMap.put("OrderType",reqJson.getInteger("OrderType"));
            paramMap.put("MerchantFee",reqJson.getString("MerchantFee"));
            paramMap.put("CompleteTime",reqJson.getString("CompleteTime"));
            paramMap.put("DealTime",reqJson.getString("DealTime"));

            String encryptData=getReqEncryptData(paramMap);

            //获取sign
            String sign = Md5Util.md5Sign("MerchantAccount="+getAccount().getAccount()+"&Result="+encryptData+"&Key="+getAccount().getSignKey());
            if (sign.equals(callBackReqVO.sign)){
                System.out.println("callBack success.");
                //处理业务逻辑
                return "SUCCESS";
            }


            System.out.println("callBack decrypt req reqResultData:" + reqResultData);
        }

        return "";
    }

    /**
     * 得到加密业务参数的data
     *
     * @param paramMap
     * @return
     */
    public String getReqEncryptData(Map<String, Object> paramMap) {
        System.out.println("getReqEncryptData.paramMap to String:" + JSON.toJSONString(paramMap));
        String encryptData = "";
        try {
            encryptData = AESUtil.encodeHexStr(AESUtil.encrypt(getAccount().getPublicKey(), JSON.toJSONString(paramMap)), false);
            System.out.println(("getReqEncryptData.encryptData:" + encryptData));
        } catch (Exception e) {
            System.out.println(e);
        }
        return encryptData;
    }

    /**
     * 得到Sign
     *
     * @param req
     * @return
     */
    public String getReqSign(PublicReqVO req) {
        //使用seckey对除sign以外的公共参数进行md5加密生成sign值
        String toSignStr = "Action=" + req.action + "&Data=" + req.data + "&MerchantAccount=" + req.merchantAccount + "&Key=" + getAccount().getSignKey();
        String reqSign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        System.out.println("getReqSign.reqSign:" + reqSign);
        return reqSign;
    }

    /**
     * 发送请求并接收响应
     */
    public JSONObject sendPost(PublicReqVO req) {
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("Action",req.action);
        reqMap.put("Data",req.data);
        reqMap.put("MerchantAccount",req.merchantAccount);
        reqMap.put("Sign",req.sign);
        //String postParam = "Action=" + req.action + "&Data=" + req.data + "&MerchantAccount=" + req.merchantAccount + "&Sign=" + req.sign;
        //发送请求到rrp
        PddMerchantSender<JSONObject> sender = new PddMerchantSender<>(getChannel().getRequestUrl(),reqMap,null);
        return sender.send();
    }

    /**
     * 接收响应并处理
     */
    public String getRes(JSONObject resJson) {
        //处理rrp的成功响应
        if (resJson.getInteger("Code") == 0) {

            PublicResVO publicResVO = new PublicResVO();

            publicResVO.code = resJson.getInteger("Code");
            publicResVO.sign = resJson.getString("Sign");
            publicResVO.result = resJson.getString("Result");
            publicResVO.errMsg = resJson.getString("ErrMsg");

            //对比签名正确
            if (verifySign(publicResVO)) {
                //解密响应
                try {
                    String resData = AESUtil.decrypt(getAccount().getPublicKey(), publicResVO.result);
                    System.out.println("res.data :" + resData);
                    return resData;
                } catch (Exception e) {
                    System.out.println(e);
                    return resJson.toString();
                }
            }

        } else {
            System.out.println("====================rrp response failed========================" + "Code:" + resJson.getString("Code") + ";ErrMsg:" + resJson.getString("ErrMsg"));
            return resJson.toString();
        }
        return resJson.toString();
    }
    public boolean verifySign(PublicResVO publicResVO) {

        //使用seckey对除sign以外的公共参数进行md5加密生成sign值
        String toSignStr = "Code=" + publicResVO.code + "&Result=" + publicResVO.result + "&Key=" + getAccount().getSignKey();
        System.out.println(("calculateSign Str:" + toSignStr));

        String sign = Md5Util.md5Sign(toSignStr).toUpperCase();
        System.out.println("calculateSign.sign:" + sign);
        System.out.println("rrp res sign:"+publicResVO.sign);
        return sign.toUpperCase().equals(publicResVO.sign.toUpperCase());
    }

}
