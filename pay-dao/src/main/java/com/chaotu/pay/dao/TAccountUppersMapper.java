package com.chaotu.pay.dao;

import com.chaotu.pay.po.TAccountUppers;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.AccountUppersVo;

import java.util.List;

public interface TAccountUppersMapper extends MyMapper<TAccountUppers> {

    /**
     * 获取所有通道商户
     * @return
     */
    List<AccountUppersVo> findAll();

    /**
     * 修改通道商户
     * @param accountUppersVo
     */
    void editAccountUppers(AccountUppersVo accountUppersVo);


}