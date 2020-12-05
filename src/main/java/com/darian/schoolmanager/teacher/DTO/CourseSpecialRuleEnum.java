package com.darian.schoolmanager.teacher.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  15:41
 */
public class CourseSpecialRuleEnum {

    @Getter
    @AllArgsConstructor
    public enum CourseSpecialBizTypeEnums {

        COURSE("COURSE", "课程类别"),

        ;

        private String bizType;

        private String desc;

        private static Map<String, CourseSpecialBizTypeEnums> ENUM_MAP = new HashMap<>();

        static {
            for (CourseSpecialBizTypeEnums value : values()) {
                ENUM_MAP.put(value.getBizType(), value);
            }
        }

        public static boolean contains(String bizType) {
            return ENUM_MAP.containsKey(bizType);
        }

    }

    @Getter
    @AllArgsConstructor
    public enum CourseSpecialRuleStringEnums {

        HALF_DAY_MUST_HAVE_ONE(
                "HALF_DAY_MUST_HAVE_ONE",
                "半天必须有一节课", null),

        HALF_DAY_HAVE_ONE_OR_ZERO(
                "HALF_DAY_HAVE_ONE_OR_ZERO",
                "半天内有1节课，或者0节课", null),

        ONE_DAY_HAVE_ONE_OR_ZERO(
                "ONE_DAY_HAVE_ONE_OR_ZERO",
                "一天内有1节课，或者0节课", null),

        HALF_DAY_CONFLICT_OTHER_COURSE(
                "HALF_DAY_CONFLICT_OTHER_COURSE",
                "半天内和【】课程不能同时上", TwoCourseModule.class),

        ONE_DAY_CONFLICT_OTHER_COURSE(
                "ONE_DAY_CONFLICT_OTHER_COURSE",
                "一天内和【】课程不能同时上", TwoCourseModule.class),

        GRADE_ONLY_ONE_CLASSROOM(
                "GRADE_ONLY_ONE_CLASSROOM",
                "一个年级，这个课程只有 1 个教室", null),

        AFTERNOON_MUST_NOT_HAVE_CLASS(
                "AFTERNOON_MUST_NOT_HAVE_CLASS",
                "这个课程下午不能排课", null),

        MORNING_MUST_HAVE_ONE_OR_MORE(
                "MORNING_MUST_HAVE_ONE_OR_MORE",
                "每天上午必须排列一节课或者或者更多", null),

        MORNING_HEAD_2_MUST_NOT_HAVE(
                "MORNING_HEAD_2_MUST_NOT_HAVE",
                "每天早上前两节不能排的课", null);

        private String code;

        private String desc;

        private Class<?> clazz;

        private static Map<String, CourseSpecialRuleStringEnums> ENUM_MAP = new HashMap<>();

        static {
            if (ENUM_MAP == null) {
                ENUM_MAP = new HashMap<>();
            }
            for (CourseSpecialRuleStringEnums value : values()) {
                ENUM_MAP.put(value.getCode(), value);
            }
        }

        public static boolean contains(String code) {
            return ENUM_MAP.containsKey(code);
        }

        public static CourseSpecialRuleStringEnums getEnumByCode(String code) {
            return ENUM_MAP.get(code);
        }
    }

    @Data
    public static class TwoCourseModule {
        private Long courseId;
    }
}
