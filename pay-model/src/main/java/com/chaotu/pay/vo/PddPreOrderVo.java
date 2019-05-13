package com.chaotu.pay.vo;

import lombok.Data;

import java.util.Map;

@Data
public class PddPreOrderVo {

    private String order_sn;
    private int  version;
    private Map<String,Object> attribute_fields;
    private String return_url;
    private int app_id;
}
