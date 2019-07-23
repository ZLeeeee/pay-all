package com.chaotu.pay.common.channel;

import com.chaotu.pay.po.TChannel;

import java.util.Map;

public interface ChannelChooser {
    TChannel getChannel(int payTypeId);
}
