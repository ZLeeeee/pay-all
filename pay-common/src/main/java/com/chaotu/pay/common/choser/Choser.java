package com.chaotu.pay.common.choser;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Choser <T>{
    T chose();
    void update(List<T> list);
    T choseByCondition(Predicate<T> predicate);
    int size();
}
