package com.chaotu.pay.service;

import com.chaotu.pay.po.TYzGoods;

import java.util.List;

public interface YzGoodsService extends CRUDService<TYzGoods> {
    List<TYzGoods> findByCondition(TYzGoods goods);
}
