package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TYzUserAddressMapper;
import com.chaotu.pay.po.TYzUserAddress;
import com.chaotu.pay.service.YzUserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class YzUserAddressServiceImpl implements YzUserAddressService {
    @Autowired
    TYzUserAddressMapper userAddressMapper;
    @Override
    public void insert(TYzUserAddress tYzUserAddress) {
        userAddressMapper.insertSelective(tYzUserAddress);
    }

    @Override
    public TYzUserAddress selectOne(TYzUserAddress tYzUserAddress) {
        return userAddressMapper.selectOne(tYzUserAddress);
    }

    @Override
    public List<TYzUserAddress> findAll() {
        return userAddressMapper.selectAll();
    }

    @Override
    public void delete(TYzUserAddress tYzUserAddress) {
        userAddressMapper.deleteByPrimaryKey(tYzUserAddress);
    }

    @Override
    public void update(TYzUserAddress tYzUserAddress) {
        userAddressMapper.updateByPrimaryKeySelective(tYzUserAddress);
    }
}
