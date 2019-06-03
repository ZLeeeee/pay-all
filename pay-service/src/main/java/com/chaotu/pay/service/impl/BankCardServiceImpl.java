package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TBankCardsMapper;
import com.chaotu.pay.dao.TBankMapper;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TBank;
import com.chaotu.pay.po.TBankCards;
import com.chaotu.pay.service.BankCradService;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.vo.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 银行卡管理
 */
@Slf4j
@Service
public class BankCardServiceImpl implements BankCradService {
    @Autowired
    private UserService userService;

    @Autowired
    private TBankCardsMapper bankCardsMapper;

    @Autowired
    private TBankMapper bankMapper;

    @Override
    public List<BankVo> getAllBank() {
        List<TBank> bankList = bankMapper.selectAll();
        List<BankVo> bankVoList = MyBeanUtils.copyList(bankList, BankVo.class);
        return bankVoList;
    }

    @Override
    public MyPageInfo<BankCardVo> findAllByPage(PageVo pageVo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TBankCards.class);
        Example.Criteria criteria = example.createCriteria();
        List<BankCardVo> bankCardVoList = bankCardsMapper.findAll();//查询所有银行卡信息
        int count = bankCardsMapper.selectCountByExample(example);
        MyPageInfo<BankCardVo> pageInfo = new MyPageInfo<>(bankCardVoList);
        pageInfo.setPageNum(pageVo.getPageNumber());
        pageInfo.setTotalElements(count);
        return pageInfo;
    }

    @Override
    public void addBankCard(BankCardVo bankCardVo) {
        log.info("添加银行卡，入参bankCardVo=["+bankCardVo.toString()+"]");
        TBankCards bankCards = new TBankCards();
        bankCards.setCreateTime(new Date());
        bankCards.setStatus("1");
        bankCards.setBankcardno(bankCardVo.getBankCardNo());
        bankCards.setBranchname(bankCardVo.getBranchName());
        bankCards.setBankId(bankCardVo.getBankId());
        bankCards.setUserId(userService.currentUser().getId());
        bankCards.setAccountname(bankCardVo.getAccountName());
        Example example = new Example(TBankCards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankcardno",bankCardVo.getBankCardNo());
        int count = bankCardsMapper.selectCountByExample(example);//保证同一张银行卡只有一条数据
        if(count>0){
            log.error("该银行卡已存在");
            throw new BizException(ExceptionCode.DATA_AREADY_EXIST.getCode(),ExceptionCode.DATA_AREADY_EXIST.getMsg());
        }
        bankCards.setCreateTime(new Date());
        bankCardsMapper.insertSelective(bankCards);
        log.info("添加银行卡成功，参数bankCards=["+bankCards.toString()+"]");

    }

    @Override
    public void editBankCard(BankCardVo bankCardVo) {

        log.info("修改银行卡，入参bankCardVo=["+bankCardVo.toString()+"]");
        bankCardVo.setUpdateTime(new Date());
        bankCardsMapper.editBankCard(bankCardVo);
        if("1".equals(bankCardVo.getStatus())){//如果修改为启用状态
            bankCardsMapper.updateStatus(bankCardVo);
        }
        log.info("修改银行卡成功，参数bankCardVo=["+bankCardVo.toString()+"]");
    }

    @Override
    public void delBankCard(String id) {
        log.info("删除银行卡，入参id=["+id+"]");
        TBankCards bankCards = bankCardsMapper.selectByPrimaryKey(id);
        if(bankCards!=null){//判断银行卡是否存在
            bankCardsMapper.deleteByPrimaryKey(id);
            log.info("删除银行卡成功，参数bankCards=["+bankCards.toString()+"]");
        }else{
            log.info("未能找到需要删除的id="+id);
        }
    }

    @Override
    public List<BankCardVo> findAllByUser() {
        BankCardVo bankCards = new BankCardVo();
        bankCards.setUserId(userService.currentUser().getId());
        return bankCardsMapper.findAllByCondition(bankCards);
    }
}
