package com.chaotu.pay.po;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "t_pdd_goods")
public class TPddGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品规格id
     */
    @Column(name = "sku_id")
    private Long skuId;

    /**
     * 商品规格id
     */
    @Column(name = "pdd_account_id")
    private int pddAccountId;

    public int getPddAccountId() {
        return pddAccountId;
    }

    public void setPddAccountId(int pddAccountId) {
        this.pddAccountId = pddAccountId;
    }

    /**
     * 拼多多商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 拼团id
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 价格
     */
    private BigDecimal amount;

    /**
     * 库存
     */
    private Integer stock;

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
    public Long getSkuId() {
        return skuId;
    }

    /**
     * 设置商品规格id
     *
     * @param skuId 商品规格id
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * 获取拼多多商品id
     *
     * @return goods_id - 拼多多商品id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置拼多多商品id
     *
     * @param goodsId 拼多多商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取拼团id
     *
     * @return group_id - 拼团id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置拼团id
     *
     * @param groupId 拼团id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
}