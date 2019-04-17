package com.chaotu.pay.service;

import cn.hutool.db.sql.Order;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.OrderVo;
import com.chaotu.pay.vo.PageVo;

/**
 * 订单
 */
public interface OrderService {
    /**
     * 分页获取全部订单
     * @return
     */
    MyPageInfo<Order> getAllOrderForPage(PageVo pageVo, OrderVo orderVo);
}
