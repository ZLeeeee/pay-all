package com.chaotu.pay.qo;

import com.chaotu.pay.vo.OrderVo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.chaotu.pay.vo.UserVo;
import lombok.Data;

/**
 * @description: 封装分页查询订单对象
 * @author: chenyupeng
 * @create: 2019-04-18 10:52
 **/
@Data
public class OrderQo {
    PageVo pageVo;
    SearchVo searchVo;
    OrderVo orderVo;

}
