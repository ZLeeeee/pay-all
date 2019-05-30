package com.chaotu.pay.dao;

import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TPddOrderMapper extends MyMapper<TPddOrder> {
    List<TPddOrder> getByTimeAndStatus(Map<String,Object> params);
    TPddOrder getByOrderSn(String orderSn);
    void updateByOrderSn(TPddOrder order);

    List<TPddOrder> getAllByNotifyTimesAndStatus();
}