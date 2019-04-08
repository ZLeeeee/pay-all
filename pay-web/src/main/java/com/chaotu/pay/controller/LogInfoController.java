package com.chaotu.pay.controller;

import com.chaotu.pay.annotation.SystemLog;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.service.LogService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 日志管理
 * @Date: Created in 19:37 2018/11/7
 * @Author: yaochenglong
 */

@Slf4j
@RequestMapping("/logInfo")
@RestController
public class LogInfoController {
    @Autowired
    private LogService logService;

    @PostMapping("/allPage")
    public Message getAllPage(@RequestBody LogQo logQo){
        log.info("查询日志,输入参数："+logQo.toString());
        PageVo pageVo = logQo.getPageVo();
        LogVo logInfoVo = logQo.getLogVo();
        SearchVo searchVo = logQo.getSearchVo();
        if(pageVo==null){
            throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR);
        }
        MyPageInfo<LogVo> allPage = logService.getAllPage(pageVo,searchVo, logInfoVo);
        return ResponseUtil.responseBody(allPage);
    }

    @SystemLog(description = "删除所有日志记录")
    @DeleteMapping("/delAll")
    public Message delAll(){
        logService.delAll();
        return ResponseUtil.responseBody("删除成功");
    }

    @DeleteMapping("/delByIds/{ids}")
    public Message delByIds(@PathVariable String ids){
        String[] split = ids.split(",");
        for (String s : split) {
            logService.delById(s);
        }
        return ResponseUtil.responseBody("删除成功");
    }
}
