package com.chaotu.pay.service.impl;

import com.chaotu.pay.common.utils.IDGeneratorUtils;
import com.chaotu.pay.common.utils.MyBeanUtils;
import com.chaotu.pay.dao.TLogMapper;
import com.chaotu.pay.po.TLog;
import com.chaotu.pay.service.LogService;
import com.chaotu.pay.vo.LogVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private TLogMapper tLogMapper;

    @Override
    public MyPageInfo<LogVo> getAllPage(PageVo pageVo, SearchVo searchVo, LogVo logVo) {
        PageHelper.startPage(pageVo.getPageNumber(),pageVo.getPageSize());
        Example example = new Example(TLog.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if(logVo!=null){
            if(!StringUtils.isEmpty(logVo.getName())){
                criteria.andLike("username","%"+logVo.getName()+"%");
            }


            if(!StringUtils.isEmpty(logVo.getIp())){
                criteria.andLike("ip","%"+logVo.getIp()+"%");
            }
            if(!StringUtils.isEmpty(logVo.getCreateBy())){
                criteria.andLike("creator","%"+logVo.getCreateBy()+"%");
            }
        }
        if(searchVo!=null){
            if(!StringUtils.isEmpty(searchVo.getStartDate())&&!StringUtils.isEmpty(searchVo.getEndDate())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    criteria.andBetween("createTime",sdf.parse(searchVo.getStartDate()+"  00:00:00"),sdf.parse(searchVo.getEndDate()+" 23:59:59"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        List<TLog> TLogList = tLogMapper.selectByExample(example);
        int count = tLogMapper.selectCountByExample(example);
        List<LogVo> LogVos = MyBeanUtils.copyList(TLogList, LogVo.class);

        MyPageInfo<LogVo> page = new MyPageInfo<>(LogVos);
        page.setTotalElements(count);
        page.setPageNum(pageVo.getPageNumber());
        return page;
    }

    @Override
    public void add(LogVo logVo) {
        log.info("添加日志，输入参数："+logVo.toString());
        TLog tLog = MyBeanUtils.copy(logVo, TLog.class);
        tLog.setId(IDGeneratorUtils.getUUID32());
        tLog.setCreateTime(new Date());
        tLog.setName(logVo.getUsername());
        tLogMapper.insertSelective(tLog);
        log.info("添加日志成功");
    }

    @Override
    public void delAll() {
        tLogMapper.deleteByExample(null);
    }

    @Override
    public void delById(String s) {
        tLogMapper.deleteByPrimaryKey(s);
    }
}
