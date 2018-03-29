/**
 * 　中科金财
 * Copyright (c) 2015-2018 zkjc,Inc.All Rights Reserved.
 */
package com.inthee.sqlsession;

/**
 *
 * @author dingyi
 * @version $Id: Excutor.java, v 0.1 2018年03月25日  上午11:40 dingyi Exp $
 */
public interface Excutor {

    public <T> T query(String statement, Object parameter, Class<T> clazz);
}
