package com.darian.schoolmanager.common.utils;


import com.darian.schoolmanager.configuration.exception.AssertionException;

import java.util.function.Supplier;

/***
 * 断言类
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/19  8:25
 */
public class AssertUtils {

    /**
     * expresionSupplier.get() = false , throw AssertionException(msg)
     *
     * @param expresionSupplier
     * @param msg
     */
    public static void assertTrue(Supplier<Boolean> expresionSupplier, String msg) {
        assertFalse(!expresionSupplier.get(), msg);
    }

    /**
     * expresionSupplier.get() = true , throw AssertionException(msg)
     *
     * @param expresionSupplier
     * @param msg
     */
    public static void assertFalse(Supplier<Boolean> expresionSupplier, String msg) {
        assertFalse(expresionSupplier.get(), msg);
    }

    /**
     * expresion == false -> throw AssertionException(msg)
     *
     * @param expresion
     * @param msg
     */
    public static void assertTrue(boolean expresion, String msg) {
        assertFalse(!expresion, msg);
    }

    /**
     * expresion == true -> throw AssertionException(msg)
     *
     * @param expresion
     * @param msg
     */
    public static void assertFalse(boolean expresion, String msg) {
        if (expresion) {
            throw new AssertionException(msg);
        }
    }

    /**
     * throw AssertionException
     *
     * @param msg
     */
    public static void throwError(String msg) {
        throw new AssertionException(msg);
    }
}
