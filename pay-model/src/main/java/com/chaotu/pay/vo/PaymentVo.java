package com.chaotu.pay.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentVo {

    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Long channelId;

    private String paymentname;

    private String paymentcode;

    private String ico;

    private BigDecimal runrate;

    private BigDecimal costrate;

    private Integer minamount;

    private Integer maxamount;

    private String status;

    private String channelname;

}
