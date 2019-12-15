package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.common.channel.YinLianChannel;
import com.chaotu.pay.dao.TYinlianAccountMapper;
import com.chaotu.pay.po.TYinlianAccount;
import com.chaotu.pay.service.YinlianAccountService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        TYinlianAccount yinlianAccount = new TYinlianAccount();
        yinlianAccount.setAccount(accountid);
        TYinlianAccount account = accountMapper.selectOne(yinlianAccount);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public MyPageInfo findAllByPage(PageVo pageVo) {
        Page<Object> objects = PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageNumber());
        List<TYinlianAccount> accounts = findAll();
        MyPageInfo pageInfo = new MyPageInfo(accounts);
        pageInfo.setTotal(objects.getTotal());
        return pageInfo;
    }
}
