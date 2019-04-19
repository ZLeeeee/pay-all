package com.chaotu.pay.qo;

import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.WithdrawsVo;
import lombok.Data;

/**
 * @description: 结算记录
 * @author: chenyupeng
 * @create: 2019-04-19 11:02
 **/
@Data
public class WithdrawsQo {
    private PageVo pageVo;
    private SearchVo searchVo;
    private WithdrawsVo withdrawsVo;
}
