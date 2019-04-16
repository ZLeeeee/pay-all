package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_cloud_solids")
public class TAccountCloudSolids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 云端固码配置id
     */
    @Column(name = "account_cloud_id")
    private Long accountCloudId;

    /**
     * 账号
     */
    private String account;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 账户类型：支付宝、微信等等
     */
    @Column(name = "accountType")
    private String accounttype;

    /**
     * 固码内容
     */
    private String content;

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
     * 获取云端固码配置id
     *
     * @return account_cloud_id - 云端固码配置id
     */
    public Long getAccountCloudId() {
        return accountCloudId;
    }

    /**
     * 设置云端固码配置id
     *
     * @param accountCloudId 云端固码配置id
     */
    public void setAccountCloudId(Long accountCloudId) {
        this.accountCloudId = accountCloudId;
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
     * 获取固码内容
     *
     * @return content - 固码内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置固码内容
     *
     * @param content 固码内容
     */
    public void setContent(String content) {
        this.content = content;
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