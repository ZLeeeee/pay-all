package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_order_day_count")
public class TOrderDayCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 代理id
     */
    @Column(name = "agent_id")
    private Long agentId;

    /**
     * 商户单日订单成功金额
     */
    @Column(name = "merchant_amount")
    private BigDecimal merchantAmount;

    /**
     * 商户单日实际收入
     */
    @Column(name = "merchant_income")
    private BigDecimal merchantIncome;

    /**
     * 商户单日订单量
     */
    @Column(name = "merchant_order_count")
    private Integer merchantOrderCount;

    /**
     * 商户单日成功订单量
     */
    @Column(name = "merchant_order_suc_count")
    private Integer merchantOrderSucCount;

    /**
     * 平台单日订单成功金额
     */
    @Column(name = "sys_amount")
    private BigDecimal sysAmount;

    /**
     * 平台单日收入金额
     */
    @Column(name = "sys_income")
    private BigDecimal sysIncome;

    /**
     * 平台单日订单总数量
     */
    @Column(name = "sys_order_count")
    private Integer sysOrderCount;

    /**
     * 平台单日成功订单数量
     */
    @Column(name = "sys_order_suc_count")
    private Integer sysOrderSucCount;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取代理id
     *
     * @return agent_id - 代理id
     */
    public Long getAgentId() {
        return agentId;
    }

    /**
     * 设置代理id
     *
     * @param agentId 代理id
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    /**
     * 获取商户单日订单成功金额
     *
     * @return merchant_amount - 商户单日订单成功金额
     */
    public BigDecimal getMerchantAmount() {
        return merchantAmount;
    }

    /**
     * 设置商户单日订单成功金额
     *
     * @param merchantAmount 商户单日订单成功金额
     */
    public void setMerchantAmount(BigDecimal merchantAmount) {
        this.merchantAmount = merchantAmount;
    }

    /**
     * 获取商户单日实际收入
     *
     * @return merchant_income - 商户单日实际收入
     */
    public BigDecimal getMerchantIncome() {
        return merchantIncome;
    }

    /**
     * 设置商户单日实际收入
     *
     * @param merchantIncome 商户单日实际收入
     */
    public void setMerchantIncome(BigDecimal merchantIncome) {
        this.merchantIncome = merchantIncome;
    }

    /**
     * 获取商户单日订单量
     *
     * @return merchant_order_count - 商户单日订单量
     */
    public Integer getMerchantOrderCount() {
        return merchantOrderCount;
    }

    /**
     * 设置商户单日订单量
     *
     * @param merchantOrderCount 商户单日订单量
     */
    public void setMerchantOrderCount(Integer merchantOrderCount) {
        this.merchantOrderCount = merchantOrderCount;
    }

    /**
     * 获取商户单日成功订单量
     *
     * @return merchant_order_suc_count - 商户单日成功订单量
     */
    public Integer getMerchantOrderSucCount() {
        return merchantOrderSucCount;
    }

    /**
     * 设置商户单日成功订单量
     *
     * @param merchantOrderSucCount 商户单日成功订单量
     */
    public void setMerchantOrderSucCount(Integer merchantOrderSucCount) {
        this.merchantOrderSucCount = merchantOrderSucCount;
    }

    /**
     * 获取平台单日订单成功金额
     *
     * @return sys_amount - 平台单日订单成功金额
     */
    public BigDecimal getSysAmount() {
        return sysAmount;
    }

    /**
     * 设置平台单日订单成功金额
     *
     * @param sysAmount 平台单日订单成功金额
     */
    public void setSysAmount(BigDecimal sysAmount) {
        this.sysAmount = sysAmount;
    }

    /**
     * 获取平台单日收入金额
     *
     * @return sys_income - 平台单日收入金额
     */
    public BigDecimal getSysIncome() {
        return sysIncome;
    }

    /**
     * 设置平台单日收入金额
     *
     * @param sysIncome 平台单日收入金额
     */
    public void setSysIncome(BigDecimal sysIncome) {
        this.sysIncome = sysIncome;
    }

    /**
     * 获取平台单日订单总数量
     *
     * @return sys_order_count - 平台单日订单总数量
     */
    public Integer getSysOrderCount() {
        return sysOrderCount;
    }

    /**
     * 设置平台单日订单总数量
     *
     * @param sysOrderCount 平台单日订单总数量
     */
    public void setSysOrderCount(Integer sysOrderCount) {
        this.sysOrderCount = sysOrderCount;
    }

    /**
     * 获取平台单日成功订单数量
     *
     * @return sys_order_suc_count - 平台单日成功订单数量
     */
    public Integer getSysOrderSucCount() {
        return sysOrderSucCount;
    }

    /**
     * 设置平台单日成功订单数量
     *
     * @param sysOrderSucCount 平台单日成功订单数量
     */
    public void setSysOrderSucCount(Integer sysOrderSucCount) {
        this.sysOrderSucCount = sysOrderSucCount;
    }
}