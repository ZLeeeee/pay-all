package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TYzGoodsMapper;
import com.chaotu.pay.po.TYzGoods;
import com.chaotu.pay.service.YzGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class YzGoodsServiceImpl implements YzGoodsService {
    @Autowired
    TYzGoodsMapper goodsMapper;
    @Override
    public void insert(TYzGoods tYzGoods) {
        goodsMapper.insertSelective(tYzGoods);
    }

    @Override
    public TYzGoods selectOne(TYzGoods tYzGoods) {
        return goodsMapper.selectOne(tYzGoods);
    }

    @Override
    public List<TYzGoods> findAll() {
        return goodsMapper.selectAll();
    }

    @Override
    public void delete(TYzGoods tYzGoods) {
        goodsMapper.deleteByPrimaryKey(tYzGoods);
    }

    @Override
    public void update(TYzGoods tYzGoods) {
        goodsMapper.updateByPrimaryKeySelective(tYzGoods);
    }
}
