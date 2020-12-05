package com.darian.schoolmanager.rangecourse.service.core.exception;

public class MatchRangeCourseAllCourseIdException extends RuntimeException {

    public MatchRangeCourseAllCourseIdException(String matchFailReason) {
        super(matchFailReason);
    }

    public MatchRangeCourseAllCourseIdException(String matchFailReason, Exception e) {

        super(matchFailReason, e);
    }
}