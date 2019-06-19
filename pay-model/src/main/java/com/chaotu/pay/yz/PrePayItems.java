package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PrePayItems {
    private int activityId;
    private int activityType;
    private int deliverTime;
    private Object extensions;
    private int goodsId;
    private int kdtId;
    private int num = 1;
    private int pointsPrice;
    private int price;
    private int skuId;
    private int storeId;
    private int umpSkuId;
    //private String itemMessage;
}
