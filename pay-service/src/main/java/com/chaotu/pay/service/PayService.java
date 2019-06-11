package com.chaotu.pay.service;

import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.qo.PddOrderQo;

import java.util.List;
import java.util.Map;

public interface PayService<T>{
    /***
     * 添加订单
     * @param order
     */
    void add(T order);

    /***
     * 创建订单
     * @param order
     */
    Map<String,Object> pay(T order);

    /***
     * 修改
     * @param order
     */
    void edit(T order);

    /***
     * 根据id获取订单
     * @param id
     * @return
     */
    T getById(String id);

    /***
     * 根据条件查询单条订单
     * @param order
     * @return
     */
    T get(T order);

    void updateByOrderSn(T order);

    List<T> getAllPaiedOrders();

    List<T> getAllSentOrders();

    List<T> getByTimeAndStatus(Map<String,Object> map);

    T getByOrderSn(String o);
    List<T> getAllByNotifyTimesAndStatus();

    void sendNotify(String id);
    List<T> findAll();
    void updateByIsHistory(T order);
}
