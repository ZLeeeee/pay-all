package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.constant.CommonStatus;
import com.chaotu.pay.dao.TPermissionMapper;
import com.chaotu.pay.dao.TRoleMapper;
import com.chaotu.pay.dao.TRolePermissionMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TPermission;
import com.chaotu.pay.po.TRole;
import com.chaotu.pay.po.TRolePermission;
import com.chaotu.pay.service.RoleService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private TRolePermissionMapper tRolePermissionMapper;
    @Autowired
    private TPermissionMapper tPermissionMapper;

    @Override
    public List<RoleVo> getAll() {
        List<TRole> tRoles = tRoleMapper.selectAll();
        List<RoleVo> roleVoList = MyBeanUtils.copyList(tRoles, RoleVo.class);
        return roleVoList;
    }

    @Override
    public RoleVo getById(String id) {
        TRole tRole = tRoleMapper.selectByPrimaryKey(id);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(tRole,roleVo);
        return roleVo;
    }

    @Override
    public void addRole(RoleVo roleVo) {
        log.info("添加角色,入参roleVo=["+roleVo.toString()+"]");
        if(StringUtils.isEmpty(roleVo.getName())){
            throw new BizException(ExceptionCode.REQUEST_PARAM_MISSING);
        }
        TRole tRole = new TRole();
        BeanUtils.copyProperties(roleVo,tRole);
        Example example = new Example(TRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",roleVo.getName());
        int i = tRoleMapper.selectCountByExample(example);
        if(i>0){
            log.error("该角色名称或编码");
            throw new BizException(ExceptionCode.ROLE_AREADY_EXIST.getCode(),ExceptionCode.ROLE_AREADY_EXIST.getMsg());

        }
        tRole.setId(IDGeneratorUtils.getUUID32());
        tRole.setCreateTime(new Date());
        tRoleMapper.insertSelective(tRole);
        log.info("添加角色成功，输出roleVo=["+tRole.toString()+"]");
    }

    @Override
    public void assignPermission(String roleId, String permissionIds) {
        log.info("分配权限，入参roleId="+roleId+",permissionIds=["+permissionIds+"]");
        delRolePermission(roleId);
        List<TRolePermission> tRoleMenuList = new ArrayList<>();
        for (String permissionId : permissionIds.split(",")) {
            TRolePermission tRolePermission = new TRolePermission();
            tRolePermission.setId(IDGeneratorUtils.getUUID32());
            tRolePermission.setRoleId(roleId);
            tRolePermission.setPermissionId(permissionId);
            tRolePermission.setCreateTime(new Date());
            tRolePermissionMapper.insertSelective(tRolePermission);
            tRoleMenuList.add(tRolePermission);
        }
        //todo 批量插入有问题，坑，回头自己写批量插入
        //tRoleMenuMapper.insertList(tRoleMenuList);
        log.info("分配权限成功，入参roleId="+roleId+",permissionIds=["+tRoleMenuList+"]");
    }

    @Override
    public void editRole(RoleVo roleVo) {
        if(StringUtils.isEmpty(roleVo.getId())||StringUtils.isEmpty(roleVo.getName())){
            throw new BizException(ExceptionCode.REQUEST_PARAM_MISSING);
        }
        log.info("修改角色,入参roleVo=["+roleVo.toString()+"]");
        TRole tRole = new TRole();
        BeanUtils.copyProperties(roleVo,tRole);
        tRoleMapper.updateByPrimaryKeySelective(tRole);
        log.info("修改角色成功，输出roleVo=["+tRole.toString()+"]");
    }

    @Override
    public void delById(String id) {
        log.info("删除角色,入参id=["+id+"]");
        TRole tRole = tRoleMapper.selectByPrimaryKey(id);
        //查询该角色下的权限和公司，如果有，则提示无法删除
        TRolePermission tRolePermission = new TRolePermission();
        tRolePermission.setRoleId(id);
        List<TRolePermission> tRolePermissionsList = tRolePermissionMapper.select(tRolePermission);
        if(!CollectionUtils.isEmpty(tRolePermissionsList)){
            throw new BizException(ExceptionCode.DEL_ROLE_HAS_PERMISSION);
        }
        if(tRole!=null){
            tRoleMapper.deleteByPrimaryKey(id);
            log.info("修改角色成功，输出id=["+tRole.toString()+"]");
        }else{
            log.info("未能找到要删除的角色id="+id);
        }
    }

    @Override
    public void delRolePermission(String roleId) {
        Example trolePermissionExmaple = new Example(TRolePermission.class);
        Example.Criteria criteria = trolePermissionExmaple.createCriteria();
        criteria.andEqualTo("roleId",roleId);
        tRolePermissionMapper.deleteByExample(trolePermissionExmaple);
    }

    @Override
    public MyPageInfo<RoleVo> findAllByPage(PageVo pageVo, RoleVo vo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TRole.class);
        example.setOrderByClause("create_time");
        if(vo!=null){
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name",vo.getName());
        }
        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        int count = tRoleMapper.selectCountByExample(example);
        List<RoleVo> roleVoList = MyBeanUtils.copyList(tRoles, RoleVo.class);
        for (RoleVo roleVo : roleVoList) {
            List<TPermission> tPermissionList = tPermissionMapper.findByRoleId(roleVo.getId());
            List<PermissionVo> permissionVoList = MyBeanUtils.copyList(tPermissionList, PermissionVo.class);
            roleVo.setPermissions(permissionVoList);
        }
        MyPageInfo<RoleVo> pageInfo = new MyPageInfo<>(roleVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }
}
