package com.chaotu.pay.po;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "t_pay_type")
public class TPayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 支付方式编码
     */
    private String code;

    /**
     * 0禁用1启用
     */
    private Integer status;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取支付方式名称
     *
     * @return name - 支付方式名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付方式名称
     *
     * @param name 支付方式名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取支付方式编码
     *
     * @return code - 支付方式编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置支付方式编码
     *
     * @param code 支付方式编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取0禁用1启用
     *
     * @return status - 0禁用1启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0禁用1启用
     *
     * @param status 0禁用1启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取费率
     *
     * @return rate - 费率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置费率
     *
     * @param rate 费率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}