package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.dao.TPddAccountMapper;
import com.chaotu.pay.po.TPddAccount;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.service.PddAccountService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@Service
public class PddAccountServiceImpl implements PddAccountService {
    @Autowired
    TPddAccountMapper mapper;
    @Autowired
    @Qualifier("pddAccountChoser")
    Choser<TPddAccount> pddAccountChoser;
    @Override
    public TPddAccount selectOne(TPddAccount account) {
        return mapper.selectOne(account);
    }

    @Override
    public List<TPddAccount> select(TPddAccount account) {
        return mapper.select(account);
    }

    @Override
    public MyPageInfo<TPddAccount> findAllByPage(PageVo pageVo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TPddAccount.class);
        List<TPddAccount> bankCardVoList = mapper.selectAll();//查询所有银行卡信息
        int count = mapper.selectCountByExample(example);
        MyPageInfo<TPddAccount> pageInfo = new MyPageInfo<>(bankCardVoList);
        pageInfo.setPageNum(pageVo.getPageNumber());
        pageInfo.setTotalElements(count);
        return pageInfo;
    }

    @Override
    public List<TPddAccount> findAllByStatus() {
        TPddAccount account = new TPddAccount();
        account.setStatus(true);
        return select(account);
    }

    @Override
    public void insert(TPddAccount account) {
        mapper.insert(account);
        account.setStatus(false);
        pddAccountChoser.update(findAllByStatus());
    }

    @Override
    public void update(TPddAccount account) {
        mapper.updateByPrimaryKeySelective(account);
        pddAccountChoser.update(findAllByStatus());
    }

    @Override
    public TPddAccount findByid(int id) {
        TPddAccount account = new TPddAccount();
        account.setId(id);
        return selectOne(account);
    }

    @Override
    public void updateAmount(BigDecimal amount, TPddAccount account) {
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        mapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public void delete(TPddAccount account) {
        mapper.deleteByPrimaryKey(account);
        Example example = new Example(TPddGoods.class);
        example.createCriteria().andEqualTo("pddAccountId",account.getId());
        mapper.deleteByExample(example);
    }

    @Override
    public void updateTodayAmountByStatus() {
        Example example = new Example(TPddAccount.class);
        example.createCriteria().andEqualTo("status",true);
        TPddAccount account = new TPddAccount();
        account.setTotalAmount(new BigDecimal(0));
        mapper.updateByExampleSelective(account,example);
    }
}
