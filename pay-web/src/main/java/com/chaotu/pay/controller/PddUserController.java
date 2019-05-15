package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.service.PddUserService;
import com.chaotu.pay.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pddUser")
public class PddUserController {
    @Autowired
    PddUserService userService;
    @PostMapping("/all")
    public Message all(){
        return ResponseUtil.responseBody(userService.getAll());
    }
    @PostMapping("/add")
    public Message add(@RequestBody TPddUser user){
        userService.insert(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/update")
    public Message update(@RequestBody TPddUser user){
        userService.update(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/delete")
    public Message delete(@RequestBody TPddUser user){
        userService.delete(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/get")
    public Message get(@RequestBody TPddUser user){
        return ResponseUtil.responseBody(userService.selectOne(user));
    }
}
