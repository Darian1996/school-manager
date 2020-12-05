package com.darian.schoolmanager.configuration.exception;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/23  1:03
 */
public class CustomerRuntimeException extends RuntimeException {
    public CustomerRuntimeException(String msg) {
        super(msg);
    }

    public CustomerRuntimeException(String msg, Exception e) {
        super(msg, e);
    }

}
