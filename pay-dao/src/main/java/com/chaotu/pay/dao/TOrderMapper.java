package com.chaotu.pay.dao;

import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.OrderVo;

import java.util.List;
import java.util.Map;

public interface TOrderMapper extends MyMapper<TOrder> {
    /**
     * 查询所有订单
     * @return
     */
    List<TOrder> findAll(OrderVo orderVo);

    /**
     * 获取总账
     * @param orderVo
     * @return
     */
    Map<String, Object> getGeneralAccount(OrderVo orderVo);

    /**
     * 修改订单状态
     * @param orderVo
     * @return
     */
    int updateStatus(OrderVo orderVo);
}