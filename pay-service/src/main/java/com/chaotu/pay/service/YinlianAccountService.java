package com.chaotu.pay.service;

import com.chaotu.pay.po.TYinlianAccount;

import java.math.BigDecimal;

public interface YinlianAccountService extends CRUDService<TYinlianAccount> {
    void updateAmount(BigDecimal amount, String account);
}
