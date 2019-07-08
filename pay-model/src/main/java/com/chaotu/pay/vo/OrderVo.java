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

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 通道id
     */
    @Column(name = "channel_id")
    private Long channelId;

    /**
     * 支付方式id
     */
    @Column(name = "pay_type_id")
    private Integer payTypeId;

    /**
     * 账号(提供二维码)
     */
    private String account;

    /**
     * 系统订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 下游订单号(商户订单号)
     */
    @Column(name = "under_order_no")
    private String underOrderNo;

    /**
     * 上游订单号
     */
    @Column(name = "upper_order_no")
    private String upperOrderNo;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单手续费
     */
    @Column(name = "order_rate")
    private BigDecimal orderRate;

    /**
     * 系统收入
     */
    @Column(name = "sys_amount")
    private BigDecimal sysAmount;

    /**
     * 用户收入
     */
    @Column(name = "user_amount")
    private BigDecimal userAmount;

    /**
     * 商户异步通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 通道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 支付方式名称
     */
    @Column(name = "pay_type_name")
    private String payTypeName;

    /**
     * 扩展字段,存储json
     */
    private String extend;

    /**
     * 订单状态：0未支付，1支付成功，2支付异常
     */
    private Byte status;

    /**
     * 是否为历史0否1是
     */
    @Column(name = "is_history")
    private Integer isHistory;

    /**
     * 通道收入
     */
    @Column(name = "channel_amount")
    private BigDecimal channelAmount;
    private String sign;
    private String startTime;
    private String endTime;
}
