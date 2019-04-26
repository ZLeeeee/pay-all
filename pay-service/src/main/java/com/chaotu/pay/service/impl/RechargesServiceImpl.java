package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TRechargesMapper;

import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TRecharges;

import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.service.RechargesService;

import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class RechargesServiceImpl implements RechargesService {

    @Autowired
    private TRechargesMapper tRechargesMapper;

    @Autowired
    private WalletService walletService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RechargeVo vo,UserVo user ) {
        log.info("账号充值开始,入参为[" + vo.toString() + "]");
        if(vo.getActualAmount().equals(new BigDecimal(0)))
             throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR);
        TWallet wallet = new TWallet();
        wallet.setUserId(user.getId());
        wallet.setType("1");
        TWallet tWallet = walletService.selectOne(wallet);
        Double oldAmount = tWallet.getResidualAmount();
        BigDecimal newAmount = new BigDecimal(oldAmount).add(vo.getActualAmount()) ;
        tWallet.setResidualAmount(newAmount.doubleValue());
        log.info("当前用户为[" + user.getUsername() + "]");
        vo.setCreateBy(user.getId());
        vo.setUserId(user.getId());
        String orderNo = "C"+ IDGeneratorUtils.getFlowNum();
        log.info("充值单号为[" + vo.toString() + "]");
        vo.setOrderno(orderNo);
        TRecharges tRecharges = new TRecharges();
        BeanUtils.copyProperties(vo,tRecharges);
        walletService.editAmount(wallet,newAmount.toString(),"0");
        log.info("余额充值成功钱包id为["+wallet.getId()+"]");
        tRechargesMapper.insert(tRecharges);
        log.info("记录增加成功");
        log.info("账号充值结束!");

    }

    @Override
    public List<RechargeVo> getAll() {
        List<TRecharges> tRecharges = tRechargesMapper.findAll();
        List<RechargeVo> rechargeVoList = MyBeanUtils.copyList(tRecharges, RechargeVo.class);

        return rechargeVoList;
    }

    @Override
    public MyPageInfo<RechargeVo> findAllByPage(PageVo pageVo,RechargeVo vo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TRecharges.class);
        //查询结果按创建时间排序
        example.setOrderByClause("create_time");
        Example.Criteria criteria = example.createCriteria();

        int count = tRechargesMapper.selectCountByExample(example);
        List<TRecharges> tRecharges = tRechargesMapper.findAll();
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

    @Override
    public MyPageInfo<RechargeVo> findByCondition(PageVo pageVo, SearchVo searchVo,RechargeVo rechargeVo)  throws ParseException {
        Example example = new Example(TRecharges.class);
        Example.Criteria criteria = example.createCriteria();
        //设置起止时间
       if(StringUtils.isNotBlank(rechargeVo.getOrderno())){
           String orderNo = rechargeVo.getOrderno().trim();
           criteria.andLike("orderNo",orderNo);
           criteria.andEqualTo("user_id",rechargeVo.getUserId());
       }
        int count = tRechargesMapper.selectCountByExample(example);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize(),true);
        List<TRecharges> tRecharges = tRechargesMapper.findByCondition(rechargeVo);
        List<RechargeVo> rechargeVoList = MyBeanUtils.copyList(tRecharges, RechargeVo.class);

        MyPageInfo<RechargeVo> pageInfo = new MyPageInfo<>(rechargeVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }
}
