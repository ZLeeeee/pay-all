package com.chaotu.pay.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_bank")
public class TBank {
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
     * 银行编码
     */
    private String code;

    /**
     * 银行名称
     */
    @Column(name = "bankName")
    private String bankname;

    /**
     * 银行Logo
     */
    private String ico;

    /**
     * 状态：0禁用，1启用
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
     * 获取银行编码
     *
     * @return code - 银行编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置银行编码
     *
     * @param code 银行编码
     */
    public void setCode(String code) {
        this.code = code;
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
     * 获取银行Logo
     *
     * @return ico - 银行Logo
     */
    public String getIco() {
        return ico;
    }

    /**
     * 设置银行Logo
     *
     * @param ico 银行Logo
     */
    public void setIco(String ico) {
        this.ico = ico;
    }

    /**
     * 获取状态：0禁用，1启用
     *
     * @return status - 状态：0禁用，1启用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：0禁用，1启用
     *
     * @param status 状态：0禁用，1启用
     */
    public void setStatus(String status) {
        this.status = status;
    }
}