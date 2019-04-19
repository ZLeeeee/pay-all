package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.constant.CommonStatus;
import com.chaotu.pay.dao.TAccountPhonesMapper;
import com.chaotu.pay.dao.TPermissionMapper;
import com.chaotu.pay.dao.TUserMapper;
import com.chaotu.pay.dao.TUserRoleMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.*;
import com.chaotu.pay.service.AccountPhonesService;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class AccountPhoneServiceImpl implements AccountPhonesService {


    @Autowired
    private TAccountPhonesMapper accountPhonesMapper;


    @Override
    public int countByCondition(AccountPhonesVo accountPhonesVo,SearchVo searchVo) {
        return accountPhonesMapper.countByCondition(accountPhonesVo,searchVo);
    }

    @Override
    public void addAccountPhonesVo(AccountPhonesVo vo) {
        TAccountPhones po = new TAccountPhones();
        BeanUtils.copyProperties(vo,po);
        accountPhonesMapper.insert(po);
    }

    @Override
    public void delAccountPhonesVo(String id) {
        log.info("删除账户，输入参数[id=" + id + "]");
        accountPhonesMapper.deleteByPrimaryKey(id);
        Example example = new Example(TAccountPhones.class);
        example.createCriteria().andEqualTo("id", id);
        accountPhonesMapper.deleteByExample(example);
        log.info("删除账户成功");
    }

    @Override
    public void editAccountPhonesVo(AccountPhonesVo vo) {
        TAccountPhones po = new TAccountPhones();
        BeanUtils.copyProperties(vo,po);
        accountPhonesMapper.updateByPrimaryKey(po);
    }


    @Override
    public AccountPhonesVo getAccountPhonesVoById(String id) {
        return null;
    }

    @Override
    public MyPageInfo<AccountPhonesVo> findByCondition(PageVo pageVo, SearchVo searchVo, AccountPhonesVo accountPhonesVo) throws ParseException {


        PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize(), true);
        List<TAccountPhones> tUsers = accountPhonesMapper.findByCondition(accountPhonesVo,searchVo);
        int count = accountPhonesMapper.countByCondition(accountPhonesVo,searchVo);

        MyPageInfo info = new MyPageInfo(tUsers);
        if(!CollectionUtils.isEmpty(tUsers)){
            info.setTotalElements(count);
            info.setPageNum(pageVo.getPageNumber());
        }
        return info;

    }


}
