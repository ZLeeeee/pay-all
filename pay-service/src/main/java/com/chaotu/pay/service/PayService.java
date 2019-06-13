package com.chaotu.pay.service;
import java.util.List;
import java.util.Map;

public interface PayService<T> extends CRUDService<T>{



    /***
     * 根据id获取订单
     * @param id
     * @return
     */
    T getById(String id);

    void updateByOrderNo(T order);

    List<T> getAllPaiedOrders();

    List<T> getAllSentOrders();

    List<T> getByTimeAndStatus(Map<String,Object> map);

    T getByOrderNo(String o);

    List<T> getAllByNotifyTimesAndStatus();

    void sendNotify(String id);

    void updateByIsHistory(T order);
}
