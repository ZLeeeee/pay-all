package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TConfigParamMapper;
import com.chaotu.pay.po.TConfigParam;
import com.chaotu.pay.service.ConfigParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConfigParamServiceImpl implements ConfigParamService {
    @Autowired
    public ConfigParamServiceImpl(TConfigParamMapper mapper){
        this.mapper = mapper;
        List<TConfigParam> params = findAll();
        for (TConfigParam param:
             params) {
            MyBeanUtils.CONFIG_PARAM_MAP
                    .put(param.getParamName(),
                    param.getParamValue());
        }
    }
    TConfigParamMapper mapper;
    @Override
    public void insert(TConfigParam tConfigParam) {
        mapper.insertSelective(tConfigParam);
    }

    @Override
    public TConfigParam selectOne(TConfigParam tConfigParam) {
        return mapper.selectOne(tConfigParam);
    }

    @Override
    public List<TConfigParam> findAll() {
        return mapper.selectAll();
    }

    @Override
    public void delete(TConfigParam tConfigParam) {
        mapper.deleteByPrimaryKey(tConfigParam);
        MyBeanUtils.CONFIG_PARAM_MAP.remove(tConfigParam.getParamValue());
    }

    @Override
    public void update(TConfigParam tConfigParam) {
        mapper.updateByPrimaryKeySelective(tConfigParam);
        MyBeanUtils.CONFIG_PARAM_MAP.put(tConfigParam.getParamName(),tConfigParam.getParamValue());
    }
}
