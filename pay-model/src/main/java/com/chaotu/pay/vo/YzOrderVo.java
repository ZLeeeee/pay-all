package com.chaotu.pay.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class YzOrderVo {

    private String id;

    /**
     * 拼多多订单号
     */

    private String orderNo;

    /**
     * 商品id
     */

    private Integer goodsId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 订单状态(0未请求,1未支付,2待发货/已支付,3已发货/未签收,4已签收/已到账,5异常)
     */
    private Byte status;

    /**
     * 商户id
     */

    private String userId;

    /**
     * 商品数量
     */

    private Integer skuNumber;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    /**
     * 拼多多用户id
     */

    private Integer yzUserId;

    /**
     * 用户自定义Json
     */
    private String extension;


    private String accessToken;

    /**
     * 回调地址
     */

    private String notifyUrl;

    /**
     * 已回调次数
     */

    private Integer notifyTimes;

    /**
     * 用户订单号
     */

    private String userOrderNo;

    /**
     * 发送次数
     */

    private Integer sendTimes;

    /**
     * 拼多多账号id
     */

    private Integer yzAccountId;

    /**
     * 是否是历史记录  0:否   1：是
     */

    private Integer isHistory;
    /***
     * 签名
     */
    private String sign;
}
