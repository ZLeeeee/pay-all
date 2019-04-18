package com.chaotu.pay.service;

import com.chaotu.pay.vo.*;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    Map<String,Object> findByCondition(PageVo pageVo, SearchVo searchVo, OrderVo orderVo) throws ParseException;
}
