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

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "del_flag")
    private String delFlag;

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
     * 归属代理id
     */
    @Column(name = "agent_id")
    private String agentId;


    /**
     * 通道id
     */
    @Column(name = "channel_id")
    private Long channelId;

    /**
     * 支付方式id
     */
    @Column(name = "channel_payment_id")
    private Long channelPaymentId;

    /**
     * 账号(提供二维码)
     */
    private String account;

    /**
     * 设备号
     */
    @Column(name = "phone_id")
    private String phoneId;

    /**
     * 设备归属商户
     */
    @Column(name = "phone_uid")
    private Long phoneUid;

    /**
     * 固码备注
     */
    @Column(name = "orderMk")
    private String ordermk;

    /**
     * 系统订单号
     */
    @Column(name = "orderNo")
    private String orderno;

    /**
     * 下游订单号(商户订单号)
     */
    @Column(name = "underOrderNo")
    private String underorderno;

    /**
     * 上游订单号
     */
    @Column(name = "onOrderNo")
    private String onorderno;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单手续费
     */
    @Column(name = "orderRate")
    private BigDecimal orderrate;

    /**
     * 系统收入
     */
    @Column(name = "sysAmount")
    private BigDecimal sysamount;

    /**
     * 代理收入
     */
    @Column(name = "agentAmount")
    private BigDecimal agentamount;

    /**
     * 用户收入
     */
    @Column(name = "userAmount")
    private BigDecimal useramount;

    /**
     * 商户异步通知地址
     */
    @Column(name = "notifyUrl")
    private String notifyurl;

    /**
     * 商户同步通知地址
     */
    @Column(name = "successUrl")
    private String successurl;

    /**
     * 通道名称
     */
    @Column(name = "channelName")
    private String channelname;

    /**
     * 支付方式名称
     */
    @Column(name = "paymentName")
    private String paymentname;

    /**
     * 扩展字段,存储json
     */
    private String extend;

    /**
     * 订单状态：0未支付，1支付成功，2支付异常
     */
    private Byte status;

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
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return del_flag
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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
     * 获取归属代理id
     *
     * @return agent_id - 归属代理id
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * 设置归属代理id
     *
     * @param agentId 归属代理id
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
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
     * 获取设备号
     *
     * @return phone_id - 设备号
     */
    public String getPhoneId() {
        return phoneId;
    }

    /**
     * 设置设备号
     *
     * @param phoneId 设备号
     */
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    /**
     * 获取设备归属商户
     *
     * @return phone_uid - 设备归属商户
     */
    public Long getPhoneUid() {
        return phoneUid;
    }

    /**
     * 设置设备归属商户
     *
     * @param phoneUid 设备归属商户
     */
    public void setPhoneUid(Long phoneUid) {
        this.phoneUid = phoneUid;
    }

    /**
     * 获取固码备注
     *
     * @return orderMk - 固码备注
     */
    public String getOrdermk() {
        return ordermk;
    }

    /**
     * 设置固码备注
     *
     * @param ordermk 固码备注
     */
    public void setOrdermk(String ordermk) {
        this.ordermk = ordermk;
    }

    /**
     * 获取系统订单号
     *
     * @return orderNo - 系统订单号
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * 设置系统订单号
     *
     * @param orderno 系统订单号
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    /**
     * 获取下游订单号(商户订单号)
     *
     * @return underOrderNo - 下游订单号(商户订单号)
     */
    public String getUnderorderno() {
        return underorderno;
    }

    /**
     * 设置下游订单号(商户订单号)
     *
     * @param underorderno 下游订单号(商户订单号)
     */
    public void setUnderorderno(String underorderno) {
        this.underorderno = underorderno;
    }

    /**
     * 获取上游订单号
     *
     * @return onOrderNo - 上游订单号
     */
    public String getOnorderno() {
        return onorderno;
    }

    /**
     * 设置上游订单号
     *
     * @param onorderno 上游订单号
     */
    public void setOnorderno(String onorderno) {
        this.onorderno = onorderno;
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
     * @return orderRate - 订单手续费
     */
    public BigDecimal getOrderrate() {
        return orderrate;
    }

    /**
     * 设置订单手续费
     *
     * @param orderrate 订单手续费
     */
    public void setOrderrate(BigDecimal orderrate) {
        this.orderrate = orderrate;
    }

    /**
     * 获取系统收入
     *
     * @return sysAmount - 系统收入
     */
    public BigDecimal getSysamount() {
        return sysamount;
    }

    /**
     * 设置系统收入
     *
     * @param sysamount 系统收入
     */
    public void setSysamount(BigDecimal sysamount) {
        this.sysamount = sysamount;
    }

    /**
     * 获取代理收入
     *
     * @return agentAmount - 代理收入
     */
    public BigDecimal getAgentamount() {
        return agentamount;
    }

    /**
     * 设置代理收入
     *
     * @param agentamount 代理收入
     */
    public void setAgentamount(BigDecimal agentamount) {
        this.agentamount = agentamount;
    }

    /**
     * 获取用户收入
     *
     * @return userAmount - 用户收入
     */
    public BigDecimal getUseramount() {
        return useramount;
    }

    /**
     * 设置用户收入
     *
     * @param useramount 用户收入
     */
    public void setUseramount(BigDecimal useramount) {
        this.useramount = useramount;
    }

    /**
     * 获取商户异步通知地址
     *
     * @return notifyUrl - 商户异步通知地址
     */
    public String getNotifyurl() {
        return notifyurl;
    }

    /**
     * 设置商户异步通知地址
     *
     * @param notifyurl 商户异步通知地址
     */
    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    /**
     * 获取商户同步通知地址
     *
     * @return successUrl - 商户同步通知地址
     */
    public String getSuccessurl() {
        return successurl;
    }

    /**
     * 设置商户同步通知地址
     *
     * @param successurl 商户同步通知地址
     */
    public void setSuccessurl(String successurl) {
        this.successurl = successurl;
    }

    /**
     * 获取通道名称
     *
     * @return channelName - 通道名称
     */
    public String getChannelname() {
        return channelname;
    }

    /**
     * 设置通道名称
     *
     * @param channelname 通道名称
     */
    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    /**
     * 获取支付方式名称
     *
     * @return paymentName - 支付方式名称
     */
    public String getPaymentname() {
        return paymentname;
    }

    /**
     * 设置支付方式名称
     *
     * @param paymentname 支付方式名称
     */
    public void setPaymentname(String paymentname) {
        this.paymentname = paymentname;
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
}