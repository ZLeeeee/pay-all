package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TYzUser;
import com.chaotu.pay.service.PddUserService;
import com.chaotu.pay.service.YzUserService;
import com.chaotu.pay.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yz/user")
public class YzUserController {
    @Autowired
    YzUserService userService;
    @PostMapping("/all")
    public Message all(){
        return ResponseUtil.responseBody(userService.findAll());
    }
    @PostMapping("/add")
    public Message add(@RequestBody TYzUser user){
        userService.insert(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/update")
    public Message update(@RequestBody TYzUser user){
        userService.update(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/delete")
    public Message delete(@RequestBody TYzUser user){
        userService.delete(user);
        return ResponseUtil.responseBody("请求成功！");
    }
    @PostMapping("/get")
    public Message get(@RequestBody TYzUser user){
        return ResponseUtil.responseBody(userService.selectOne(user));
    }
}
