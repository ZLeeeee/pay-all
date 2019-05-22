package com.chaotu.pay.service;


import com.chaotu.pay.po.TPddGoods;

import java.math.BigDecimal;
import java.util.List;

public interface PddGoodsService {
    /***
     * 增加商品
     * @param goods
     */
    void add(TPddGoods goods);

    /***
     * 获取所有商品
     * @return
     */
    List<TPddGoods> getAllGoods();

    /***
     * 根据id获取商品信息
     * @param id
     * @return
     */
    TPddGoods getById(int id);

    /***
     * 获取单个商品
     * @param goods
     * @return
     */
    TPddGoods get(TPddGoods goods);

    TPddGoods getByAccountIdAndAmount(int accountId, BigDecimal amount);

    List<TPddGoods> findByCondition(TPddGoods goods);

    void edit(TPddGoods goods);
}
