package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.WXMyConfigUtil;
import com.chaotu.pay.common.utils.WXPay;
import com.chaotu.pay.common.utils.WXPayUtil;
import com.chaotu.pay.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信支付
 */
@Slf4j
@Service
public class WXPayServiceImpl implements WXPayService {

    /**
     * 支付结果通知
     * @param notifyData 异步通知后的xml数据
     * @return
     */
    @Override
    public String payBack(String notifyData) {




        return null;

    }
}
