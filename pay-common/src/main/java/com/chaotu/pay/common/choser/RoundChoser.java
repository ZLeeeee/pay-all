package com.chaotu.pay.common.choser;

import com.chaotu.pay.dao.TAccountUppersMapper;
import com.chaotu.pay.vo.AccountUppersVo;

import java.util.AbstractList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

public class RoundChoser<T> implements Choser{
    private AtomicInteger integer;
    private volatile List<T> list;
    public RoundChoser (List<T> list){
        this.list = list;
        this.integer = new AtomicInteger(0);
    }
    @Override
    public T chose() {
        int i = integer.getAndIncrement();
        if( i >= list.size()){
            integer.set(1);
            return list.get(0);
        }
        return list.get(i);
    }

    @Override
    public void update(List list) {
        this.list = list;
        integer.set(0);
    }

    @Override
    public T choseByCondition(Predicate predicate) {
        for (int i = 0; i<list.size(); i++){
            T t = chose();
            if (predicate.test(t)) {
                return t;
            }else{
                list.remove(i);
                i--;
                integer.getAndDecrement();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return list.size();
    }
}
