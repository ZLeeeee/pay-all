package com.chaotu.pay.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PddResult {
    private int totalItemNum;
    private List<Map<String,Object>> pageItems;
}
