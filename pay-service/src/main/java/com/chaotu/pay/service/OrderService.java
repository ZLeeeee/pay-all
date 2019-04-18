package com.chaotu.pay.service;

import com.chaotu.pay.vo.*;

import java.text.ParseException;

public interface OrderService {
    MyPageInfo<OrderVo> findByCondition(PageVo pageVo, SearchVo searchVo, OrderVo orderVo) throws ParseException;
}
