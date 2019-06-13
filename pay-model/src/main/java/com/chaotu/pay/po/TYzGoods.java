package com.chaotu.pay.po;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "t_yz_goods")
public class TYzGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品规格id
     */
    @Column(name = "sku_id")
    private Integer skuId;

    /**
     * 拼多多商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 价格
     */
    private BigDecimal amount;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 拼多多账号id
     */
    @Column(name = "yz_account_id")
    private Integer yzAccountId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品规格id
     *
     * @return sku_id - 商品规格id
     */
    public Integer getSkuId() {
        return skuId;
    }

    /**
     * 设置商品规格id
     *
     * @param skuId 商品规格id
     */
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    /**
     * 获取拼多多商品id
     *
     * @return goods_id - 拼多多商品id
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置拼多多商品id
     *
     * @param goodsId 拼多多商品id
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取价格
     *
     * @return amount - 价格
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置价格
     *
     * @param amount 价格
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取拼多多账号id
     *
     * @return yz_account_id - 拼多多账号id
     */
    public Integer getYzAccountId() {
        return yzAccountId;
    }

    /**
     * 设置拼多多账号id
     *
     * @param yzAccountId 拼多多账号id
     */
    public void setYzAccountId(Integer yzAccountId) {
        this.yzAccountId = yzAccountId;
    }
}