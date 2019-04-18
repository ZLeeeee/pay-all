package com.chaotu.pay.dao;

import com.chaotu.pay.po.TRecharges;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.RechargeVo;

import java.util.List;

public interface TRechargesMapper extends MyMapper<TRecharges> {
    /**
     * 查询所有充值记录
     * @return
     */
    List<TRecharges> findAll();

    /**
     * 条件搜索
     * @return
     */
    List<TRecharges> findByCondition(RechargeVo rechargeVo);
}