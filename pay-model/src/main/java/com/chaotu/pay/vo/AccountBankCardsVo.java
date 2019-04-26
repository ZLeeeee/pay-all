package com.chaotu.pay.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class AccountBankCardsVo {

    private Long id;


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
     * 单日限额: 0不限额
     */

    private BigDecimal dayquota;

    /**
     * 手机标识
     */

    private String phoneId;

    /**
     * 银行名称
     */

    private String bankName;

    /**
     * 银行缩写
     */

    private String bankMark;

    /**
     * 持卡人
     */

    private String bankAccount;

    /**
     * 银行卡账号
     */

    private String cardno;

    /**
     * 交易成功金额
     */

    private BigDecimal tradeamount;

    /**
     * 状态：0禁用。1启用
     */
    private String status;

    /**
     * 有效标识：0-无效；1-有效；默认1
     */
    private String valid;

    /**
     * 密钥与手机通讯时使用
     */

    private String signkey;

    /**
     * 银行卡在支付宝里的id
     */

    private String chardIndex;

    /**
     * 银行任意金额码链接
     */
    private String qrcode;


    private String createBy;


    private Date createTime;


    private String updateBy;


    private Date updateTime;
}
