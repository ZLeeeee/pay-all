package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.service.ChannelService;
import com.chaotu.pay.vo.ChannelVo;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 通道
 */
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * 查询所有通道
     * @return
     */
    @GetMapping("/all")
    public Message getAllChannel(){
        return ResponseUtil.responseBody(channelService.getAll());
    }

    /**
     * 分页查询通道
     * @param pageVo
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(channelService.findAllByPage(pageVo,null));
    }

    /**
     * 添加通道
     */
    @PostMapping("/add")
    public Message addChannel(@RequestBody ChannelVo channelVo){
        channelService.addChannel(channelVo);
        return ResponseUtil.responseBody("添加通道成功");
    }

    /**
     * 修改通道
     * @return
     */
    @PostMapping("/edit")
    public Message editChannel(@RequestBody ChannelVo channelVo){
        channelService.editChannel(channelVo);
        return ResponseUtil.responseBody("修改通道成功");
    }


    /**
     * 删除通道
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
    public Message delChannel(String id){
        channelService.delChannel(id);
        return ResponseUtil.responseBody("删除通道成功");
    }

    /**
     * 更改接口状态
     * @param channelVo
     * @return
     */
    @PostMapping("/update")
    public Message updateStatus(@RequestBody ChannelVo channelVo){
        channelService.updateStatus(channelVo);
        return ResponseUtil.responseBody("更改接口状态成功");
    }






}
