/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.config;

import java.util.List;

/**
 *
 * @author dingyi
 * @version $Id: MapperBean.java, v 0.1 2018年03月25日  上午11:22 dingyi Exp $
 */
public class MapperBean {
    private String interfaceName;

    private List<Function> list;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}
