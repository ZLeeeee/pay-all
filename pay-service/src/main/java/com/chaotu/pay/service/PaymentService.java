package com.chaotu.pay.service;

import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.PaymentVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 支付方式
 */
public interface PaymentService {

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    MyPageInfo<PaymentVo> findAllByPage(PageVo pageVo);


    /**
     * 添加支付方式
     */
    void addPayment(PaymentVo paymentVo, MultipartFile file) throws Exception;

    /**
     * 修改支付方式
     */
    void editPayment(PaymentVo paymentVo);

    /**
     * 通过id删除支付方式
     * @param id
     */
    void delPayment(String id);

}
