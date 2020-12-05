package com.darian.schoolmanager.rangecourse.service.core.exception;

public class MatchRangeCourseAllFieldNameException extends RuntimeException {

    public MatchRangeCourseAllFieldNameException(String matchFailReason) {
        super(matchFailReason);
    }

    public MatchRangeCourseAllFieldNameException(String matchFailReason, Exception e) {

        super(matchFailReason, e);

    }
}