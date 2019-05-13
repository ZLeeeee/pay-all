package com.chaotu.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.dao.TPddOrderMapper;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.vo.PddGoodsVo;
import com.chaotu.pay.vo.PddOrderVo;
import com.chaotu.pay.vo.PddPreOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PddOrderServiceImpl implements PddOrderService {

    @Value("${pdd.preOrderUrl}")
    private String preOrderUrl;
    @Value("${pdd.aliPayUrl}")
    private String aliPayUrl;
    @Value("${pdd.returnUrl}")
    private String returnUrl;

    @Autowired
    TPddOrderMapper mapper;
    @Autowired
    PddGoodsService goodsService;
    @Autowired
    @Qualifier("antiContentChoser")
    Choser<String> antiContentChoser;
    @Autowired
    @Qualifier("accessTokenChoser")
    Choser<String> accessTokenChoser;
    @Autowired
    @Qualifier("createOrderTokenChoser")
    Choser<String> createOrderTokenChoser;
    @Autowired
    @Qualifier("pddUserChoser")
    Choser<TPddUser> pddUserChoser;
    @Override
    public void add(TPddOrder order) {
        mapper.insert(order);
    }

    @Override
    public void edit(TPddOrder order) {
        mapper.updateByPrimaryKey(order);
    }

    @Override
    public TPddOrder getById(String id) {
        TPddOrder order = new TPddOrder();
        order.setId(id);
        return get(order);
    }

    @Override
    public TPddOrder get(TPddOrder order) {
        return mapper.selectOne(order);
    }

    @Override
    public String pay(TPddOrder order) {
        BigDecimal amount = order.getAmount();
        Integer skuNumber = order.getSkuNumber();
        Integer goodsId = order.getGoodsId();
        TPddGoods good = goodsService.getById(goodsId);
        if(!good.getAmount().multiply(new BigDecimal(skuNumber)).equals(amount))
            throw new IllegalArgumentException("订单总价与商品价格与数量不符");
        PddOrderVo vo = new PddOrderVo();
        TPddUser pddUser = pddUserChoser.chose();
        String antiContent = antiContentChoser.chose();
        String accessToken = accessTokenChoser.chose();
        String createOrderToken = createOrderTokenChoser.chose();
        vo.setAddress_id(pddUser.getAddressId());
        List<PddGoodsVo> list = new ArrayList<>();
        vo.setAddress_id(pddUser.getAddressId());
        vo.setAnti_content(antiContent);
        vo.setGroup_id(good.getGroupId());
        PddGoodsVo goodVo = new PddGoodsVo();
        goodVo.setGoods_id(good.getGoodsId().toString());
        goodVo.setSku_id(good.getSkuId());
        goodVo.setSku_number(skuNumber);
        list.add(goodVo);
        vo.setGoods(list);
        Map<String,Object> attr = new HashMap<>();
        attr.put("create_order_token",createOrderToken);
        attr.put("order_amount",amount);
        attr.put("original_front_env",0);
        attr.put("PTRACER-TRACE-UUID","4641841400000000000000#1557210294996#st2-glb-382");
        vo.setAttribute_fields(attr);
        String id = "P"+IDGeneratorUtils.getFlowNum();
        order.setId(id);
        order.setGoodsId(good.getId());
        order.setPddUserId(pddUser.getId());
        order.setStatus(new Byte("0"));
        order.setSkuNumber(skuNumber);
        order.setAccessToken(accessToken);
        mapper.insert(order);
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
            mapper.updateByPrimaryKey(order);
            return sb.toString();
        }catch (Exception e){
            order.setStatus(new Byte("4"));
            mapper.updateByPrimaryKey(order);
            return null;
        }
    }
}
