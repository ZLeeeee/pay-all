package com.chaotu.pay.po;

import javax.persistence.*;

@Table(name = "t_yz_user_address")
public class TYzUserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 住址详情
     */
    @Column(name = "address_detail")
    private String addressDetail;

    /**
     * 地区代码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 城市
     */
    private String city;

    /**
     * 组织
     */
    private String community;

    /**
     * 国家
     */
    private String country;

    /**
     * 国家类型
     */
    @Column(name = "country_type")
    private Integer countryType;

    /**
     * 区
     */
    private String county;

    /**
     * 是否默认
     */
    @Column(name = "is_default")
    private Integer isDefault;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lon;

    @Column(name = "postal_code")
    private String postalCode;

    /**
     * 省
     */
    private String province;

    /**
     * 来源
     */
    private Integer source;

    /**
     * 手机
     */
    private String tel;

    /**
     * 有赞用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 有赞用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 收货人
     */
    private String recipients;

    /**
     * 地址id
     */
    @Column(name = "address_id")
    private Long addressId;

    /**
     * ip
     */
    private String ip;

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
     * 获取住址详情
     *
     * @return address_detail - 住址详情
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 设置住址详情
     *
     * @param addressDetail 住址详情
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
     * 获取地区代码
     *
     * @return area_code - 地区代码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置地区代码
     *
     * @param areaCode 地区代码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取组织
     *
     * @return community - 组织
     */
    public String getCommunity() {
        return community;
    }

    /**
     * 设置组织
     *
     * @param community 组织
     */
    public void setCommunity(String community) {
        this.community = community;
    }

    /**
     * 获取国家
     *
     * @return country - 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取国家类型
     *
     * @return country_type - 国家类型
     */
    public Integer getCountryType() {
        return countryType;
    }

    /**
     * 设置国家类型
     *
     * @param countryType 国家类型
     */
    public void setCountryType(Integer countryType) {
        this.countryType = countryType;
    }

    /**
     * 获取区
     *
     * @return county - 区
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置区
     *
     * @param county 区
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 获取是否默认
     *
     * @return is_default - 是否默认
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认
     *
     * @param isDefault 是否默认
     */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取纬度
     *
     * @return lat - 纬度
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 获取经度
     *
     * @return lon - 经度
     */
    public String getLon() {
        return lon;
    }

    /**
     * 设置经度
     *
     * @param lon 经度
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     * @return postal_code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取来源
     *
     * @return source - 来源
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置来源
     *
     * @param source 来源
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 获取手机
     *
     * @return tel - 手机
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置手机
     *
     * @param tel 手机
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取有赞用户id
     *
     * @return user_id - 有赞用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置有赞用户id
     *
     * @param userId 有赞用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取有赞用户名
     *
     * @return user_name - 有赞用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置有赞用户名
     *
     * @param userName 有赞用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取收货人
     *
     * @return recipients - 收货人
     */
    public String getRecipients() {
        return recipients;
    }

    /**
     * 设置收货人
     *
     * @param recipients 收货人
     */
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    /**
     * 获取地址id
     *
     * @return address_id - 地址id
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * 设置地址id
     *
     * @param addressId 地址id
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}