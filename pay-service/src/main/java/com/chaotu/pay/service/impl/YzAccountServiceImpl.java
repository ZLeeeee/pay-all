package com.chaotu.pay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.sender.GetSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.dao.TYzAccountMapper;
import com.chaotu.pay.po.TYzAccount;
import com.chaotu.pay.po.TYzGoods;
import com.chaotu.pay.service.YzAccountService;
import com.chaotu.pay.service.YzGoodsService;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class YzAccountServiceImpl implements YzAccountService {
    @Autowired
    TYzAccountMapper accountMapper;
    @Autowired
    @Qualifier("yzAccountChoser")
    Choser<TYzAccount> yzAccountChoser;
    @Value("${yz.goodsListUrl}")
    private String goodsListUrl;
    @Value("${yz.goodsDetailUrl}")
    private String goodsDetailUrl;
    @Autowired
    YzGoodsService goodsService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(TYzAccount tYzAccount) {
        String cookie = tYzAccount.getCookie();
        int begin = cookie.indexOf(CommonConstant.KDTSESSIONID)+CommonConstant.KDTSESSIONID.length()+1;
        int end = cookie.indexOf(";", begin);
        String kdtSessionId = cookie.substring(begin,end);
        int kdtBegin = cookie.indexOf(CommonConstant.KDT_ID)+CommonConstant.KDT_ID.length()+1;
        int kdtEnd = cookie.indexOf(";", kdtBegin);
        String kdtId = cookie.substring(kdtBegin,kdtEnd);
        tYzAccount.setKdtId(Integer.valueOf(kdtId));
        tYzAccount.setKdtSessionId(kdtSessionId);
        accountMapper.insertSelective(tYzAccount);
        Integer accountId = tYzAccount.getId();
        Sender<Map<String, Object>> sender = new GetSender<>(goodsListUrl+"&disPlayOnEndTime="+System.currentTimeMillis(), cookie);
        Map<String, Object> result = sender.send();
        JSONObject data = (JSONObject) result.get("data");
        JSONArray item = data.getJSONArray("items");

        item.forEach((o) -> {
            JSONObject object = (JSONObject) o;
            int goodsId = object.getInteger("itemId");
            Sender<Map<String, Object>> detailSender = new GetSender<>(goodsDetailUrl + "?id=" + goodsId, cookie);
            Map<String, Object> objectMap = detailSender.send();
            JSONObject objData = (JSONObject) objectMap.get("data");
            JSONObject dataMap = (JSONObject) objData.getJSONArray("data").get(0);
            String title = object.getString("title");
            Double price = dataMap.getDouble("price");
            JSONArray itemSkuCommonExtendModelList = dataMap.getJSONArray("item_sku_common_extend_model_list");
            JSONObject itemSkuCommonExtendModel = (JSONObject) itemSkuCommonExtendModelList.get(0);
            Integer skuId = itemSkuCommonExtendModel.getInteger("sku_id");
            TYzGoods goods = new TYzGoods();
            goods.setAmount(new BigDecimal(price));
            goods.setYzAccountId(accountId);
            goods.setGoodsId(goodsId);
            goods.setSkuId(skuId);
            goodsService.insert(goods);
        });
        tYzAccount.setStatus(false);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public TYzAccount selectOne(TYzAccount tYzAccount) {
        return accountMapper.selectOne(tYzAccount);
    }

    @Override
    public List<TYzAccount> findAll() {
        return accountMapper.selectAll();
    }

    @Override
    public void delete(TYzAccount tYzAccount) {
        accountMapper.deleteByPrimaryKey(tYzAccount);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public void update(TYzAccount tYzAccount) {
        accountMapper.updateByPrimaryKeySelective(tYzAccount);
        yzAccountChoser.update(findByStatus());
    }

    @Override
    public List<TYzAccount> findByStatus() {
        Example example = new Example(TYzAccount.class);
        example.createCriteria().andEqualTo("status", true);
        List<TYzAccount> tYzAccounts = accountMapper.selectByExample(example);
        return tYzAccounts;
    }

    @Override
    public synchronized void updateAmount(BigDecimal amount, int id) {
        TYzAccount account = findByid(id);
        BigDecimal todayAmount = account.getTodayAmount().add(amount);
        BigDecimal totalAmount = account.getTotalAmount().add(amount);
        account.setTodayAmount(todayAmount);
        account.setTotalAmount(totalAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public TYzAccount findByid(int id) {
        TYzAccount account = new TYzAccount();
        account.setId(id);
        return selectOne(account);
    }

    @Override
    public void updateTodayAmountByStatus() {
        Example example = new Example(TYzAccount.class);
        example.createCriteria().andEqualTo("status", true);
        TYzAccount account = new TYzAccount();
        account.setTodayAmount(new BigDecimal(0));
        accountMapper.updateByExampleSelective(account, example);
    }

    @Override
    public List<TYzAccount> findAllByStatus() {
        TYzAccount account = new TYzAccount();
        account.setStatus(true);
        return accountMapper.select(account);
    }

    @Override
    public MyPageInfo<TYzAccount> findAllByPage(PageVo pageVo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TYzAccount.class);
        List<TYzAccount> bankCardVoList = accountMapper.selectAll();//查询所有信息
        int count = accountMapper.selectCountByExample(example);
        MyPageInfo<TYzAccount> pageInfo = new MyPageInfo<>(bankCardVoList);
        pageInfo.setPageNum(pageVo.getPageNumber());
        pageInfo.setTotalElements(count);
        return pageInfo;
    }
}
