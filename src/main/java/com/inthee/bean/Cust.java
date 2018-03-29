/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.bean;

import java.math.BigDecimal;

/**
 *
 * @author dingyi
 * @version $Id: Cust.java, v 0.1 2018年03月25日  下午17:31 dingyi Exp $
 */
public class Cust {
    private String id;

    private BigDecimal money;

    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "Cust{" +
                "id='" + id + '\'' +
                ", money=" + money +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
