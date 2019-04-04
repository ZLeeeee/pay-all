package com.chaotu.pay.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_log")
public class TLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "cost_time")
    private Integer costTime;

    private String ip;

    @Column(name = "ip_info")
    private String ipInfo;

    private String name;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "request_url")
    private String requestUrl;

    private String username;

    @Column(name = "log_type")
    private Integer logType;

    @Column(name = "request_param")
    private String requestParam;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
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
     * @return del_flag
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
     * @return cost_time
     */
    public Integer getCostTime() {
        return costTime;
    }

    /**
     * @param costTime
     */
    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return ip_info
     */
    public String getIpInfo() {
        return ipInfo;
    }

    /**
     * @param ipInfo
     */
    public void setIpInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return request_type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @param requestType
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * @return request_url
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * @param requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return log_type
     */
    public Integer getLogType() {
        return logType;
    }

    /**
     * @param logType
     */
    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    /**
     * @return request_param
     */
    public String getRequestParam() {
        return requestParam;
    }

    /**
     * @param requestParam
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
}