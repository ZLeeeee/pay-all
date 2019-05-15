package com.chaotu.pay.common.choser;

import java.util.List;

public interface Choser <T>{
    T chose();
    void update(List<T> list);
}
