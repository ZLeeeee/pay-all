package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.StringResultSender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.RequestUtil;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.mq.MsgProducer;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.qo.OrderQo;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.OrderVo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 订单管理
 * @author: chenyupeng
 * @create: 2019-04-18 10:11
 **/

@Slf4j
@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private MsgProducer producer;

    @Autowired
    private OrderService orderService;






    /**
     * 多条件分页获取订单列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllAgent(@RequestBody OrderQo orderQo){

        Map<String,Object> pageInfo = null;
        PageVo pageVo = orderQo.getPageVo();
        SearchVo searchVo = orderQo.getSearchVo();
        OrderVo orderVo = orderQo.getOrderVo();

        try {
            pageInfo = orderService.findByCondition(pageVo,searchVo,orderVo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }



    /**
     * 修改订单状态为成功
     * @param orderVo
     * @return
     */
    @PostMapping("/update")
    public Message updateStatus(@RequestBody OrderVo orderVo){
        int i = orderService.updateStatus(orderVo);
        return ResponseUtil.responseBody(i);
    }

    @PostMapping("/pay")
    public Object pay(@RequestBody OrderVo orderVo){
        try {
            /*String key = "channel:"+orderVo.getChannelId()+":"+ DateUtil.getMinute();
            Long times = jedis.incr(key);
            Long ttl = jedis.ttl(key);
            if(-1 == ttl){
                jedis.expire(key,60);
            }*/
            return orderService.pay(orderVo);
        }catch (Exception e){
            e.printStackTrace();
            Map<Object,Object > resultMap = new HashMap<>();
            resultMap.put("success","0");
            resultMap.put("msg","系统异常");
            return resultMap;
        }

    }
    @RequestMapping("/notify/{channelId}/{orderNo}")
    public String notify(/*@RequestBody Map<String,Object> params,*/ @PathVariable Long channelId,@PathVariable String orderNo, HttpServletRequest request){

        try {
            Map<String, Object> map = orderService.notify(null,orderNo, channelId,request);
            if(map!=null){
                TOrder order = (TOrder) map.remove("order");
                producer.sendAll(JSONObject.toJSONString(order));
                return (String)map.get("successParam");
            }
        }catch (Exception e){
           log.info("接收回调异常,订单:"+orderNo+"\n+通道id:"+channelId);
        }
        Map<String,Object > resultMap = new HashMap<>();
        resultMap.put("success","0");
        resultMap.put("msg","系统异常");
        return "false";
    }
    @PostMapping("/ronghe/notify")
    public String notifyRonghe( HttpServletRequest request){
        String orderNo = request.getParameter("cpOrderId");
        try {
            Map<String, Object> map = orderService.notify(null,orderNo, 7L,request);
            if(map!=null){
                TOrder order = (TOrder) map.remove("order");
                producer.sendAll(JSONObject.toJSONString(order));
                return (String)map.get("successParam");
            }
        }catch (Exception e){
            log.info("接收回调异常,订单:"+orderNo+"\n+通道id:"+7);
        }
        Map<String,Object > resultMap = new HashMap<>();
        resultMap.put("success","0");
        resultMap.put("msg","系统异常");
        return "false";
    }

    @PostMapping("/test")
    public Map<String,String> test(@RequestBody OrderVo orderVo){
        try {
            Map<String,String> map = new HashMap<String , String>();
            map.put("OUT_TRADE_NO","OUT_TRADE_NO");
            map.put("ORDER_NO","ORDER_NO");
            map.put("SUCCESS","1");
            map.put("sign","SIGN");
            map.put("NOTIFY_URL","NOTIFY_URL");
            map.put("QRCODE","QRCODE");
            map.put("AMOUNT","10");
            return map;
        }catch (Exception e){
            Map<String,String > resultMap = new HashMap<>();
            resultMap.put("success","0");
            resultMap.put("msg","系统异常");
            return resultMap;
        }

    }
    @GetMapping("/redirect")
    public String redirect(HttpServletRequest request){
        String url = "" ;
        Map<Object,Object> map = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            if(StringUtils.equals("url",name)) {
                url = value;
            }else {
                map.put(name,value);
            }
        }
        StringResultSender sender = new StringResultSender(url, RequestUtil.createPostParamStr(map),"");
        return sender.send();
    }
}
