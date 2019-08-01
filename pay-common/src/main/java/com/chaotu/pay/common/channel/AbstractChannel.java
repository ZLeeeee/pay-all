package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;
import com.chaotu.pay.vo.OrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
public abstract class AbstractChannel implements Channel {
    public AbstractChannel(TChannel channel,TChannelAccount account){
        this.channel = channel;
        this.account = account;
    }
    private TChannel channel;
    private TChannelAccount account;
    @Override
    public Object pay(OrderVo orderVo) {
        return requestUpper(orderVo, createSign(createSignMap(orderVo)));
    }
    public abstract String createSign(Map<String, Object> signParam);

    public abstract String createNotifySign(Map<String, Object> signParam, HttpServletRequest request);

    public abstract boolean checkNotify(Map<String, Object> signParam, HttpServletRequest request);

    @Override
    public abstract String getSuccessNotifyStr() ;

    abstract Map<String,Object> createSignMap(OrderVo orderVo);
    public abstract Object requestUpper(OrderVo orderVo,String sign);

    public TChannel getChannel() {
        return channel;
    }

    public void setChannel(TChannel channel) {
        this.channel = channel;
    }

    public TChannelAccount getAccount() {
        return account;
    }

    public void setAccount(TChannelAccount account) {
        this.account = account;
    }
}
