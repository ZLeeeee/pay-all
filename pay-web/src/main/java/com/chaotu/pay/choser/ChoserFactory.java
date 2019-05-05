package com.chaotu.pay.choser;



import com.chaotu.pay.dao.TAccountUppersMapper;
import com.chaotu.pay.vo.AccountUppersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChoserFactory {
    @Autowired
    public ChoserFactory(TAccountUppersMapper mapper){
        this.mapper = mapper;
        this.map = new ConcurrentHashMap<>();
        List<AccountUppersVo> vos = mapper.findAll();
        Choser choser = new RoundChoser(vos);
        map.put("roundChroser",choser);

    }
    private static Map<String,Choser> map;
    private static  TAccountUppersMapper mapper;

    public static Choser getRoundChroser(){


        return map.get("roundChroser");
    }

}
