package com.chaotu.pay.common.channel;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;

import java.util.Map;

public interface Channel {
    Map<String,Object> pay(OrderVo orderVo);

    TChannel getChannel();

    void setChannel(TChannel channel);

    TChannelAccount getAccount();

    void setAccount(TChannelAccount account);

    String createSign(Map<String, Object> signParam);
}
