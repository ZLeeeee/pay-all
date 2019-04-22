package com.chaotu.pay.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BankVo {

    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String code;

    private String bankname;

    private String ico;

    private String status;
}
