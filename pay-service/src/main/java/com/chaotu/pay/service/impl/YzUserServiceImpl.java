package com.chaotu.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.sender.GetSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.dao.TYzUserMapper;
import com.chaotu.pay.po.TYzUser;
import com.chaotu.pay.po.TYzUserAddress;
import com.chaotu.pay.service.YzUserAddressService;
import com.chaotu.pay.service.YzUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Slf4j
@Service
public class YzUserServiceImpl implements YzUserService {

    @Value("${yz.userAddressUrl}")
    String userAddressUrl;
    @Autowired
    TYzUserMapper userMapper;
    @Autowired
    YzUserAddressService userAddressService;
    @Autowired
    @Qualifier("yzUserChoser")
    Choser<TYzUser> yzUserChoser;
    @Override
    public List<TYzUser> findByStatus() {
        Example example = new Example(TYzUser.class);
        example.createCriteria().andEqualTo("status",1);
        List<TYzUser> tYzAccounts = userMapper.selectByExample(example);
        return tYzAccounts;
    }

    @Override
    public String login(TYzUser user) {
        return null;
    }

    @Override
    public void insert(TYzUser tYzUser) {
        String cookie = tYzUser.getCookie();
        int begin = cookie.indexOf(CommonConstant.KDTSESSIONID)+CommonConstant.KDTSESSIONID.length()+1;
        int end = cookie.indexOf(";", begin);
        String kdtSessionId = cookie.substring(begin,end);
        tYzUser.setKdtSessionid(kdtSessionId);
        Sender<String> sender = new GetSender<>(userAddressUrl , tYzUser.getCookie());
        String result = (String) sender.send(String.class);
        if (StringUtils.isNotBlank(result)) {
            int beginIndex = result.indexOf(CommonConstant.ADDRESS_LIST)+CommonConstant.ADDRESS_LIST.length()+3;
            int endIndex = result.indexOf("]", beginIndex);
            String addressJson = result.substring(beginIndex,endIndex);
            TYzUserAddress tYzUserAddress = JSONObject.parseObject(addressJson, TYzUserAddress.class);
            tYzUserAddress.setAddressId(tYzUserAddress.getId());
            tYzUserAddress.setIp("1.68.32.132");
            tYzUserAddress.setRecipients(tYzUserAddress.getUserName());
            tYzUser.setId(tYzUserAddress.getUserId());
            userAddressService.insert(tYzUserAddress);
        }
        userMapper.insertSelective(tYzUser);
        yzUserChoser.update(findByStatus());
    }

    @Override
    public TYzUser selectOne(TYzUser tYzUser) {
        return userMapper.selectOne(tYzUser);
    }

    @Override
    public List<TYzUser> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public void delete(TYzUser tYzUser) {
        userMapper.deleteByPrimaryKey(tYzUser);
        yzUserChoser.update(findByStatus());
    }

    @Override
    public void update(TYzUser tYzUser) {
        userMapper.updateByPrimaryKeySelective(tYzUser);
        yzUserChoser.update(findByStatus());
    }
}
