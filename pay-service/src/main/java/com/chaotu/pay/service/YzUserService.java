package com.chaotu.pay.service;

import com.chaotu.pay.po.TYzUser;

import java.util.List;

public interface YzUserService extends CRUDService<TYzUser> {
    List<TYzUser> findByStatus();
}
