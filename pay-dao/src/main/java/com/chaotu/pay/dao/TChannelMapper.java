package com.chaotu.pay.dao;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.ChannelVo;

public interface TChannelMapper extends MyMapper<TChannel> {
    /**
     * 修改通道
     * @param channelVo
     */
    void updateChannel(ChannelVo channelVo);

}