package com.chaotu.pay.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVo {

    private String id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String address;

    private String avatar;

    private String description;

    private String email;

    private String mobile;

    private String nickName;

    private String password;

    private Integer sex;

    private Integer status;

    private Integer type;

    private String username;

    private Integer delFlag;

    private String departmentId;

    private String street;

    private String passStrength;

    private List<RoleVo> roles;

    private String roleIds;

    private List<PermissionVo> permissions;

    private String startDate;
    private String endDate;

    private String roleNames;
}
