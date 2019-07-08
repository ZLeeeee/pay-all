package com.chaotu.pay.service;

import com.chaotu.pay.po.TUser;
import com.chaotu.pay.vo.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @Description:用户管理
 * @Author: yaochenglong
 * @Date: 16:55 2018/10/21
 */
public interface UserService {

    /**
     * 用户登录,返回token
     * @param vo
     * @return
     */
    boolean login(UserVo vo);

    /**
     * 获取所有用户数据
     * @return
     */
    List<UserVo> getAllUser();



    /**
     * 通过userName查找用户
     * @param username
     * @return
     */
    UserVo getUserByUserName(String username);

    /**
     * 通过主键id查询用户
     * @param id
     * @return
     */
    UserVo getUserById(String id);
    /**
     * @Description: 已分页方式获取用户
     * @param pageNow : 当前页
     * @param pageSize: 每页显示都条数
     * @Return: 返回带用户数据带分页对象
     * @Author: yaochenglong
     * @Date: Created in 16:57 2018/10/21
     */

    MyPageInfo<UserVo> getUserByPage(int pageNow, int pageSize);

    /**
     * @Description:
     * @param pageNow
     * @param pageSize
     * @param vo 额外都查询条件
     * @return
     */
    MyPageInfo<UserVo> getUserByPage(int pageNow, int pageSize, UserVo vo);

    /**
     * @Description:添加用户
     * @param vo
     */
    void addUser(UserVo vo);

    /**
     * 删除用户
     * @param id 用户唯一id
     */
    void delUser(String id);


    /**
     * 修改用户
     * @param vo
     */
    void editUser(UserVo vo);

    /**
     * 修改密码
     * @param userVo
     */
    void updatePassord(UserVo userVo);

    ////////////////////////////////////////
    List<UserVo> findByDepartmentId(String departmentId);


    MyPageInfo<UserVo> findByCondition(PageVo pageVo, SearchVo searchVo, UserVo userVo) throws ParseException;

    /***
     * 根据role分页查询角色
     * @param pageVo
     * @param userVo
     * @return
     */
    MyPageInfo<UserVo> getUserByRole(PageVo pageVo,UserVo userVo);

    /**
     * 根据role查询行数
     * @param userVo
     * @return
     */
    int countUserByRole(UserVo userVo);

    /***
     * 获取所有下级代理/商户
     */

    List<TUser> findByParentId(String parentId);

    UserVo currentUser();

    UserVo getUser(UserVo userVo);

    void updateAmount(BigDecimal userAmount, String id);
}
