package com.darian.schoolmanager.rangecourse.service.core;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.configuration.exception.AssertionException;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseLogDO;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseTaskDO;
import com.darian.schoolmanager.rangecourse.common.RangeCourseConstant;
import com.darian.schoolmanager.rangecourse.service.RangeCourseLogService;
import com.darian.schoolmanager.rangecourse.service.RangeCourseTaskService;
import com.darian.schoolmanager.rangecourse.service.core.exception.MatchRangeCourseAllCourseIdException;
import com.darian.schoolmanager.rangecourse.service.core.exception.MatchRangeCourseAllFieldNameException;
import com.darian.schoolmanager.rangecourse.service.core.exception.MatchRangeCourseException;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseEnum;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder.FilterListModule;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder.OneFilter;
import com.darian.schoolmanager.teacher.DO.ClassesCourseTeacherDO;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DO.GradeCourseDO;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import com.darian.schoolmanager.teacher.service.ClassesCourseTeacherService;
import com.darian.schoolmanager.teacher.service.ClassesService;
import com.darian.schoolmanager.teacher.service.CourseService;
import com.darian.schoolmanager.teacher.service.GradeCourseService;
import com.darian.schoolmanager.teacher.service.WeeklyCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:14
 */
@Service
public class RangeCourseService {

    private static Logger LOGGER = LoggerFactory.getLogger(RangeCourseConstant.LOGGER_NAME);

    @Resource
    private ClassesService classesService;

    @Resource
    private CourseService courseService;

    @Resource
    private RangeCourseLogService rangeCourseLogService;

    @Resource
    private RangeCourseTaskService rangeCourseTaskService;

    @Resource
    private GradeCourseService gradeCourseService;

    @Resource
    private WeeklyCourseService weeklyCourseService;

    @Resource
    private ClassesCourseTeacherService classesCourseTeacherService;

    @Resource
    private MatchRangeCourseChain matchRangeCourseChain;

    public void rangeCourseMonitor() {
        long start = System.currentTimeMillis();
        boolean success = true;
        try {
            rangeCourse();
        } catch (MatchRangeCourseAllFieldNameException | AssertionException e) {
            success = false;
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            success = false;
            LOGGER.error("RangeCourseService.rangeCourseLogAll error", e);
        } finally {
            long end = System.currentTimeMillis();
            LOGGER.info("[RangeCourseService.rangeCourseLogAll][{}][{}]",
                    end - start + "ms",
                    success);
        }
    }

    /**
     * 排课系统
     */
    private void rangeCourse() {
        boolean taskStatus = true;
        matchRangeCourseChain.initProcessor();
        RangeCourseTaskDO rangeCourseTaskDO = rangeCourseTaskService.initRangeCourseTask();
        RangeCourseContextHolder rangeCourseContextHolder = generateContentHolder(rangeCourseTaskDO);
        try {

            FilterListModule filterListModule = rangeCourseContextHolder.getFilterListModule();

            List<Long> classesIdList = filterListModule.getClassesIdList();
            OneFilter oneFilter = new OneFilter();
            oneFilter.setClassesId(classesIdList.get(0));
            oneFilter.setOrderId(filterListModule.getOrderIdList().get(0));
            oneFilter.setFieldName(filterListModule.getFieldNameList().get(0));
            //oneFilter.setCourseIdIndex(0);
            oneFilter.setCourseId(filterListModule.getFirstCourseId(oneFilter.getClassesId()));
            rangeCourseContextHolder.setOneFilter(oneFilter);

            matchAllFieldName(rangeCourseContextHolder);
        } catch (Exception e) {
            taskStatus = false;
            throw e;
        } finally {
            AssertUtils.assertTrue(Objects.nonNull(rangeCourseTaskDO), "任务没有生成");
            if (taskStatus) {
                rangeCourseTaskService.completeTask(rangeCourseTaskDO.getId());
            } else {
                rangeCourseTaskService.failTask(rangeCourseTaskDO.getId());
            }
        }
    }

    public void matchAllFieldName(RangeCourseContextHolder rangeCourseContextHolder) throws MatchRangeCourseAllFieldNameException {
        Long taskId = rangeCourseContextHolder.getRangeCourseTaskDO().getId();
        FilterListModule filterListModule = rangeCourseContextHolder.getFilterListModule();
        OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();

        long start = System.currentTimeMillis();
        boolean matchAllCourseId = true;
        try {
            matchAllCourseId(rangeCourseContextHolder);
        } catch (MatchRangeCourseAllCourseIdException e) {
            matchAllCourseId = false;
        } finally {
            long end = System.currentTimeMillis();
            LOGGER.info("[RangeCourseService.matchAllCourseId][{}][{}]",
                    end - start + "ms",
                    matchAllCourseId);
        }

        if (!matchAllCourseId) {
            // 失败的话进行回溯
            Long nextCourseId = null; // 一直找到下一个课程Id，下一个课程Id 为空的一直回溯，回溯到找到 上一个节点的课程Id不为空为止。

            while (nextCourseId == null) {
                // 此时说明上一个 fieldName 的 courseId 不正确。导致后边所有匹配不了，所以需要将上一个 fieldName 的值取出来，
                // 设置为下一个 courseId 然后再进行下边的匹配。

                // TODO: 上一个 pre 为 -1 的话，需要设置
                OneFilter preOneFilter = filterListModule.getPreOneFilter(oneFilter.getClassesId(), oneFilter.getOrderId(),
                        oneFilter.getFieldName());

                if (preOneFilter == null) {
                    // 说明已经匹配到了 fieldName 全部匹配不正确;
                    throw new MatchRangeCourseAllFieldNameException("这时候所有的匹配规则都已经失败了，所以，本次任务失败");
                }
                rangeCourseContextHolder.setOneFilter(preOneFilter);
                oneFilter = preOneFilter;
                String preFieldName = oneFilter.getFieldName();

                // 查询当前这个 preFieldName 的 courseId 的值 然后，进行 Next 往后遍历。
                RangeCourseLogDO rangeCourseLogDO = rangeCourseLogService.getOneLog(taskId, oneFilter.getClassesId(),
                        oneFilter.getOrderId());

                Long preFieldNameCourseId = rangeCourseLogDO.getFieldValue(preFieldName);
                if (Objects.nonNull(preFieldNameCourseId) && preFieldNameCourseId == -1L) {
                    // 这一节课为 学校禁止的课程，所以再往前进行移动要给节点
                    nextCourseId = null;
                } else {
                    //nextCourseId = filterListModule.getNextCourseId(preFieldNameCourseId);
                    nextCourseId = filterListModule.getNextCourseId(oneFilter.getClassesId(), preFieldNameCourseId);
                    // 需要先能得到上一个的值，然后再进行置空，然后将上一个 preFieldName 置为空
                    rangeCourseLogDO.setFieldValue(preFieldName, null);
                    rangeCourseLogService.updateById(rangeCourseLogDO);

                    // 这个年级的资源池重新添加进去
                    Long classesId = oneFilter.getClassesId();
                    List<Long> courseIdList = filterListModule.getClassesCourseIdMap().get(classesId);
                    courseIdList.add(preFieldNameCourseId);
                    LOGGER.info("classesId:[{}], courseIdList[{}]", classesId, courseIdList);
                }
            }

            oneFilter.setCourseId(nextCourseId);
            matchAllFieldName(rangeCourseContextHolder);
        } else {
            // TODO:  课程匹配成功，匹配下一个节点，然后取出来第一个 courseId 进行往下匹配。
            OneFilter nextOneFilter = filterListModule.getNextFilterFirstCourseId(oneFilter.getClassesId(),
                    oneFilter.getOrderId(), oneFilter.getFieldName());
            if (Objects.isNull(nextOneFilter)) {
                // 下一个课程没有了，说明匹配成功了。 可以直接返回了
                return;
            }
            // 设置第一个课程，进行往下匹配。
            Long nextCourseId = filterListModule.getFirstCourseId(nextOneFilter.getClassesId());
            nextOneFilter.setCourseId(nextCourseId);
            rangeCourseContextHolder.setOneFilter(nextOneFilter);
            matchAllFieldName(rangeCourseContextHolder);
        }
    }

    public void matchAllCourseId(RangeCourseContextHolder rangeCourseContextHolder)
            throws MatchRangeCourseAllCourseIdException {
        Long taskId = rangeCourseContextHolder.getRangeCourseTaskDO().getId();
        OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();

        Long classesId = oneFilter.getClassesId();
        Integer orderId = oneFilter.getOrderId();
        String fieldName = oneFilter.getFieldName();

        FilterListModule filterListModule = rangeCourseContextHolder.getFilterListModule();

        boolean matchRule = true;
        try {
            tryMatchRule(rangeCourseContextHolder);
        } catch (MatchRangeCourseException e) {
            matchRule = false;
        }
        if (!matchRule) {

            RangeCourseLogDO rangeCourseLogDO = rangeCourseLogService.getOneLog(taskId, classesId, orderId);
            rangeCourseLogDO.setFieldValue(fieldName, null);
            rangeCourseLogService.updateById(rangeCourseLogDO);

            //Long nextCourseId = filterListModule.getNextCourseId(oneFilter.getCourseId());
            //Long gradeId = rangeCourseContextHolder.getGradeId(oneFilter);
            Long nextCourseId = filterListModule.getNextCourseId(classesId, oneFilter.getCourseId());
            if (nextCourseId == null) {
                // 说明已经匹配到了最后一个课程，失败
                throw new MatchRangeCourseAllCourseIdException("所有课程匹配失败");
            }
            oneFilter.setCourseId(nextCourseId);
            matchAllCourseId(rangeCourseContextHolder);
        }
        // 这个方法最终会匹配成功一个 courseId
    }

    @Transactional(rollbackFor = Exception.class)
    public void tryMatchRule(RangeCourseContextHolder rangeCourseContextHolder) throws MatchRangeCourseException {
        Long taskId = rangeCourseContextHolder.getRangeCourseTaskDO().getId();
        FilterListModule filterListModule = rangeCourseContextHolder.getFilterListModule();

        OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();
        Long classesId = oneFilter.getClassesId();
        Integer orderId = oneFilter.getOrderId();
        String fieldName = oneFilter.getFieldName();
        Long courseId = oneFilter.getCourseId();

        /**
         * 先更新这节课，然后后边可以进行匹配总数等等，如果不可以，在进行抛出错误进行回滚的事务，用事务来进行状态的回滚
         */
        RangeCourseLogDO rangeCourseLogDO = rangeCourseLogService.getOneLog(taskId, classesId, orderId);
        rangeCourseLogDO.setFieldValue(fieldName, courseId);
        rangeCourseLogService.updateById(rangeCourseLogDO);

        // update 这节课
        MatcherRangeCourseResult matcherRangeCourseResult = matchRangeCourseChain.matchRule(rangeCourseContextHolder);
        if (MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            LOGGER.info("匹配规则通过...");
            List<Long> courseIdList = filterListModule.getClassesCourseIdMap().get(classesId);
            courseIdList.remove(courseId);
            LOGGER.info("classesId:[{}], courseIdList[{}]", classesId, courseIdList);
            return;
        } else if (MatcherRangeCourseEnum.SCHOOL_MATCHER_MUST_NOT.equals(
                matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            LOGGER.info("学校不允许...这节课排课...");
            rangeCourseLogDO.setFieldValue(fieldName, Long.valueOf(-1));
            rangeCourseLogService.updateById(rangeCourseLogDO);
            // 更新这节课，也是匹配注规则了，为特殊的成功
            return;
        } else {
            LOGGER.info("匹配规则失败...回滚...");
            throw new MatchRangeCourseException("匹配规则失败");
        }
    }

    private RangeCourseContextHolder generateContentHolder(RangeCourseTaskDO rangeCourseTaskDO) {
        RangeCourseContextHolder rangeCourseContextHolder = new RangeCourseContextHolder();

        rangeCourseContextHolder.setRangeCourseTaskDO(rangeCourseTaskDO);
        AssertUtils.assertTrue(Objects.nonNull(rangeCourseTaskDO), "任务没有生成");
        Long taskId = rangeCourseTaskDO.getId();
        AssertUtils.assertTrue(Objects.nonNull(taskId), "任务Id不能为空");

        Map<Long, ClassesDTO> allNotDeleteClassesDTOMap = classesService.getAllNotDeleteClassesDTOMap();
        Map<Long, CourseDO> allNotDeleteCourseDOMap = courseService.getAllNotDeleteMap();
        List<GradeCourseDO> allNotDeleteGradeCourseDOList = gradeCourseService.getAllNotDelete();
        List<WeeklyCourseDO> schoolWeeklyCourseDOList = weeklyCourseService.getAllSchoolList();
        List<WeeklyCourseDO> courseWeeklyCourseDOList = weeklyCourseService.getAllCourseList();
        List<WeeklyCourseDO> teacherWeeklyCourseDOList = weeklyCourseService.getAllTeacherList();

        List<ClassesCourseTeacherDO> classesCourseTeacherDOList = classesCourseTeacherService.getALLDOListNotDelete();

        rangeCourseContextHolder.setAllNotDeleteClassesDTOMap(allNotDeleteClassesDTOMap);
        rangeCourseContextHolder.setAllNotDeleteCourseDOMap(allNotDeleteCourseDOMap);
        rangeCourseContextHolder.setAllNotDeleteGradeCourseDOList(allNotDeleteGradeCourseDOList);
        rangeCourseContextHolder.setSchoolWeeklyCourseDOList(schoolWeeklyCourseDOList);
        rangeCourseContextHolder.setCourseWeeklyCourseDOList(courseWeeklyCourseDOList);
        rangeCourseContextHolder.setTeacherWeeklyCourseDOList(teacherWeeklyCourseDOList);
        rangeCourseContextHolder.setClassesCourseTeacherDOList(classesCourseTeacherDOList);

        int schoolWeeklyCourseCount = schoolWeeklyCourseDOList.stream()
                .mapToInt(WeeklyCourseDO::getCourseCount)
                .sum();
        LOGGER.info("学校一周一共上课[{}]节", schoolWeeklyCourseCount);
        rangeCourseContextHolder.setSchoolWeeklyCourseCount(schoolWeeklyCourseCount);

        rangeCourseLogService.initRangeCourseLogList(taskId, new ArrayList<>(allNotDeleteClassesDTOMap.keySet()));
        Map<Long, List<RangeCourseLogDO>> rangeCourseLogMap = rangeCourseLogService.getMapByTaskId(taskId);
        rangeCourseContextHolder.setRangeCourseLogMap(rangeCourseLogMap);

        FilterListModule filterListModule = new FilterListModule();
        filterListModule.setClassesIdList(new ArrayList<>(allNotDeleteClassesDTOMap.keySet()));
        filterListModule.setCourseIdList(new ArrayList<>(allNotDeleteCourseDOMap.keySet()));

        List<GradeCourseDO> gradeCourseDOAllList = rangeCourseContextHolder.getAllNotDeleteGradeCourseDOList();
        Map<Long, List<GradeCourseDO>> gradeCourseMap = gradeCourseDOAllList.stream()
                .collect(Collectors.groupingBy(GradeCourseDO::getGradeId));

        Map<Long, List<Long>> gradeCourseIdListMap = new HashMap<>();
        for (Entry<Long, List<GradeCourseDO>> longListEntry : gradeCourseMap.entrySet()) {
            Long gradeId = longListEntry.getKey();
            List<Long> oneGradeCourseIdList = new ArrayList<>();

            List<GradeCourseDO> oneGradeCourseDOList = longListEntry.getValue();
            for (GradeCourseDO gradeCourseDO : oneGradeCourseDOList) {
                Integer courseCount = gradeCourseDO.getCourseCount();
                for (Integer i = 0; i < courseCount; i++) {
                    Long courseId = gradeCourseDO.getCourseId();
                    oneGradeCourseIdList.add(courseId);
                }
            }

            AssertUtils.assertTrue(oneGradeCourseIdList.size() <= schoolWeeklyCourseCount, "年级生成的课程总数不能大于学校的课程总数");

            for (Integer j = oneGradeCourseIdList.size(); j < schoolWeeklyCourseCount; j++) {
                // TODO 自习课的id
                oneGradeCourseIdList.add(17L);
            }
            LOGGER.info("grade[{}]oneGradeCourseIdList.size()[{}]", gradeId, oneGradeCourseIdList.size());
            AssertUtils.assertTrue(oneGradeCourseIdList.size() == schoolWeeklyCourseCount, "年级生成的课程数据不对");
            gradeCourseIdListMap.put(gradeId, oneGradeCourseIdList);
        }

        Map<Long, List<Long>> classesCourseIdMap = new HashMap<>();
        for (ClassesDTO classesDTO : allNotDeleteClassesDTOMap.values()) {
            Long classesId = classesDTO.getId();
            Long gradeId = classesDTO.getGradeId();
            // 每个班级的下边的课程id需要重新 new 一个 list
            classesCourseIdMap.put(classesId, new ArrayList<>(gradeCourseIdListMap.get(gradeId)));
        }

        filterListModule.setClassesCourseIdMap(classesCourseIdMap);

        rangeCourseContextHolder.setFilterListModule(filterListModule);

        loggerRangeCourseContextHolder(rangeCourseContextHolder);
        return rangeCourseContextHolder;
    }

    private void loggerRangeCourseContextHolder(RangeCourseContextHolder rangeCourseContextHolder) {
        Map<Long, ClassesDTO> allNotDeleteClassesDTOMap = rangeCourseContextHolder.getAllNotDeleteClassesDTOMap();
        Map<Long, CourseDO> allNotDeleteCourseDOMap = rangeCourseContextHolder.getAllNotDeleteCourseDOMap();
        RangeCourseTaskDO rangeCourseTaskDO = rangeCourseContextHolder.getRangeCourseTaskDO();
        Map<Long, List<RangeCourseLogDO>> rangeCourseLogMap = rangeCourseContextHolder.getRangeCourseLogMap();
        FilterListModule filterListModule = rangeCourseContextHolder.getFilterListModule();

        List<GradeCourseDO> allNotDeleteGradeCourseDOList = rangeCourseContextHolder.getAllNotDeleteGradeCourseDOList();

        int schoolWeeklyCourseCount = rangeCourseContextHolder.getSchoolWeeklyCourseCount();
        LOGGER.info("学校一周一共上课[{}]节", schoolWeeklyCourseCount);

        Map<Long, List<GradeCourseDO>> gradeCourseMap = allNotDeleteGradeCourseDOList.stream()
                .collect(Collectors.groupingBy(GradeCourseDO::getGradeId));

        for (Map.Entry<Long, List<GradeCourseDO>> longListEntry : gradeCourseMap.entrySet()) {
            Long gradeId = longListEntry.getKey();
            List<GradeCourseDO> gradeCourseDOList = longListEntry.getValue();
            int gradeCourseCount = gradeCourseDOList.stream()
                    .mapToInt(GradeCourseDO::getCourseCount)
                    .sum();
            LOGGER.info("年级[{}]共需要排课[{}]节", gradeId, gradeCourseCount);
        }

        LOGGER.info("智能排课系统任务为[{}]", rangeCourseTaskDO);
        LOGGER.info("一共有[{}]个班级", allNotDeleteClassesDTOMap.size());
        LOGGER.info("一共有[{}]个课程", allNotDeleteCourseDOMap.size());
        long rangeCourseLogCount = rangeCourseLogMap.values()
                .stream()
                .flatMap(Collection::stream)
                .count();

        LOGGER.info("一共生成了[{}]条任务", rangeCourseLogCount);
        LOGGER.info("智能排课系统排课顺序链表为：[{}]", filterListModule);

    }

}
