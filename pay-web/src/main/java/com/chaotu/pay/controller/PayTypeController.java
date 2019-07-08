package com.chaotu.pay.controller;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPayType;
import com.chaotu.pay.service.PayTypeService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 银行卡信息管理
 */
@RestController
@RequestMapping("/channel/payType")
public class PayTypeController {

    @Autowired
    private PayTypeService payTypeService;

    /**
     * 获取所有
     * @return
     */
    @GetMapping("/all")
    public Message getAll(){
        return ResponseUtil.responseBody(payTypeService.findAll());
    }

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(payTypeService.findAllByPage(pageVo));
    }


    /**
     * 添加
     * @param payType
     * @return
     */
    @PostMapping("/add")
    public Message addBankCard(@RequestBody TPayType payType){
        
        payTypeService.insert(payType);

        return ResponseUtil.responseBody("添加成功");
    }

    /**
     * 修改
     * @param payType
     * @return
     */
    @PostMapping("/update")
    public Message update(@RequestBody TPayType payType){
        payTypeService.update(payType);
        return ResponseUtil.responseBody("更新成功");
    }

    /**
     * 删除
     * @param payType
     * @return
     */
    @PostMapping("/del")
    public Message delete(@RequestBody TPayType payType){
        payTypeService.delete(payType);
        return ResponseUtil.responseBody("删除成功");
    }

}
