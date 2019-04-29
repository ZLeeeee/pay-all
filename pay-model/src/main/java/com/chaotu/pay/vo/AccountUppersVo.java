package com.chaotu.pay.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 通道商户VO
 */
@Data
public class AccountUppersVo {

    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String account;

    private String signkey;

    private Long channelId;

    private Long channelPaymentId;

    private String status;

    private Integer orderNum;//订单量

    private BigDecimal dayQuota;//限额

    private String privatekey;//私钥(微信只有私钥)

    private String publikey;//公钥

    private String accountType;//支付类型，1：支付宝 2：微信

    private String channelName;//通道名称

    private String paymentName;//支付名称
}
