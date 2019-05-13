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
}