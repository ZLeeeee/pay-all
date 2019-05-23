package com.chaotu.pay.vo;

import lombok.Data;

@Data
public class PddOrderResponse {

    private boolean success;
    private int errorCode;
    private String errorMsg;
    private PddResult result;
}
