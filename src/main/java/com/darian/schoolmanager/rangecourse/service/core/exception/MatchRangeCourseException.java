package com.darian.schoolmanager.rangecourse.service.core.exception;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:46
 */
public class MatchRangeCourseException extends RuntimeException {

    private static String EXCEPTION_FORMAT = "排课系统匹配失败：原因：[{}]";

    public MatchRangeCourseException(String matchFailReason) {

        super(String.format(EXCEPTION_FORMAT, matchFailReason));
    }

    public MatchRangeCourseException(String matchFailReason, Exception e) {

        super(String.format(EXCEPTION_FORMAT, matchFailReason), e);
    }
}
