package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PaymentPreparation {

    private String bizAction;
    private String bizExt;
    private int bizMode;
    private int bizProd;
    private String currencyCodel;
    private int discountableAmount;
    private String excludePayTools;
    private String expiredAt;
    private String fromPlatform;
    private String mchId;
    private String outBizNo;
    private String partnerId;
    private String partnerReturnUrl;
    private int payAmount;
    private String payTools;
    private int payerId;
    private String sign;
    private String signType;
    private String tradeDesc;
    private int undiscountableAmount;
}
