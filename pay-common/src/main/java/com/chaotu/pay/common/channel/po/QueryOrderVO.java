package com.chaotu.pay.common.channel.po;

public class QueryOrderVO {

    public String merchantOrderNo;//商户订单号

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    @Override
    public String toString() {
        return "QueryOrderVO{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                '}';
    }
}
