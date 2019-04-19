package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TRechargesMapper;
import com.chaotu.pay.dao.TUserMapper;
import com.chaotu.pay.po.TRecharges;
import com.chaotu.pay.po.TRole;
import com.chaotu.pay.po.TUser;
import com.chaotu.pay.service.RechargesService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

@Service
public class RechargesServiceImpl implements RechargesService {

    @Autowired
    private TRechargesMapper tRechargesMapper;

    @Override
    public List<RechargeVo> getAll() {
        List<TRecharges> tRecharges = tRechargesMapper.findAll();
        List<RechargeVo> rechargeVoList = MyBeanUtils.copyList(tRecharges, RechargeVo.class);
        return rechargeVoList;
    }

    @Override
    public MyPageInfo<RechargeVo> findAllByPage(PageVo pageVo,RechargeVo vo) {
        Example example = new Example(TRecharges.class);
        //查询结果按创建时间排序
        example.setOrderByClause("create_time");
        Example.Criteria criteria = example.createCriteria();
        /*if(vo!=null){
            criteria.andLike("merchant",vo.getMerchant());
        }*/
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        int count = tRechargesMapper.selectCountByExample(example);
        List<TRecharges> tRecharges = tRechargesMapper.findAll();;
        List<RechargeVo> rechargeVoList = MyBeanUtils.copyList(tRecharges, RechargeVo.class);

        MyPageInfo<RechargeVo> pageInfo = new MyPageInfo<>(rechargeVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }

    @Override
    public MyPageInfo<RechargeVo> search(PageVo pageVo, SearchVo searchVo,RechargeVo rechargeVo)  throws ParseException {
        //去空格处理
        if(!StringUtils.isEmpty(rechargeVo.getOrderno())){
            String orderNo = rechargeVo.getOrderno().trim();
            rechargeVo.setOrderno(orderNo);
        }
        if(!StringUtils.isEmpty(rechargeVo.getMerchant())){
            String merchant = rechargeVo.getMerchant().trim();
            rechargeVo.setMerchant(merchant);
        }
        Example example = new Example(TRecharges.class);
        Example.Criteria criteria = example.createCriteria();
        //设置起止时间
        if(!StringUtils.isEmpty(searchVo.getStartDate())){
            rechargeVo.setStartDate(searchVo.getStartDate());
        }
        if(!StringUtils.isEmpty(searchVo.getEndDate())){
            rechargeVo.setEndDate(searchVo.getEndDate());
        }
        int count = tRechargesMapper.selectCountByExample(example);

        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize(),true);
        List<TRecharges> tRecharges = tRechargesMapper.findByCondition(rechargeVo);
        List<RechargeVo> rechargeVoList = MyBeanUtils.copyList(tRecharges, RechargeVo.class);

        MyPageInfo<RechargeVo> pageInfo = new MyPageInfo<>(rechargeVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }
}
