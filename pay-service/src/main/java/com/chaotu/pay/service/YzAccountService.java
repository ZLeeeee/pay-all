package com.chaotu.pay.service;

import com.chaotu.pay.po.TYzAccount;

import java.math.BigDecimal;
import java.util.List;

public interface YzAccountService extends CRUDService<TYzAccount> {
    List<TYzAccount> findByStatus();

    void updateAmount(BigDecimal amount, int id);

    TYzAccount findByid(int id);

    void updateTodayAmountByStatus();

    List<TYzAccount> findAllByStatus();
}
