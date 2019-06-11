package com.chaotu.pay.yz;

import com.chaotu.pay.po.TYzUserAddress;
import lombok.Data;

@Data
public class PrePayDelivery {

    private TYzUserAddress address;

    private boolean hasFreightInsurance = false;

    private String expressType = "express";

    private int expressTypeChoice = 0;

}
