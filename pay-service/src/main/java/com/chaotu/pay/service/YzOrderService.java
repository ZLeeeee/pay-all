package com.chaotu.pay.service;

import com.chaotu.pay.po.TYzOrder;
import com.chaotu.pay.vo.YzOrderVo;

import java.util.Map;

public interface YzOrderService extends PayService<TYzOrder>{

    Map<Object,Object> pay(YzOrderVo vo);

}
