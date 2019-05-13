package com.chaotu.pay.dao;

import java.util.List;

public interface PddConfigMapper {
    /***
     * 获取所有antiContent
     * @return
     */
    List<String> getAllAntiContent();

    /***
     * 获取所有accessToken
     * @return
     */
    List<String> getAllAccessToken();

    /***
     * 获取所有createOrderToken
     * @return
     */
    List<String> getAllCreateOrderToken();
}
