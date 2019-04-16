package com.chaotu.pay.dao;

import com.chaotu.pay.po.TPermission;
import com.chaotu.pay.utils.MyMapper;

import java.util.List;

public interface TPermissionMapper extends MyMapper<TPermission> {
    /**
     * 通过用户id获取该用户所拥有的权限
     * @param userId
     * @return
     */
    List<TPermission> findByUserId(String userId);

    /**
     * 通过用户id查询改用户id所拥有的权限
     * 查询rolePermission中间表
     * @param roleId
     * @return
     */
    List<TPermission> findByRoleId(String roleId);
}