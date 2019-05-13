package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.service.PddPayService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pddPay")
public class PddPayController {
    @Value("${pdd.preOrderUrl}")
    private String preOrderUrl;
    @Value("${pdd.aliPayUrl}")
    private String aliPayUrl;
    @Autowired
    private PddOrderService pddOrderService;
    @Autowired
    private PddGoodsService pddGoodsService;

    @PostMapping("/pay")
    public Message pay(){
        PddOrderVo vo = new PddOrderVo();
        List<PddGoodsVo> goods = new ArrayList<>();
        vo.setAddress_id(7240866294L);
        vo.setAnti_content("0alAfxnUmNhYq9dayy8SgULmbaUgtS3k_wj8QqkvgyugpiYuEQ2_HdF_-GMaiGgkwfQLmJC0FqJ5r6OAOZwoyWQRhIq8IQA3og9J9No4sMd2LYDIAiC0RDQNlsk1N47CyANQvCUWLlc1NHpYxaFF9OcIXX1GpVpYSVFkm1Br_kPXaJ7dMvByK7ZI4ydUGXardWgm_8QorJCDk_1n3ks4mkinYZFeljzoKZJP8NMvt1XA0X-CqKXAe9YHOT9BpTrYvjj6VArbsd8DhX1spxPy6IT_O7g76rZI4ScahCK7SdF6qRzeIoGmR6Ymuuis20DuZy4DuAqHwmJSeQBrkj3_qEo99uTe68TDRwmRS47XlA60IBSw_MjWmc87WhnERLPB5UEER9meV6H9W9kFCtbIcz_jtgAB864XmzdwfCTQNBYwdC4MHsF8shIr201H9N7xq8oEhdpRPEuRqzp0LUTSzUDB9YhrY1OpOBUES8XB7PXWP1cig1yxgDMtVWXMnyXDlR14g81zh2hQGIPlNl-IUgunGqJr6vjAEag7M0YWIlcPqpnNP4b3vP0kr_52HgdqDeIwFR4fBqx7pEJVcOjct5ecF1-dD7YOwe8ZjUoMr3hbbNzNLWHXq383ucQj78JpOl1ijR368cqhDJ");
        vo.setGroup_id("14312964464");
        PddGoodsVo good = new PddGoodsVo();
        good.setGoods_id("8463748136");
        good.setSku_id(235600893273L);
        good.setSku_number(1);
        goods.add(good);
        vo.setGoods(goods);
        Map<String,Object> attr = new HashMap<>();
        attr.put("create_order_token","4499e23d-4143-4b3d-95a2-08f2b15c23b5");
        attr.put("order_amount",2000);
        attr.put("original_front_env",0);
        attr.put("PTRACER-TRACE-UUID","4641841400000000000000#1557210294996#st2-glb-382");
        vo.setAttribute_fields(attr);
        Sender<Map> sender = new PddSender<>(preOrderUrl+"?pdduid=2121113020469",vo,"6UKLQF3FDKB3VYS4MEMOYIPM6YPNTLEKPR6LPACWEREAATX5QX6A1003544");
        Map<String,String> m = sender.send();
        String order_sn = m.get("order_sn");
        PddPreOrderVo preOrderVo = new PddPreOrderVo();
        preOrderVo.setApp_id(3);
        preOrderVo.setReturn_url("https://mobile.yangkeduo.com/transac_wappay_callback.html?order_sn=190507-054024602452101");
        Map<String,Object> files = new HashMap<>();
        files.put("paid_times",0);
        files.put("forbid_contractcode","1");
        files.put("forbid_pappay","1");
        preOrderVo.setAttribute_fields(files);
        preOrderVo.setOrder_sn(order_sn);
        preOrderVo.setApp_id(9);
        Sender<Map> preOrder = new PddSender<>(aliPayUrl+"?pdduid=2121113020469",preOrderVo,"6UKLQF3FDKB3VYS4MEMOYIPM6YPNTLEKPR6LPACWEREAATX5QX6A1003544");
        Map<String,Object> map = preOrder.send();
        String gateway_url = (String) map.get("gateway_url");
        JSONObject object = (JSONObject)map.get("query");
        Map<String,Object> result = object.toJavaObject(Map.class);
        StringBuilder sb = new StringBuilder(gateway_url);
        sb.append("?");
        try {
            for (Map.Entry entry: result.entrySet()) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"UTF-8")).append("&");
            }
            sb.deleteCharAt(sb.length()-1);
            return ResponseUtil.responseBody(sb);
        }catch (Exception e){

        }


        return ResponseUtil.responseBody("1","请求失败");
    }
}
