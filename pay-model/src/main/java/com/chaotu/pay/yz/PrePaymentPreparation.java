package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PrePaymentPreparation {
    private String cashierSalt;
    private String cashierSign;
    private String partnerId;
    private boolean prepay;
    private String prepayId;
    private boolean prepaySuccess;
}
