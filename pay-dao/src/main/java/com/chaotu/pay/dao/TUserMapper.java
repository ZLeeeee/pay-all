package com.chaotu.pay.dao;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.UserVo;

import java.util.List;

public interface TUserMapper extends MyMapper<TUser> {
    /**
     * 查询所有用户
     * @param userVo
     * @return
     */
    List<TUser> findAll(UserVo userVo);
}