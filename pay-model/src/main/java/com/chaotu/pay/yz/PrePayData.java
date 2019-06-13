package com.chaotu.pay.yz;

import lombok.Data;

import java.util.List;

@Data
public class PrePayData {
    private String orderNo;
    private List<String> orderNos;
    private PaymentPreparation paymentPreparation;
    private PaymentPreparationExtra paymentPreparationExtra;
    private PrePaymentPreparation prePaymentPreparation;
    private boolean showPayResult;
    private boolean zeroOrder;
}
