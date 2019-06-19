package com.chaotu.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.dao.TYzOrderMapper;
import com.chaotu.pay.po.*;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.OrderVo;
import com.chaotu.pay.vo.UserVo;
import com.chaotu.pay.vo.YzOrderVo;
import com.chaotu.pay.yz.PrePayData;
import com.chaotu.pay.yz.PrePayRequest;
import com.chaotu.pay.yz.PrePaymentPreparation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class YzOrderServiceImpl implements YzOrderService {
    @Autowired
    OrderService tOrderService;
    @Autowired
    TYzOrderMapper mapper;
    @Autowired
    @Qualifier("yzAccountChoser")
    Choser<TYzAccount> yzAccountChoser;
    @Autowired
    @Qualifier("yzUserChoser")
    Choser<TYzUser> yzUserChoser;
    @Autowired
    UserService userService;
    @Autowired
    YzAccountService accountService;
    @Autowired
    YzGoodsService goodsService;
    @Autowired
    YzUserAddressService userAddressService;
    @Autowired
    WalletService walletService;
    @Autowired
    YzUserService yzUserService;
    @Value("${yz.preOrderUrl}")
    private String preOrderUrl;
    @Value("${yz.payUrl}")
    private String payUrl;
    @Override
    public Map<Object,Object> pay(YzOrderVo vo) {
        TYzOrder order = new TYzOrder();
        BeanUtils.copyProperties(vo,order);
        log.info("开始创建订单，订单信息["+ JSON.toJSON(order)+"]");
        TYzAccount account = null;
        if(yzAccountChoser.size()>0)
            account = yzAccountChoser.chose();
        else
            throw new IllegalArgumentException("无可用收款账号");
        if(account.getTodayAmount().compareTo(account.getLimitAmount())>0){
            TYzAccount a = new TYzAccount();
            a.setId(account.getId());
            a.setStatus(false);
            accountService.update(a);
            if(yzAccountChoser.size()>0)
                account = yzAccountChoser.chose();
            else
                throw new IllegalArgumentException("无可用收款账号");
        }
        UserVo user = userService.getUserById(vo.getUserId());
        String key = user.getSignKey();
        SortedMap<Object,Object> sortedMap= new TreeMap<>();
        sortedMap.put("userId",order.getUserId());
        sortedMap.put("amount",order.getAmount());
        sortedMap.put("userOrderNo",order.getUserOrderNo());
        sortedMap.put("notifyUrl",order.getNotifyUrl());
        String sign = DigestUtil.createSign(sortedMap, key);
        String qoSign = vo.getSign();
        if(!StringUtils.equals(sign,qoSign)) {
            log.info("签名验证失败:["+JSON.toJSONString(vo)+"]");
            throw new IllegalArgumentException("签名验证失败");
        }
        TYzGoods g = new TYzGoods();
        g.setAmount(order.getAmount());
        g.setYzAccountId(account.getId());
        TYzGoods goods = goodsService.selectOne(g);
        if(goods == null)
            throw new IllegalArgumentException("订单总价与商品价格不符");
        TYzUser yzUser = yzUserChoser.chose();
        TYzUserAddress ua = new TYzUserAddress();
        ua.setUserId(yzUser.getId());
        TYzUserAddress userAddress = userAddressService.selectOne(ua);
        PrePayRequest request = new PrePayRequest(userAddress,account.getKdtSessionId(),userAddress.getIp(),goods.getGoodsId(),goods.getSkuId(),account.getKdtId(),order.getAmount().multiply(new BigDecimal(100)).intValue());
        PddMerchantSender<Map<String,Object>> sender = new PddMerchantSender<>(preOrderUrl,request,yzUser.getCookie());
        Map<String,Object> map = sender.send();
        String code = map.get("code").toString();
        if(StringUtils.equals("101356002",code)){
            log.error("买家已过期:"+JSONObject.toJSONString(yzUser));
            yzUser.setMark(code+":"+map.get("msg"));
            yzUser.setStatus(false);
            yzUserService.update(yzUser);
            throw new IllegalArgumentException("买家已过期");
        }

        JSONObject jsonObject = (JSONObject)map.get("data");
        PrePayData prePayData = jsonObject.toJavaObject(PrePayData.class);
        String orderNo = prePayData.getOrderNo();
        PrePaymentPreparation prePaymentPreparation = prePayData.getPrePaymentPreparation();
        String id = "P"+ IDGeneratorUtils.getFlowNum();
        order.setId(id);
        order.setGoodsId(goods.getId());
        order.setYzAccountId(account.getId());
        order.setYzUserId(yzUser.getId());
        order.setStatus(new Byte("0"));
        order.setSkuNumber(1);
        order.setNotifyTimes(0);
        order.setIsHistory(0);
        order.setOrderNo(orderNo);
        order.setSendTimes(0);
        Date now = new Date();
        order.setCreateTime(now);
        insert(order);
        TOrder o = new TOrder();
        o.setCreateTime(now);
        o.setUserId(order.getUserId());
        o.setAmount(order.getAmount());
        o.setUnderorderno(order.getUserOrderNo());
        o.setOnorderno(order.getOrderNo());
        o.setOrderno(order.getId());
        o.setMerchant(user.getUsername());
        BigDecimal sysAmount = order.getAmount().multiply(user.getRate());
        o.setSysamount(sysAmount);
        BigDecimal userAmount = order.getAmount().subtract(sysAmount);
        o.setUseramount(userAmount);
        o.setStatus((byte) 0);
        tOrderService.add(o);
        Map<String, Object> payParam = createPayParam(prePaymentPreparation);
        String postParam = RequestUtil.createPostParamStr(payParam);
        PddMerchantSender<Map<String,Object>> paySender = new PddMerchantSender<>(payUrl,postParam,account.getCookie());
        Map<String, Object> resultParam = paySender.send();
        JSONObject data = (JSONObject)resultParam.get("data");
        JSONObject payData = (JSONObject)data.get("pay_data");
        JSONObject submitData = (JSONObject)payData.get("submit_data");
        Map<String, Object> aliPayParam = submitData.toJavaObject(Map.class);
        String bizContent = aliPayParam.get("biz_content").toString().replaceAll("&quot;","\"");
        aliPayParam.put("biz_content",bizContent);
        String qrCode = payData.get("submit_url")+"&"+RequestUtil.createPostParamStr(aliPayParam);
        order.setStatus(new Byte("1"));
        update(order);
        SortedMap<Object,Object> resultMap = new TreeMap<>();
        resultMap.put("success","1");
        resultMap.put("userOrderNo",order.getUserOrderNo());
        resultMap.put("orderNo",id);
        resultMap.put("amount",order.getAmount());
        resultMap.put("userId",order.getUserId());
        resultMap.put("qrCode",qrCode);
        String resSing = DigestUtil.createSign(resultMap,key);
        resultMap.put("sign",resSing);
        log.info("开始创建订单success，订单信息["+ JSON.toJSON(order)+"]");
        return resultMap;
    }
    private Map<String,Object> createPayParam(PrePaymentPreparation prePaymentPreparation){
        String cashierSalt = prePaymentPreparation.getCashierSalt();
        String cashierSign = prePaymentPreparation.getCashierSign();
        String partnerId = prePaymentPreparation.getPartnerId();
        String prepayId = prePaymentPreparation.getPrepayId();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("payDataJson[prepay]","true");
        paramMap.put("payDataJson[scene]","VALUE_COMMON");
        paramMap.put("payDataJson[cashier_salt]",cashierSalt);
        paramMap.put("payDataJson[cashier_sign]",cashierSign);
        paramMap.put("payDataJson[partner_id]",partnerId);
        paramMap.put("payDataJson[prepay_id]",prepayId);
        paramMap.put("payDataJson[prepay_success]","true");
        paramMap.put("payTool","ALIPAY_WAP");
        paramMap.put("accept_price","0");
        paramMap.put("new_price","-1");
        paramMap.put("prepay_id",prepayId);
        paramMap.put("cashier_salt",cashierSalt);
        paramMap.put("cashier_sign",cashierSign);
        paramMap.put("partner_id",partnerId);
        return paramMap;
    }

    @Override
    public TYzOrder getById(String id) {
        TYzOrder order = new TYzOrder();
        order.setId(id);
        return selectOne(order);
    }

    @Override
    public void updateByOrderNo(TYzOrder order) {
        mapper.updateByOrderSn(order);
    }

    @Override
    public List<TYzOrder> getAllPaiedOrders() {
        Map<String,Object> map = new HashMap<>();
        map.put("endTime",new Date(System.currentTimeMillis()-Integer.valueOf(MyBeanUtils.CONFIG_PARAM_MAP.get("SEND_MIN"))*60*1000));
        map.put("status",2);
        return getByTimeAndStatus(map);
    }

    @Override
    public List<TYzOrder> getAllSentOrders() {
        Map<String,Object> map = new HashMap<>();
        map.put("endTime",new Date(System.currentTimeMillis()-Integer.valueOf(MyBeanUtils.CONFIG_PARAM_MAP.get("CONFIRM_MIN"))*60*1000));
        map.put("status",3);
        map.put("sendTimes",11);
        return getByTimeAndStatus(map);
    }

    @Override
    public List<TYzOrder> getByTimeAndStatus(Map<String, Object> map) {
        return mapper.getByTimeAndStatus(map);
    }

    @Override
    public List<TYzOrder> getAllByNotifyTimesAndStatus() {
        return mapper.getAllByNotifyTimesAndStatus();
    }

    @Override
    public TYzOrder getByOrderNo(String o) {
        TYzOrder order = new TYzOrder();
        order.setOrderNo(o);
        return selectOne(order);
    }



    @Override
    public void sendNotify(String id) {
        TYzOrder order = getById(id);
        SortedMap<Object,Object> params = new TreeMap<>();
        params.put("success","1");
        params.put("orderNo",order.getId());
        params.put("amount",order.getAmount().toString());
        params.put("userOrderNo",order.getUserOrderNo());
        //params.put("endTime",order.getUpdateTime());
        params.put("userId",order.getUserId());
        UserVo userVo = userService.getUserById(order.getUserId());
        String sign = DigestUtil.createSign(params,userVo.getSignKey());
        params.put("sign",sign);
        PddSender<Map<String,Object>> sender = new PddSender<>(order.getNotifyUrl(),params,"");
        Map<String, Object> result = sender.send();
        if(result != null){
            if(order.getStatus() > 1){

            }else {
                order.setStatus(new Byte("3"));
                TWallet wallet = new TWallet();
                wallet.setUserId(order.getUserId());
                wallet.setType("3");
                BigDecimal sysAmount = order.getAmount().multiply(userVo.getRate());
                BigDecimal userAmount = order.getAmount().subtract(sysAmount);
                walletService.editAmount(wallet, userAmount.toString(), "0");
                accountService.updateAmount(userAmount,order.getYzAccountId());
            }
            OrderVo vo = new OrderVo();
            vo.setUnderorderno(order.getUserOrderNo());
            OrderVo orderVo = tOrderService.selectOneOrderDeails(vo);
            tOrderService.updateStatus(orderVo);
            order.setNotifyTimes(6);
            update(order);


        }
    }

    @Override
    public void updateByIsHistory(TYzOrder order) {
        Example example = new Example(TYzOrder.class);
        example.createCriteria().andEqualTo("isHistory",0);
        mapper.updateByExampleSelective(order,example);
    }

    @Override
    public void insert(TYzOrder tYzOrder) {
        mapper.insert(tYzOrder);
    }

    @Override
    public TYzOrder selectOne(TYzOrder tYzOrder) {
        return mapper.selectOne(tYzOrder);
    }

    @Override
    public List<TYzOrder> findAll() {
        return mapper.selectAll();
    }

    @Override
    public void delete(TYzOrder tYzOrder) {
        mapper.deleteByPrimaryKey(tYzOrder);
    }

    @Override
    public void update(TYzOrder tYzOrder) {
        mapper.updateByPrimaryKeySelective(tYzOrder);
    }
}
