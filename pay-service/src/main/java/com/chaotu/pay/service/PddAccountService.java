package com.chaotu.pay.service;

import com.chaotu.pay.po.TPddAccount;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.math.BigDecimal;
import java.util.List;

public interface PddAccountService {
    TPddAccount selectOne(TPddAccount account);
    List<TPddAccount> select(TPddAccount account);
    List<TPddAccount> findAllByStatus();
    void insert(TPddAccount account);
    void update(TPddAccount account);
    TPddAccount findByid(int id);
    void updateAmount(BigDecimal amount,TPddAccount account);

    MyPageInfo<TPddAccount> findAllByPage(PageVo pageVo);

    void delete(TPddAccount account);
    void updateTodayAmountByStatus();

}
