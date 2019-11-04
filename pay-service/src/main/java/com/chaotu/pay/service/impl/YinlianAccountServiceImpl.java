package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.common.channel.YinLianChannel;
import com.chaotu.pay.dao.TYinlianAccountMapper;
import com.chaotu.pay.po.TYinlianAccount;
import com.chaotu.pay.service.YinlianAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
@Service
public class YinlianAccountServiceImpl implements YinlianAccountService {
    @Autowired
    TYinlianAccountMapper accountMapper;
    @Autowired
    ChannelFactory channelFactory;
    private YinLianChannel getChannel(){
        return (YinLianChannel) channelFactory.getChannel(32L);
    }
    @Override
    public void insert(TYinlianAccount tYinlianAccount) {
        accountMapper.insertSelective(tYinlianAccount);
        getChannel().update();
    }

    @Override
    public TYinlianAccount selectOne(TYinlianAccount tYinlianAccount) {
        return accountMapper.selectOne(tYinlianAccount);
    }

    @Override
    public List<TYinlianAccount> findAll() {
        return accountMapper.selectAll();
    }

    @Override
    public void delete(TYinlianAccount tYinlianAccount) {
        accountMapper.deleteByPrimaryKey(tYinlianAccount.getId());
        getChannel().update();
    }

    @Override
    public void update(TYinlianAccount tYinlianAccount) {
        accountMapper.updateByPrimaryKeySelective(tYinlianAccount);
        getChannel().update();
    }
    @Override
    public synchronized void updateAmount(BigDecimal amount, String accountid) {
        Example example = new Example(TYinlianAccount.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account",accountid);
        TYinlianAccount account = accountMapper.selectOneByExample(example);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }
}
