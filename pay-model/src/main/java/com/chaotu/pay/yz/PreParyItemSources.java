package com.chaotu.pay.yz;

import lombok.Data;

@Data
public class PreParyItemSources {
    private int activityId = 0;
    private int activityType = 0;
    private String bizTracePointExt = "{\"st\":\"js\",\"sv\":\"0.8.6\",\"yai\":\"wsc_c\",\"uuid\":\"f2099561-fd52-a2ae-b20a-9ea1f0cb6830\",\"platform\":\"h5\",\"biz\":\"wsc\",\"client\":\"\"}";
    private int cartCreateTime = 0;
    private int cartUpdateTime = 0;
    private String gdtId = "";
    private int goodsId;
    private String pageSource = "";
    private int skuId;
}
