package com.chaotu.pay.service;

import com.chaotu.pay.vo.AccountUppersVo;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;

/**
 * 通道商户
 */
public interface AccountUppersService {

    /**
     * 分页获取通道商户列表
     * @param pageVo
     * @return
     */
    MyPageInfo<AccountUppersVo> getAllAccountUppers(PageVo pageVo);

    /**
     * 添加通道商户
     * @param accountUppersVo
     */
    void addAccountUppers(AccountUppersVo accountUppersVo);

    /**
     * 修改通道商户
     * @param accountUppersVo
     */
    void editAccountUppers(AccountUppersVo accountUppersVo);

    /**
     * 删除通道商户
     * @param id
     */
    void delAccountUppers(Integer id);

}
