package com.chaotu.pay.service.impl;


import cn.hutool.db.sql.Order;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.OrderVo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public MyPageInfo<Order> getAllOrderForPage(PageVo pageVo, OrderVo orderVo) {
        PageHelper.startPage(pageVo.getPageSize(), pageVo.getPageNumber());
        return null;
    }
}
