package com.chaotu.pay.service;

import com.chaotu.pay.vo.*;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    /**
     * 展示所有订单和总账目
     * @param pageVo
     * @param searchVo
     * @param orderVo
     * @return
     * @throws ParseException
     */
    Map<String,Object> findByCondition(PageVo pageVo, SearchVo searchVo, OrderVo orderVo) throws ParseException;

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    OrderVo selectOneOrderDeails(OrderVo orderVo);
}
