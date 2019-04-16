package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_clouds")
public class TAccountClouds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 支付方式id
     */
    @Column(name = "channel_payment_id")
    private Long channelPaymentId;

    /**
     * 账户类型：支付宝、微信等等
     */
    @Column(name = "accountType")
    private String accounttype;

    /**
     * 账号
     */
    private String account;

    /**
     * 云端地址
     */
    @Column(name = "cloudAddress")
    private String cloudaddress;

    /**
     * 单日限额: 0不限额
     */
    @Column(name = "dayQuota")
    private BigDecimal dayquota;

    /**
     * 交易成功金额
     */
    @Column(name = "tradeAmount")
    private BigDecimal tradeamount;

    /**
     * 任意金额码内容
     */
    private String content;

    /**
     * 状态：0禁用。1启用
     */
    private String status;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取支付方式id
     *
     * @return channel_payment_id - 支付方式id
     */
    public Long getChannelPaymentId() {
        return channelPaymentId;
    }

    /**
     * 设置支付方式id
     *
     * @param channelPaymentId 支付方式id
     */
    public void setChannelPaymentId(Long channelPaymentId) {
        this.channelPaymentId = channelPaymentId;
    }

    /**
     * 获取账户类型：支付宝、微信等等
     *
     * @return accountType - 账户类型：支付宝、微信等等
     */
    public String getAccounttype() {
        return accounttype;
    }

    /**
     * 设置账户类型：支付宝、微信等等
     *
     * @param accounttype 账户类型：支付宝、微信等等
     */
    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
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
     * 获取云端地址
     *
     * @return cloudAddress - 云端地址
     */
    public String getCloudaddress() {
        return cloudaddress;
    }

    /**
     * 设置云端地址
     *
     * @param cloudaddress 云端地址
     */
    public void setCloudaddress(String cloudaddress) {
        this.cloudaddress = cloudaddress;
    }

    /**
     * 获取单日限额: 0不限额
     *
     * @return dayQuota - 单日限额: 0不限额
     */
    public BigDecimal getDayquota() {
        return dayquota;
    }

    /**
     * 设置单日限额: 0不限额
     *
     * @param dayquota 单日限额: 0不限额
     */
    public void setDayquota(BigDecimal dayquota) {
        this.dayquota = dayquota;
    }

    /**
     * 获取交易成功金额
     *
     * @return tradeAmount - 交易成功金额
     */
    public BigDecimal getTradeamount() {
        return tradeamount;
    }

    /**
     * 设置交易成功金额
     *
     * @param tradeamount 交易成功金额
     */
    public void setTradeamount(BigDecimal tradeamount) {
        this.tradeamount = tradeamount;
    }

    /**
     * 获取任意金额码内容
     *
     * @return content - 任意金额码内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置任意金额码内容
     *
     * @param content 任意金额码内容
     */
    public void setContent(String content) {
        this.content = content;
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
}