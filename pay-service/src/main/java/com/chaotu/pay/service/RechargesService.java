package com.chaotu.pay.service;

import com.chaotu.pay.vo.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


public interface RechargesService {

    /**
     * 获取所有充值记录
     * @return
     */
    List<RechargeVo> getAll();

    /**
     * 分页查找所有充值记录
     * @param pageVo
     * @return
     */
    MyPageInfo<RechargeVo> findAllByPage(PageVo pageVo, RechargeVo vo);

    /**
     * 条件查询
     * @param pageVo
     * @param rechargeVo
     * @return
     */
    MyPageInfo<RechargeVo> search(PageVo pageVo, SearchVo searchVo, RechargeVo rechargeVo) throws ParseException;

    /***
     * 账号充值
     * @param vo
     */
    void add(RechargeVo vo, UserVo user );

    /***
     * 代理后台查询
     * @param pageVo
     * @param searchVo
     * @param rechargeVo
     * @return
     */
    public MyPageInfo<RechargeVo> findByCondition(PageVo pageVo, SearchVo searchVo,RechargeVo rechargeVo) throws ParseException;

}
