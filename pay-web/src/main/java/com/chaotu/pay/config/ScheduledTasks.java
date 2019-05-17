package com.chaotu.pay.config;

import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.mq.MsgProducer;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.service.PddUserService;
import com.chaotu.pay.vo.PddMerchantParamsVo;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.request.PddLogisticsOnlineCreateRequest;
import com.pdd.pop.sdk.http.api.request.PddOrderNumberListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddLogisticsOnlineCreateResponse;
import com.pdd.pop.sdk.http.api.response.PddOrderNumberListGetResponse;
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
public class ScheduledTasks{
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
    @Autowired
    MsgProducer producer;
/*    @Autowired
    private MsgProducer producer;*/
    private static final AtomicLong TRACKINGNUMBER = new AtomicLong(821267789900L);
    @Autowired
    private PopClient client;
    @Autowired
    private PddOrderService service;

    @Autowired
    private PddUserService userService;
    //每30分钟执行一次
    @Scheduled(cron = "0/30 * * * * ? ")
    public void reportCurrentByCron(){
        List<TPddOrder> orders = service.getAllPaiedOrders();

        if(orders != null && !orders.isEmpty()) {
        orders.stream().parallel().forEach((o) -> {
                /*try {
                    *//*PddLogisticsOnlineCreateRequest request = new PddLogisticsOnlineCreateRequest();
                    request.setTrackingNumber("821267789903");
                    request.setShippingId(85);
                    request.setReturnId("1_1_405326111");
                    request.setDeliveryPhone("18292568962");
                    request.setDeliveryName("李伟杰");
                    request.setDeliveryAddress("陕西省西安市雁塔区科技四路南窑头社区28排二单元701");
                    request.setDeliveryId("7338138");
                    request.setOrderSn(o.getOrderSn());
                    PddLogisticsOnlineCreateResponse response = client.syncInvoke(request, accessToken);
                    if (response.getLogisticsOnlineCreateResponse() != null) {
                        if (response.getLogisticsOnlineCreateResponse().getIsSuccess()) {
                            o.setStatus((byte) 3);
                            service.edit(o);
                        }
                    }*//*
                    PddMerchantParamsVo vo = new PddMerchantParamsVo();
                    List<Map<String,Object>> list = new ArrayList<>();
                    Map<String, Object> params = new HashMap<>();
                    params.put("orderSn", o.getOrderSn());
                    params.put("shippingId", 85);
                    params.put("shippingName", "圆通快递");
                    params.put("trackingNumber", String.valueOf(TRACKINGNUMBER.getAndIncrement()));
                    params.put("deliveryType", 0);
                    params.put("returnAddressId", "2_1_405326111");
                    params.put("importTime", 0);
                    list.add(params);
                    vo.setFunctionType(3);
                    vo.setIsSingleShipment(1);
                    vo.setOverWrite(1);
                    vo.setVirtualGoods(false);
                    vo.setOperateFrom("MMS");
                    vo.setOrderShipRequestList(list);

                    Sender<Map<String,Object>> sender = new PddMerchantSender<>(sentUrl,vo,cookie);
                    Map<String, Object> result = sender.send();
                    if (result != null) {
                        if(Boolean.valueOf(result.get("success").toString())){
                            o.setStatus((byte) 3);
                            service.edit(o);
                        }
                    }
                } catch (Exception e) {
                    o.setStatus((byte) 3);
                    service.edit(o);
                    log.error("发货失败,订单号:"+o.getOrderSn(), e.getMessage());
                }*/
            send(o);
            });
        }

    }
    @Scheduled(cron = "0/30 * * * * ? ")
    public void confirmSend(){
        List<TPddOrder> orders = service.getAllSentOrders();
        if(orders != null && !orders.isEmpty()) {
            orders.stream().parallel().forEach((o) -> {
                try {
                    TPddUser u = new TPddUser();
                    u.setId(o.getPddUserId());
                    TPddUser pddUser = userService.selectOne(u);
                    Map<String,String> params = new HashMap<>();
                    Sender<Map<String,Object>> sender = new PddSender<>(confirmUrl+o.getOrderSn()+"/received?pdduid="+pddUser.getPdduid(),params,pddUser.getAccesstoken());
                    Map<String, Object> result = sender.send();
                    if (result != null) {
                        o.setStatus((byte)4);
                        service.edit(o);
                    }
                } catch (Exception e) {
                    log.error("发货失败,订单号:"+o.getOrderSn(), e.getMessage());
                    o.setStatus((byte)4);
                    service.edit(o);
                }
                send(o);
            });
        }

    }
    @Scheduled(cron = "0/5 * * * * ? ")
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
                    if(order.getStatus()==1&&order.getSendTimes()==0) {
                        order.setSendTimes(1);
                        service.edit(order);
                        producer.sendAll(JsonUtils.getJsonStrFromObj(order));
                    }else if (order.getCreateTime().before(new Date(System.currentTimeMillis()-1000*60*10))&&order.getSendTimes()<10) {
                        send(order);
                        order.setSendTimes(order.getSendTimes()+1);
                        service.edit(order);
                    }
                       /* order.setStatus(new Byte("2"));
                        service.updateByOrderSn(order);*/

                });
            }
        }catch (Exception e){

        }
    }

    private void send(TPddOrder o){
        try {
            PddMerchantParamsVo vo = new PddMerchantParamsVo();
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("orderSn", o.getOrderSn());
            params.put("shippingId", 85);
            params.put("shippingName", "圆通快递");
            params.put("trackingNumber", String.valueOf(TRACKINGNUMBER.getAndIncrement()));
            params.put("deliveryType", 0);
            params.put("returnAddressId", "2_1_405326111");
            params.put("importTime", 0);
            list.add(params);
            vo.setFunctionType(3);
            vo.setIsSingleShipment(1);
            vo.setOverWrite(1);
            vo.setVirtualGoods(false);
            vo.setOperateFrom("MMS");
            vo.setOrderShipRequestList(list);

            Sender<Map<String,Object>> sender = new PddMerchantSender<>(sentUrl,vo,cookie);
            Map<String, Object> result = sender.send();
            if (result != null) {
                if(Boolean.valueOf(result.get("success").toString())){
                    o.setStatus((byte) 3);
                    service.edit(o);
                }
            }
        } catch (Exception e) {
            o.setStatus((byte) 3);
            service.edit(o);
            log.error("发货失败,订单号:"+o.getOrderSn(), e.getMessage());
        }
    }

}