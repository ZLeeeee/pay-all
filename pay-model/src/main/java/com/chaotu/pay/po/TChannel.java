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
     * 上游密钥
     */
    private String signkey;

    /**
     * 通道名称
     */
    @Column(name = "channelName")
    private String channelname;

    /**
     * 通道编码
     */
    @Column(name = "channelCode")
    private String channelcode;

    /**
     * 防封域名
     */
    @Column(name = "refererDomain")
    private String refererdomain;

    /**
     * 状态:0禁用，1启用，2删除
     */
    private String status;

    /**
     * 通道限额：0不限额
     */
    @Column(name = "channelQuota")
    private Integer channelquota;

    /**
     * 交易成功金额
     */
    @Column(name = "tradeAmount")
    private BigDecimal tradeamount;

    /**
     * 扩展字段,存放json
     */
    private String extend;

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
     * 获取上游密钥
     *
     * @return signkey - 上游密钥
     */
    public String getSignkey() {
        return signkey;
    }

    /**
     * 设置上游密钥
     *
     * @param signkey 上游密钥
     */
    public void setSignkey(String signkey) {
        this.signkey = signkey;
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
     * 获取通道编码
     *
     * @return channelCode - 通道编码
     */
    public String getChannelcode() {
        return channelcode;
    }

    /**
     * 设置通道编码
     *
     * @param channelcode 通道编码
     */
    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    /**
     * 获取防封域名
     *
     * @return refererDomain - 防封域名
     */
    public String getRefererdomain() {
        return refererdomain;
    }

    /**
     * 设置防封域名
     *
     * @param refererdomain 防封域名
     */
    public void setRefererdomain(String refererdomain) {
        this.refererdomain = refererdomain;
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
     * @return channelQuota - 通道限额：0不限额
     */
    public Integer getChannelquota() {
        return channelquota;
    }

    /**
     * 设置通道限额：0不限额
     *
     * @param channelquota 通道限额：0不限额
     */
    public void setChannelquota(Integer channelquota) {
        this.channelquota = channelquota;
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
}