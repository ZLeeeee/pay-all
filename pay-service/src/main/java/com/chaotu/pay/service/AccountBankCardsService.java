package com.chaotu.pay.service;

import com.chaotu.pay.po.TAccountBankCards;
import com.chaotu.pay.po.TUser;
import com.chaotu.pay.vo.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Description:用户管理
 * @Author: yaochenglong
 * @Date: 16:55 2018/10/21
 */
public interface AccountBankCardsService {

    /**
     * 获取所有用户数据
     * @return
     */
    List<AccountBankCardsVo> getAllAccountBankCards();





    /**
     * 通过主键id查询用户
     * @param id
     * @return
     */
    TAccountBankCards getAccountBankCardsById(Long id);

    /**
     * @Description:添加用户
     * @param vo
     */
    void addAccountBankCardsVo(AccountBankCardsVo vo);


    void delAccountPhonesVo(String id);
    /**
     * 修改用户
     * @param vo
     */
    void editAccountBankCards(AccountBankCardsVo vo);




    MyPageInfo<AccountBankCardsVo> findByCondition(PageVo pageVo, SearchVo searchVo, AccountBankCardsVo vo) throws ParseException;


}
