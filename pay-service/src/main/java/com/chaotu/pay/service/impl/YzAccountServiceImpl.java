package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.dao.TYzAccountMapper;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.po.TYzAccount;
import com.chaotu.pay.service.YzAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@Service
public class YzAccountServiceImpl implements YzAccountService {
    @Autowired
    TYzAccountMapper accountMapper;
    @Autowired
    @Qualifier("yzAccountChoser")
    Choser<TYzAccount> yzAccountChoser;

    @Override
    public void insert(TYzAccount tYzAccount) {
        tYzAccount.setStatus(false);
        accountMapper.insertSelective(tYzAccount);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public TYzAccount selectOne(TYzAccount tYzAccount) {
        return accountMapper.selectOne(tYzAccount);
    }

    @Override
    public List<TYzAccount> findAll() {
        return accountMapper.selectAll();
    }

    @Override
    public void delete(TYzAccount tYzAccount) {
        accountMapper.deleteByPrimaryKey(tYzAccount);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public void update(TYzAccount tYzAccount) {
        accountMapper.updateByPrimaryKeySelective(tYzAccount);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public List<TYzAccount> findByStatus() {
        Example example = new Example(TYzAccount.class);
        example.createCriteria().andEqualTo("status",true);
        List<TYzAccount> tYzAccounts = accountMapper.selectByExample(example);
        return tYzAccounts;
    }

    @Override
    public synchronized void updateAmount(BigDecimal amount, int id) {
        TYzAccount account = findByid(id);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }
    @Override
    public TYzAccount findByid(int id) {
        TYzAccount account = new TYzAccount();
        account.setId(id);
        return selectOne(account);
    }

    @Override
    public void updateTodayAmountByStatus() {
        Example example = new Example(TYzAccount.class);
        example.createCriteria().andEqualTo("status",true);
        TYzAccount account = new TYzAccount();
        account.setTotalAmount(new BigDecimal(0));
        accountMapper.updateByExampleSelective(account,example);
    }

    @Override
    public List<TYzAccount> findAllByStatus() {
        TYzAccount account = new TYzAccount();
        account.setStatus(true);
        return accountMapper.select(account);
    }
}
