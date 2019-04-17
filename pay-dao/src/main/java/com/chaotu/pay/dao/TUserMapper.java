package com.chaotu.pay.dao;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.RoleVo;
import com.chaotu.pay.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUserMapper extends MyMapper<TUser> {
    List<TUser> findAllUserByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 查询所有用户
     * @param userVo
     * @return
     */
    List<TUser> findAll(UserVo userVo);

    /**
     * 根据角色查询用户
     * @param userVo
     * @return
     */
    List<UserVo> getUserByRole(UserVo userVo);
    /**
     * 根据角色查询用户数量
     * @param userVo
     * @return
     */
    int countUserByRole(UserVo userVo);
}