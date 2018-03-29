/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee;

import com.inthee.bean.Cust;
import com.inthee.mapper.CustMapper;
import com.inthee.sqlsession.MySqlSession;

/**
 *
 * @author dingyi
 * @version $Id: TestMybatis.java, v 0.1 2018年03月25日  上午11:56 dingyi Exp $
 */
public class TestMybatis {
    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession();
        CustMapper mapper = mySqlSession.getMapper(CustMapper.class, "CustMapper.xml");
        Cust cust = mapper.getCustById("1");
        System.out.println(cust);
    }
}
