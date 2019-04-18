package com.chaotu.pay.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ChannelVo {

    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String merchant;

    private String signkey;

    private String channelname;

    private String channelcode;

    private String refererdomain;

    private String status;

    private Integer channelquota;

    private BigDecimal tradeamount;

    private String extend;


}
