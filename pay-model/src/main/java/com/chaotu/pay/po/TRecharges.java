package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_recharges")
public class TRecharges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "udpate_time")
    private Date udpateTime;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 充值金额
     */
    @Column(name = "recharge_amount")
    private BigDecimal rechargeAmount;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 实付金额
     */
    @Column(name = "actual_amount")
    private BigDecimal actualAmount;

    /**
     * 系统单号
     */
    @Column(name = "orderNo")
    private String orderno;

    /**
     * 固码备注
     */
    @Column(name = "orderMk")
    private String ordermk;

    /**
     * 订单状态：0未支付，1已支付，3异常
     */
    @Column(name = "pay_status")
    private Byte payStatus;

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
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return udpate_time
     */
    public Date getUdpateTime() {
        return udpateTime;
    }

    /**
     * @param udpateTime
     */
    public void setUdpateTime(Date udpateTime) {
        this.udpateTime = udpateTime;
    }

    /**
     * 获取商户id
     *
     * @return user_id - 商户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置商户id
     *
     * @param userId 商户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取充值金额
     *
     * @return recharge_amount - 充值金额
     */
    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    /**
     * 设置充值金额
     *
     * @param rechargeAmount 充值金额
     */
    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    /**
     * 获取商户号
     *
     * @return merchant - 商户号
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * 设置商户号
     *
     * @param merchant 商户号
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * 获取实付金额
     *
     * @return actual_amount - 实付金额
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 设置实付金额
     *
     * @param actualAmount 实付金额
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 获取系统单号
     *
     * @return orderNo - 系统单号
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * 设置系统单号
     *
     * @param orderno 系统单号
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    /**
     * 获取固码备注
     *
     * @return orderMk - 固码备注
     */
    public String getOrdermk() {
        return ordermk;
    }

    /**
     * 设置固码备注
     *
     * @param ordermk 固码备注
     */
    public void setOrdermk(String ordermk) {
        this.ordermk = ordermk;
    }

    /**
     * 获取订单状态：0未支付，1已支付，3异常
     *
     * @return pay_status - 订单状态：0未支付，1已支付，3异常
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置订单状态：0未支付，1已支付，3异常
     *
     * @param payStatus 订单状态：0未支付，1已支付，3异常
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }
}