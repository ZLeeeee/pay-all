package com.chaotu.pay.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PddOrderVo {
    private Long address_id;

    private List<PddGoodsVo> goods;

    private String group_id;

    private String anti_content;

    private int duoduo_type = 0;

    private int biz_type = 0;

    private Map<String,Object> attribute_fields;

    private String source_channel = "0";

    private int source_type = 0;

    private int pay_app_id = 9;

    private String is_app = "0";

    private  int version = 1;

    private String page_id = "10004_1557210365695_Ox062u4hPj";
}
