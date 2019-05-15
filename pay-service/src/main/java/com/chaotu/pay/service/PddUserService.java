package com.chaotu.pay.service;

import com.chaotu.pay.po.TPddUser;

import java.util.List;

public interface PddUserService {
    List<TPddUser> getAll();
    void insert(TPddUser user);
    void update(TPddUser user);
    void delete(TPddUser user);
    TPddUser selectOne(TPddUser user);
    List<TPddUser> getAllByStatus(Boolean status);
}
