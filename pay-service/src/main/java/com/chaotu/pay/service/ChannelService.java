package com.chaotu.pay.service;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.vo.ChannelVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 通道管理
 */
public interface ChannelService extends CRUDService<TChannel>{



    /**
     * 分页查询
     * @return
     */
    MyPageInfo<TChannel> findAllByPage(PageVo pageVo);

    TChannel findById(Long id);
    void updateAmount(BigDecimal amount, Long channelId);


}


