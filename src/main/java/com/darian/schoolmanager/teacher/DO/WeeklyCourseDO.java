package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import com.darian.schoolmanager.configuration.exception.AssertionException;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/27  23:15
 */
@Data
@TableName("t_weekly_course")
public class WeeklyCourseDO extends DataBaseModel<WeeklyCourseDO> {

    private String bizType;

    private Long bizId;

    private Integer orderId;

    private String name;

    private Boolean one;

    private Boolean two;

    private Boolean three;

    private Boolean four;

    private Boolean five;

    private Boolean six;

    private Boolean seven;

    //private Boolean eight;
    //
    //private Boolean nine;
    //
    //private Boolean ten;

    public Boolean getFieldValue(String fieldName) {
        if ("one".equals(fieldName)) {
            return this.one;
        } else if ("two".equals(fieldName)) {
            return this.two;
        } else if ("three".equals(fieldName)) {
            return this.three;
        } else if ("four".equals(fieldName)) {
            return this.four;
        } else if ("five".equals(fieldName)) {
            return this.five;
        } else if ("six".equals(fieldName)) {
            return this.six;
        } else if ("seven".equals(fieldName)) {
            return this.seven;
        }
        //else if ("eight".equals(fieldName)) {
        //    return this.eight;
        //} else if ("nine".equals(fieldName)) {
        //    return this.nine;
        //} else if ("ten".equals(fieldName)) {
        //    return this.ten;
        //}
        else {
            throw new AssertionException("fieldName 非法");
        }
    }

    public Integer getCourseCount() {
        Integer count = 0;
        if (Boolean.TRUE.equals(this.one)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.two)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.three)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.four)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.five)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.six)) {
            count++;
        }
        if (Boolean.TRUE.equals(this.seven)) {
            count++;
        }
        //if (Boolean.TRUE.equals(this.eight)) {
        //    count++;
        //}
        //if (Boolean.TRUE.equals(this.nine)) {
        //    count++;
        //}
        //if (Boolean.TRUE.equals(this.ten)) {
        //    count++;
        //}
        return count;
    }

    /**
     * 有不能上的课程
     *
     * @return
     */
    public boolean hasMustNotCourse() {
        if (Boolean.FALSE.equals(this.one)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.two)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.three)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.four)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.five)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.six)) {
            return true;
        }
        if (Boolean.FALSE.equals(this.seven)) {
            return true;
        }
        return false;
    }

    public static List<String> getFieldNameList() {
        return Arrays.asList("one", "two", "three", "four", "five", "six", "seven");
    }
}
