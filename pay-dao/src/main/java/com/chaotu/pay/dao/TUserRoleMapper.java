package com.chaotu.pay.dao;

import com.chaotu.pay.po.TRole;
import com.chaotu.pay.po.TUserRole;
import com.chaotu.pay.utils.MyMapper;

import java.util.List;

public interface TUserRoleMapper extends MyMapper<TUserRole> {

    /**
     * 获取userId用户所拥有的权限
     * @param userId
     * @return
     */
    List<TRole> findByUserId(String userId);
}