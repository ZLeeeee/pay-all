package com.chaotu.pay.service;

import com.chaotu.pay.po.TBankCards;
import com.chaotu.pay.vo.BankCardVo;
import com.chaotu.pay.vo.BankVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.util.List;

/**
 * 银行卡信息管理
 */
public interface BankCradService {


    /**
     * 分页查询所有银行卡
     * @param pageVo
     * @return
     */
    MyPageInfo<BankCardVo> findAllByPage(PageVo pageVo);

    /**
     * 添加银行卡
     * @param bankCardVo
     */
    void addBankCard(BankCardVo bankCardVo);


    /**
     * 获取所有银行
     * @return
     */
    List<BankVo> getAllBank();

    /**
     * 修改银行卡
     * @param bankCardVo
     */
    void editBankCard(BankCardVo bankCardVo);

    /**
     * 删除银行卡
     * @param id
     */
    void delBankCard(String id);

}
