package com.chaotu.pay.qo;

import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.UserVo;
import lombok.Data;

/**
 * @Description: 封装分页查询钱包对象
 * @Date: Created in 19:57 2018/11/2
 * @Author: yaochenglong
 */
@Data
public class WalletQo {
    PageVo pageVo;
    SearchVo searchVo;
    TWallet walletVo;
    String amount;
    String option;
}
