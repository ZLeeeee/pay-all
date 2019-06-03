package com.chaotu.pay.po;

import javax.persistence.*;

@Table(name = "t_pdd_user")
public class TPddUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 拼多多用户id
     */
    private String pdduid;

    /**
     * 收货地址id
     */
    @Column(name = "address_id")
    private Long addressId;

    /**
     * 用户状态(1可用,0不可用)
     */
    private Boolean status;

    /**
     * 拼多多的accessToken
     */
    @Column(name = "accessToken")
    private String accesstoken;

    /**
     * 扩展字段(json)
     */
    private String extar;

    /**
     * 备注
     */
    private String mark;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
     * 获取拼多多用户id
     *
     * @return pdduid - 拼多多用户id
     */
    public String getPdduid() {
        return pdduid;
    }

    /**
     * 设置拼多多用户id
     *
     * @param pdduid 拼多多用户id
     */
    public void setPdduid(String pdduid) {
        this.pdduid = pdduid;
    }

    /**
     * 获取收货地址id
     *
     * @return address_id - 收货地址id
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * 设置收货地址id
     *
     * @param addressId 收货地址id
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * 获取用户状态(1可用,0不可用)
     *
     * @return status - 用户状态(1可用,0不可用)
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置用户状态(1可用,0不可用)
     *
     * @param status 用户状态(1可用,0不可用)
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取拼多多的accessToken
     *
     * @return accessToken - 拼多多的accessToken
     */
    public String getAccesstoken() {
        return accesstoken;
    }

    /**
     * 设置拼多多的accessToken
     *
     * @param accesstoken 拼多多的accessToken
     */
    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    /**
     * 获取扩展字段(json)
     *
     * @return extar - 扩展字段(json)
     */
    public String getExtar() {
        return extar;
    }

    /**
     * 设置扩展字段(json)
     *
     * @param extar 扩展字段(json)
     */
    public void setExtar(String extar) {
        this.extar = extar;
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
}