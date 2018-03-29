/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.sqlsession;

import java.lang.reflect.Proxy;

/**
 *
 * @author dingyi
 * @version $Id: MySqlSession.java, v 0.1 2018年03月25日  上午11:39 dingyi Exp $
 */
public class MySqlSession {

    private Excutor excutor = new MyExcutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter, Class<T> clazz) {
        return excutor.query(statement, parameter, clazz);

    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz, String mapperPath) {
        MyMapperProxy<T> myMapperProxy = new MyMapperProxy<>(this, myConfiguration, mapperPath, clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, myMapperProxy);
    }
}
