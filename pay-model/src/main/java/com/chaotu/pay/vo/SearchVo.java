package com.chaotu.pay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 查询条件，这里主要针对时间
 * @Date: Created in 11:53 2018/10/23
 * @Author: yaochenglong
 */

@Data
public class SearchVo implements Serializable {

    private String startDate;

    private String endDate;

}