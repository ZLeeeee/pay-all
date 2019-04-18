package com.chaotu.pay.dao;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.ChannelVo;

public interface TChannelMapper extends MyMapper<TChannel> {
    /**
     * 更新接口状态
     * @param channelVo
     */
    void updateStatus(ChannelVo channelVo);
}