package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_bank_cards")
public class TAccountBankCards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 单日限额: 0不限额
     */
    @Column(name = "dayQuota")
    private BigDecimal dayquota;

    /**
     * 手机标识
     */
    @Column(name = "phone_id")
    private String phoneId;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 银行缩写
     */
    @Column(name = "bank_mark")
    private String bankMark;

    /**
     * 持卡人
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 银行卡账号
     */
    @Column(name = "cardNo")
    private String cardno;

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
     * 有效标识：0-无效；1-有效；默认1
     */
    private String valid;

    /**
     * 密钥与手机通讯时使用
     */
    @Column(name = "signKey")
    private String signkey;

    /**
     * 银行卡在支付宝里的id
     */
    @Column(name = "chard_index")
    private String chardIndex;

    /**
     * 银行任意金额码链接
     */
    private String qrcode;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
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
     * 获取银行名称
     *
     * @return bank_name - 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取银行缩写
     *
     * @return bank_mark - 银行缩写
     */
    public String getBankMark() {
        return bankMark;
    }

    /**
     * 设置银行缩写
     *
     * @param bankMark 银行缩写
     */
    public void setBankMark(String bankMark) {
        this.bankMark = bankMark;
    }

    /**
     * 获取持卡人
     *
     * @return bank_account - 持卡人
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置持卡人
     *
     * @param bankAccount 持卡人
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取银行卡账号
     *
     * @return cardNo - 银行卡账号
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * 设置银行卡账号
     *
     * @param cardno 银行卡账号
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
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
     * 获取有效标识：0-无效；1-有效；默认1
     *
     * @return valid - 有效标识：0-无效；1-有效；默认1
     */
    public String getValid() {
        return valid;
    }

    /**
     * 设置有效标识：0-无效；1-有效；默认1
     *
     * @param valid 有效标识：0-无效；1-有效；默认1
     */
    public void setValid(String valid) {
        this.valid = valid;
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

    /**
     * 获取银行卡在支付宝里的id
     *
     * @return chard_index - 银行卡在支付宝里的id
     */
    public String getChardIndex() {
        return chardIndex;
    }

    /**
     * 设置银行卡在支付宝里的id
     *
     * @param chardIndex 银行卡在支付宝里的id
     */
    public void setChardIndex(String chardIndex) {
        this.chardIndex = chardIndex;
    }

    /**
     * 获取银行任意金额码链接
     *
     * @return qrcode - 银行任意金额码链接
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置银行任意金额码链接
     *
     * @param qrcode 银行任意金额码链接
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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