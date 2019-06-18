package com.chaotu.pay.common.sender;

public interface Sender <T>{
    T send();
    Object send(Class<T> clzz);
}
