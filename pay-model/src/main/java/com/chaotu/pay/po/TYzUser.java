package com.chaotu.pay.po;

import javax.persistence.*;

@Table(name = "t_yz_user")
public class TYzUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 有赞用户sessionid
     */
    @Column(name = "kdt_sessionId")
    private String kdtSessionid;

    /**
     * 用户状态(1可用,0不可用)
     */
    private Boolean status;

    /**
     * 扩展字段(json)
     */
    private String extar;

    /**
     * 备注
     */
    private String mark;

    /**
     * 手机
     */
    private String phone;

    /**
     * cookie
     */
    private String cookie;

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
     * 获取有赞用户sessionid
     *
     * @return kdt_sessionId - 有赞用户sessionid
     */
    public String getKdtSessionid() {
        return kdtSessionid;
    }

    /**
     * 设置有赞用户sessionid
     *
     * @param kdtSessionid 有赞用户sessionid
     */
    public void setKdtSessionid(String kdtSessionid) {
        this.kdtSessionid = kdtSessionid;
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

    /**
     * 获取手机
     *
     * @return phone - 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机
     *
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取cookie
     *
     * @return cookie - cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * 设置cookie
     *
     * @param cookie cookie
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}