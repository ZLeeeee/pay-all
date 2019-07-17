package com.chaotu.pay.channel;

import com.chaotu.pay.vo.OrderVo;

import java.util.Map;

public interface Channel {
    Map<String,Object> pay(OrderVo orderVo);
}
