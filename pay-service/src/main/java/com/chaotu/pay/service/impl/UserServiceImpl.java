package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.constant.CommonStatus;
import com.chaotu.pay.dao.TPermissionMapper;
import com.chaotu.pay.dao.TUserMapper;
import com.chaotu.pay.dao.TUserRoleMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.*;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TUserRoleMapper tUserRoleMapper;
    @Autowired
    private TPermissionMapper tPermissionMapper;
    @Autowired
    WalletService walletService;

    @Override
    public int countUserByRole(UserVo userVo) {
        return tUserMapper.countUserByRole(userVo);
    }


    /***
     * 获取用户的子用户
     * @param parentId
     * @return
     */
    @Override
    public  List<TUser> findByParentId(String parentId) {
       TUser user = new TUser();
       user.setParentId(parentId);
        List<TUser> users = tUserMapper.select(user);
        if(!users.isEmpty()){
            List<String> pIds = new ArrayList<>();
            for (TUser u:users) {
                pIds.add(u.getId());
            }
            return findByParentId(users,pIds);
        }
        return null;
    }

    /**
     * 获取所有子用户的递归方法
     * @param list
     * @param ids
     * @return
     */
    private List<TUser> findByParentId(List<TUser> list,List<String> ids){
        List<TUser> users = tUserMapper.getUserByParents(ids);
        //若查到用户为空  返回list
        if(users==null ||users.isEmpty())
            return list;
        List<String> pIds = new ArrayList<>();
        for (TUser user: users) {
            pIds.add(user.getId());
        }
        list.addAll(users);
        return findByParentId(list,pIds);
    }

    @Override
    public MyPageInfo<UserVo> getUserByRole(PageVo pageVo, UserVo userVo) {


        PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize(), true);
        List<UserVo> userVoList = tUserMapper.getUserByRole(userVo);
        MyPageInfo info = new MyPageInfo(userVoList);
       /* if(!CollectionUtils.isEmpty(userVoList)){
            //info.setTotalElements(countUserByRole(userVo));
            //info.setTotal(pageObj.getTotal());
            info.setPageNum(pageVo.getPageNumber());
        }*/
        return info;

    }

    @Override
    public boolean login(UserVo vo) {
        if (vo == null) {
            throw new BizException(ExceptionCode.USER_INFO_IS_NOT_EXIST);
        }

        if (StringUtils.isEmpty(vo.getUsername())) {
            throw new BizException(ExceptionCode.REQUEST_PARAM_MISSING);
        }

        TUser tUser = null;
        try {
            Example example = new Example(TUser.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", vo.getUsername());
            tUser = tUserMapper.selectOneByExample(example);
            if (tUser == null) {
                return false;
            }
        } catch (Exception e) {
            throw new BizException(ExceptionCode.DATA_AREADY_EXIST);
        }
        /*String salt = tUser.getSalt();
        String oriPassword = tUser.getPassword();
        String password = DigestUtil.encryptMD5(vo.getPassword(), salt);
        if (oriPassword.equals(password)) {
            return true;
        } else {
            throw new BizException(ExceptionCode.PASSORD_ERROR);
        }*/
        return true;
    }

    @Override
    public List<UserVo> getAllUser() {
        List<TUser> tUsers = tUserMapper.selectAll();
        List<UserVo> voList = MyBeanUtils.copyList(tUsers, UserVo.class);
        return voList;
    }

    @Override
    public UserVo getUserByUserName(String username) {
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        try {
            TUser tUser = tUserMapper.selectOneByExample(example);
            if (tUser != null) {
                UserVo userVo = MyBeanUtils.copy(tUser, UserVo.class);
              /*  //关联部门
                List<TDepartment> tDepartments = tUserDepartmentMapper.findByUserId(tUser.getId());
                List<DepartmentVo> departmentVos = MyBeanUtils.copyList(tDepartments, DepartmentVo.class);
                userVo.setDepartments(departmentVos);*/
                //关联角色
                List<TRole> tRoles = tUserRoleMapper.findByUserId(tUser.getId());
                List<RoleVo> roleVos = MyBeanUtils.copyList(tRoles, RoleVo.class);
                userVo.setRoles(roleVos);
                //关联权限菜单
                List<TPermission> tPermissions = tPermissionMapper.findByUserId(tUser.getId());
                List<PermissionVo> permissionVos = MyBeanUtils.copyList(tPermissions, PermissionVo.class);
                userVo.setPermissions(permissionVos);
                return userVo;
            }
            return null;

        } catch (Exception e) {
            log.error("查询用户" + username + "出错", e);
            throw new BizException(ExceptionCode.DATA_AREADY_EXIST);
        }
    }

    @Override
    public UserVo getUserById(String id) {
        TUser tUser = tUserMapper.selectByPrimaryKey(id);
        if (tUser == null) {
            throw new BizException(ExceptionCode.USER_INFO_IS_NOT_EXIST);
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(tUser, vo, "password2");
        return vo;
    }

    @Override
    public MyPageInfo<UserVo> getUserByPage(int pageNow, int pageSize) {
        if (pageNow < 0 || pageSize <= 0) {
            throw new BizException("分页参数不正确");
        }
        PageHelper.startPage(pageNow, pageSize, true);
        Example example = new Example(TUser.class);
        example.createCriteria().andEqualTo("is_del", CommonStatus.NORMAL_FLAG);
        List<TUser> tUsers = tUserMapper.selectByExample(example);
        int count = tUserMapper.selectCountByExample(example);
        List<UserVo> userVoList = MyBeanUtils.copyList(tUsers, UserVo.class);
        MyPageInfo<UserVo> page = new MyPageInfo<>(userVoList);
        page.setTotalElements(count);
        page.setPageNum(pageNow);
        return page;
    }

    @Override
    public MyPageInfo<UserVo> getUserByPage(int pageNow, int pageSize, UserVo vo) {
        TUser tuser = new TUser();
        BeanUtils.copyProperties(vo, tuser);
        PageHelper.startPage(pageNow, pageSize, true);
        tuser.setStatus(CommonStatus.NORMAL_FLAG);
        List<TUser> tUsers = tUserMapper.select(tuser);
        int count = tUserMapper.selectCount(tuser);
        List<UserVo> userVoList = MyBeanUtils.copyList(tUsers, UserVo.class);
        MyPageInfo<UserVo> page = new MyPageInfo<>(userVoList);
        page.setTotalElements(count);
        page.setPageNum(pageNow);
        return page;
    }

    @Override
    public void addUser(UserVo vo) {
        log.info("添加用户开始,入参为[" + vo.toString() + "]");
        TUser tuser = new TUser();
        boolean bool = isExistByUserName(vo.getUsername());
        if (bool) {
            throw new BizException(ExceptionCode.USER_NAME_AREADY_EXIST);
        }

        log.info("验证用户唯一性通过，");
        //这里将密码加密成暗文
        BeanUtils.copyProperties(vo, tuser);
        String uuid32 = IDGeneratorUtils.getUUID32();
        tuser.setTodayAmount(new BigDecimal(0));
        tuser.setTotalAmount(new BigDecimal(0));
        tuser.setId(uuid32);
        tuser.setSignKey(IDGeneratorUtils.getUUID32());
        tuser.setMerchant(IDGeneratorUtils.getFlowNum());
        String encryptPass = new BCryptPasswordEncoder().encode(vo.getPassword());
        tuser.setCreateTime(new Date());
        tuser.setPassword(encryptPass);
        UserVo pVo = currentUser();
        tuser.setCreateBy(pVo.getId());
        tuser.setParentId(pVo.getId());
        tuser.setStatus(0);
        log.info("添加用户中....,参数为[" + tuser.toString() + "]");
        tUserMapper.insertSelective(tuser);

        if(!StringUtils.isEmpty(vo.getRoleIds())){
            //保存用户-角色关系
            String[] roleIds = vo.getRoleIds().split(",");
            for (String roleId : roleIds) {
                TUserRole tUserRole = new TUserRole();
                tUserRole.setId(IDGeneratorUtils.getUUID32());
                tUserRole.setUserId(tuser.getId());
                tUserRole.setRoleId(roleId);
                tUserRoleMapper.insert(tUserRole);
                log.debug("保存用户-角色成功，结果:" + tUserRole);
            }
        }
        TWallet wallet = new TWallet();
        TWallet wallet2 = new TWallet();
        wallet.setUserId(uuid32);
        wallet2.setUserId(uuid32);
        wallet.setType("1");
        wallet2.setType("2");
        Date date = new Date();
        wallet.setCreateTime(date);
        wallet2.setCreateTime(date);
        wallet.setCreateBy(pVo.getId());
        wallet2.setCreateBy(pVo.getId());
        wallet.setResidualAmount(new BigDecimal(0));
        wallet2.setResidualAmount(new BigDecimal(0));
        walletService.add(wallet);
        walletService.add(wallet2);
        log.info("添加用户成功");
    }

    @Override
    public void delUser(String id) {
        log.info("删除用户，输入参数[id=" + id + "]");
        tUserMapper.deleteByPrimaryKey(id);
        Example example = new Example(TUserRole.class);
        example.createCriteria().andEqualTo("userId", id);
        tUserRoleMapper.deleteByExample(example);
        log.info("删除用户成功");
    }

    @Override
    public void editUser(UserVo vo) {
        Example queryExample = new Example(TUser.class);
        if(!StringUtils.isEmpty(vo.getUsername())){
            TUser tUser = tUserMapper.selectByPrimaryKey(vo.getId());
            if(!tUser.getUsername().equals(vo.getUsername())){
                if(!tUser.getUsername().equals(vo.getUsername())){
                    queryExample.createCriteria().andEqualTo("username",vo.getUsername());
                    int i = tUserMapper.selectCountByExample(queryExample);
                    if(i>0){
                        throw new BizException(ExceptionCode.DATA_AREADY_EXIST.getCode(),"用户名已存在");
                    }
                }
            }
        }
        TUser tuser = tUserMapper.selectByPrimaryKey(vo.getId());
        BeanUtils.copyProperties(vo, tuser, "password");
        String roleIds = vo.getRoleIds();
        Example userRoleExample = new Example(TUserRole.class);
        userRoleExample.createCriteria().andEqualTo("userId", vo.getId());
        if (!StringUtils.isEmpty(roleIds)) {
            //清除用户先前的角色，重新添加角色
            tUserRoleMapper.deleteByExample(userRoleExample);
            log.debug("修改用户中，清除用户原有的角色成功");
            String[] split = roleIds.split(",");
            for (String s : split) {
                TUserRole tUserRole = new TUserRole();
                tUserRole.setId(IDGeneratorUtils.getUUID32());
                tUserRole.setUserId(vo.getId());
                tUserRole.setRoleId(s);
                tUserRoleMapper.insertSelective(tUserRole);
            }
            log.debug("修改用户.....重新给用户添加角色成功");
        }else{

        }
        tUserMapper.updateByPrimaryKeySelective(tuser);
    }

    @Override
    public void updatePassord(UserVo userVo) {
        log.info("开始修改用户密码，输入参数:" + userVo);
        TUser tuser = new TUser();
        BeanUtils.copyProperties(userVo, tuser);
        tUserMapper.updateByPrimaryKeySelective(tuser);
        log.info("修改密码成功");
    }

    @Override
    public List<UserVo> findByDepartmentId(String departmentId) {
        return null;
    }

    @Override
    public UserVo currentUser() {
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserVo vo = getUserByUserName(user.getUsername());
           return vo;

        }catch (Exception e){
            throw new BizException(ExceptionCode.TOKEN_ERROR);
        }
    }

    @Override
    public MyPageInfo<UserVo> findByCondition(PageVo pageVo, SearchVo searchVo, UserVo userVo) throws ParseException {
        //去空格处理
        if(!StringUtils.isEmpty(userVo.getUsername())) {
            String username = userVo.getUsername().trim();
            userVo.setUsername(username);
        }

        String email = userVo.getEmail();
        Integer sex = userVo.getSex();
        Integer status = userVo.getStatus();
        //Date createTime = userVo.getCreateTime();
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(searchVo.getStartDate())){
            userVo.setStartDate(searchVo.getStartDate()+" 00:00:00");
        }
        if(!StringUtils.isEmpty(searchVo.getEndDate())){
            userVo.setEndDate(searchVo.getEndDate()+ " 23:59:59");
        }

        PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize(), true);
        List<TUser> tUsers = tUserMapper.findAll(userVo);
        int count = tUserMapper.selectCountByExample(example);
        List<UserVo> userVoList = MyBeanUtils.copyList(tUsers, UserVo.class, "password", "password2");
        Iterator<UserVo> iterator = userVoList.iterator();
        String roleName="";
        while (iterator.hasNext()){
            UserVo vo = iterator.next();
            List<TRole> tRoleList = tUserRoleMapper.findByUserId(vo.getId());
            roleName="";
            if (!CollectionUtils.isEmpty(tRoleList)) {
                for (TRole tRole : tRoleList) {
                    roleName+=tRole.getName()+",";
                }
                List<RoleVo> roleVos = MyBeanUtils.copyList(tRoleList, RoleVo.class);
                vo.setRoles(roleVos);
            }
            if(!StringUtils.isEmpty(userVo.getRoleIds())){
                boolean flag = false;
                for (TRole tRole : tRoleList) {

                    if(tRole.getId().equals(userVo.getRoleIds())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    try {
                        iterator.remove();
                    }catch (Exception e){

                    }
                }
            }
            if(!StringUtils.isEmpty(roleName)){
                vo.setRoleNames(roleName.trim().substring(0,roleName.trim().lastIndexOf(",")));
            }
        }
        MyPageInfo info = new MyPageInfo(userVoList);
        if(!CollectionUtils.isEmpty(userVoList)){
            info.setTotalElements(count);
            info.setPageNum(pageVo.getPageNumber());
        }
        return info;

    }

    /**
     * 判断用户是否存在，
     *
     * @return
     * @Param username 用户名
     */
    private boolean isExistByUserName(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new BizException(ExceptionCode.REQUEST_PARAM_MISSING);
        }
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("username", username);
        int i = tUserMapper.selectCountByExample(example);
        return i > 0;
    }

    @Override
    public UserVo getUser(UserVo userVo) {
        return tUserMapper.getUser(userVo);
    }

    @Override
    public synchronized void updateAmount(BigDecimal userAmount, String id) {
        UserVo account = getUserById(id);
        BigDecimal todayAmount = account.getTodayAmount().add(userAmount);
        BigDecimal totalAmount = account.getTotalAmount().add(userAmount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        TUser user = new TUser();
        BeanUtils.copyProperties(account,user);
        tUserMapper.updateByPrimaryKeySelective(user);
    }
}
