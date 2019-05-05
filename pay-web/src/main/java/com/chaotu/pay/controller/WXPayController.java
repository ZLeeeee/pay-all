package com.chaotu.pay.controller;

import com.chaotu.pay.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@RestController
@Transactional
@RequestMapping("/wxpay")
public class WXPayController {

    @Autowired
    private WXPayService wxPayService;

    /**
     * 订单支付异步通知
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/notify")
    public String WXPayBack(HttpServletRequest request, HttpServletResponse response){
        String resXml = "";
        try {
            InputStream is = request.getInputStream();
            //将inputstream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try{
                while((line = reader.readLine())!=null){
                    sb.append(line + "\n");
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = wxPayService.payBack(resXml);
            return result;
        } catch (Exception e) {
            log.error("手机支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }


    }

}
