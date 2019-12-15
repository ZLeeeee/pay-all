package com.chaotu.pay.service;

import com.chaotu.pay.po.TYinlianAccount;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.math.BigDecimal;

public interface YinlianAccountService extends CRUDService<TYinlianAccount> {
    void updateAmount(BigDecimal amount, String account);

    MyPageInfo findAllByPage(PageVo pageVo);
}
