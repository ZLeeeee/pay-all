package com.chaotu.pay.service;

import com.chaotu.pay.po.TPayType;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

public interface PayTypeService extends CRUDService<TPayType> {
    TPayType findById(int id);
    MyPageInfo findAllByPage(PageVo pageVo);
}
