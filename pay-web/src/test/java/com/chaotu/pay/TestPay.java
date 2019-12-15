package com.chaotu.pay;


import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddMerchantSender;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestPay {
    public static void main(String[] args) {
        /*String str = "{\n" +
                "    \"userId\": \"2d7b48d58a574fc1b6b874a930829a62\",\n" +
                "    \"underOrderNo\": \"111\",\n" +
                "    \"notifyUrl\": \"http://127.0.0.1:8080/yz/order/notify\",\n" +
                "    \"amount\": \"1.00\",\n" +
                "    \"sign\": \"dce88090fae7402c9c3930266f5d92e4\",\n" +
                "    \"payTypeId\":2\n" +
                "}";*/
        String str = "{\n" +
                "    \"bank_type\":\"CCB_DEBIT\",\n" +
                "    \"cash_fee\":\"100\",\n" +
                "    \"charset\":\"UTF-8\",\n" +
                "    \"coupon_fee\":\"0\",\n" +
                "    \"device_info\":\"QR_ORDER\",\n" +
                "    \"fee_type\":\"CNY\",\n" +
                "    \"mch_id\":\"QRA7918502107PT\",\n" +
                "    \"mdiscount\":\"0\",\n" +
                "    \"nonce_str\":\"1573930506687\",\n" +
                "    \"openid\":\"ooIeqs5A69hVrSzXV-DCsooRBBXg\",\n" +
                "    \"out_trade_no\":\"P201911170254031461613\",\n" +
                "    \"out_transaction_id\":\"4200000436201911174804601475\",\n" +
                "    \"pay_result\":\"0\",\n" +
                "    \"result_code\":\"0\",\n" +
                "    \"sign_type\":\"MD5\",\n" +
                "    \"status\":\"0\",\n" +
                "    \"time_end\":\"20191117025505\",\n" +
                "    \"total_fee\":\"100\",\n" +
                "    \"trade_type\":\"pay.weixin.jspay\",\n" +
                "    \"transaction_id\":\"9551600000201911173106015890\",\n" +
                "    \"version\":\"2.0\"\n" +
                "}";
        Map<String,Object> map = JSONObject.parseObject(str,Map.class);
        SortedMap<Object,Object> sortedMap = new TreeMap<>();
        sortedMap.putAll(map);
        map.remove("sign");
        map.put("sign",DigestUtil.createSign(sortedMap,"845a3fca64520b857efc71cc3d7124cd"));
        System.out.println(JSONObject.toJSONString(map));
        String str2 = "amount=1.0000&datetime=20190814211433&memberid=10042&orderid=P201908142113042988023&returncode=00&transaction_id=20190814211343555410&key=06k1cbv7w5wq4s7ntnxjuaww8xmi106c";
        System.out.println(DigestUtils.md5Hex(str2));
    }

    @Test
    public void foo(){
        SortedMap<Object,Object> params = new TreeMap<>();
        params.put("success","1");
        params.put("orderNo","P201911302312027175124");
        params.put("amount","198.00");
        params.put("underOrderNo","1000920191130231227");
        params.put("userId","bed81b60e5de4074b86858cba4dbd239");

        String key = "0991578829954dd79e29b514068beba0";
        String sign = DigestUtil.createSign(params, key);
        params.put("sign",sign);
        System.out.println(JSONObject.toJSONString(params));

    }
}
