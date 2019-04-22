package com.chaotu.pay.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 结算展示对象
 * @author: chenyupeng
 * @create: 2019-04-19 11:03
 **/
@Data
public class WithdrawsVo {
    private Integer id;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String startTime;
    private String endTime;

    private String updateBy;

    private String username;
    /**
     * 商户id
     */
    private String userId;

    /**
     * 商户所属代理id
     */
    private Integer agentId;

    /**
     * 银行名称
     */
    private String bankname;

    /**
     * 提现金额
     */
    private BigDecimal withdrawamount;

    /**
     * 提现手续费
     */
    private BigDecimal withdrawrate;

    /**
     * 到账金额
     */
    private BigDecimal toamount;

    /**
     * 银行开户名
     */
    private String accountname;

    /**
     * 银行卡号
     */
    private String bankcardno;

    /**
     * 银行支行名
     */
    private String branchname;

    /**
     * 银行编码
     */
    private String bankcode;

    /**
     * 结算单流水号
     */
    private String orderid;

    /**
     * 商户侧提交结算单号
     */
    private String outorderid;

    /**
     * 上游结算单号
     */
    private String uporderid;

    /**
     * 结算通道编码
     */
    private String channelcode;

    /**
     * 结算类型:1普通结算 2代付结算
     */
    private Byte type;

    /**
     * 结算备注信息
     */
    private String comment;

    /**
     * 体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算
     */
    private Byte status;

    /**
     * 扩展银行卡信息,json格式
     */
    private String extend;
}
