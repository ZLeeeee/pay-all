package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.dao.TYzUserMapper;
import com.chaotu.pay.po.TYzAccount;
import com.chaotu.pay.po.TYzUser;
import com.chaotu.pay.service.YzUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Slf4j
@Service
public class YzUserServiceImpl implements YzUserService {
    @Autowired
    TYzUserMapper userMapper;
    @Autowired
    @Qualifier("yzUserChoser")
    Choser<TYzUser> yzUserChoser;
    @Override
    public List<TYzUser> findByStatus() {
        Example example = new Example(TYzUser.class);
        example.createCriteria().andEqualTo("status",1);
        List<TYzUser> tYzAccounts = userMapper.selectByExample(example);
        return tYzAccounts;
    }

    @Override
    public void insert(TYzUser tYzUser) {
        userMapper.insertSelective(tYzUser);
        yzUserChoser.update(findByStatus());
    }

    @Override
    public TYzUser selectOne(TYzUser tYzUser) {
        return userMapper.selectOne(tYzUser);
    }

    @Override
    public List<TYzUser> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public void delete(TYzUser tYzUser) {
        userMapper.deleteByPrimaryKey(tYzUser);
        yzUserChoser.update(findByStatus());
    }

    @Override
    public void update(TYzUser tYzUser) {
        userMapper.updateByPrimaryKeySelective(tYzUser);
        yzUserChoser.update(findByStatus());
    }
}
