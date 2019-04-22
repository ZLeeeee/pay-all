package com.chaotu.pay.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BankCardVo {

    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String userId;

    private Long bankId;

    private String bankCardNo;

    private String accountName;

    private String branchName;

    private String status;

    private String code;

    private String bankName;

    private String ico;

}
