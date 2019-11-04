package com.chaotu.pay.common.channel;

import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface Channel {
    Object pay(OrderVo orderVo);

    TChannel getChannel();

    void setChannel(TChannel channel);

    TChannelAccount getAccount();

    void setAccount(TChannelAccount account);

    String createSign(Map<String, Object> signParam);

    boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request);

    String getSuccessNotifyStr();

    String getAccountId();

    //String createNotifySign(Map<String, Object> signParam, HttpServletRequest request);
}
