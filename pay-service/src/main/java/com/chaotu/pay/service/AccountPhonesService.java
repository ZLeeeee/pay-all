package com.chaotu.pay.service;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.vo.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Description:用户管理
 * @Author: yaochenglong
 * @Date: 16:55 2018/10/21
 */
public interface AccountPhonesService {

    /**
     * 通过主键id查询用户
     * @param id
     * @return
     */
    AccountPhonesVo getAccountPhonesVoById(String id);



    /**
     * @Description:添加用户
     * @param vo
     */
    void addAccountPhonesVo(AccountPhonesVo vo);

    /**
     * 删除用户
     * @param id 用户唯一id
     */
    void delAccountPhonesVo(String id);


    /**
     * 修改用户
     * @param vo
     */
    void editAccountPhonesVo(AccountPhonesVo vo);




    MyPageInfo<AccountPhonesVo> findByCondition(PageVo pageVo, SearchVo searchVo, AccountPhonesVo accountPhonesVo) throws ParseException;


    /**
     * 根据role查询行数
     * @param userVo
     * @return
     */
    int countByCondition(AccountPhonesVo accountPhonesVo,SearchVo searchVo);



}
