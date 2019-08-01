package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.channel.Channel;
import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.choser.ChoserFactory;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.service.ChannelService;
import com.chaotu.pay.vo.BizException;
import com.chaotu.pay.vo.ChannelVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 通道管理
 */
@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private TChannelMapper channelMapper;
    @Autowired
    private ChannelFactory factory;
    @Autowired
    private ChoserFactory choserFactory;


    @Override
    public List<TChannel> findAll() {
        List<TChannel>  tChannels = channelMapper.selectAll();
        return tChannels;
    }

    @Override
    public MyPageInfo<TChannel> findAllByPage(PageVo pageVo) {
        Page<Object> p = PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize());
        List<TChannel> tChannels = findAll();
        MyPageInfo<TChannel> pageInfo = new MyPageInfo(tChannels);
        pageInfo.setTotal(p.getTotal());
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }

    @Override
    public void insert(TChannel channelVo) {
        channelVo.setTodayAmount(new BigDecimal(0));
        channelVo.setTotalAmount(new BigDecimal(0));
        Date date = new Date();
        channelVo.setCreateTime(date);
        channelVo.setUpdateTime(date);
        channelMapper.insertSelective(channelVo);
    }

    @Override
    public void update(TChannel channelVo) {
        //factory.getChannel(channelVo.getId()).setChannel(channelVo);
        channelMapper.updateByPrimaryKeySelective(channelVo);
        choserFactory.update();

    }

    @Override
    public void delete(TChannel channel) {
        channelMapper.deleteByPrimaryKey(channel);
    }

    @Override
    public TChannel selectOne(TChannel channel) {
        return channelMapper.selectOne(channel);
    }

    @Override
    public TChannel findById(Long id) {
        TChannel channel = new TChannel();
        channel.setId(id);
        return selectOne(channel);
    }
    @Override
    public synchronized void updateAmount(BigDecimal amount, Long channelId) {
        TChannel account = findById(channelId);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        Channel channel = factory.getChannel(channelId);
        TChannelAccount account1 = channel.getAccount();
        TChannel tChannel = channel.getChannel();
        tChannel.setTodayAmount(todayAmount);
        tChannel.setTotalAmount(totalAmount);
        account1.setTodayAmount(todayAmount);
        account1.setTotalAmount(totalAmount);
        channelMapper.updateByPrimaryKeySelective(account);
    }
}
