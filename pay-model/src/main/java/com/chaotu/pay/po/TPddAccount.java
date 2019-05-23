package com.chaotu.pay.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_pdd_account")
public class TPddAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号名
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 限额
     */
    @Column(name = "limit_amount")
    private BigDecimal limitAmount;

    /**
     * 登录后的cooki
     */
    private String cookie;

    /**
     * 退款地址id
     */
    @Column(name = "return_address_id")
    private String returnAddressId;

    /**
     * 账号是否可用(0可用1禁用)
     */
    private Boolean status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 扩展字段
     */
    private String extent;

    /**
     * 备注
     */
    private String mark;

    /**
     * 今日额度
     */
    @Column(name = "today_amount")
    private BigDecimal todayAmount;

    /**
     * 累计收益
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

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
     * 获取账号名
     *
     * @return account_name - 账号名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号名
     *
     * @param accountName 账号名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取限额
     *
     * @return limitAmount - 限额
     */
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    /**
     * 设置限额
     *
     * @param limitAmount 限额
     */
    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    /**
     * 获取登录后的cooki
     *
     * @return cookie - 登录后的cooki
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * 设置登录后的cooki
     *
     * @param cookie 登录后的cooki
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取退款地址id
     *
     * @return return_address_id - 退款地址id
     */
    public String getReturnAddressId() {
        return returnAddressId;
    }

    /**
     * 设置退款地址id
     *
     * @param returnAddressId 退款地址id
     */
    public void setReturnAddressId(String returnAddressId) {
        this.returnAddressId = returnAddressId;
    }

    /**
     * 获取账号是否可用(0可用1禁用)
     *
     * @return status - 账号是否可用(0可用1禁用)
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置账号是否可用(0可用1禁用)
     *
     * @param status 账号是否可用(0可用1禁用)
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取扩展字段
     *
     * @return extent - 扩展字段
     */
    public String getExtent() {
        return extent;
    }

    /**
     * 设置扩展字段
     *
     * @param extent 扩展字段
     */
    public void setExtent(String extent) {
        this.extent = extent;
    }

    /**
     * 获取备注
     *
     * @return mark - 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置备注
     *
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取今日额度
     *
     * @return today_amount - 今日额度
     */
    public BigDecimal getTodayAmount() {
        return todayAmount;
    }

    /**
     * 设置今日额度
     *
     * @param todayAmount 今日额度
     */
    public void setTodayAmount(BigDecimal todayAmount) {
        this.todayAmount = todayAmount;
    }

    /**
     * 获取累计收益
     *
     * @return total_amount - 累计收益
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置累计收益
     *
     * @param totalAmount 累计收益
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}