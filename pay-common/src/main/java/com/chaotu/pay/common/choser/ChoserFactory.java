package com.chaotu.pay.common.choser;



import com.chaotu.pay.dao.PddConfigMapper;
import com.chaotu.pay.dao.TAccountUppersMapper;
import com.chaotu.pay.dao.TPddUserMapper;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.vo.AccountUppersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.entity.Example;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ChoserFactory {
    @Autowired
    public ChoserFactory(TAccountUppersMapper uppersMapper, PddConfigMapper configMapper, TPddUserMapper mapper){
        this.map = new ConcurrentHashMap<>();
        List<AccountUppersVo> vos = uppersMapper.findAll();
        Example example = new Example(TPddUser.class);
        example.createCriteria().andEqualTo("status",true);
        List<TPddUser> users = mapper.selectByExample(example);
        List<String> antiContents = configMapper.getAllAntiContent();
        List<String> createOrderTokens = configMapper.getAllCreateOrderToken();
        Choser upperChoser = new RoundChoser(vos);
        Choser antiContentChoser = new RoundChoser(antiContents);
        Choser createOrderTokenChoser = new RoundChoser(createOrderTokens);
        Choser pddUserChoser = new RoundChoser(users);
        map.put("pddUserChoser",pddUserChoser);
        map.put("accountUpperChroser",upperChoser);
        map.put("createOrderTokenChoser",createOrderTokenChoser);
        map.put("antiContentChoser",antiContentChoser);

    }
    private static Map<String,Choser> map;
    @Bean("accountUpperChroser")
    public Choser accountUpperChroser(){
        return map.get("accountUpperChroser");
    }
    @Bean("pddUserChoser")
    public Choser pddUserChoser(){
        return map.get("pddUserChoser");
    }
    @Bean("antiContentChoser")
    public Choser antiContentChroser(){
        return map.get("antiContentChoser");
    }
    @Bean("createOrderTokenChoser")
    public Choser createOrderTokenChoser(){
        return map.get("createOrderTokenChoser");
    }

}
