package com.chaotu.pay.service;

import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.RoleVo;

import java.util.List;

/**
 * @Description: 角色管理
 * @Date: Created in 20:02 2018/10/23
 * @Author: yaochenglong
 */

public interface RoleService {

    /**
     * 获取所有角色
     * @return
     */
    List<RoleVo> getAll();

    /**
     * 通过id查找
     * @param id
     * @return
     */
    RoleVo getById(String id);

    /**
     * 添加角色
     * @param roleVo
     */
    void addRole(RoleVo roleVo);

    /**
     * 分页查找所有角色
     * @param pageVo
     * @param vo
     * @return
     */
    MyPageInfo<RoleVo> findAllByPage(PageVo pageVo, RoleVo vo);


    /**
     * 分配权限
     * @param roleId
     * @param permissionIds
     */
    void assignPermission(String roleId, String permissionIds);


    /**
     * 修改角色
      * @param roleVo
     */
    void editRole(RoleVo roleVo);

    /**
     * 删除角色
     * @param id
     */
    void delById(String id);

    void delRolePermission(String roleId);


}
