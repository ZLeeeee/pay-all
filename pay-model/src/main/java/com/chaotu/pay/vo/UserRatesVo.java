package com.chaotu.pay.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserRatesVo {

    private Integer id;

    /**
     * 用户id
     */

    private Integer userId;

    /**
     * 通道id
     */

    private Integer channelId;

    /**
     * 支付id
     */

    private Integer channelPaymentId;

    /**
     * 商户费率：为0时走通道运营费率
     */
    private BigDecimal rate;

    /**
     * 状态：0禁用。1启用
     */
    private Byte status;

    private String paymentName;
}
