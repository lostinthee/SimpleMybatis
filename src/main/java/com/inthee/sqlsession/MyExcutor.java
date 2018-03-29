/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.sqlsession;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dingyi
 * @version $Id: MyExcutor.java, v 0.1 2018年03月25日  上午11:41 dingyi Exp $
 */
public class MyExcutor implements Excutor {

    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T query(String statement, Object parameter, Class<T> clazz) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, parameter.toString());
            set = preparedStatement.executeQuery();
            T t = clazz.newInstance();
//            User user = new User();
            while (set.next()) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if ("BigDecimal".equals(field.getType().getSimpleName())) {
                        field.set(t, set.getBigDecimal(field.getName()));
                    } else {
                        field.set(t, set.getString(field.getName()));
                    }
                }
//                user.setId(set.getString(1));
//                user.setUsername(set.getString(2));
//                user.setPassword(set.getString(3));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return xmlConfiguration.build("config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
