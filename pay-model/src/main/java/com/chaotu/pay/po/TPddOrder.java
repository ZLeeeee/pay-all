package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_pdd_order")
public class TPddOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 拼多多订单号
     */
    @Column(name = "order_sn")
    private String orderSn;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 订单状态(0未支付,1待发货/已支付,2已发货/未签收,3已签收/已到账)
     */
    private Byte status;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商品数量
     */
    @Column(name = "sku_number")
    private Integer skuNumber;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 拼多多用户id
     */
    @Column(name = "pdd_user_id")
    private Integer pddUserId;

    /**
     * 用户自定义Json
     */
    private String extension;

    @Column(name = "access_token")
    private String accessToken;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取拼多多订单号
     *
     * @return order_sn - 拼多多订单号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置拼多多订单号
     *
     * @param orderSn 拼多多订单号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取订单状态(0未支付,1待发货/已支付,2已发货/未签收,3已签收/已到账)
     *
     * @return status - 订单状态(0未支付,1待发货/已支付,2已发货/未签收,3已签收/已到账)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态(0未支付,1待发货/已支付,2已发货/未签收,3已签收/已到账)
     *
     * @param status 订单状态(0未支付,1待发货/已支付,2已发货/未签收,3已签收/已到账)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取商户id
     *
     * @return user_id - 商户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置商户id
     *
     * @param userId 商户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取商品数量
     *
     * @return sku_number - 商品数量
     */
    public Integer getSkuNumber() {
        return skuNumber;
    }

    /**
     * 设置商品数量
     *
     * @param skuNumber 商品数量
     */
    public void setSkuNumber(Integer skuNumber) {
        this.skuNumber = skuNumber;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取拼多多用户id
     *
     * @return pdd_user_id - 拼多多用户id
     */
    public Integer getPddUserId() {
        return pddUserId;
    }

    /**
     * 设置拼多多用户id
     *
     * @param pddUserId 拼多多用户id
     */
    public void setPddUserId(Integer pddUserId) {
        this.pddUserId = pddUserId;
    }

    /**
     * 获取用户自定义Json
     *
     * @return extension - 用户自定义Json
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置用户自定义Json
     *
     * @param extension 用户自定义Json
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return access_token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}