package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_channel_payments")
public class TChannelPayments {
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
     * 通道id
     */
    @Column(name = "channel_id")
    private Long channelId;

    /**
     * 支付名称
     */
    @Column(name = "paymentName")
    private String paymentname;

    /**
     * 支付编码
     */
    @Column(name = "paymentCode")
    private String paymentcode;

    /**
     * 支付方式log
     */
    private String ico;

    /**
     * 运营费率
     */
    @Column(name = "runRate")
    private BigDecimal runrate;

    /**
     * 成本费率
     */
    @Column(name = "costRate")
    private BigDecimal costrate;

    /**
     * 单笔最小金额
     */
    @Column(name = "minAmount")
    private Integer minamount;

    /**
     * 单笔最大金额
     */
    @Column(name = "maxAmount")
    private Integer maxamount;

    /**
     * 状态：0关闭，1启用
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
     * 获取支付名称
     *
     * @return paymentName - 支付名称
     */
    public String getPaymentname() {
        return paymentname;
    }

    /**
     * 设置支付名称
     *
     * @param paymentname 支付名称
     */
    public void setPaymentname(String paymentname) {
        this.paymentname = paymentname;
    }

    /**
     * 获取支付编码
     *
     * @return paymentCode - 支付编码
     */
    public String getPaymentcode() {
        return paymentcode;
    }

    /**
     * 设置支付编码
     *
     * @param paymentcode 支付编码
     */
    public void setPaymentcode(String paymentcode) {
        this.paymentcode = paymentcode;
    }

    /**
     * 获取支付方式log
     *
     * @return ico - 支付方式log
     */
    public String getIco() {
        return ico;
    }

    /**
     * 设置支付方式log
     *
     * @param ico 支付方式log
     */
    public void setIco(String ico) {
        this.ico = ico;
    }

    /**
     * 获取运营费率
     *
     * @return runRate - 运营费率
     */
    public BigDecimal getRunrate() {
        return runrate;
    }

    /**
     * 设置运营费率
     *
     * @param runrate 运营费率
     */
    public void setRunrate(BigDecimal runrate) {
        this.runrate = runrate;
    }

    /**
     * 获取成本费率
     *
     * @return costRate - 成本费率
     */
    public BigDecimal getCostrate() {
        return costrate;
    }

    /**
     * 设置成本费率
     *
     * @param costrate 成本费率
     */
    public void setCostrate(BigDecimal costrate) {
        this.costrate = costrate;
    }

    /**
     * 获取单笔最小金额
     *
     * @return minAmount - 单笔最小金额
     */
    public Integer getMinamount() {
        return minamount;
    }

    /**
     * 设置单笔最小金额
     *
     * @param minamount 单笔最小金额
     */
    public void setMinamount(Integer minamount) {
        this.minamount = minamount;
    }

    /**
     * 获取单笔最大金额
     *
     * @return maxAmount - 单笔最大金额
     */
    public Integer getMaxamount() {
        return maxamount;
    }

    /**
     * 设置单笔最大金额
     *
     * @param maxamount 单笔最大金额
     */
    public void setMaxamount(Integer maxamount) {
        this.maxamount = maxamount;
    }

    /**
     * 获取状态：0关闭，1启用
     *
     * @return status - 状态：0关闭，1启用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：0关闭，1启用
     *
     * @param status 状态：0关闭，1启用
     */
    public void setStatus(String status) {
        this.status = status;
    }
}