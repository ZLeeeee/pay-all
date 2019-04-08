package com.chaotu.pay.config.security;

import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.vo.BizException;
import com.chaotu.pay.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserVo user = userService.getUserByUserName(username);
        if(user==null){
            throw new BizException(ExceptionCode.LOGIN_INFO_IS_NOT_EXIST);
        }
        return new SecurityUserDetails(user);
    }
}
