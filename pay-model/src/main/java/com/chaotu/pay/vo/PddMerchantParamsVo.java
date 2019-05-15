package com.chaotu.pay.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PddMerchantParamsVo {
    boolean isVirtualGoods;
    List<Map<String,Object>> orderShipRequestList;
    int functionType;
    int overWrite;
    int isSingleShipment;
    String operateFrom;

}
