package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TWithdrawsMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.po.TWithdraws;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.service.WithdrawsService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 结算管理
 * @author: chenyupeng
 * @create: 2019-04-19 11:11
 **/
@Service
public class WithdrawsServiceImpl implements WithdrawsService {
    @Autowired
    private UserService userService;
    @Autowired
    private TWithdrawsMapper tWithdrawsMapper;
    @Autowired
    private WalletService walletService;
    @Override
    public Map<String, Object> findByCondition(PageVo pageVo, SearchVo searchVo, WithdrawsVo withdrawsVo) {
        Example example = new Example(TWithdraws.class);
        Example.Criteria criteria = example.createCriteria();


        //通过时间查询所有订单
        //如果时间为空，则为当日00:00:00至当前时间
        if(!StringUtils.isEmpty(searchVo.getStartDate())){
            withdrawsVo.setStartTime(searchVo.getStartDate());
        }

        if(!StringUtils.isEmpty(searchVo.getEndDate())){
            withdrawsVo.setEndTime(searchVo.getStartDate());
        }

       /* try {*/
            PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize());
            List<TWithdraws> withdrawsList = tWithdrawsMapper.findAll(withdrawsVo);
            int count = tWithdrawsMapper.selectCountByExample(example);

            Map<String,Object> generalAccount = tWithdrawsMapper.getGeneralAccount(withdrawsVo);
            generalAccount.put("allcount", withdrawsList.size());
            Map<String,Object> map = new HashMap<>();

            MyPageInfo info = new MyPageInfo(withdrawsList);
            if(!CollectionUtils.isEmpty(withdrawsList)){
                info.setTotal(count);
                info.setPageNum(pageVo.getPageNumber());
            }

            map.put("pageInfo", info);
            map.put("generalAccount", generalAccount);
            return map;
     /*   } catch (Exception e) {
            throw new BizException(ExceptionCode.UNKNOWN_ERROR);
        }*/

    }

    @Override
    public void add(WithdrawsVo vo) {
        TWithdraws withdraws = new TWithdraws();
        TWallet w = new TWallet();
        w.setUserId(vo.getUserId());
        w.setType("2");
        TWallet wallet = walletService.selectOne(w);
        Double residualAmount = wallet.getResidualAmount();
        BigDecimal rate = new BigDecimal(5);
        if (vo.getWithdrawamount().add(rate).compareTo(new BigDecimal(residualAmount)) <0 )
            throw new IllegalArgumentException("余额不足");
        String userId = userService.currentUser().getId();
        BeanUtils.copyProperties(vo,withdraws);
        withdraws.setWithdrawrate(rate);
        withdraws.setToamount(withdraws.getWithdrawamount().subtract(rate));
        withdraws.setStatus((byte)0);
        withdraws.setCreateBy(userId);
        withdraws.setCreateTime(new Date());
        withdraws.setUserId(userId);
        tWithdrawsMapper.insert(withdraws);
    }

    @Override
    public void update(TWithdraws withdraws) {
        tWithdrawsMapper.updateByPrimaryKey(withdraws);
    }

    @Override
    public void pass(TWithdraws vo) {
        TWithdraws withdraws = tWithdrawsMapper.selectByPrimaryKey(vo);
        withdraws.setStatus((byte)2);
        TWallet w = new TWallet();
        w.setUserId(vo.getUserId());
        w.setType("2");
        walletService.editAmount(w,withdraws.getWithdrawamount().toString(),"1");
        tWithdrawsMapper.updateByPrimaryKey(withdraws);
    }
}
