package com.chaotu.pay.common.choser;



import com.chaotu.pay.dao.*;
import com.chaotu.pay.po.TPddAccount;
import com.chaotu.pay.po.TPddUser;
import com.chaotu.pay.po.TYzAccount;
import com.chaotu.pay.po.TYzUser;
import com.chaotu.pay.vo.AccountUppersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.entity.Example;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Configuration
public class ChoserFactory {
    @Autowired
    public ChoserFactory(TYzAccountMapper accountMapper,TYzUserMapper userMapper){
        this.map = new ConcurrentHashMap<>();
        Example example = new Example(TYzAccount.class);
        example.createCriteria().andEqualTo("status",1);
        List<TYzAccount> tYzAccounts = accountMapper.selectByExample(example);
        Example userEx = new Example(TYzUser.class);
        example.createCriteria().andEqualTo("status",1);
        List<TYzUser> tYzUsers = userMapper.selectByExample(userEx);
        Choser<TYzAccount> yzAccountChoser = new RoundChoser<>(tYzAccounts);
        Choser<TYzUser> yzUserChoser = new RoundChoser<>(tYzUsers);
        map.put("yzAccountChoser",yzAccountChoser);
        map.put("yzUserChoser",yzUserChoser);
    }
    private static Map<String,Choser> map;
    @Bean("yzAccountChoser")
    public Choser yzAccountChoser(){
        return map.get("yzAccountChoser");
    }
    @Bean("yzUserChoser")
    public Choser yzUserChoser(){
        return map.get("yzUserChoser");
    }


}
