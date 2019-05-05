package com.chaotu.pay.service;

/**
 * 微信支付
 */
public interface WXPayService {

    /**
     * 支付结果通知
     * @param notifyData 异步通知后的xml数据
     * @return
     */
    String payBack(String notifyData);
}
