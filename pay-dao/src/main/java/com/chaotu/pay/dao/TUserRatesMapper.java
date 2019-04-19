package com.chaotu.pay.dao;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.UserRatesVo;
import com.chaotu.pay.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUserRatesMapper extends MyMapper<TUserRates> {

    /***
     * 根据用户查询UserRates
     * @param userVo
     * @return
     */
    List<UserRatesVo> getUserRatesByUser(UserVo userVo);

    /***
     * 根据用户id,支付方式id修改userRates
     * @param users
     * @param userRates
     */
    void updateByUserId(@Param("users")List<TUser> users,@Param("userRates")TUserRates userRates);
}