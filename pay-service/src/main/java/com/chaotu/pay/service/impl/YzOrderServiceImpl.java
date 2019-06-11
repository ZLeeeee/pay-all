package com.chaotu.pay.service.impl;

import com.chaotu.pay.po.TYzOrder;
import com.chaotu.pay.service.YzOrderService;

import java.util.List;
import java.util.Map;

public class YzOrderServiceImpl implements YzOrderService {
    @Override
    public void add(TYzOrder order) {

    }

    @Override
    public Map<String, Object> pay(TYzOrder order) {
        return null;
    }

    @Override
    public void edit(TYzOrder order) {

    }

    @Override
    public TYzOrder getById(String id) {
        return null;
    }

    @Override
    public TYzOrder get(TYzOrder order) {
        return null;
    }

    @Override
    public void updateByOrderSn(TYzOrder order) {

    }

    @Override
    public List<TYzOrder> getAllPaiedOrders() {
        return null;
    }

    @Override
    public List<TYzOrder> getAllSentOrders() {
        return null;
    }

    @Override
    public List<TYzOrder> getByTimeAndStatus(Map<String, Object> map) {
        return null;
    }

    @Override
    public TYzOrder getByOrderSn(String o) {
        return null;
    }

    @Override
    public List<TYzOrder> getAllByNotifyTimesAndStatus() {
        return null;
    }

    @Override
    public void sendNotify(String id) {

    }

    @Override
    public List<TYzOrder> findAll() {
        return null;
    }

    @Override
    public void updateByIsHistory(TYzOrder order) {

    }
}
