package com.chaotu.pay.service.impl;

import com.chaotu.pay.dao.TAccountUppersMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TAccountUppers;
import com.chaotu.pay.service.AccountUppersService;
import com.chaotu.pay.vo.AccountUppersVo;
import com.chaotu.pay.vo.BizException;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 通道商户
 */
@Slf4j
@Service
public class AccountUppersServiceImpl implements AccountUppersService {

    @Autowired
    private TAccountUppersMapper accountUppersMapper;

    @Override
    public MyPageInfo<AccountUppersVo> getAllAccountUppers(PageVo pageVo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TAccountUppers.class);
        Example.Criteria criteria = example.createCriteria();
        List<AccountUppersVo> accountUppersVoList = accountUppersMapper.findAll();
        int count = accountUppersMapper.selectCountByExample(example);
        MyPageInfo<AccountUppersVo> pageInfo = new MyPageInfo<>(accountUppersVoList);
        pageInfo.setTotalElements(count);
        pageInfo.setPageNum(pageVo.getPageNumber());
        return pageInfo;
    }

    @Override
    public void addAccountUppers(AccountUppersVo accountUppersVo) {

        log.info("添加通道商户，入参accountUppersVo=["+accountUppersVo.toString()+"]");
        TAccountUppers accountUppers = new TAccountUppers();
        BeanUtils.copyProperties(accountUppersVo,accountUppers);
        Example example = new Example(TAccountUppers.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account",accountUppersVo.getAccount());
        criteria.andEqualTo("privatekey",accountUppersVo.getPrivatekey());//私钥
        criteria.andEqualTo("publikey",accountUppersVo.getPublikey());//公钥
        int count = accountUppersMapper.selectCountByExample(example);
        if(count>0){//数据存在
            log.error("该商户名或私钥、公钥已存在");
            throw new BizException(ExceptionCode.DATA_AREADY_EXIST.getCode(),ExceptionCode.DATA_AREADY_EXIST.getMsg());
        }
        accountUppers.setCreateTime(new Date());
        accountUppers.setStatus("1");//默认启用状态
        accountUppersMapper.insertSelective(accountUppers);
        log.info("添加通道商户成功，参数accountUppers=["+accountUppers.toString()+"]");

    }

    @Override
    public void editAccountUppers(AccountUppersVo accountUppersVo) {

        log.info("修改通道商户，入参accountUppersVo=["+accountUppersVo.toString()+"]");
        if (accountUppersMapper.selectByPrimaryKey(accountUppersVo.getId())!=null) {//判断修改的对象是否存在

            accountUppersVo.setUpdateTime(new Date());
            accountUppersMapper.editAccountUppers(accountUppersVo);
            log.info("修改通道商户成功，参数accountUppersVo=["+accountUppersVo.toString()+"]");
        }else{
            log.info("未能找到需要删除的accountUppersVo="+accountUppersVo);
        }

    }

    @Override
    public void delAccountUppers(Integer id) {
        log.info("删除通道商户，入参id=["+id+"]");
        TAccountUppers accountUppers = accountUppersMapper.selectByPrimaryKey(id);
        if(accountUppers!=null){//判断通过id查找的通道商户是否存在
            accountUppersMapper.deleteByPrimaryKey(id);
            log.info("删除通道商户成功，参数accountUppers=["+accountUppers.toString()+"]");
        }else{
            log.info("未能找到需要删除的id="+id);
        }
    }

}
