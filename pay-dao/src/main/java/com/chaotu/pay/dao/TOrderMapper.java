package com.chaotu.pay.dao;

import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.OrderVo;

import java.util.List;
import java.util.Map;

public interface TOrderMapper extends MyMapper<TOrder> {
    Map<String, Object> getGeneralAccount(OrderVo orderVo);

    int countByCondition(OrderVo orderVo);

    List<TOrder> findAll(OrderVo orderVo);

    int updateStatus(OrderVo orderVo);

}