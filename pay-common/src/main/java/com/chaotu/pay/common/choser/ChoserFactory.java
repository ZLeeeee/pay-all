package com.chaotu.pay.common.choser;



import com.chaotu.pay.dao.*;
import com.chaotu.pay.po.*;
import com.chaotu.pay.vo.AccountUppersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.entity.Example;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
public class ChoserFactory {
    @Autowired
    public ChoserFactory(TChannelMapper channelMapper){
        this.channelMapper = channelMapper;
        this.map = new ConcurrentHashMap<>();
        update();
    }
    private TChannelMapper channelMapper;
    private static Map<Integer,Choser> map;

    public Choser getChannelChooserByPayTypeId(int payTypeId){
        return map.get(payTypeId);
    }
    public void update(){
        Example example = new Example(TChannel.class);
        example.createCriteria().andEqualTo("status",1);
        Map<Integer, List<TChannel>> collect = channelMapper.
                selectByExample(example).
                stream().
                collect(Collectors.groupingBy(TChannel::getPayTypeId));
        for (Map.Entry<Integer, List<TChannel>> integerListEntry : collect.entrySet()) {
            Choser<TChannel> channelChoser = new RoundChoser<>(integerListEntry.getValue());
            map.put(integerListEntry.getKey(),channelChoser);
        }
    }
}
