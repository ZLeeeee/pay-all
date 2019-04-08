package com.chaotu.pay.vo;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class LogVo {

    private String id;

    private String createBy;

    private Date createTime;

    private Integer delFlag;

    private String updateBy;

    private Date updateTime;

    private Integer costTime;

    private String ip;

    private String ipInfo;

    private String name;

    private String requestType;

    private String requestUrl;

    private String username;

    private Integer logType;

    private String requestParam;

    /**
     * 转换请求参数为Json
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        this.requestParam = JSON.toJSONString(paramMap);
    }
}
