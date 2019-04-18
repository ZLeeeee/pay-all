package com.chaotu.pay.dao;

import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.OrderVo;

import java.util.List;

public interface TOrderMapper extends MyMapper<TOrder> {
    /**
     * 查询所有订单
     * @return
     */
    List<TOrder> findAll(OrderVo orderVo);
}