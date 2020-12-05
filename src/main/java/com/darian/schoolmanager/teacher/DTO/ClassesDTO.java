package com.darian.schoolmanager.teacher.DTO;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/27  0:41
 */
@Data
public class ClassesDTO {

    private Long id;

    private Long gradeId;

    private Integer classesNumber;

    private String name;

    private Integer isDeleted;

    // 扩展

    private String gradeName;
}
