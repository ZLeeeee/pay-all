package com.chaotu.pay.service;

import com.chaotu.pay.po.TPddOrder;

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
    String pay(TPddOrder order);

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
}
