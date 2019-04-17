package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.qo.UserQo;
import com.chaotu.pay.service.UserService;
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
@RequestMapping("/agent")
@CacheConfig(cacheNames = "agent")
@Transactional
public class AgentController {

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
    public Message getAllAgent(@RequestBody UserQo userQo){

        MyPageInfo<UserVo> pageInfo = null;

        pageInfo = userService.getUserByRole(userQo.getPageVo(),userQo.getUserVo());

        return ResponseUtil.responseBody(pageInfo);
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
        vo.setMerchant(vo.getMerchant());
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
}
