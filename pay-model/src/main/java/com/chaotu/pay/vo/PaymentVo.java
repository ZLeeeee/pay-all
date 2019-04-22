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

    private String paymentName;

    private String paymentCode;

    private String ico;

    private BigDecimal runRate;

    private BigDecimal costRate;

    private Integer minAmount;

    private Integer maxAmount;

    private String status;

    private String channelName;

}
