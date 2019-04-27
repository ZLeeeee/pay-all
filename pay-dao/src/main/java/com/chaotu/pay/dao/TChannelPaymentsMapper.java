package com.chaotu.pay.dao;

import com.chaotu.pay.po.TChannelPayments;
import com.chaotu.pay.utils.MyMapper;
import com.chaotu.pay.vo.PaymentVo;

import java.util.List;

public interface TChannelPaymentsMapper extends MyMapper<TChannelPayments> {

    /**
     * 修改支付方式
     * @param paymentVo
     */
    void updatePayment(PaymentVo paymentVo);


    /**
     * 查询所有支付方式
     * @return
     */
    List<PaymentVo> findAll();
}