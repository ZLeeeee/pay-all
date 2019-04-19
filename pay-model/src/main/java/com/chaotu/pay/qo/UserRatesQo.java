package com.chaotu.pay.qo;

import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.UserVo;
import lombok.Data;

/**
 * @Description: 封装分页查询用户对象
 * @Date: Created in 19:57 2018/11/2
 * @Author: yaochenglong
 */
@Data
public class UserRatesQo {
    PageVo pageVo;
    SearchVo searchVo;
    TUserRates userRatesVo;
    String userId;
}
