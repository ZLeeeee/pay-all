package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
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
import java.util.*;

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
            e.printStackTrace();
           log.info("接收回调异常,订单:"+orderNo+"\n+通道id:"+channelId);
        }
        Map<String,Object > resultMap = new HashMap<>();
        resultMap.put("success","0");
        resultMap.put("msg","系统异常");
        return "false";
    }
    @RequestMapping("/notify/chaoren/{orderNo}")
    public String notify(@RequestBody String body, @PathVariable String orderNo, HttpServletRequest request){
        try {
            log.info("body: "+body);
            request.setAttribute("body",body);
            Map<String, Object> map = orderService.notify(null,orderNo, 28L,request);
            if(map!=null){
                TOrder order = (TOrder) map.remove("order");
                producer.sendAll(JSONObject.toJSONString(order));
                return (String)map.get("successParam");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("接收回调异常,订单:"+orderNo+"\n+通道id:"+28);
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
    public Message test(@RequestBody OrderVo orderVo){
        try {
            String str = "{\n" +
                    "    \"userId\": \"2d7b48d58a574fc1b6b874a930829a62\",\n" +
                    "    \"underOrderNo\": \"111\",\n" +
                    "    \"notifyUrl\": \"http://127.0.0.1:8080/yz/order/notify\",\n" +
                    "    \"amount\": \"1.00\",\n" +
                    "    \"sign\": \"dce88090fae7402c9c3930266f5d92e4\",\n" +
                    "    \"payTypeId\":2\n" +
                    "}";
            Map<String,Object> map = JSONObject.parseObject(str,Map.class);
            SortedMap<Object,Object> sortedMap = new TreeMap<>();
            sortedMap.putAll(map);
            sortedMap.put("payTypeId",orderVo.getPayTypeId());
            sortedMap.put("amount",orderVo.getAmount());
            sortedMap.remove("sign");
            sortedMap.put("sign",DigestUtil.createSign(sortedMap,"1cc3352776504514ad4d5ae7be327c22"));
            PddMerchantSender<Map<String,Object>> merchantSender = new PddMerchantSender<>("http://localhost:8080/order/pay",sortedMap,null);
            Map<String, Object> send = merchantSender.send();
            return ResponseUtil.responseBody(send);
        }catch (Exception e){
            return ResponseUtil.responseBody("0001","系统异常");
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
    @GetMapping("/redirect/form")
    public String redirectForm(HttpServletRequest request){
        String url = request.getParameter("url");
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" ).
               append("<html>\n" ).
               append("<head>\n" ).
               append("<title>To Pay</title>\n" ).
               append("<script>\n" ).
               append("self.moveTo(0,0);\n" ).
               append("self.resizeTo(screen.availWidth,screen.availHeight);\n" ).
               append("</script>\n" ).
               append("<style> \n" ).
               append(".tabPages{\n" ).
               append("margin-top:150px;text-align:center;display:block; border:3px solid #d9d9de; padding:30px; font-size:14px;\n" ).
               append("}\n" ).
               append("</style>\n" ).
               append("</head>\n" ).
               append("<body onLoad=\"document.uncome.submit()\">\n" ).
               append("<div id=\"Content\">\n" ).
               append("  <div class=\"tabPages\">我们正在为您连接银行，请稍等......</div>\n" ).
               append("</div>\n" ).
               append("<form name=\"uncome\" action=\"").append(url).append("\" method=\"post\">\n" );
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            if(StringUtils.equals("url",name)) {
            }else {
                sb.append("<input type=\"hidden\" name=\"").append(name).append("\"  value=\"").append(value).append("\">\n" );
            }
        }
        sb.append("</form>\n" ).
                append("</body>\n" ).
                append("</html>");
        return sb.toString();
    }
}
