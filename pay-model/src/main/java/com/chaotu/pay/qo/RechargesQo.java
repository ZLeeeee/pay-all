package com.chaotu.pay.qo;

import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.RechargeVo;
import com.chaotu.pay.vo.SearchVo;
import lombok.Data;

/**
 * 封装分页查询充值记录
 */
@Data
public class RechargesQo {

    PageVo pageVo;

    SearchVo searchVo;

    RechargeVo rechargeVo;
}
