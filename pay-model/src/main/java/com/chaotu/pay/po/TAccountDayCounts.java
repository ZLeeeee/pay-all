package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_day_counts")
public class TAccountDayCounts {
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
     * 账号
     */
    private String account;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 账号单日成功交易金额
     */
    @Column(name = "account_amount")
    private BigDecimal accountAmount;

    /**
     * 账号单日订单量
     */
    @Column(name = "account_order_count")
    private Integer accountOrderCount;

    /**
     * 账号单日成功量
     */
    @Column(name = "account_order_suc_count")
    private Integer accountOrderSucCount;

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
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
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
     * 获取账号单日成功交易金额
     *
     * @return account_amount - 账号单日成功交易金额
     */
    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    /**
     * 设置账号单日成功交易金额
     *
     * @param accountAmount 账号单日成功交易金额
     */
    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    /**
     * 获取账号单日订单量
     *
     * @return account_order_count - 账号单日订单量
     */
    public Integer getAccountOrderCount() {
        return accountOrderCount;
    }

    /**
     * 设置账号单日订单量
     *
     * @param accountOrderCount 账号单日订单量
     */
    public void setAccountOrderCount(Integer accountOrderCount) {
        this.accountOrderCount = accountOrderCount;
    }

    /**
     * 获取账号单日成功量
     *
     * @return account_order_suc_count - 账号单日成功量
     */
    public Integer getAccountOrderSucCount() {
        return accountOrderSucCount;
    }

    /**
     * 设置账号单日成功量
     *
     * @param accountOrderSucCount 账号单日成功量
     */
    public void setAccountOrderSucCount(Integer accountOrderSucCount) {
        this.accountOrderSucCount = accountOrderSucCount;
    }
}