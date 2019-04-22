package com.chaotu.pay.dao;

import com.chaotu.pay.po.TBankCards;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.BankCardVo;

import java.util.List;

public interface TBankCardsMapper extends MyMapper<TBankCards> {

    /**
     * 查询所有银行卡信息
     * @return
     */
    List<BankCardVo> findAll();

    /**
     * 修改状态
     */
    void updateStatus(BankCardVo bankCardVo);

    /**
     * 修改银行卡
     * @param bankCardVo
     */
    void editBankCard(BankCardVo bankCardVo);
}