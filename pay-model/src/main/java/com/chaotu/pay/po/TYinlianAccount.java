package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_yinlian_account")
public class TYinlianAccount {
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
     * 账号(签约后生成的设备id)
     */
    private String account;

    /**
     * 私钥
     */
    @Column(name = "sign_key")
    private String signKey;

    /**
     * 状态：0禁用。1启用
     */
    private String status;

    /**
     * 今日收入
     */
    @Column(name = "today_amount")
    private BigDecimal todayAmount;

    /**
     * 收入总额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单量
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 限额
     */
    @Column(name = "limit_amount")
    private BigDecimal limitAmount;

    /**
     * 密码
     */
    private String password;

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
     * 获取账号(签约后生成的设备id)
     *
     * @return account - 账号(签约后生成的设备id)
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号(签约后生成的设备id)
     *
     * @param account 账号(签约后生成的设备id)
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取私钥
     *
     * @return sign_key - 私钥
     */
    public String getSignKey() {
        return signKey;
    }

    /**
     * 设置私钥
     *
     * @param signKey 私钥
     */
    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    /**
     * 获取状态：0禁用。1启用
     *
     * @return status - 状态：0禁用。1启用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：0禁用。1启用
     *
     * @param status 状态：0禁用。1启用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取今日收入
     *
     * @return today_amount - 今日收入
     */
    public BigDecimal getTodayAmount() {
        return todayAmount;
    }

    /**
     * 设置今日收入
     *
     * @param todayAmount 今日收入
     */
    public void setTodayAmount(BigDecimal todayAmount) {
        this.todayAmount = todayAmount;
    }

    /**
     * 获取收入总额
     *
     * @return total_amount - 收入总额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置收入总额
     *
     * @param totalAmount 收入总额
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取订单量
     *
     * @return order_num - 订单量
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单量
     *
     * @param orderNum 订单量
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取限额
     *
     * @return limit_amount - 限额
     */
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    /**
     * 设置限额
     *
     * @param limitAmount 限额
     */
    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}