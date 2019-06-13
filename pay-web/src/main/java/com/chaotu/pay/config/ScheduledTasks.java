package com.chaotu.pay.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.GetSender;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.mq.MsgProducer;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.po.TYzAccount;
import com.chaotu.pay.po.TYzOrder;
import com.chaotu.pay.po.TYzUser;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.PddMerchantParamsVo;
import com.chaotu.pay.vo.PddOrderResponse;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
/*@Component
@Configurable
@EnableScheduling*/
public class ScheduledTasks {
    /*  public ScheduledTasks (MsgProducer producer, PopClient client, PopAccessTokenClient accessTokenClient,@Value("${pdd.accessToken}") String accessToken){
          this.producer = producer;
          this.client = client;
          try{

              this.accessToken = accessToken;
             *//* PddLogisticsCompaniesGetRequest request = new PddLogisticsCompaniesGetRequest();
            PddLogisticsCompaniesGetResponse response = client.syncInvoke(request);
            log.info("订单公司: "+JsonUtils.getJosnFromObject(response));

            PddRefundAddressListGetRequest getRequest = new PddRefundAddressListGetRequest();
            PddRefundAddressListGetResponse getResponse = client.syncInvoke(getRequest, accessToken);
            log.info("退货地址: "+JsonUtils.getJosnFromObject(getResponse));
            PddPmcUserPermitRequest permitRequest = new PddPmcUserPermitRequest();
            permitRequest.setTopics("pdd_trade_TradeConfirmed");
            PddPmcUserPermitResponse permitResponse = client.syncInvoke(permitRequest, accessToken);
            System.out.println("订阅消息: "+JsonUtils.getJosnFromObject(permitResponse));*//*

        }catch (Exception e){
            log.error("获取AccessToken失败",e.getMessage());
        }
    }*/
    @Value("${yz.confirmUrl}")
    private String confirmUrl;
    @Value("${yz.sentUrl}")
    private String sentUrl;
    @Value("${yz.sentUrl}")
    private String getItemIdUrl;
    @Value("${yz.getItemIdUrl}")
    private String orderListUrl;
    @Autowired
    YzAccountService accountService;
    @Autowired
    MsgProducer producer;
    /*    @Autowired
        private MsgProducer producer;*/
    private static final AtomicLong TRACKINGNUMBER = new AtomicLong(821267789900L);
    /*@Autowired
    private PopClient client;*/
    @Autowired
    private YzOrderService service;

    @Autowired
    private YzUserService userService;

    @Autowired
    private OrderService orderService;

    //发货
    @Scheduled(cron = "0/30 * * * * ? ")
    public void reportCurrentByCron() {
        List<TYzOrder> orders = service.getAllPaiedOrders();

        if (orders != null && !orders.isEmpty()) {
            orders.stream().parallel().forEach((o) -> send(o));
        }

    }

    //收货
    @Scheduled(cron = "0/30 * * * * ? ")
    public void confirmSend() {
        List<TYzOrder> orders = service.getAllSentOrders();

        if (orders != null && !orders.isEmpty()) {
            orders.stream().parallel().forEach((o) -> {
                log.info("收货开始,订单号: " + o.getOrderNo());
                TYzOrder order1 = new TYzOrder();
                order1.setId(o.getId());
                try {
                    TYzUser u = new TYzUser();
                    u.setId(o.getYzUserId());
                    TYzUser pddUser = userService.selectOne(u);
                    Map<String, String> params = new HashMap<>();
                    Sender<Map<String, Object>> sender = new PddSender<>(confirmUrl + o.getOrderNo() + "/received?pdduid=" + "", params, "");
                    Map<String, Object> result = sender.send();
                    if (result != null) {
                        order1.setStatus((byte) 4);
                        service.update(order1);
                        log.info("收货成功");
                    } else if (o.getSendTimes() < 10) {
                        log.error("收货失败,订单号:" + o.getOrderNo());
                        order1.setStatus((byte) 3);
                        order1.setSendTimes(o.getSendTimes() + 1);
                        service.update(order1);
                        order1.setYzAccountId(o.getYzAccountId());
                        order1.setOrderNo(o.getOrderNo());
                        order1.setId(o.getId());
                        send(o);
                    }
                } catch (Exception e) {
                    log.error("收货失败,订单号:" + o.getOrderNo(), e.getMessage());
                    order1.setStatus((byte) 2);
                    order1.setSendTimes(o.getSendTimes() + 1);
                    service.update(order1);
                    order1.setYzAccountId(o.getYzAccountId());
                    order1.setOrderNo(o.getOrderNo());
                    order1.setId(o.getId());
                }
                //
            });
        }

    }

    //获取已支付订单
    @Scheduled(cron = "0/5 * * * * ? ")
    public void getPaiedOrder2() {
        List<TYzAccount> accounts = accountService.findAllByStatus();
        accounts.parallelStream().forEach((a) -> getSentOrder(a));

    }
    private void getSentOrder(TYzAccount account) {
        try {
            GetSender<Map<String,Object>> sender = new GetSender<>(orderListUrl, account.getCookie());
            Map<String,Object> response = sender.send();
            if (response.get("code")!=null&&(int)response.get("code")!=0){
                account.setStatus(false);
                account.setMark((String) response.get("msg"));
                accountService.update(account);
                return;
            }
            JSONObject data = (JSONObject)response.get("data");
            JSONArray array = data.getJSONArray("list");
            if (array != null) {
                array.parallelStream().forEach((o) -> {
                    JSONObject object = (JSONObject)o;
                    TYzOrder order = service.getByOrderNo(object.get("orderNo").toString());
                    if (order != null) {
                        TYzOrder order1 = new TYzOrder();
                        order1.setId(order.getId());
                        if (order.getStatus() == 1 && order.getSendTimes() == 0) {
                            order1.setSendTimes(1);
                            service.update(order1);
                            producer.sendAll(JsonUtils.getJsonStrFromObj(order));
                        } else if (order.getCreateTime().before(new Date(System.currentTimeMillis() - 1000 * 60 * 10)) && order.getSendTimes() < 10) {
                            send(order);
                            order1.setSendTimes(order.getSendTimes() + 1);
                            service.update(order1);
                        }
                    }
                       /* order.setStatus(new Byte("2"));
                        service.updateByOrderSn(order);*/

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(TYzOrder o) {
        try {
            TYzAccount account = accountService.findByid(o.getYzAccountId());
            Map<String,Object> map = new HashMap<>();
            map.put("orderNo",o.getOrderNo());
            map.put("callSource","1");
            String postParamStr1 = RequestUtil.createPostParamStr(map);
            PddMerchantSender<Map<String,Object>> itemIdSender = new PddMerchantSender<>(getItemIdUrl,postParamStr1,account.getCookie());
            Map<String, Object> send = itemIdSender.send();
            JSONObject data = (JSONObject)send.get("data");
            JSONArray itemDetailInfoList = data.getJSONArray("item_detail_info_list");
            JSONObject itemDetailInfo = (JSONObject)itemDetailInfoList.get(0);
            String itemId = itemDetailInfo.get("item_id").toString();
            Map<String, Object> sendMap = createSendMap(o);
            sendMap.put("deliveryItems[0][itemId]",itemId);
            String postParamStr = RequestUtil.createPostParamStr(sendMap);
            PddMerchantSender<Map<String,Object>> sender = new PddMerchantSender<>(sentUrl,postParamStr,account.getCookie());
            Map<String, Object> send1 = sender.send();
            if (send1 != null &&(int)send1.get("code")==0) {
                o.setStatus((byte) 3);
                service.update(o);
            }
        } catch (Exception e) {
            o.setStatus((byte) 3);
            service.update(o);
            log.error("发货失败,订单号:" + o.getOrderNo(), e.getMessage());
        }
    }

    private static Map<String,Object> createSendMap(TYzOrder order){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo",order.getOrderNo());
        map.put("deliveryItems[0][itemId]",System.currentTimeMillis()*19787);
        map.put("deliveryItems[0][num]","1");
        map.put("deliveryInfo[deliveryType]","12");
        map.put("deliveryInfo[express][expressId]","2");
        map.put("deliveryInfo[express][expressNo]",String.valueOf(TRACKINGNUMBER.getAndIncrement()));
        map.put("wholeOrderDeliverTag","false");
        map.put("callSource","1");
        return  map;
    }

    //发货
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateTodayAmount() {
        accountService.updateTodayAmountByStatus();
        TOrder order = new TOrder();
        order.setIsHistory(1);
        orderService.updateByIsHistory(order);

    }

}