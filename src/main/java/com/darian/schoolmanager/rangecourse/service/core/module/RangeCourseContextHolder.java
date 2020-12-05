package com.darian.schoolmanager.rangecourse.service.core.module;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseLogDO;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseTaskDO;
import com.darian.schoolmanager.teacher.DO.ClassesCourseTeacherDO;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DO.GradeCourseDO;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 排课系统处理上下文
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:10
 */
@Data
public class RangeCourseContextHolder {

    private int schoolWeeklyCourseCount;

    private RangeCourseTaskDO rangeCourseTaskDO;

    private Map<Long, ClassesDTO> allNotDeleteClassesDTOMap = new HashMap<>();

    private Map<Long, CourseDO> allNotDeleteCourseDOMap = new HashMap<>();

    private Map<Long, List<RangeCourseLogDO>> rangeCourseLogMap = new HashMap<>();

    private FilterListModule filterListModule;

    private OneFilter oneFilter;

    private List<GradeCourseDO> allNotDeleteGradeCourseDOList;

    private List<WeeklyCourseDO> schoolWeeklyCourseDOList;

    private List<WeeklyCourseDO> courseWeeklyCourseDOList;

    private List<WeeklyCourseDO> teacherWeeklyCourseDOList;

    /**
     * 班级对应课程对应老师的关系
     */
    private List<ClassesCourseTeacherDO> classesCourseTeacherDOList;


    @Data
    public static class FilterListModule {
        private List<Long> classesIdList;

        private List<Integer> orderIdList = WeeklyCourseEnum.WeeklyCourseOrderIdEnum.getOrderIdList();

        private List<String> fieldNameList = WeeklyCourseDO.getFieldNameList();

        private List<Long> courseIdList;

        private Map<Long, List<Long>> classesCourseIdMap = new HashMap<>();

        public Long getFirstCourseId(Long classesId) {
            int i = 0;
            Long courseId = null;
            while (courseId == null) {
                if (i >= courseIdList.size()) {
                    return null;
                }

                courseId = courseIdList.get(i);
                i++;
                if (classesCourseIdMap.get(classesId).contains(courseId)) {
                    return courseId;
                } else {
                    courseId = null;
                }
            }
            return courseId;
        }

        /**
         * 画一个矩阵
         *
         * @param classId
         * @param orderId
         * @param fieldName
         * @return
         */
        public OneFilter getPreOneFilter(Long classId, Integer orderId, String fieldName) {
            int fieldNameIndex = fieldNameList.indexOf(fieldName);
            AssertUtils.assertTrue(fieldNameIndex != -1, "找不到对应的 fieldName:" + fieldName);
            int fieldNameCursor = fieldNameIndex - 1;
            if (fieldNameCursor <= -1) {
                // 设置前一个 orderId 的最后一个 fieldName;

                int orderIdIndex = orderIdList.indexOf(orderId);
                int orderIdCurSor = orderIdIndex - 1;
                if (orderIdCurSor <= -1) {
                    // 设置上一个 classId 最后一个 orderId 最后一个 fieldName
                    int classesIdIndex = classesIdList.indexOf(classId);
                    int classesIdCurSor = classesIdIndex - 1;
                    if (classesIdCurSor <= -1) {
                        // 没有了上一个节点
                        return null;
                    }
                    OneFilter oneFilter = new OneFilter();
                    oneFilter.setClassesId(classesIdList.get(classesIdCurSor));
                    oneFilter.setOrderId(orderIdList.get(orderIdList.size() - 1)); // 最后一个 OrderIdList
                    oneFilter.setFieldName(fieldNameList.get(fieldNameList.size() - 1)); // 最后一个 fieldName;
                    return oneFilter;
                }
                OneFilter oneFilter = new OneFilter();
                oneFilter.setClassesId(classId);
                oneFilter.setOrderId(orderIdList.get(orderIdCurSor));
                oneFilter.setFieldName(fieldNameList.get(fieldNameList.size() - 1)); // 最后一个 fieldName;
                return oneFilter;
            }
            OneFilter oneFilter = new OneFilter();
            oneFilter.setClassesId(classId);
            oneFilter.setOrderId(orderId);
            oneFilter.setFieldName(fieldNameList.get(fieldNameCursor));
            return oneFilter;
        }

        public OneFilter getNextFilterFirstCourseId(Long classId, Integer orderId, String fieldName) {
            int fieldNameIndex = fieldNameList.indexOf(fieldName);
            AssertUtils.assertTrue(fieldNameIndex != -1, "找不到对应的 fieldName:" + fieldName);
            int fieldNameCursor = fieldNameIndex + 1;
            if (fieldNameCursor > (fieldNameList.size() - 1)) {
                // 设置前一个 orderId 的最后一个 fieldName;

                int orderIdIndex = orderIdList.indexOf(orderId);
                int orderIdCurSor = orderIdIndex + 1;
                if (orderIdCurSor > (orderIdList.size() - 1)) {
                    // 设置上一个 classId 最后一个 orderId 最后一个 fieldName
                    int classesIdIndex = classesIdList.indexOf(classId);
                    int classesIdCurSor = classesIdIndex + 1;
                    if (classesIdCurSor > (classesIdList.size() - 1)) {
                        // 没有了最后一个节点没有了，此时意味着已经匹配结束了。
                        return null;
                    }
                    OneFilter oneFilter = new OneFilter();
                    oneFilter.setClassesId(classesIdList.get(classesIdCurSor));
                    oneFilter.setOrderId(orderIdList.get(0)); // 第一个OrderIdList
                    oneFilter.setFieldName(fieldNameList.get(0)); // 第一个fieldName;
                    return oneFilter;
                }
                OneFilter oneFilter = new OneFilter();
                oneFilter.setClassesId(classId);
                oneFilter.setOrderId(orderIdList.get(orderIdCurSor));
                oneFilter.setFieldName(fieldNameList.get(0)); // 第一个fieldName;
                return oneFilter;
            }
            OneFilter oneFilter = new OneFilter();
            oneFilter.setClassesId(classId);
            oneFilter.setOrderId(orderId);
            oneFilter.setFieldName(fieldNameList.get(fieldNameCursor));
            return oneFilter;
        }

        @Deprecated
        public Long getNextCourseId(Long courseId) {
            int i = courseIdList.indexOf(courseId);
            AssertUtils.assertTrue(i != -1, "找不到对应的 courseId:" + courseId);
            int cursor = i + 1;
            if (cursor > (courseIdList.size() - 1)) {
                return null;
            }
            return courseIdList.get(cursor);
        }

        public Long getNextCourseId(Long classesId, Long courseId) {
            if (courseId == -1L) {
                return null;
            }
            int i = courseIdList.indexOf(courseId);
            AssertUtils.assertTrue(i != -1, "找不到对应的 courseId:" + courseId);

            int cursor = i;
            Long nextCourseId = null;
            while (nextCourseId == null) {
                cursor++;
                if (cursor > (courseIdList.size() - 1)) {
                    return null;
                }
                nextCourseId = courseIdList.get(cursor);
                if (this.classesCourseIdMap.get(classesId).contains(nextCourseId)) {
                    // 假如存在才返回
                    return nextCourseId;
                } else {
                    nextCourseId = null;
                }
            }

            return nextCourseId;
        }

    }

    @Data
    public static class OneFilter {

        private Long classesId;

        private Integer orderId;

        private String fieldName;

        private Long courseId;
    }
}
