package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PrePayActivites {

    private int activityId = 0;
    private int activityType = 0;
    private int externalPointId = 0;
    private int goodsId ;
    private int kdtId;
    private int pointsPrice = 0;
    private int skuId ;
    private boolean usePoints = false;
}
