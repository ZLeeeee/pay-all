package com.chaotu.pay.service;

import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.vo.MyPageInfo;

import com.chaotu.pay.vo.UserVo;


import java.util.List;

/**
 * @Description:用户管理
 * @Author: yaochenglong
 * @Date: 16:55 2018/10/21
 */
public interface UserRatesService {


    /**
     * 获取所有UserRates
     * @return
     */
    List<TUserRates> getAllUserRates();


    /**
     * 通过主键id查询UserRates
     * @param vo
     * @return
     */
    TUserRates getUserRatesById(TUserRates vo);

    /**
     * @Description:
     * 分页查询
     * @param pageNow
     * @param pageSize
     * @param vo 额外都查询条件
     * @return
     */
    MyPageInfo<UserVo> getUserRatesByPage(int pageNow, int pageSize, TUserRates vo);

    /**
     * @Description:添加UserRates
     * @param vo
     */
    void addUserRates(TUserRates vo);

    /**
     * 删除用户
     * @param vo 用户唯一id
     */
    void delUserRates(TUserRates vo);


    /**
     * 修改UserRates
     * @param vo
     */
    void editUserRates(TUserRates vo);


    /***
     * 根据用户查询UserRates
     * @param userVo
     * @return
     */
    List<TUserRates> getUserRatesByUser(UserVo userVo);

    /**
     * 查询UserRates行数
     * @param userVo
     * @return
     */
    int countUserRatesByAgent(UserVo userVo);

    void editUserRatesByUserId(TUserRates userRates,String userId);

}
