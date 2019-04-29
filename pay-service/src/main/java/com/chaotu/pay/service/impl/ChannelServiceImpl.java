package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.service.ChannelService;
import com.chaotu.pay.vo.BizException;
import com.chaotu.pay.vo.ChannelVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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


    @Override
    public List<ChannelVo> getAll() {
        List<TChannel>  tChannels = channelMapper.selectAll();
        List<ChannelVo> channelVoList = MyBeanUtils.copyList(tChannels, ChannelVo.class);
        return channelVoList;
    }

    @Override
    public MyPageInfo<ChannelVo> findAllByPage(PageVo pageVo,ChannelVo vo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TChannel.class);
        Example.Criteria criteria = example.createCriteria();

        List<TChannel> tChannels = channelMapper.selectByExample(example);
        int count = channelMapper.selectCountByExample(example);
        List<ChannelVo> channelVoList = MyBeanUtils.copyList(tChannels, ChannelVo.class);
        MyPageInfo<ChannelVo> pageInfo = new MyPageInfo(channelVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }

    @Override
    public void addChannel(ChannelVo channelVo) {
        log.info("添加通道，入参channelVo=["+channelVo.toString()+"]");
        TChannel channel = new TChannel();
        BeanUtils.copyProperties(channelVo,channel);
        Example example = new Example(TChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("channelname",channelVo.getChannelName());
        criteria.andEqualTo("channelcode",channelVo.getChannelCode());
        int count = channelMapper.selectCountByExample(example);
        if(count>0){//数据存在
            log.error("该通道名称或通道编码");
            throw new BizException(ExceptionCode.ROLE_AREADY_EXIST.getCode(),ExceptionCode.ROLE_AREADY_EXIST.getMsg());
        }
        channel.setCreateTime(new Date());
        channel.setChannelname(channelVo.getChannelName());
        channel.setChannelcode(channelVo.getChannelCode());
        channelMapper.insertSelective(channel);
        log.info("添加通道成功，参数channelVo=["+channel.toString()+"]");
    }

    @Override
    public void editChannel(ChannelVo channelVo) {
       /* if( StringUtils.isEmpty(channelVo.getChannelName())){
            throw new BizException(ExceptionCode.REQUEST_PARAM_MISSING);
        }*/
        log.info("修改通道，入参cahnnelVo=["+channelVo.toString()+"]");
        TChannel channel = new TChannel();
        BeanUtils.copyProperties(channelVo,channel);
        channelVo.setUpdateTime(new Date());
        channelMapper.updateChannel(channelVo);
        log.info("修改通道成功，参数channelVo=["+channelVo.toString()+"]");
    }

    @Override
    public void delChannel(String id) {
        log.info("删除通道，入参id=["+id+"]");
        TChannel channel = channelMapper.selectByPrimaryKey(id);
        if (channel!=null){//判断通过id查找的通道是否存在
            channelMapper.deleteByPrimaryKey(id);
            log.info("删除通道成功，输出channel=["+channel.toString()+"]");
        }else{
            log.info("未能找到需要删除的id="+id);
        }
    }

}
