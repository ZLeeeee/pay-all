package com.chaotu.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TPddOrderMapper;
import com.chaotu.pay.po.*;
import com.chaotu.pay.qo.PddOrderQo;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Slf4j

public class PddOrderServiceImpl implements PddOrderService {

    @Value("${pdd.preOrderUrl}")
    private String preOrderUrl;
    @Value("${pdd.aliPayUrl}")
    private String aliPayUrl;
    @Value("${pdd.returnUrl}")
    private String returnUrl;

    @Autowired
    OrderService tOrderService;
    @Autowired
    UserService userService;
    @Autowired
    TPddOrderMapper mapper;
    @Autowired
    PddGoodsService goodsService;
    @Autowired
    PddAccountService accountService;
    @Autowired
    @Qualifier("antiContentChoser")
    Choser<String> antiContentChoser;
    @Autowired
    @Qualifier("createOrderTokenChoser")
    Choser<String> createOrderTokenChoser;
    @Autowired
    @Qualifier("pddUserChoser")
    Choser<TPddUser> pddUserChoser;
    @Autowired
    @Qualifier("pddAccountChoser")
    Choser<TPddAccount> pddAccountChoser;
    @Autowired
    private WalletService walletService;
    @Override
    public void add(TPddOrder order) {
        mapper.insert(order);
    }

    @Override
    public void edit(TPddOrder order) {
        mapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public TPddOrder getById(String id) {
        TPddOrder order = new TPddOrder();
        order.setId(id);
        return get(order);
    }

    @Override
    public void updateByOrderSn(TPddOrder order) {
        mapper.updateByOrderSn(order);
    }

    @Override
    public List<TPddOrder> getAllPaiedOrders() {
        Map<String,Object> map = new HashMap<>();
        map.put("endTime",new Date(System.currentTimeMillis()-60*1000*2));
        map.put("status",2);
        return mapper.getByTimeAndStatus(map);
    }

    @Override
    public List<TPddOrder> getAllSentOrders() {
        Map<String,Object> map = new HashMap<>();
        map.put("endTime",new Date(System.currentTimeMillis()-1000*30*60));
        map.put("status",3);
        map.put("sendTimes",11);
        return mapper.getByTimeAndStatus(map);
    }

    @Override
    public TPddOrder get(TPddOrder order) {
        return mapper.selectOne(order);
    }

    @Override
    public TPddOrder getByOrderSn(String orderSn) {
        return mapper.getByOrderSn(orderSn);
    }


    @Override
    public Map<Object,Object> pay(PddOrderQo orderQo) {

        TPddOrder order = new TPddOrder();
        BeanUtils.copyProperties(orderQo,order);
        log.info("开始创建订单，订单信息["+ JSON.toJSON(order)+"]");
        TPddAccount account = null;
        if(pddAccountChoser.size()>0)
            account = pddAccountChoser.chose();
        else
            throw new IllegalArgumentException("无可用收款账号");
        if(account.getTodayAmount().compareTo(account.getLimitAmount())>0){
            TPddAccount a = new TPddAccount();
            a.setId(account.getId());
            a.setStatus(false);
            accountService.update(a);
            if(pddAccountChoser.size()>0)
                account = pddAccountChoser.chose();
            else
                throw new IllegalArgumentException("无可用收款账号");
        }
        UserVo user = userService.getUserById(order.getUserId());
        String key = user.getSignKey();
        SortedMap<Object,Object> sortedMap= new TreeMap<>();
        sortedMap.put("userId",order.getUserId());
        sortedMap.put("amount",order.getAmount());
        sortedMap.put("userOrderSn",order.getUserOrderSn());
        sortedMap.put("notifyUrl",order.getNotifyUrl());
        String sign = DigestUtil.createSign(sortedMap, key);
        if(!StringUtils.equals(sign,sign)) {
            throw new IllegalArgumentException("签名验证失败");
        }

        TPddGoods g = new TPddGoods();
        g.setAmount(order.getAmount());
        g.setPddAccountId(account.getId());
        TPddGoods good = goodsService.get(g);
        if(good == null)
            throw new IllegalArgumentException("订单总价与商品价格与数量不符");
        PddOrderVo vo = new PddOrderVo();
        TPddUser pddUser = pddUserChoser.chose();
        String antiContent = antiContentChoser.chose();
        String createOrderToken = createOrderTokenChoser.chose();
        String accessToken = pddUser.getAccesstoken();
        vo.setAddress_id(pddUser.getAddressId());
        List<PddGoodsVo> list = new ArrayList<>();
        vo.setAddress_id(pddUser.getAddressId());
        vo.setAnti_content(antiContent);
        vo.setGroup_id(good.getGroupId());
        PddGoodsVo goodVo = new PddGoodsVo();
        goodVo.setGoods_id(good.getGoodsId().toString());
        goodVo.setSku_id(good.getSkuId());
        goodVo.setSku_number(1);
        list.add(goodVo);
        vo.setGoods(list);
        Map<String,Object> attr = new HashMap<>();
        attr.put("create_order_token",createOrderToken);
        attr.put("order_amount",order.getAmount());
        attr.put("original_front_env",0);
        attr.put("PTRACER-TRACE-UUID","4641841400000000000000#1557210294996#st2-glb-382");
        vo.setAttribute_fields(attr);
        String id = "P"+IDGeneratorUtils.getFlowNum();
        order.setId(id);
        order.setGoodsId(good.getId());
        order.setPddUserId(pddUser.getId());
        order.setStatus(new Byte("0"));
        order.setSkuNumber(1);
        order.setAccessToken(accessToken);
        order.setNotifyTimes(0);
        order.setPddAccountId(account.getId());
        order.setIsHistory(0);
        mapper.insert(order);

        TOrder o = new TOrder();
        o.setCreateTime(new Date());
        o.setUserId(order.getUserId());
        o.setAmount(order.getAmount());
        o.setUnderorderno(order.getUserOrderSn());
        o.setOnorderno(order.getOrderSn());
        o.setOrderno(order.getId());
        o.setMerchant(user.getUsername());
        BigDecimal sysAmount = order.getAmount().multiply(user.getRate());
        o.setSysamount(sysAmount);
        BigDecimal userAmount = order.getAmount().subtract(sysAmount);
        o.setUseramount(userAmount);
        o.setStatus((byte) 0);
        tOrderService.add(o);
        try {
        Sender<Map<String,String>> sender = new PddSender<>(preOrderUrl+"?pdduid="+pddUser.getPdduid(),vo,accessToken);
        Map<String,String> m = sender.send();
        String order_sn = m.get("order_sn");
        PddPreOrderVo preOrderVo = new PddPreOrderVo();
        preOrderVo.setApp_id(3);
        preOrderVo.setReturn_url(returnUrl+"?order_sn="+order_sn);
        Map<String,Object> files = new HashMap<>();
        files.put("paid_times",0);
        files.put("forbid_contractcode","1");
        files.put("forbid_pappay","1");
        preOrderVo.setAttribute_fields(files);
        preOrderVo.setOrder_sn(order_sn);
        preOrderVo.setApp_id(9);
        Sender<Map> preOrder = new PddSender<>(aliPayUrl+"?pdduid="+pddUser.getPdduid(),preOrderVo,accessToken);
        Map<String,Object> map = preOrder.send();
        String gateway_url = (String) map.get("gateway_url");
        JSONObject object = (JSONObject)map.get("query");
        Map<String,Object> result = object.toJavaObject(Map.class);
        StringBuilder sb = new StringBuilder(gateway_url);
        sb.append("?");
            for (Map.Entry entry : result.entrySet()) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            order.setStatus(new Byte("1"));
            order.setOrderSn(order_sn);
            mapper.updateByPrimaryKeySelective(order);
            SortedMap<Object,Object> resultMap = new TreeMap<>();
            resultMap.put("success","1");
            resultMap.put("userOrderSn",order.getUserOrderSn());
            resultMap.put("orderSn",id);
            resultMap.put("amount",order.getAmount());
            resultMap.put("userId",order.getUserId());
            resultMap.put("qrCode",sb.toString());
            String resSing = DigestUtil.createSign(resultMap,key);
            resultMap.put("sign",resSing);
            log.info("开始创建订单success，订单信息["+ JSON.toJSON(order)+"]");
            return resultMap;
        }catch (Exception e){
            order.setStatus(new Byte("-1"));
            mapper.updateByPrimaryKey(order);
            Map<Object,Object> resultMap = new HashMap<>();
            resultMap.put("success","0");
            resultMap.put("errCode","-1");
            log.info("开始创建订单success，订单信息["+ JSON.toJSON(order)+"]");
            return resultMap;
        }
    }

    @Override
    public List<TPddOrder> getByTimeAndStatus(Map<String, Object> map) {
        return mapper.getByTimeAndStatus(map);
    }

    @Override
    public List<TPddOrder> getAllByNotifyTimesAndStatus() {
        return mapper.getAllByNotifyTimesAndStatus();
    }

    @Override
    public void sendNotify(String id) {
        TPddOrder order = getById(id);
        Map<String,Object> params = new HashMap<>();
        params.put("success","1");
        params.put("orderSn",order.getId());
        params.put("amount",order.getAmount());
        params.put("userOrderSn",order.getUserOrderSn());
        params.put("userId",order.getUserId());
        PddSender<Map<String,Object>> sender = new PddSender<>(order.getNotifyUrl(),params,"");
        Map<String, Object> result = sender.send();
        if(result != null){
            order.setNotifyTimes(6);
            edit(order);
            TWallet wallet = new TWallet();
            wallet.setUserId(order.getUserId());
            wallet.setType("2");
            OrderVo vo = new OrderVo();
            vo.setUnderorderno(order.getUserOrderSn());
            OrderVo orderVo = tOrderService.selectOneOrderDeails(vo);
            tOrderService.updateStatus(orderVo);
            UserVo userVo = userService.getUserById(order.getUserId());
            BigDecimal sysAmount = order.getAmount().multiply(userVo.getRate());
            BigDecimal userAmount = order.getAmount().subtract(sysAmount);
            walletService.editAmount(wallet, userAmount.toString(), "0");
        }

    }

    @Override
    public List<TPddOrder> findAll() {
        return mapper.selectAll();
    }

    @Override
    public void updateByIsHistory(TPddOrder order) {
        Example example = new Example(TPddOrder.class);
        example.createCriteria().andEqualTo("isHistory",0);
        mapper.updateByExampleSelective(order,example);
    }
}
