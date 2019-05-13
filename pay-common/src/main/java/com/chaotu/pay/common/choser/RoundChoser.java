package com.chaotu.pay.common.choser;

import com.chaotu.pay.dao.TAccountUppersMapper;
import com.chaotu.pay.vo.AccountUppersVo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundChoser<T> implements Choser{
    private AtomicInteger integer;
    private List<T> list;
    public RoundChoser (List<T> list){
        this.list = list;
        this.integer = new AtomicInteger(0);
    }
    @Override
    public T chose() {
        AccountUppersVo vo = null;
        int i = integer.getAndIncrement();
        if( i >= list.size()){
            integer.set(1);
            return list.get(0);
        }
        return list.get(i);
    }
}
