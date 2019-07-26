package com.chaotu.pay.service.impl;
import com.chaotu.pay.common.channel.Channel;
import com.chaotu.pay.common.channel.ChannelChooser;
import com.chaotu.pay.common.channel.ChannelFactory;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.redis.RedisUtils;
import com.chaotu.pay.common.sender.PayRequestSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.dao.TOrderMapper;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.po.TPayType;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.rmi.MarshalledObject;
import java.util.*;

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
    @Autowired
    private UserService userService;
    @Autowired
    private ChannelAccountService accountService;
    @Autowired
    ChannelService channelService;
    @Autowired
    PayTypeService payTypeService;
    @Autowired
    ChannelFactory channelFactory;
    @Autowired
    ChannelChooser channelChooser;
    @Autowired
    RedisUtils redisUtils;


    @Override
    public Map<String,Object> findByCondition(PageVo pageVo, SearchVo searchVo, OrderVo orderVo){


        //通过时间查询所有订单
        //如果时间为空，则为当日00:00:00至当前时间
        if(!StringUtils.isEmpty(searchVo.getStartDate())){
            orderVo.setStartTime(searchVo.getStartDate());
        }

        if(!StringUtils.isEmpty(searchVo.getEndDate())){
            orderVo.setEndTime(searchVo.getEndDate());
        }
        UserVo userVo = userService.currentUser();
        String userId = userVo.getId();

        if("682265633886208".equalsIgnoreCase(userId))
            userId = null;
        if(null == orderVo)
            orderVo = new OrderVo();

        orderVo.setUserId(userId);

        /* try {*/
        Page<Object> p = PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize());
        //获取所有订单
            List<TOrder> orderList = tOrderMapper.findAll(orderVo);
        if("682265633886208".equalsIgnoreCase(userVo.getId())){
            for (TOrder tOrder:orderList
                 ) {
                tOrder.setExtend("1");
            }
        }

            //获取订单总数量
            Map<String,Object> generalAccount = tOrderMapper.getGeneralAccount(orderVo);
            Map<String, Object> map = new HashMap<>();

        int totalCount = tOrderMapper.countByCondition(orderVo);
        if(generalAccount == null) {
            generalAccount = new HashMap<>();
            generalAccount.put("allAmount",0);
            generalAccount.put("allOrderRate",0);
            generalAccount.put("sysAmount",0);
            generalAccount.put("allAgentAmount",0);
            generalAccount.put("allUserMount",0);
        }

        generalAccount.put("successRatio",totalCount == 0?"0%":new BigDecimal(p.getTotal()*100).divide(new BigDecimal(totalCount),2,BigDecimal.ROUND_HALF_UP)+"%");
        MyPageInfo info = new MyPageInfo(orderList);
            if(!CollectionUtils.isEmpty(orderList)){
                info.setTotal(p.getTotal());
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
    public int updateStatus(OrderVo orderVo) {
        log.info("修改订单状态: 输入参数;["+orderVo+"]");
        orderVo.setStatus((byte)1);
        return tOrderMapper.updateStatus(orderVo);
    }

    @Override
    public void updateByIsHistory(TOrder order) {
        Example example = new Example(TOrder.class);
        example.createCriteria().andEqualTo("isHistory",0);
        tOrderMapper.updateByExampleSelective(order,example);
    }

    @Override
    public void insert(TOrder order) {
        tOrderMapper.insertSelective(order);
    }

    @Override
    public TOrder selectOne(TOrder order) {
        return tOrderMapper.selectOne(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object pay(OrderVo vo) {
        log.info("开始创建订单:["+ JSONObject.toJSONString(vo) +"]");
        TChannel channel1 = channelChooser.getChannel(vo.getPayTypeId());
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        if(channel1 == null){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道繁忙或无可用通道");
            log.info("创建订单失败:["+ JSONObject.toJSONString(vo) +"]");
            return sortedMap;
        }
        vo.setChannelId(channel1.getId());
        TOrder order = new TOrder();
        BeanUtils.copyProperties(vo,order);
        TChannel channel = channelService.findById(order.getChannelId());
        if(channel.getTodayAmount().compareTo(channel.getLimitAmount())>=0){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道超限额");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道:"+channel.getChannelName()+"超限额");
            return sortedMap;
        }
        if(StringUtils.equals(channel.getStatus(),CommonConstant.CHANNEL_STATUS_FALSE)){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道已关闭");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道:"+channel.getChannelName()+"已关闭");
            return sortedMap;
        }
        if(StringUtils.isNotBlank(channel.getChannelQuota())){
            String[] amounts = channel.getChannelQuota().split(",");
            boolean flag = true;
            for (int i = 0; i < amounts.length; i++) {
                if (new BigDecimal(amounts[i]).equals(order.getAmount())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if(StringUtils.equals(channel.getStatus(),CommonConstant.CHANNEL_STATUS_FALSE)){
                    sortedMap.put("success","0");
                    sortedMap.put("msg","金额不正确,正确金额:"+Arrays.toString(amounts));
                    log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道:"+order.getAccount()+"金额不正确");
                    return sortedMap;
                }
            }

        }
        TChannelAccount account = new TChannelAccount();
        account.setChannelId(channel.getId());
        account = accountService.selectOne(account);
        if(StringUtils.equals(account.getStatus(),CommonConstant.CHANNEL_STATUS_FALSE)){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道账号已关闭");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道账号:"+account.getAccount()+"已关闭");
            return sortedMap;
        }
        if(account.getTodayAmount().compareTo(account.getLimitAmount())>=0){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道账号超限额");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道账号:"+channel.getChannelName()+"超限额");
            return sortedMap;
        }
        UserVo userVo = userService.getUserById(order.getUserId());
        if(userVo.getTodayAmount().compareTo(userVo.getLimitAmount())>=0){
            sortedMap.put("success","0");
            sortedMap.put("msg","已超限额");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],用户:"+userVo.getUsername()+"超限额");
            return sortedMap;
        }
        SortedMap<Object,Object> checkSignMap = new TreeMap<>();
        checkSignMap.put("underOrderNo",order.getUnderOrderNo());
        checkSignMap.put("sign",vo.getSign());
        checkSignMap.put("amount",order.getAmount());
        checkSignMap.put("notifyUrl",order.getNotifyUrl());
        checkSignMap.put("userId",order.getUserId());
        checkSignMap.put("payTypeId",order.getPayTypeId());
        if(!DigestUtil.checkSign(checkSignMap,userVo.getSignKey())){
            sortedMap.put("success","0");
            sortedMap.put("msg","验签失败");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"验签失败]");
            return sortedMap;
        }
        /*if (checkRequestTimes(channel)) {
            sortedMap.put("success","0");
            sortedMap.put("msg","服务繁忙，请稍后再试");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"验签失败]");
            return sortedMap;
        }*/
        TPayType payType = payTypeService.findById(channel.getPayTypeId());
        String orderNo = "P"+ IDGeneratorUtils.getFlowNum();
        order.setIsNotify(CommonConstant.ORDER_STATUS_HASNT_NOTIFYED);
        order.setPayTypeId(channel.getPayTypeId());
        order.setChannelName(channel.getChannelName());
        order.setChannelId(channel.getId());
        order.setCreateTime(new Date());
        order.setOrderNo(orderNo);
        order.setPayTypeName(payType.getName());
        order.setMerchant(userVo.getMerchant());
        order.setOrderRate(userVo.getRate());
        order.setStatus(CommonConstant.ORDER_STATUS_NOT_PAIED);
        order.setUnderOrderNo(vo.getUnderOrderNo());
        order.setAccount(account.getAccount());
        /*JSONObject jso = JSONObject.parseObject(channel.getExtend());

        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_ACCOUNT_ID),account.getAccount());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_OUT_TRADE_NO),orderNo);
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_AMOUNT),order.getAmount());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_NOTIFY_URL),channel.getNotifyUrl());
        String sign = DigestUtil.createSign(sortedMap, account.getSignKey());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_SIGN),sign);*/
        vo.setUserKey(userVo.getSignKey());
        Channel c = channelFactory.getChannel(channel.getId());
        BeanUtils.copyProperties(order,vo);
        Object resultT = c.pay(vo);
        /*Sender<Map<Object, Object>> sender = null;
        if(StringUtils.equals(channel.getContentType(), CommonConstant.CONTENT_TYPE_JSON)){
            sender =  new PayRequestSender<>(channel.getRequestUrl(),sortedMap);
        }else{
            sender = new PayRequestSender<>(channel.getRequestUrl(), RequestUtil.createPostParamStr(sortedMap));
        }
        Map<Object, Object> resultT = sender.send();*/
        if(resultT == null){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道验签失败");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"通道验签失败]");
            return sortedMap;
        }
        TreeMap<Object,Object> resultMap = new TreeMap<>();
        if(resultT instanceof Map){
            resultMap.putAll((Map<String,Object>)resultT);
            order.setUpperOrderNo(resultMap.get("upperOrderNo").toString());
        }else {
            order.setUpperOrderNo("1");
        }

        insert(order);
        redisUtils.zadd(CommonConstant.CHANNEL_ZSET_KEY+channel1.getId(),new Double(System.currentTimeMillis()),order.getOrderNo());

        return resultT;
    }

    @Override
    public Map<String, Object> notify(Map<String,Object> map,String orderNo, Long channelId, HttpServletRequest request) {
        log.info("接收回调开始，订单:"+orderNo);
        Channel channel = channelFactory.getChannel(channelId);
        if (channel.checkNotify(map,request)) {
            JSONObject object = JSONObject.parseObject(channel.getChannel().getExtend());
            TOrder order = new TOrder();
            order.setOrderNo(orderNo);
            order = selectOne(order);
            Map<String,Object> result = new HashMap<>();
            result.put(object.getString(CommonConstant.PARAM_NAME_SUCCESS_KEY),object.getString(CommonConstant.PARAM_NAME_SUCCESS_VAL));
            result.put("order",order);
            log.info("接收回调结束，订单:"+order.getId()+"接收回调成功!");
            return result;
        }
        log.info("接收回调失败，订单:"+orderNo+"验签失败!");
       return null;
    }

    @Override
    public List<TOrder> findAll() {
        return tOrderMapper.selectAll();
    }

    @Override
    public void delete(TOrder order) {
        tOrderMapper.deleteByPrimaryKey(order);
    }

    @Override
    public void update(TOrder order) {
        tOrderMapper.updateByPrimaryKeySelective(order);
    }
    private boolean checkRequestTimes(TChannel channel ){
        long currentTimeMillis = System.currentTimeMillis();
        Date endTime = new Date(currentTimeMillis);
        Date startTime = new Date(currentTimeMillis-10*1000);
        Example example = new Example(TOrder.class);
        example.createCriteria().andBetween("createTime",startTime,endTime);
        return channel.getLimitTimes() <= tOrderMapper.selectCountByExample(example);
    }
}
