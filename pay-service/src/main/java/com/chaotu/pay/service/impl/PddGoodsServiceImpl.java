package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TPddGoodsMapper;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
//@Service
public class PddGoodsServiceImpl implements PddGoodsService {

    @Autowired
    TPddGoodsMapper mapper;
    @Override
    public void add(TPddGoods goods) {
        mapper.insert(goods);
    }

    @Override
    public List<TPddGoods> getAllGoods() {
        return mapper.selectAll();
    }

    @Override
    public TPddGoods getById(int id) {
        TPddGoods goods = new TPddGoods();
        goods.setId(id);
        return get(goods);
    }

    @Override
    public TPddGoods get(TPddGoods goods) {
        return mapper.selectOne(goods);
    }

    @Override
    public TPddGoods getByAccountIdAndAmount(int accountId, BigDecimal amount) {
        TPddGoods goods = new TPddGoods();
        goods.setPddAccountId(accountId);
        goods.setAmount(amount);
        return get(goods);
    }

    @Override
    public List<TPddGoods> findByCondition(TPddGoods goods) {
        return mapper.select(goods);
    }

    @Override
    public void edit(TPddGoods goods) {
        mapper.updateByPrimaryKeySelective(goods);
    }
}
