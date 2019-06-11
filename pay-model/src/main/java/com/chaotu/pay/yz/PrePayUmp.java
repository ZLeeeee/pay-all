package com.chaotu.pay.yz;

import lombok.Data;

import java.util.List;

@Data
public class PrePayUmp {

    private List<PrePayActivites> activities;

    private Object coupon;

    private UseCustomerCardInfo useCustomerCardInfo = new UseCustomerCardInfo();

    private Object points;

}
