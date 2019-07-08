package com.chaotu.pay.service.impl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import java.math.BigDecimal;
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
        /*if("682265633886208".equalsIgnoreCase(userVo.getId())){
            for (TOrder tOrder:orderList
                 ) {
                tOrder.s("1");
            }
        }*/

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
    public Map<Object, Object> pay(OrderVo vo) {
        TOrder order = new TOrder();
        BeanUtils.copyProperties(vo,order);
        log.info("开始创建订单:["+ JSONObject.toJSONString(order) +"]");
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        TChannel channel = channelService.findById(order.getChannelId());
        if(channel.getTodayAmount().compareTo(channel.getLimitAmount())<=0){
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
        if(account.getTodayAmount().compareTo(account.getLimitAmount())<=0){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道账号超限额");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"],通道账号:"+channel.getChannelName()+"超限额");
            return sortedMap;
        }
        UserVo userVo = userService.getUserById(order.getUserId());
        if(userVo.getTodayAmount().compareTo(userVo.getLimitAmount())<=0){
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
        if(DigestUtil.checkSign(checkSignMap,userVo.getSignKey())){
            sortedMap.put("success","0");
            sortedMap.put("msg","验签失败");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"验签失败]");
            return sortedMap;
        }
        TPayType payType = payTypeService.findById(channel.getPayTypeId());
        String orderNo = "P"+ IDGeneratorUtils.getFlowNum();
        order.setIsNotify(CommonConstant.ORDER_STATUS_HASNT_NOTIFYED);
        order.setPayTypeId(channel.getPayTypeId());
        order.setChannelName(channel.getChannelName());
        order.setCreateTime(new Date());
        order.setOrderNo(orderNo);
        order.setPayTypeName(payType.getName());
        order.setMerchant(userVo.getMerchant());
        order.setOrderRate(userVo.getRate());
        JSONObject jso = JSONObject.parseObject(channel.getExtend());

        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_ACCOUNT_ID),account.getId());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_OUT_TRADE_NO),orderNo);
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_AMOUNT),order.getAmount().intValue());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_NOTIFY_URL),channel.getNotifyUrl());
        String sign = DigestUtil.createSign(sortedMap, account.getSignKey());
        sortedMap.put(jso.get(CommonConstant.PARAM_NAME_SIGN),sign);
        Sender<TreeMap<Object, Object>> sender = null;
        if(StringUtils.equals(channel.getContentType(), CommonConstant.CONTENT_TYPE_JSON)){
            sender =  new PayRequestSender<>(channel.getRequestUrl(),sortedMap);
        }else{
            sender = new PayRequestSender<>(channel.getRequestUrl(), RequestUtil.createPostParamStr(sortedMap));
        }
        TreeMap<Object, Object> resultMap = sender.send();
        if(DigestUtil.checkSign(resultMap,account.getSignKey())){
            sortedMap.put("success","0");
            sortedMap.put("msg","通道验签失败");
            log.info("创建订单失败:["+ JSONObject.toJSONString(order) +"通道验签失败]");
            return sortedMap;
        }
        order.setUpperOrderNo(resultMap.get(CommonConstant.PARAM_NAME_ORDER_NO).toString());
        insert(order);
        SortedMap<Object,Object> result = new TreeMap<>();

        result.put("userId",userVo.getId());
        result.put("amount",order.getAmount());
        result.put("qrCode",resultMap.get(CommonConstant.PARAM_NAME_QRCODE));
        result.put("success","1");
        result.put("underOrderNo",order.getUnderOrderNo());
        result.put("orderNo",orderNo);
        String resultSign = DigestUtil.createSign(result,userVo.getSignKey());
        result.put("sign",resultSign);
        return result;
    }

    @Override
    public Map<String, Object> notify(Map<String, Object> params,Long channelId) {
        log.info("接收回调开始，订单:"+JSONObject.toJSONString(params));
        TChannel channel = channelService.findById(channelId);
        JSONObject object = JSONObject.parseObject(channel.getExtend());
        TOrder order = new TOrder();
        order.setOrderNo(object.getString(CommonConstant.PARAM_NAME_OUT_TRADE_NO));
        order = selectOne(order);
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        sortedMap.putAll(params);
        TChannelAccount account = new TChannelAccount();
        account.setChannelId(channel.getId());
        account = accountService.selectOne(account);
        if(DigestUtil.checkSign(sortedMap,account.getSignKey())){
            Map<String,Object> result = new HashMap<>();
            result.put(object.getString(CommonConstant.PARAM_NAME_SUCCESS_KEY),object.getString(CommonConstant.PARAM_NAME_SUCCESS_VAL));
            result.put("order",order);
            log.info("接收回调结束，订单:"+order.getId()+"接收回调成功!");
            return result;
        }
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
}
