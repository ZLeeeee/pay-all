package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.dao.TPddUserMapper;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.service.PddUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Slf4j
@Service
public class PddUserServiceImpl implements PddUserService {
    @Autowired
    @Qualifier("pddUserChoser")
    Choser<TPddUser> pddUserChoser;
    @Autowired
    TPddUserMapper mapper;
    @Override
    public List<TPddUser> getAll() {
        return mapper.selectAll();
    }

    @Override
    public void insert(TPddUser user) {
        mapper.insert(user);
        pddUserChoser.update(getAllByStatus(true));
    }

    @Override
    public void update(TPddUser user) {
        mapper.updateByPrimaryKey(user);
        pddUserChoser.update(getAllByStatus(true));
    }

    @Override
    public void delete(TPddUser user) {
        mapper.delete(user);
        pddUserChoser.update(getAllByStatus(true));
    }

    @Override
    public TPddUser selectOne(TPddUser user) {
        return mapper.selectOne(user);
    }

    @Override
    public List<TPddUser> getAllByStatus(Boolean status) {
        Example example = new Example(TPddUser.class);
        example.createCriteria().andEqualTo("status",status);
        return mapper.selectByExample(example);
    }
}
