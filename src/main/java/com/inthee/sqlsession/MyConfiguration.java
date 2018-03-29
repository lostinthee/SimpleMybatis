/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.sqlsession;

import com.inthee.config.Function;
import com.inthee.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dingyi
 * @version $Id: MyConfiguration.java, v 0.1 2018年03月25日  上午11:09 dingyi Exp $
 */
public class MyConfiguration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDaaSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml" + resource);
        }
    }

    private Connection evalDaaSource(Element node) throws ClassNotFoundException {
        if (!"database".equals(node.getName())) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> shoule contain name and value");
            }

            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }

        Class.forName(driverClassName);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    public MapperBean readMapper(String path) {
        MapperBean mapperBean = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapperBean.setInterfaceName(root.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<>();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Function function = new Function();
                Element element = (Element) iterator.next();
                String sqlType = element.getName().trim();
                String funcName = element.attributeValue("id").trim();
                String sql = element.getText().trim();
                String resultType = element.attributeValue("resultType").trim();
                function.setSqlType(sqlType);
                function.setFuncName(funcName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                function.setResultType(newInstance);
                function.setSql(sql);
                list.add(function);
            }
            mapperBean.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapperBean;
    }
}
