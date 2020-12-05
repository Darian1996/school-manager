package com.darian.schoolmanager.teacher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/29  0:35
 */
public class WeeklyCourseEnum {

    @Getter
    @AllArgsConstructor
    public enum WeeklyCourseBizTypeEnum {

        SCHOOL("SCHOOL", "学校类别"),

        COURSE("COURSE", "课程类别"),

        TEACHER("TEACHER", "老师级别"),
        ;

        private String bizType;

        private String desc;

        private static Map<String, WeeklyCourseBizTypeEnum> ENUM_MAP = new HashMap<>();

        static {
            WeeklyCourseBizTypeEnum[] values = values();
            for (WeeklyCourseBizTypeEnum value : values) {
                ENUM_MAP.put(value.getBizType(), value);
            }
        }

        public static boolean contains(String bizType) {
            return ENUM_MAP.containsKey(bizType);
        }

    }

    @Getter
    @AllArgsConstructor
    public enum WeeklyCourseOrderIdEnum {

        ORDER_1(1, "星期一"),

        ORDER_2(2, "星期二"),

        ORDER_3(3, "星期三"),

        ORDER_4(4, "星期四"),

        ORDER_5(5, "星期五"),

        //ORDER_1(1, "上午第一节课"),
        //
        //ORDER_2(2, "上午第二节课"),
        //
        //ORDER_3(3, "大课间"),
        //
        //ORDER_4(4, "上午第三节课"),
        //
        //ORDER_5(5, "上午第四节课"),
        //
        //ORDER_6(6, "午休"),
        //
        //ORDER_7(7, "下午第一节课"),
        //
        //ORDER_8(8, "下午第二节课"),
        //
        //ORDER_9(9, "下午第三节课"),
        //
        //ORDER_10(10, "下午第四节课"),

        ;

        private Integer orderId;

        private String desc;

        public static List<Integer> getOrderIdList() {
            return Arrays.asList(1, 2, 3, 4, 5);
        }

    }

}
