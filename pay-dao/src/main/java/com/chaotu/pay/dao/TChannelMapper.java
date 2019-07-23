package com.chaotu.pay.dao;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.utils.MyMapper;

import java.util.Map;

public interface TChannelMapper extends MyMapper<TChannel> {
    TChannel getChannelByPayTypeRandom(Map<String,Integer> map);
}