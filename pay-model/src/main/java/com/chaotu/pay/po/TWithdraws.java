package com.chaotu.pay.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_withdraws")
public class TWithdraws {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_by")
    private String updateBy;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商户所属代理id
     */
    @Column(name = "agent_id")
    private Integer agentId;

    /**
     * 银行名称
     */
    @Column(name = "bankName")
    private String bankname;

    /**
     * 提现金额
     */
    @Column(name = "withdrawAmount")
    private BigDecimal withdrawamount;

    /**
     * 提现手续费
     */
    @Column(name = "withdrawRate")
    private BigDecimal withdrawrate;

    /**
     * 到账金额
     */
    @Column(name = "toAmount")
    private BigDecimal toamount;

    /**
     * 银行开户名
     */
    @Column(name = "accountName")
    private String accountname;

    /**
     * 银行卡号
     */
    @Column(name = "bankCardNo")
    private String bankcardno;

    /**
     * 银行支行名
     */
    @Column(name = "branchName")
    private String branchname;

    /**
     * 银行编码
     */
    @Column(name = "bankCode")
    private String bankcode;

    /**
     * 结算单流水号
     */
    @Column(name = "orderId")
    private String orderid;

    /**
     * 商户侧提交结算单号
     */
    @Column(name = "outOrderId")
    private String outorderid;

    /**
     * 上游结算单号
     */
    @Column(name = "upOrderId")
    private String uporderid;

    /**
     * 结算通道编码
     */
    @Column(name = "channelCode")
    private String channelcode;

    /**
     * 结算类型:1普通结算 2代付结算
     */
    private Byte type;

    /**
     * 结算备注信息
     */
    private String comment;

    /**
     * 体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     */
    private Byte status;

    /**
     * 扩展银行卡信息,json格式
     */
    private String extend;

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
     * 获取商户所属代理id
     *
     * @return agent_id - 商户所属代理id
     */
    public Integer getAgentId() {
        return agentId;
    }

    /**
     * 设置商户所属代理id
     *
     * @param agentId 商户所属代理id
     */
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    /**
     * 获取银行名称
     *
     * @return bankName - 银行名称
     */
    public String getBankname() {
        return bankname;
    }

    /**
     * 设置银行名称
     *
     * @param bankname 银行名称
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    /**
     * 获取提现金额
     *
     * @return withdrawAmount - 提现金额
     */
    public BigDecimal getWithdrawamount() {
        return withdrawamount;
    }

    /**
     * 设置提现金额
     *
     * @param withdrawamount 提现金额
     */
    public void setWithdrawamount(BigDecimal withdrawamount) {
        this.withdrawamount = withdrawamount;
    }

    /**
     * 获取提现手续费
     *
     * @return withdrawRate - 提现手续费
     */
    public BigDecimal getWithdrawrate() {
        return withdrawrate;
    }

    /**
     * 设置提现手续费
     *
     * @param withdrawrate 提现手续费
     */
    public void setWithdrawrate(BigDecimal withdrawrate) {
        this.withdrawrate = withdrawrate;
    }

    /**
     * 获取到账金额
     *
     * @return toAmount - 到账金额
     */
    public BigDecimal getToamount() {
        return toamount;
    }

    /**
     * 设置到账金额
     *
     * @param toamount 到账金额
     */
    public void setToamount(BigDecimal toamount) {
        this.toamount = toamount;
    }

    /**
     * 获取银行开户名
     *
     * @return accountName - 银行开户名
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * 设置银行开户名
     *
     * @param accountname 银行开户名
     */
    public void setAccountname(String accountname) {
        this.accountname = accountname;
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
     * 获取银行支行名
     *
     * @return branchName - 银行支行名
     */
    public String getBranchname() {
        return branchname;
    }

    /**
     * 设置银行支行名
     *
     * @param branchname 银行支行名
     */
    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    /**
     * 获取银行编码
     *
     * @return bankCode - 银行编码
     */
    public String getBankcode() {
        return bankcode;
    }

    /**
     * 设置银行编码
     *
     * @param bankcode 银行编码
     */
    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    /**
     * 获取结算单流水号
     *
     * @return orderId - 结算单流水号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置结算单流水号
     *
     * @param orderid 结算单流水号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取商户侧提交结算单号
     *
     * @return outOrderId - 商户侧提交结算单号
     */
    public String getOutorderid() {
        return outorderid;
    }

    /**
     * 设置商户侧提交结算单号
     *
     * @param outorderid 商户侧提交结算单号
     */
    public void setOutorderid(String outorderid) {
        this.outorderid = outorderid;
    }

    /**
     * 获取上游结算单号
     *
     * @return upOrderId - 上游结算单号
     */
    public String getUporderid() {
        return uporderid;
    }

    /**
     * 设置上游结算单号
     *
     * @param uporderid 上游结算单号
     */
    public void setUporderid(String uporderid) {
        this.uporderid = uporderid;
    }

    /**
     * 获取结算通道编码
     *
     * @return channelCode - 结算通道编码
     */
    public String getChannelcode() {
        return channelcode;
    }

    /**
     * 设置结算通道编码
     *
     * @param channelcode 结算通道编码
     */
    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    /**
     * 获取结算类型:1普通结算 2代付结算
     *
     * @return type - 结算类型:1普通结算 2代付结算
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置结算类型:1普通结算 2代付结算
     *
     * @param type 结算类型:1普通结算 2代付结算
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取结算备注信息
     *
     * @return comment - 结算备注信息
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置结算备注信息
     *
     * @param comment 结算备注信息
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     *
     * @return status - 体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     *
     * @param status 体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取扩展银行卡信息,json格式
     *
     * @return extend - 扩展银行卡信息,json格式
     */
    public String getExtend() {
        return extend;
    }

    /**
     * 设置扩展银行卡信息,json格式
     *
     * @param extend 扩展银行卡信息,json格式
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }
}