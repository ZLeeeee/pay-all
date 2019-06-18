package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TYzGoods;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.YzGoodsService;
import com.chaotu.pay.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/yz/goods")
public class YzGoodsController {
    @Autowired
    YzGoodsService service;
    @PostMapping("/all")
    public Message all(){
        List<TYzGoods> allGoods = service.findAll();
        List<Map<String,Object>> result = new ArrayList<>();
        allGoods.forEach((g)->{
            Map<String,Object> map = new HashMap<>();
            map.put("id",g.getId());
            map.put("amount",g.getAmount());
            map.put("stock",g.getStock());
            result.add(map);
        });
        return ResponseUtil.responseBody(result);
    }
    @PostMapping("/add")
    public Message all(@RequestBody TYzGoods goods){
        service.insert(goods);
        return ResponseUtil.responseBody("添加成功");
    }

    @PostMapping("/update")
    public Message edit(@RequestBody TYzGoods goods){
        service.update(goods);
        return ResponseUtil.responseBody("修改成功");
    }
    @PostMapping("/get")
    public Message get(@RequestBody TYzGoods goods){
        return ResponseUtil.responseBody(service.findByCondition(goods));
    }
}
