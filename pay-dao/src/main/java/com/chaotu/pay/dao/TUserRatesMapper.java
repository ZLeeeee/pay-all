package com.chaotu.pay.dao;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUserRatesMapper extends MyMapper<TUserRates> {

    List<TUserRates> getUserRatesByUser(UserVo userVo);

    void updateByUserId(@Param("users")List<TUser> users,@Param("userRates")TUserRates userRates);
}