package com.chaotu.pay.common.sender;

public interface Sender <T>{
    T send();
    T send(Class<T> clzz);
}
