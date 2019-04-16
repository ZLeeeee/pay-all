package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_phones")
public class TAccountPhones {
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
     * 第三方挂号，0-用户 1-第三方
     */
    private String third;

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
     * 单日限额
     */
    @Column(name = "dayQuota")
    private BigDecimal dayquota;

    /**
     * 手机标识
     */
    @Column(name = "phone_id")
    private String phoneId;

    /**
     * 支付宝userid
     */
    private String alipayuserid;

    /**
     * 支付宝实名
     */
    private String alipayusername;

    /**
     * 收款账号
     */
    private String account;

    /**
     * 收款任意金额码
     */
    private String qrcode;

    /**
     * 交易成功金额
     */
    @Column(name = "tradeAmount")
    private BigDecimal tradeamount;

    /**
     * 状态：0禁用。1启用
     */
    private String status;

    /**
     * 密钥与手机通讯时使用
     */
    @Column(name = "signKey")
    private String signkey;

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
     * 获取第三方挂号，0-用户 1-第三方
     *
     * @return third - 第三方挂号，0-用户 1-第三方
     */
    public String getThird() {
        return third;
    }

    /**
     * 设置第三方挂号，0-用户 1-第三方
     *
     * @param third 第三方挂号，0-用户 1-第三方
     */
    public void setThird(String third) {
        this.third = third;
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
     * 获取单日限额
     *
     * @return dayQuota - 单日限额
     */
    public BigDecimal getDayquota() {
        return dayquota;
    }

    /**
     * 设置单日限额
     *
     * @param dayquota 单日限额
     */
    public void setDayquota(BigDecimal dayquota) {
        this.dayquota = dayquota;
    }

    /**
     * 获取手机标识
     *
     * @return phone_id - 手机标识
     */
    public String getPhoneId() {
        return phoneId;
    }

    /**
     * 设置手机标识
     *
     * @param phoneId 手机标识
     */
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    /**
     * 获取支付宝userid
     *
     * @return alipayuserid - 支付宝userid
     */
    public String getAlipayuserid() {
        return alipayuserid;
    }

    /**
     * 设置支付宝userid
     *
     * @param alipayuserid 支付宝userid
     */
    public void setAlipayuserid(String alipayuserid) {
        this.alipayuserid = alipayuserid;
    }

    /**
     * 获取支付宝实名
     *
     * @return alipayusername - 支付宝实名
     */
    public String getAlipayusername() {
        return alipayusername;
    }

    /**
     * 设置支付宝实名
     *
     * @param alipayusername 支付宝实名
     */
    public void setAlipayusername(String alipayusername) {
        this.alipayusername = alipayusername;
    }

    /**
     * 获取收款账号
     *
     * @return account - 收款账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置收款账号
     *
     * @param account 收款账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取收款任意金额码
     *
     * @return qrcode - 收款任意金额码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置收款任意金额码
     *
     * @param qrcode 收款任意金额码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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
     * 获取密钥与手机通讯时使用
     *
     * @return signKey - 密钥与手机通讯时使用
     */
    public String getSignkey() {
        return signkey;
    }

    /**
     * 设置密钥与手机通讯时使用
     *
     * @param signkey 密钥与手机通讯时使用
     */
    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }
}