package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.Base64Util;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.dao.TChannelPaymentsMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelPayments;
import com.chaotu.pay.service.PaymentService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 支付方式管理
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TChannelPaymentsMapper channelPaymentsMapper;


    @Override
    public MyPageInfo<PaymentVo> findAllByPage(PageVo pageVo) {

        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TChannelPayments.class);
        Example.Criteria criteria = example.createCriteria();
        List tChannelPayments = channelPaymentsMapper.findAll();
        int count = channelPaymentsMapper.selectCountByExample(example);
        List<PaymentVo> paymentVoList = MyBeanUtils.copyList(tChannelPayments, PaymentVo.class);
        MyPageInfo<PaymentVo> pageInfo = new MyPageInfo<>(paymentVoList);
        pageInfo.setPageNum(pageVo.getPageNumber());
        pageInfo.setTotalElements(count);
        return pageInfo;
    }

    @Override
    public void addPayment(PaymentVo paymentVo,MultipartFile file) throws Exception{
        log.info("添加支付方式，入参paymentVo=["+paymentVo.toString()+"]");
        TChannelPayments payments = new TChannelPayments();
        BeanUtils.copyProperties(paymentVo,payments);
        Example example = new Example(TChannelPayments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("paymentname",paymentVo.getPaymentName());
        criteria.andEqualTo("paymentcode",paymentVo.getPaymentCode());
        int count = channelPaymentsMapper.selectCountByExample(example);
        if(count>0){//数据存在
            log.error("该支付名称或支付编码已存在");
            throw new BizException(ExceptionCode.DATA_AREADY_EXIST.getCode(),ExceptionCode.DATA_AREADY_EXIST.getMsg());
        }

        try {
            if(!file.isEmpty()){
                String oldFilename = file.getOriginalFilename();//获取原文件名
                String path = "F:/upload";//保存文件的路径
                String randomStr = UUID.randomUUID().toString();//获取随机字符串
                //拼接新文件名
                String newFileName = randomStr+oldFilename.substring(oldFilename.lastIndexOf("."));
                File newFile = new File(path,newFileName);
                if(!newFile.getParentFile().exists()){
                    newFile.getParentFile().mkdirs();
                }
                //将上传文件写到服务器上指定的文件
                file.transferTo(newFile);
                //通过base64来转换图片
                /*BASE64Encoder encoder = new BASE64Encoder();
                String imageStr = encoder.encode(newFile.getBytes());*/
                payments.setIco(newFile.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        payments.setCreateTime(new Date());
        payments.setPaymentname(paymentVo.getPaymentName());
        payments.setPaymentcode(paymentVo.getPaymentCode());
        payments.setChannelId(paymentVo.getChannelId());
        payments.setRunrate(paymentVo.getRunRate());
        payments.setCostrate(paymentVo.getCostRate());
        payments.setMinamount(paymentVo.getMinAmount());
        payments.setMaxamount(paymentVo.getMaxAmount());
        payments.setStatus(paymentVo.getStatus());
        channelPaymentsMapper.insertSelective(payments);
        log.info("添加支付方式成功，参数payments=["+payments.toString()+"]");
    }

    @Override
    public void editPayment(PaymentVo paymentVo) {
        log.info("修改支付方式，入参paymentVo=["+paymentVo.toString()+"]");
        paymentVo.setUpdateTime(new Date());
        channelPaymentsMapper.updatePayment(paymentVo);
        log.info("修改支付方式成功，参数paymentVo=["+paymentVo.toString()+"]");
    }

    @Override
    public void delPayment(String id) {
        log.info("删除支付方式，入参id=["+id+"]");
        TChannelPayments payments = channelPaymentsMapper.selectByPrimaryKey(id);
        if(payments!=null){
            channelPaymentsMapper.deleteByPrimaryKey(id);
            log.info("删除支付方式成功，参数id=["+id+"]");
        }else{
            log.info("未能找到需要删除的id="+id);
        }
    }

}
