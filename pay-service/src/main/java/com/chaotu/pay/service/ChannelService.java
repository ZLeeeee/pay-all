package com.chaotu.pay.service;

import com.chaotu.pay.vo.ChannelVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.util.List;

/**
 * 通道管理
 */
public interface ChannelService {

    /**
     * 查询所有通道
     * @return
     */
    List<ChannelVo> getAll();

    /**
     * 分页查询
     * @return
     */
    MyPageInfo<ChannelVo> findAllByPage(PageVo pageVo, ChannelVo vo);

    /**
     * 添加通道
     * @param channelVo
     */
    void addChannel(ChannelVo channelVo);

    /**
     * 修改通道
     * @param channelVo
     */
    void editChannel(ChannelVo channelVo);

    /**
     * 删除通道
     * @param id
     */
    void delChannel(String id);

    /**
     * 更改接口状态
     * @param channelVo
     */
    void updateStatus(ChannelVo channelVo);


}


