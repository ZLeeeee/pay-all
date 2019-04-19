package com.chaotu.pay.dao;

import com.chaotu.pay.po.TWithdraws;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.WithdrawsVo;

import java.util.List;
import java.util.Map;

public interface TWithdrawsMapper extends MyMapper<TWithdraws> {
    /**
     * 获取所有结算记录
     * @param withdrawsVo
     * @return
     */
    List<TWithdraws> findAll(WithdrawsVo withdrawsVo);

    /**
     * 获取结算总账目
     * @param withdrawsVo
     * @return
     */
    Map<String, Object> getGeneralAccount(WithdrawsVo withdrawsVo);
}