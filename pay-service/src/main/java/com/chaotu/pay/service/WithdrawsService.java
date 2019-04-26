package com.chaotu.pay.service;

import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.WithdrawsVo;

import java.text.ParseException;
import java.util.Map;

/**
 * @description:
 * @author: chenyupeng
 * @create: 2019-04-19 11:11
 **/

public interface WithdrawsService {
    Map<String, Object> findByCondition(PageVo pageVo, SearchVo searchVo, WithdrawsVo withdrawsVo) throws ParseException;
    void add(WithdrawsVo vo);
}
