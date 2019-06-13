package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PrePayResponse {
    private int code;
    private String msg;
    private PrePayData data;
}
