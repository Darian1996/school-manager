package com.darian.schoolmanager.rangecourse.service.core.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:12
 */
@AllArgsConstructor
@Getter
public enum MatcherRangeCourseEnum {

    SCHOOL_MATCHER_MUST_NOT("SCHOOL_MATCHER_MUST_NOT", "学校不允许排课"),

    MATCHER_ERROR("MATCHER_ERROR", "匹配失败"),

    MATCHER_SUCCESS("MATCHER_SUCCESS", "匹配成功"),

    ;
    private String code;

    private String desc;
}
