package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TUserRatesMapper;
import com.chaotu.pay.po.TUser;
import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.service.UserRatesService;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.vo.MyPageInfo;

import com.chaotu.pay.vo.UserRatesVo;
import com.chaotu.pay.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Slf4j
@Service
public class UserRatesServiceImpl implements UserRatesService {
    @Autowired
    TUserRatesMapper userRatesMapper;

    @Autowired
    UserService userService;
    @Override
    public TUserRates getUserRatesById(TUserRates vo) {

        return userRatesMapper.selectOne(vo);
    }

    @Override
    public MyPageInfo<UserVo> getUserRatesByPage(int pageNow, int pageSize, TUserRates vo) {
        return null;
    }

    @Override
    public void addUserRates(TUserRates vo) {
    userRatesMapper.insert(vo);
    }

    @Override
    public void delUserRates(TUserRates vo) {
    userRatesMapper.updateByPrimaryKey(vo);
    }

    @Override
    public void editUserRatesByUserId(TUserRates userRates, String userId) {
        List<TUser> users = userService.findByParentId(userId);
        userRatesMapper.updateByUserId(users,userRates);
    }



    @Override
    public void editUserRates(TUserRates vo) {
        userRatesMapper.updateByPrimaryKey(vo);
    }

    @Override
    public List<UserRatesVo> getUserRatesByUser(UserVo userVo) {
        return userRatesMapper.getUserRatesByUser(userVo);
    }

    @Override
    public int countUserRatesByAgent(UserVo userVo) {
        return 0;
    }

    @Override
    public List<UserRatesVo> getAllUserRates() {
        return null;
    }
}
