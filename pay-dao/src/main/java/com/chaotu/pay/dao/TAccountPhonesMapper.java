package com.chaotu.pay.dao;

import com.chaotu.pay.po.TAccountPhones;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.AccountPhonesVo;
import com.chaotu.pay.vo.SearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountPhonesMapper extends MyMapper<TAccountPhones> {
    int countByCondition(@Param("accountPhonesVo") AccountPhonesVo accountPhonesVo, @Param("searchVo")SearchVo searchVo);
    List<TAccountPhones> findByCondition(@Param("accountPhonesVo")AccountPhonesVo accountPhonesVo, @Param("searchVo")SearchVo searchVo);
}