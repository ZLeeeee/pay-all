package com.chaotu.pay.service;

import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.qo.OrderQo;
import com.chaotu.pay.qo.PddOrderQo;

import java.util.List;
import java.util.Map;

public interface PddOrderService {
    /***
     * 添加订单
     * @param order
     */
    void add(TPddOrder order);

    /***
     * 创建订单
     * @param order
     */
    Map<Object,Object> pay(PddOrderQo order);

    /***
     * 修改
     * @param order
     */
    void edit(TPddOrder order);

    /***
     * 根据id获取订单
     * @param id
     * @return
     */
    TPddOrder getById(String id);

    /***
     * 根据条件查询单条订单
     * @param order
     * @return
     */
    TPddOrder get(TPddOrder order);

    void updateByOrderSn(TPddOrder order);

    List<TPddOrder> getAllPaiedOrders();

    List<TPddOrder> getAllSentOrders();

    List<TPddOrder> getByTimeAndStatus(Map<String,Object> map);

    TPddOrder getByOrderSn(String o);
    List<TPddOrder> getAllByNotifyTimesAndStatus();

    void sendNotify(String id);
    List<TPddOrder> findAll();
    void updateByIsHistory(TPddOrder order);
}
