package com.chaotu.pay;


import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.utils.DigestUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestPay {
    public static void main(String[] args) {
        String str = "{\n" +
                "    \"userId\": \"2d7b48d58a574fc1b6b874a930829a62\",\n" +
                "    \"underOrderNo\": \"111\",\n" +
                "    \"notifyUrl\": \"http://127.0.0.1:8080/yz/order/notify\",\n" +
                "    \"amount\": \"1.00\",\n" +
                "    \"sign\": \"71598D89CCE7052A4536A61360D8DCD7\",\n" +
                "    \"payTypeId\":2\n" +
                "}";
        Map<String,Object> map = JSONObject.parseObject(str,Map.class);
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        sortedMap.putAll(map);
        map.remove("sign");
        map.put("sign",DigestUtil.createSign(sortedMap,"1cc3352776504514ad4d5ae7be327c22"));
        System.out.println(JSONObject.toJSONString(map));
        String str2 = "amount=1.0000&datetime=20190814211433&memberid=10042&orderid=P201908142113042988023&returncode=00&transaction_id=20190814211343555410&key=06k1cbv7w5wq4s7ntnxjuaww8xmi106c";
        System.out.println(DigestUtils.md5Hex(str2));
    }
}
