package com.chaotu.pay.service;

import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.service.CRUDService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

import java.math.BigDecimal;
import java.util.List;

public interface ChannelAccountService extends CRUDService<TChannelAccount> {
    TChannelAccount findById(Long id);

    void updateAmount(BigDecimal amount, Long channelId);

    MyPageInfo findAllByPage(PageVo pageVo);
}
