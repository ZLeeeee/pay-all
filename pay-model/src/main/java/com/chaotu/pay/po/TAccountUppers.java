package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_uppers")
public class TAccountUppers {
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
     * 账号(签约后生成的设备id)
     */
    private String account;

    /**
     * 加密key
     */
    private String signkey;

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
     * 状态：0禁用。1启用
     */
    private String status;

    /**
     * 订单量
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 限额
     */
    @Column(name = "dayQuota")
    private BigDecimal dayquota;

    /**
     * 私钥
     */
    private String privatekey;

    /**
     * 公钥
     */
    private String publikey;

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
     * 获取账号(签约后生成的设备id)
     *
     * @return account - 账号(签约后生成的设备id)
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号(签约后生成的设备id)
     *
     * @param account 账号(签约后生成的设备id)
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取加密key
     *
     * @return signkey - 加密key
     */
    public String getSignkey() {
        return signkey;
    }

    /**
     * 设置加密key
     *
     * @param signkey 加密key
     */
    public void setSignkey(String signkey) {
        this.signkey = signkey;
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
     * 获取订单量
     *
     * @return order_num - 订单量
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单量
     *
     * @param orderNum 订单量
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取限额
     *
     * @return dayQuota - 限额
     */
    public BigDecimal getDayquota() {
        return dayquota;
    }

    /**
     * 设置限额
     *
     * @param dayquota 限额
     */
    public void setDayquota(BigDecimal dayquota) {
        this.dayquota = dayquota;
    }

    /**
     * 获取私钥
     *
     * @return privatekey - 私钥
     */
    public String getPrivatekey() {
        return privatekey;
    }

    /**
     * 设置私钥
     *
     * @param privatekey 私钥
     */
    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    /**
     * 获取公钥
     *
     * @return publikey - 公钥
     */
    public String getPublikey() {
        return publikey;
    }

    /**
     * 设置公钥
     *
     * @param publikey 公钥
     */
    public void setPublikey(String publikey) {
        this.publikey = publikey;
    }
}