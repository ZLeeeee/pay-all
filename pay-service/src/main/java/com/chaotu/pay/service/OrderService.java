package com.chaotu.pay.service;

import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

public interface OrderService extends CRUDService<TOrder>{
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
     * 修改订单状态
     * @param orderVo
     * @return
     */
    int updateStatus(OrderVo orderVo);

    void updateByIsHistory(TOrder order);
    TOrder selectOne(TOrder order);
    Map<Object,Object> pay(OrderVo order);
    Map<String,Object> notify(Map<String, Object> params, String OrderNo, Long channelId, HttpServletRequest request);
}
