package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.dao.TChannelAccountMapper;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.service.ChannelAccountService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ChannelAccountServiceImpl implements ChannelAccountService {
    @Autowired
    TChannelAccountMapper accountMapper;
    @Autowired
    private ChannelFactory factory;
    @Override
    public TChannelAccount findById(Long id) {
        TChannelAccount channel = new TChannelAccount();
        channel.setId(id);
        return selectOne(channel);
    }

    @Override
    public synchronized void updateAmount(BigDecimal amount, Long channelId) {
        TChannelAccount account = findById(channelId);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public MyPageInfo findAllByPage(PageVo pageVo) {
        Page<Object> objects = PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageNumber());
        List<TChannelAccount> accounts = findAll();
        MyPageInfo pageInfo = new MyPageInfo(accounts);
        pageInfo.setTotal(objects.getTotal());
        return pageInfo;
    }

    @Override
    public void insert(TChannelAccount tChannelAccount) {
        accountMapper.insertSelective(tChannelAccount);
    }

    @Override
    public TChannelAccount selectOne(TChannelAccount tChannelAccount) {

        return accountMapper.selectOne(tChannelAccount);
    }

    @Override
    public List<TChannelAccount> findAll() {
        return accountMapper.selectAll();
    }

    @Override
    public void delete(TChannelAccount tChannelAccount) {
        accountMapper.deleteByPrimaryKey(tChannelAccount);
    }

    @Override
    public void update(TChannelAccount tChannelAccount) {
        factory.getChannel(tChannelAccount.getChannelId()).setAccount(tChannelAccount);
        accountMapper.updateByPrimaryKeySelective(tChannelAccount);
    }
}
