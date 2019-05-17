package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.qo.UserQo;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;



    /**
     * 多条件分页获取用户列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllUser(@RequestBody UserQo userQo){
        PageVo pageVo = userQo.getPageVo();
        SearchVo searchVo = userQo.getSearchVo();
        UserVo userVo = userQo.getUserVo();
        MyPageInfo<UserVo> pageInfo = null;
        try {
            pageInfo = userService.findByCondition(pageVo,searchVo,userVo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }

    /**
     *获取当前登录用户接口
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Message getUserInfo(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.getUserByUserName(user.getUsername());
        // 清除持久上下文环境 避免后面语句导致持久化
        //entityManager.clear();
        userVo.setPassword(null);
        return ResponseUtil.responseBody(userVo);
    }

    /**
     * 用户登录
     * @param vo
     * @return
     */
    @PostMapping("/login")
    public Message login(UserVo vo) {
        if(StringUtils.isEmpty(vo.getUsername())||StringUtils.isEmpty(vo.getPassword())){
            throw new BizException(ExceptionCode.USER_NAME_IS_NOT_NULL);
        }
        if(StringUtils.isEmpty(vo.getPassword())){
            throw new BizException(ExceptionCode.PASSWORD_IS_NOT_NULL);
        }
        boolean bool = userService.login(vo);
        //String token = jwtTokenUtil.generateToken(userDetails);
        //log.info("用户登录token:"+token);
        return ResponseUtil.responseBody("登录成功");
    }


    /**
     * 用户登出
     * 使用jwt所以，此处需要前端做处理。
     * @param vo
     * @return
     */
    public Message logout(UserVo vo){
        return ResponseUtil.responseBody("登出成功");
    }

    /**
     * 修改密码
     *
     * @param map
     * map 参数包含  id   password  newPass 三个参数
     * @return
     */
    @PostMapping("/editPassword")
    public Message editPassword(@RequestBody ModelMap map) {
        String id = (String) map.get("id");
        String password = (String) map.get("password");
        String newPassword = (String) map.get("newPass");

        if(StringUtils.isEmpty(id)|| StringUtils.isEmpty(password)|| StringUtils.isEmpty(newPassword)){
            throw new BizException(ExceptionCode.CONFIRM_PASSWORD_NOT_MATCH);
        }
        UserVo userVo = userService.getUserById(id);
        if(!new BCryptPasswordEncoder().matches(password,userVo.getPassword())){
            throw new BizException(ExceptionCode.OLD_PASSWORD_INCORRECT);
        }
        String newEncryptPass= new BCryptPasswordEncoder().encode(newPassword);
        userVo.setPassword(newEncryptPass);
        userService.updatePassord(userVo);
        return ResponseUtil.responseBody("修改成功");
    }

    /**
     * 这里不存在注册用户，所有的账户需要管理员去添加，去分配
     * 后台管理员添加用户，
     * @param vo
     * @return
     */
    @PostMapping("/add")
    public Message add(@RequestBody UserVo vo){
        if(vo==null){
            throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR);
        }

//        //用户名：英文+数字
//        boolean b = checkInput(vo.getUsername());
//        if(!b){
//            throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR.getCode(),"用户名只能包含数字＋英文");
//        }
       // vo.setMerchant(vo.getMerchant());
        userService.addUser(vo);
        return ResponseUtil.responseBody("添加成功");
    }

    private boolean checkInput(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = pattern.matcher(input);
        if( !m.matches() ){ //匹配不到,說明輸入的不符合條件
            return false;
        }
        return true;
    }


    /**
     * 通过id逻辑删除用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    public Message delAllByIds(@PathVariable String[] ids){
        for(String id:ids){
            if("682265633886208".equals(id)){
                throw new BizException(ExceptionCode.CANNOT_DELETE);
            }
            userService.delUser(id);
        }
        return ResponseUtil.responseBody("删除用户成功");
    }

    @PostMapping("/edit")
    public Message editUser(@RequestBody UserVo userVo){
        userService.editUser(userVo);
        //删除缓存
        stringRedisTemplate.delete("user::"+userVo.getUsername());
        //手动删除缓存
        stringRedisTemplate.delete("userRole::"+userVo.getId());

        return ResponseUtil.responseBody("修改用户成功");
    }

    /**
     * 多条件分页根据角色获取用户列表
     * @return
     */
    @PostMapping("/all/byRole")
    public Message getAllByRole(@RequestBody UserQo userQo){

        MyPageInfo<UserVo> pageInfo = null;

        pageInfo = userService.getUserByRole(userQo.getPageVo(),userQo.getUserVo());

        return ResponseUtil.responseBody(pageInfo);
    }

    @PostMapping("/all/byAgent")
    public Message getAllByAgent(@RequestBody UserQo userQo){

        MyPageInfo<UserVo> pageInfo = null;
        UserVo userVo = userQo.getUserVo();
        userVo.setParentId(userService.currentUser().getId());
        pageInfo = userService.getUserByRole(userQo.getPageVo(),userVo);

        return ResponseUtil.responseBody(pageInfo);
    }
    /**
     * 修改密码
     *
     * @param map
     * map 参数包含  id   password  newPass 三个参数
     * @return
     */
    @PostMapping("/editPayPassword")
    public Message editPayPassword(@RequestBody ModelMap map) {
        String id = (String) map.get("id");
        String password = (String) map.get("payPassword");
        String newPassword = (String) map.get("newPass");

        if(StringUtils.isEmpty(id)|| StringUtils.isEmpty(password)|| StringUtils.isEmpty(newPassword)){
            throw new BizException(ExceptionCode.CONFIRM_PASSWORD_NOT_MATCH);
        }
        UserVo userVo = userService.getUserById(id);
        if(!new BCryptPasswordEncoder().matches(password,userVo.getPayPassword())){
            throw new BizException(ExceptionCode.OLD_PASSWORD_INCORRECT);
        }
        String newEncryptPass= new BCryptPasswordEncoder().encode(newPassword);
        userVo.setPayPassword(newEncryptPass);
        userService.updatePassord(userVo);
        return ResponseUtil.responseBody("修改成功");
    }
}
