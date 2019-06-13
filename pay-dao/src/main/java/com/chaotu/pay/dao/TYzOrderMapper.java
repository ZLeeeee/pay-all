package com.chaotu.pay.dao;

import com.chaotu.pay.po.TYzOrder;
import com.chaotu.pay.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TYzOrderMapper extends MyMapper<TYzOrder> {
    List<TYzOrder> getAllByNotifyTimesAndStatus();

    List<TYzOrder> getByTimeAndStatus(Map<String, Object> map);

    void updateByOrderSn(TYzOrder order);
}