package com.chaotu.pay.vo;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;


@Data
public class AccountPhonesVo {


    private Long id;


    private String createBy;


    private Date createTime;


    private String updateBy;


    private Date updateTime;

    /**
     * 用户id
     */

    private String userId;

    /**
     * 第三方挂号，0-用户 1-第三方
     */
    private String third;

    /**
     * 支付方式id
     */

    private Long channelPaymentId;

    /**
     * 账户类型：支付宝、微信等等
     */

    private String accounttype;

    /**
     * 单日限额
     */

    private BigDecimal dayquota;

    /**
     * 手机标识
     */

    private String phoneId;

    /**
     * 支付宝userid
     */
    private String alipayuserid;

    /**
     * 支付宝实名
     */
    private String alipayusername;

    /**
     * 收款账号
     */
    private String account;

    /**
     * 收款任意金额码
     */
    private String qrcode;

    /**
     * 交易成功金额
     */

    private BigDecimal tradeamount;

    /**
     * 状态：0禁用。1启用
     */
    private String status;

    /**
     * 密钥与手机通讯时使用
     */

    private String signkey;

    /***
     * 支付名称
     */
    private String paymentName;

    /***
     * 当日交易额
     */
    private BigDecimal accountAmount;

    /***
     * 当日订单量
     */
    private BigDecimal accountOrderCount;

    /***
     *当日订成功次数
     */
    private BigDecimal accountOrderSucCount;
    /***
     * 交易成功率
     */
    private BigDecimal sucRates;
}
