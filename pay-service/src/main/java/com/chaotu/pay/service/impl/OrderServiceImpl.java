package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TOrderMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单管理
 * @author: chenyupeng
 * @create: 2019-04-18 10:14
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TOrderMapper tOrderMapper;

    @Override
    public Map<String,Object> findByCondition(PageVo pageVo, SearchVo searchVo, OrderVo orderVo){
        Example example = new Example(TOrder.class);
        Example.Criteria criteria = example.createCriteria();

        //通过时间查询所有订单
        //如果时间为空，则为当日00:00:00至当前时间
        if(!StringUtils.isEmpty(searchVo.getStartDate())){
            orderVo.setStartTime(searchVo.getStartDate());
        }

        if(!StringUtils.isEmpty(searchVo.getEndDate())){
            orderVo.setEndTime(searchVo.getStartDate());
        }


       /* try {*/
            PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
            //获取所有订单
            List<TOrder> orderList = tOrderMapper.findAll(orderVo);
            //获取订单总数量
            int count = tOrderMapper.selectCountByExample(example);
            Map<String,Object> generalAccount = tOrderMapper.getGeneralAccount(orderVo);
            Map<String, Object> map = new HashMap<>();


            MyPageInfo info = new MyPageInfo(orderList);
            if(!CollectionUtils.isEmpty(orderList)){
                info.setTotal(count);
                info.setPageNum(pageVo.getPageNumber());
            }
            map.put("pageInfo", info);
            map.put("generalAccount", generalAccount);
            log.info("查询所有订单: 输出参数;["+map+"]");
            return map;
       /* } catch (Exception e) {
            throw new BizException(ExceptionCode.UNKNOWN_ERROR);
        }*/
    }

    @Override
    public OrderVo selectOneOrderDeails(OrderVo orderVo) {
        Example example = new Example(TOrder.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("id",orderVo.getId());
        TOrder tOrder = tOrderMapper.selectOneByExample(example);
        OrderVo order = MyBeanUtils.copy(tOrder, OrderVo.class);
        log.info("查询所有订单详情: 输出参数;["+order+"]");
        return order;
    }

    @Override
    public int updateStatus(OrderVo orderVo) {
        log.info("修改订单状态: 输入参数;["+orderVo+"]");
        orderVo.setStatus((byte)1);
        return tOrderMapper.updateStatus(orderVo);
    }


}
