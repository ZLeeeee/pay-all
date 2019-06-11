package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PrePayConfig {
    private boolean containsUnavailableItems = false;
    private int paymentExpiry = 0;
    private boolean receiveMsg = true;
    private boolean usePoints = false;
    private boolean useWxpay = false;
    private String buyerMsg = "";
}
