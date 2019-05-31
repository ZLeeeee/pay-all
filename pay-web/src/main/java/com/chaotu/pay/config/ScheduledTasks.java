package com.chaotu.pay.config;

import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.mq.MsgProducer;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.po.TPddAccount;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.service.PddAccountService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.service.PddUserService;
import com.chaotu.pay.vo.PddMerchantParamsVo;
import com.chaotu.pay.vo.PddOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@Configurable
@EnableScheduling
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
    @Value("${pdd.confirmUrl}")
    private String confirmUrl;
    @Value("${pdd.cookie}")
    private String cookie;
    @Value("${pdd.sentUrl}")
    private String sentUrl;
    @Value("${pdd.accessToken}")
    private String accessToken;
    @Value("${pdd.orderListUrl}")
    private String orderListUrl;
    @Autowired
    PddAccountService accountService;
    @Autowired
    MsgProducer producer;
    /*    @Autowired
        private MsgProducer producer;*/
    private static final AtomicLong TRACKINGNUMBER = new AtomicLong(821267789900L);
    /*@Autowired
    private PopClient client;*/
    @Autowired
    private PddOrderService service;

    @Autowired
    private PddUserService userService;

    @Autowired
    private OrderService orderService;

    //发货
    @Scheduled(cron = "0/30 * * * * ? ")
    public void reportCurrentByCron() {
        List<TPddOrder> orders = service.getAllPaiedOrders();

        if (orders != null && !orders.isEmpty()) {
            orders.stream().parallel().forEach((o) -> send(o));
        }

    }

    //收货
    @Scheduled(cron = "0/30 * * * * ? ")
    public void confirmSend() {
        List<TPddOrder> orders = service.getAllSentOrders();

        if (orders != null && !orders.isEmpty()) {
            orders.stream().parallel().forEach((o) -> {
                log.info("收货开始,订单号: " + o.getOrderSn());
                TPddOrder order1 = new TPddOrder();
                order1.setId(o.getId());
                try {
                    TPddUser u = new TPddUser();
                    u.setId(o.getPddUserId());
                    TPddUser pddUser = userService.selectOne(u);
                    Map<String, String> params = new HashMap<>();
                    Sender<Map<String, Object>> sender = new PddSender<>(confirmUrl + o.getOrderSn() + "/received?pdduid=" + pddUser.getPdduid(), params, pddUser.getAccesstoken());
                    Map<String, Object> result = sender.send();
                    if (result != null) {
                        order1.setStatus((byte) 4);
                        service.edit(order1);
                        log.info("收货成功");
                    } else if (o.getSendTimes() < 10) {
                        log.error("收货失败,订单号:" + o.getOrderSn());
                        order1.setStatus((byte) 3);
                        order1.setSendTimes(o.getSendTimes() + 1);
                        service.edit(order1);
                        order1.setPddAccountId(o.getPddAccountId());
                        order1.setOrderSn(o.getOrderSn());
                        order1.setId(o.getId());
                        send(o);
                    }
                } catch (Exception e) {
                    log.error("收货失败,订单号:" + o.getOrderSn(), e.getMessage());
                    order1.setStatus((byte) 2);
                    order1.setSendTimes(o.getSendTimes() + 1);
                    service.edit(order1);
                    order1.setPddAccountId(o.getPddAccountId());
                    order1.setOrderSn(o.getOrderSn());
                    order1.setId(o.getId());
                    //send(o);
                }
                //
            });
        }

    }

    /* @Scheduled(cron = "0/5 * * * * ? ")
     public void getPaiedOrder(){
         try {
             PddOrderNumberListGetRequest request = new PddOrderNumberListGetRequest();
             request.setOrderStatus(1);
             PddOrderNumberListGetResponse response = client.syncInvoke(request, accessToken);
             if(response.getOrderSnListGetResponse()==null)
                 return;
             List<PddOrderNumberListGetResponse.OrderSnListGetResponseOrderSnListItem> orderSnList = response.getOrderSnListGetResponse().getOrderSnList();
             if (orderSnList != null) {
                 orderSnList.stream().parallel().forEach((o)->{
                     TPddOrder order = service.getByOrderSn(o.getOrderSn());
                     TPddOrder order1 = new TPddOrder();
                     order1.setId(order.getId());
                     if(order.getStatus()==1&&order.getSendTimes()==0) {
                         order1.setSendTimes(1);
                         service.edit(order1);
                         producer.sendAll(JsonUtils.getJsonStrFromObj(order));
                     }else if (order.getCreateTime().before(new Date(System.currentTimeMillis()-1000*60*10))&&order.getSendTimes()<10) {
                         send(order);
                         order1.setSendTimes(order.getSendTimes()+1);
                         service.edit(order1);
                     }
                        *//* order.setStatus(new Byte("2"));
                        service.updateByOrderSn(order);*//*

                });
            }
        }catch (Exception e){

        }
    }*/
    //获取已支付订单
    @Scheduled(cron = "0/5 * * * * ? ")
    public void getPaiedOrder2() {
        List<TPddAccount> accounts = accountService.findAllByStatus();
        accounts.parallelStream().forEach((a) -> getSentOrder(a));

    }

    //获取已支付订单
   /* @Scheduled(cron = "0/5 * * * * ? ")
    public void notifyUser(){
        List<TPddOrder> list = service.getAllByNotifyTimesAndStatus();
        list.parallelStream().forEach((a)->sendNotify(a));

    }*/
    private void sendNotify(TPddOrder order) {
        if (order == null)
            return;
        log.info("订单: " + order.getOrderSn() + "回调开始");
        TPddOrder order1 = new TPddOrder();
        order1.setId(order.getId());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("success", "1");
            params.put("orderSn", order.getId());
            params.put("amount", order.getAmount());
            params.put("userOrderSn", order.getUserOrderSn());
            params.put("userId", order.getUserId());
            Sender<Map<String, Object>> sender = new PddSender<>(order.getNotifyUrl(), params, null);
            Map<String, Object> result = sender.send();
            if (result != null) {
                order1.setNotifyTimes(6);
                service.edit(order1);
            }
        } catch (Exception e) {
            order1.setNotifyTimes(1 + order.getNotifyTimes());
            service.edit(order1);
            log.info("订单: " + order.getOrderSn() + "回调异常");
        }
        log.info("订单: " + order.getOrderSn() + "回调结束");
    }

    private void getSentOrder(TPddAccount account) {
        try {
            long now = System.currentTimeMillis() / 1000;
            Map<String, Object> params = new HashMap<>();
            params.put("isLucky", -1);
            params.put("orderType", "1");
            params.put("afterSaleType", "1");
            params.put("pageNumber", 1);
            params.put("remarkStatus", -1);
            params.put("pageSize", 30);
            params.put("source", "MMS");
            params.put("groupStartTime", now - 3600 * 24 * 30);
            params.put("groupEndTime", now);
            PddMerchantSender<PddOrderResponse> sender = new PddMerchantSender<>(orderListUrl, params, account.getCookie());
            PddOrderResponse response = sender.send(PddOrderResponse.class);
            if (response.getErrorCode() != 1000000){
                account.setStatus(false);
                account.setMark(response.getErrorMsg());
                accountService.update(account);
                return;
            }
            List<Map<String, Object>> orderSnList = response.getResult().getPageItems();
            if (orderSnList != null) {
                orderSnList.parallelStream().forEach((o) -> {
                    TPddOrder order = service.getByOrderSn(o.get("order_sn").toString());
                    if (order != null) {
                        TPddOrder order1 = new TPddOrder();
                        order1.setId(order.getId());
                        if (order.getStatus() == 1 && order.getSendTimes() == 0) {
                            order1.setSendTimes(1);
                            service.edit(order1);
                            producer.sendAll(JsonUtils.getJsonStrFromObj(order));
                        } else if (order.getCreateTime().before(new Date(System.currentTimeMillis() - 1000 * 60 * 10)) && order.getSendTimes() < 10) {
                            send(order);
                            order1.setSendTimes(order.getSendTimes() + 1);
                            service.edit(order1);
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

    private void send(TPddOrder o) {
        try {
            TPddAccount account = accountService.findByid(o.getPddAccountId());
            PddMerchantParamsVo vo = new PddMerchantParamsVo();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("orderSn", o.getOrderSn());
            params.put("shippingId", 85);
            params.put("shippingName", "圆通快递");
            params.put("trackingNumber", String.valueOf(TRACKINGNUMBER.getAndIncrement()));
            params.put("deliveryType", 0);
            params.put("returnAddressId", account.getReturnAddressId());
            params.put("importTime", 0);
            list.add(params);
            vo.setFunctionType(3);
            vo.setIsSingleShipment(1);
            vo.setOverWrite(1);
            vo.setVirtualGoods(false);
            vo.setOperateFrom("MMS");
            vo.setOrderShipRequestList(list);

            Sender<Map<String, Object>> sender = new PddMerchantSender<>(sentUrl, vo, account.getCookie());
            Map<String, Object> result = sender.send();
            if (result != null) {
                if (Boolean.valueOf(result.get("success").toString())) {
                    o.setStatus((byte) 3);
                    service.edit(o);
                }
            }
        } catch (Exception e) {
            o.setStatus((byte) 3);
            service.edit(o);
            log.error("发货失败,订单号:" + o.getOrderSn(), e.getMessage());
        }
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