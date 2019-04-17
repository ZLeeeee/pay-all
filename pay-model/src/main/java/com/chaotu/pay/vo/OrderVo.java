package com.chaotu.pay.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: chenyupeng
 * @create: 2019-04-17 16:47
 **/

public class OrderVo {
    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private String updateTime;

    private String delFlag;

    /**
     * 商户id
     */
    private String userId;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 归属代理id
     */
    private String agentId;

    /**
     * 通道id
     */
    private Long channelId;

    /**
     * 支付方式id
     */
    private Long channelPaymentId;

    /**
     * 账号(提供二维码)
     */
    private String account;

    /**
     * 设备号
     */
    private String phoneId;

    /**
     * 设备归属商户
     */
    private Long phoneUid;

    /**
     * 固码备注
     */
    private String ordermk;

    /**
     * 系统订单号
     */
    private String orderno;

    /**
     * 下游订单号(商户订单号)
     */
    private String underorderno;

    /**
     * 上游订单号
     */
    private String onorderno;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单手续费
     */
    private BigDecimal orderrate;

    /**
     * 系统收入
     */
    private BigDecimal sysamount;

    /**
     * 代理收入
     */
    private BigDecimal agentamount;

    /**
     * 用户收入
     */
    private BigDecimal useramount;

    /**
     * 商户异步通知地址
     */
    private String notifyurl;

    /**
     * 商户同步通知地址
     */
    private String successurl;

    /**
     * 通道名称
     */
    private String channelname;

    /**
     * 支付方式名称
     */
    private String paymentname;
}
