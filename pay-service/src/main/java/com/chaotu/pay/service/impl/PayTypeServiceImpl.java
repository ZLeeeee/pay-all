package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TPayTypeMapper;
import com.chaotu.pay.po.TPayType;
import com.chaotu.pay.service.PayTypeService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PayTypeServiceImpl implements PayTypeService {
    @Autowired
    TPayTypeMapper payTypeMapper;
    @Override
    public void insert(TPayType tPayType) {
        payTypeMapper.insertSelective(tPayType);
    }

    @Override
    public TPayType selectOne(TPayType tPayType) {
        return payTypeMapper.selectOne(tPayType);
    }

    @Override
    public List<TPayType> findAll() {
        return payTypeMapper.selectAll();
    }

    @Override
    public void delete(TPayType tPayType) {
        payTypeMapper.deleteByPrimaryKey(tPayType);
    }

    @Override
    public void update(TPayType tPayType) {
        payTypeMapper.updateByPrimaryKeySelective(tPayType);
    }

    @Override
    public TPayType findById(int id) {
        TPayType payType = new TPayType();
        payType.setId(id);
        return selectOne(payType);
    }

    @Override
    public MyPageInfo findAllByPage(PageVo pageVo) {
        Page<Object> p = PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize());
        List<TPayType> list = findAll();
        MyPageInfo pageInfo = new MyPageInfo(list);
        pageInfo.setTotal(p.getTotal());
        return pageInfo;
    }
}
