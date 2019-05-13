package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class PddGoodsController {
    @Autowired
    PddGoodsService service;
    @PostMapping("/all")
    public Message all(){
        return ResponseUtil.responseBody(service.getAllGoods());
    }
    @PostMapping("/add")
    public Message all(@RequestBody TPddGoods goods){
        service.add(goods);
        return ResponseUtil.responseBody("添加成功");
    }
}
