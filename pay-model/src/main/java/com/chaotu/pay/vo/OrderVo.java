package com.chaotu.pay.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单视图
 * @author: chenyupeng
 * @create: 2019-04-18 10:49
 **/
@Data
public class OrderVo {
    private Long id;
    private Date createTime;
    private String updateTime;

    private String startTime;
    private String endTime;

    /**
     * 商户id
     */
    private String userId;

    private int isHistory;

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
     * 支付方式名称
     */
    private String paymentname;

    /**
     * 商户异步通知地址
     */
    private String notifyurl;

    /**
     * 商户同步通知地址
     */
    private String successurl;

    /**
     * 订单状态：0未支付，1支付成功，2支付异常
     */
    private Byte status;
}
