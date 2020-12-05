package com.darian.schoolmanager.teacher.DTO.request;

import com.darian.schoolmanager.common.modle.BasePageRequest;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/11  1:33
 */
@Data
public class ClassesCourseTeacherGetAllSearchRequest extends BasePageRequest {
    private Long id;

    private Long classesId;

    private Long courseId;

    private Long teacherId;
}
