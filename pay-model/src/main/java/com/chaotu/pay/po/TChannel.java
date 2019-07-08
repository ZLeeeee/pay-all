package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_channel")
public class TChannel {
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
     * 上游商户号
     */
    private String merchant;

    /**
     * 通道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 通道编码
     */
    @Column(name = "channel_code")
    private String channelCode;

    /**
     * 支付方式
     */
    @Column(name = "pay_type_id")
    private Integer payTypeId;

    /**
     * 状态:0禁用，1启用，2删除
     */
    private String status;

    /**
     * 通道限额：0不限额
     */
    @Column(name = "channel_quota")
    private String channelQuota;

    /**
     * 交易成功金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 扩展字段,存放json
     */
    private String extend;

    /**
     * 今日金额
     */
    @Column(name = "today_amount")
    private BigDecimal todayAmount;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 数据格式/json/text
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 请求方式/get/post
     */
    @Column(name = "request_type")
    private String requestType;

    /**
     * 请求地址
     */
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 回调地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;
    /**
     * 限额
     */
    @Column(name = "limit_amount")
    private BigDecimal limitAmount;

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

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
     * 获取上游商户号
     *
     * @return merchant - 上游商户号
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * 设置上游商户号
     *
     * @param merchant 上游商户号
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
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
     * 获取通道编码
     *
     * @return channel_code - 通道编码
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置通道编码
     *
     * @param channelCode 通道编码
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * 获取支付方式
     *
     * @return pay_type_id - 支付方式
     */
    public Integer getPayTypeId() {
        return payTypeId;
    }

    /**
     * 设置支付方式
     *
     * @param payTypeId 支付方式
     */
    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    /**
     * 获取状态:0禁用，1启用，2删除
     *
     * @return status - 状态:0禁用，1启用，2删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态:0禁用，1启用，2删除
     *
     * @param status 状态:0禁用，1启用，2删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取通道限额：0不限额
     *
     * @return channel_quota - 通道限额：0不限额
     */
    public String getChannelQuota() {
        return channelQuota;
    }

    /**
     * 设置通道限额：0不限额
     *
     * @param channelQuota 通道限额：0不限额
     */
    public void setChannelQuota(String channelQuota) {
        this.channelQuota = channelQuota;
    }

    /**
     * 获取交易成功金额
     *
     * @return total_amount - 交易成功金额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置交易成功金额
     *
     * @param totalAmount 交易成功金额
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取扩展字段,存放json
     *
     * @return extend - 扩展字段,存放json
     */
    public String getExtend() {
        return extend;
    }

    /**
     * 设置扩展字段,存放json
     *
     * @param extend 扩展字段,存放json
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * 获取今日金额
     *
     * @return today_amount - 今日金额
     */
    public BigDecimal getTodayAmount() {
        return todayAmount;
    }

    /**
     * 设置今日金额
     *
     * @param todayAmount 今日金额
     */
    public void setTodayAmount(BigDecimal todayAmount) {
        this.todayAmount = todayAmount;
    }

    /**
     * 获取费率
     *
     * @return rate - 费率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置费率
     *
     * @param rate 费率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 获取数据格式/json/text
     *
     * @return content_type - 数据格式/json/text
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置数据格式/json/text
     *
     * @param contentType 数据格式/json/text
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取请求方式/get/post
     *
     * @return request_type - 请求方式/get/post
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * 设置请求方式/get/post
     *
     * @param requestType 请求方式/get/post
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * 获取请求地址
     *
     * @return request_url - 请求地址
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 设置请求地址
     *
     * @param requestUrl 请求地址
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * 获取回调地址
     *
     * @return notify_url - 回调地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置回调地址
     *
     * @param notifyUrl 回调地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}