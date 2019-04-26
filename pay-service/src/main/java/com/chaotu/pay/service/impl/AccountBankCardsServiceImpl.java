package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.DateUtil;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TAccountBankCardsMapper;
import com.chaotu.pay.dao.TAccountPhonesMapper;
import com.chaotu.pay.po.TAccountBankCards;
import com.chaotu.pay.po.TAccountPhones;
import com.chaotu.pay.po.TRole;
import com.chaotu.pay.po.TUser;
import com.chaotu.pay.service.AccountBankCardsService;
import com.chaotu.pay.service.AccountPhonesService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.IdGenerator;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class AccountBankCardsServiceImpl implements AccountBankCardsService {


    @Autowired
    private TAccountBankCardsMapper accountBankCardsMapper;

    @Override
    public List<AccountBankCardsVo> getAllAccountBankCards() {
        return null;
    }

    @Override
    public TAccountBankCards getAccountBankCardsById(Long id) {
        return accountBankCardsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addAccountBankCardsVo(AccountBankCardsVo vo) {
        Long id = Long.valueOf(IDGeneratorUtils.getFlowNum());
        log.info("增加账户["+id+"]");
        vo.setId(id);
        vo.setCreateTime(new Date());
        TAccountBankCards accountBankCards = new TAccountBankCards();
        BeanUtils.copyProperties(vo,accountBankCards);
        accountBankCardsMapper.insert(accountBankCards);
        log.info("增加结束");
    }

    @Override
    public void editAccountBankCards(AccountBankCardsVo vo) {
        TAccountBankCards accountBankCards = new TAccountBankCards();
        BeanUtils.copyProperties(vo,accountBankCards);
        accountBankCardsMapper.updateByPrimaryKey(accountBankCards);

    }

    @Override
    public MyPageInfo<AccountBankCardsVo> findByCondition(PageVo pageVo, SearchVo searchVo, AccountBankCardsVo vo) throws ParseException {
        Example example = new Example(TAccountBankCards.class);
        Example.Criteria criteria = example.createCriteria();
       if(StringUtils.isNotBlank(vo.getCardno()))
           criteria.andLike("cardNo" , vo.getCardno());
        if(StringUtils.isNotBlank(vo.getAccounttype()))
            criteria.andEqualTo("accountType" , vo.getAccounttype());

        PageHelper.startPage(pageVo.getPageNumber(), pageVo.getPageSize(), true);
        List<AccountBankCardsVo> vos = accountBankCardsMapper.findByCondition(vo);
        int count = accountBankCardsMapper.selectCountByExample(example);


        MyPageInfo info = new MyPageInfo(vos);
        if(!CollectionUtils.isEmpty(vos)){
            info.setTotalElements(count);
            info.setPageNum(pageVo.getPageNumber());
        }
        return info;
    }

    @Override
    public void delAccountPhonesVo(String id) {
        log.info("删除账户["+id+"]");
        accountBankCardsMapper.deleteByPrimaryKey(id);
        log.info("删除结束");
    }


}
