package com.chaotu.pay.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_order")
public class TOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 通道id
     */
    @Column(name = "channel_id")
    private Long channelId;

    /**
     * 支付方式id
     */
    @Column(name = "pay_type_id")
    private Integer payTypeId;

    /**
     * 账号(提供二维码)
     */
    private String account;

    /**
     * 系统订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 下游订单号(商户订单号)
     */
    @Column(name = "under_order_no")
    private String underOrderNo;

    /**
     * 上游订单号
     */
    @Column(name = "upper_order_no")
    private String upperOrderNo;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单手续费
     */
    @Column(name = "order_rate")
    private BigDecimal orderRate;

    /**
     * 系统收入
     */
    @Column(name = "sys_amount")
    private BigDecimal sysAmount;

    /**
     * 用户收入
     */
    @Column(name = "user_amount")
    private BigDecimal userAmount;

    /**
     * 商户异步通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 通道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 支付方式名称
     */
    @Column(name = "pay_type_name")
    private String payTypeName;

    /**
     * 扩展字段,存储json
     */
    private String extend;

    /**
     * 订单状态：0未支付，1支付成功，2支付异常
     */
    private Byte status;

    /**
     * 是否为历史0否1是
     */
    @Column(name = "is_history")
    private Integer isHistory;

    @Column(name = "is_notify")
    private Integer isNotify;

    public Integer getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Integer isNotify) {
        this.isNotify = isNotify;
    }

    /**
     * 通道收入
     */
    @Column(name = "channel_amount")
    private BigDecimal channelAmount;

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
     * 获取通道id
     *
     * @return channel_id - 通道id
     */
    public Long getChannelId() {
        return channelId;
    }

    /**
     * 设置通道id
     *
     * @param channelId 通道id
     */
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取支付方式id
     *
     * @return pay_type_id - 支付方式id
     */
    public Integer getPayTypeId() {
        return payTypeId;
    }

    /**
     * 设置支付方式id
     *
     * @param payTypeId 支付方式id
     */
    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    /**
     * 获取账号(提供二维码)
     *
     * @return account - 账号(提供二维码)
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号(提供二维码)
     *
     * @param account 账号(提供二维码)
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取系统订单号
     *
     * @return order_no - 系统订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置系统订单号
     *
     * @param orderNo 系统订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取下游订单号(商户订单号)
     *
     * @return under_order_no - 下游订单号(商户订单号)
     */
    public String getUnderOrderNo() {
        return underOrderNo;
    }

    /**
     * 设置下游订单号(商户订单号)
     *
     * @param underOrderNo 下游订单号(商户订单号)
     */
    public void setUnderOrderNo(String underOrderNo) {
        this.underOrderNo = underOrderNo;
    }

    /**
     * 获取上游订单号
     *
     * @return upper_order_no - 上游订单号
     */
    public String getUpperOrderNo() {
        return upperOrderNo;
    }

    /**
     * 设置上游订单号
     *
     * @param upperOrderNo 上游订单号
     */
    public void setUpperOrderNo(String upperOrderNo) {
        this.upperOrderNo = upperOrderNo;
    }

    /**
     * 获取订单金额
     *
     * @return amount - 订单金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置订单金额
     *
     * @param amount 订单金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取订单手续费
     *
     * @return order_rate - 订单手续费
     */
    public BigDecimal getOrderRate() {
        return orderRate;
    }

    /**
     * 设置订单手续费
     *
     * @param orderRate 订单手续费
     */
    public void setOrderRate(BigDecimal orderRate) {
        this.orderRate = orderRate;
    }

    /**
     * 获取系统收入
     *
     * @return sys_amount - 系统收入
     */
    public BigDecimal getSysAmount() {
        return sysAmount;
    }

    /**
     * 设置系统收入
     *
     * @param sysAmount 系统收入
     */
    public void setSysAmount(BigDecimal sysAmount) {
        this.sysAmount = sysAmount;
    }

    /**
     * 获取用户收入
     *
     * @return user_amount - 用户收入
     */
    public BigDecimal getUserAmount() {
        return userAmount;
    }

    /**
     * 设置用户收入
     *
     * @param userAmount 用户收入
     */
    public void setUserAmount(BigDecimal userAmount) {
        this.userAmount = userAmount;
    }

    /**
     * 获取商户异步通知地址
     *
     * @return notify_url - 商户异步通知地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置商户异步通知地址
     *
     * @param notifyUrl 商户异步通知地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取通道名称
     *
     * @return channel_name - 通道名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置通道名称
     *
     * @param channelName 通道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取支付方式名称
     *
     * @return pay_type_name - 支付方式名称
     */
    public String getPayTypeName() {
        return payTypeName;
    }

    /**
     * 设置支付方式名称
     *
     * @param payTypeName 支付方式名称
     */
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    /**
     * 获取扩展字段,存储json
     *
     * @return extend - 扩展字段,存储json
     */
    public String getExtend() {
        return extend;
    }

    /**
     * 设置扩展字段,存储json
     *
     * @param extend 扩展字段,存储json
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * 获取订单状态：0未支付，1支付成功，2支付异常
     *
     * @return status - 订单状态：0未支付，1支付成功，2支付异常
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态：0未支付，1支付成功，2支付异常
     *
     * @param status 订单状态：0未支付，1支付成功，2支付异常
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取是否为历史0否1是
     *
     * @return is_history - 是否为历史0否1是
     */
    public Integer getIsHistory() {
        return isHistory;
    }

    /**
     * 设置是否为历史0否1是
     *
     * @param isHistory 是否为历史0否1是
     */
    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }

    /**
     * 获取通道收入
     *
     * @return channel_amount - 通道收入
     */
    public BigDecimal getChannelAmount() {
        return channelAmount;
    }

    /**
     * 设置通道收入
     *
     * @param channelAmount 通道收入
     */
    public void setChannelAmount(BigDecimal channelAmount) {
        this.channelAmount = channelAmount;
    }


}