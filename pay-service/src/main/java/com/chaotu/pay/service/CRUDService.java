package com.chaotu.pay.service;

import java.util.List;

public interface CRUDService<T> {
    /***
     * 添加
     * @param t
     */
    void insert(T t);

    T selectOne(T t);

    List<T> findAll();

    void delete(T t);

    void update(T t);
}
