package com.chaotu.pay.service;

import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.UserVo;

import java.text.ParseException;
import java.util.List;

/**
 * @Description:钱包管理
 * @Author: yaochenglong
 * @Date: 16:55 2018/10/21
 */
public interface WalletService {
    /***
     * 增减余额金额
     * @param wallet
     * @param amount
     * @param option
     * @return
     */
    int editAmount(TWallet wallet, String amount, String option);

}
