package com.darian.schoolmanager.rangecourse.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import com.darian.schoolmanager.configuration.exception.AssertionException;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  1:01
 */
@Data
@TableName("t_range_course_log")
public class RangeCourseLogDO extends DataBaseModel<RangeCourseLogDO> {

    private Long taskId;

    private Long classesId;

    private Integer orderId;

    private String name;

    private Long one;

    private Long two;

    private Long three;

    private Long four;

    private Long five;

    private Long six;

    private Long seven;

    //private Long eight;
    //
    //private Long nine;
    //
    //private Long ten;

    public Integer getCourseCount(Long courseId) {
        // 可能会出现某些课程还没有 填充
        Integer count = 0;
        if (courseId.equals(this.one)) {
            count++;
        }
        if (courseId.equals(this.two)) {
            count++;
        }
        if (courseId.equals(this.three)) {
            count++;
        }
        if (courseId.equals(this.four)) {
            count++;
        }
        if (courseId.equals(this.five)) {
            count++;
        }
        if (courseId.equals(this.six)) {
            count++;
        }
        if (courseId.equals(this.seven)) {
            count++;
        }
        //if (courseId.equals(this.eight)) {
        //    count++;
        //}
        //if (courseId.equals(this.nine)) {
        //    count++;
        //}
        //if (courseId.equals(this.ten)) {
        //    count++;
        //}
        return count;
    }

    public Long getFieldValue(String fieldName) {
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

    public void setFieldValue(String fieldName, Long value) {
        if ("one".equals(fieldName)) {
            this.one = value;
        } else if ("two".equals(fieldName)) {
            this.two = value;
        } else if ("three".equals(fieldName)) {
            this.three = value;
        } else if ("four".equals(fieldName)) {
            this.four = value;
        } else if ("five".equals(fieldName)) {
            this.five = value;
        } else if ("six".equals(fieldName)) {
            this.six = value;
        } else if ("seven".equals(fieldName)) {
            this.seven = value;
        }
        //else if ("eight".equals(fieldName)) {
        //    this.eight = value;
        //} else if ("nine".equals(fieldName)) {
        //    this.nine = value;
        //} else if ("ten".equals(fieldName)) {
        //    this.ten = value;
        //}
        else {
            throw new AssertionException("fieldName 非法");
        }
    }
}
