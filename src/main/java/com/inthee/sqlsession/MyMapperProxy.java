/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.sqlsession;

import com.inthee.config.Function;
import com.inthee.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 *
 * @author dingyi
 * @version $Id: MyMapperProxy.java, v 0.1 2018年03月25日  上午11:48 dingyi Exp $
 */
public class MyMapperProxy<T> implements InvocationHandler {

    private MySqlSession mySqlSession;

    private MyConfiguration myConfiguration;

    private String mapperPath;

    private Class<T> clazz;


    public MyMapperProxy(MySqlSession mySqlSession, MyConfiguration myConfiguration, String mapperPath, Class<T> clazz) {
        this.mySqlSession = mySqlSession;
        this.myConfiguration = myConfiguration;
        this.mapperPath = mapperPath;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MapperBean mapperBean = myConfiguration.readMapper(mapperPath);
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
                return null;
            }
            List<Function> list = mapperBean.getList();
            if (null != list && 0 != list.size()) {
                for (Function function : list) {
                    if (method.getName().equals(function.getFuncName())) {
                        return mySqlSession.selectOne(function.getSql(), String.valueOf(args[0]), clazz);
                    }
                }
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public T getMapper() {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }
}
