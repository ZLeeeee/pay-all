package com.chaotu.pay.vo;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeVo {

    private Integer id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date udpateTime;

    private String userId;

    private BigDecimal rechargeAmount;

    private String merchant;

    private BigDecimal actualAmount;

    private String orderno;

    private String ordermk;

    private Byte payStatus;

    private String startDate;

    private String endDate;

    //用户名
    private String username;
}
