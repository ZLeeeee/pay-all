package com.chaotu.pay.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_bank_cards")
public class TBankCards {
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
     * 银行id
     */
    @Column(name = "bank_id")
    private Long bankId;

    /**
     * 银行卡号
     */
    @Column(name = "bankCardNo")
    private String bankcardno;

    /**
     * 开户名
     */
    @Column(name = "accountName")
    private String accountname;

    /**
     * 支行名
     */
    @Column(name = "branchName")
    private String branchname;

    /**
     * 状态：0禁用，1启用，只能1张卡为1
     */
    private String status;

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
     * 获取银行id
     *
     * @return bank_id - 银行id
     */
    public Long getBankId() {
        return bankId;
    }

    /**
     * 设置银行id
     *
     * @param bankId 银行id
     */
    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    /**
     * 获取银行卡号
     *
     * @return bankCardNo - 银行卡号
     */
    public String getBankcardno() {
        return bankcardno;
    }

    /**
     * 设置银行卡号
     *
     * @param bankcardno 银行卡号
     */
    public void setBankcardno(String bankcardno) {
        this.bankcardno = bankcardno;
    }

    /**
     * 获取开户名
     *
     * @return accountName - 开户名
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * 设置开户名
     *
     * @param accountname 开户名
     */
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    /**
     * 获取支行名
     *
     * @return branchName - 支行名
     */
    public String getBranchname() {
        return branchname;
    }

    /**
     * 设置支行名
     *
     * @param branchname 支行名
     */
    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    /**
     * 获取状态：0禁用，1启用，只能1张卡为1
     *
     * @return status - 状态：0禁用，1启用，只能1张卡为1
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：0禁用，1启用，只能1张卡为1
     *
     * @param status 状态：0禁用，1启用，只能1张卡为1
     */
    public void setStatus(String status) {
        this.status = status;
    }
}