package com.chaotu.pay.dao;

import com.chaotu.pay.po.TAccountBankCards;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.AccountBankCardsVo;

import java.util.List;

public interface TAccountBankCardsMapper extends MyMapper<TAccountBankCards> {
    List<AccountBankCardsVo> findByCondition(AccountBankCardsVo vo);
}