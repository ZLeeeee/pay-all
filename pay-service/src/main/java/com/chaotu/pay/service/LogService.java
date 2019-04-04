package com.chaotu.pay.service;

import com.chaotu.pay.vo.LogVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.SearchVo;

public interface LogService {


    /**
     * 分页获取日志信息
     * @param pageVo
     * @return
     */
    MyPageInfo<LogVo> getAllPage(PageVo pageVo, SearchVo searchVo, LogVo logVo);

    /**
     * 保存日志
     * @param logInfoVo
     */
    void add(LogVo logInfoVo);

    void delAll();

    void delById(String s);
}
