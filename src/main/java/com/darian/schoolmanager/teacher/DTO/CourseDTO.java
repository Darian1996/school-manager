package com.darian.schoolmanager.teacher.DTO;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  9:51
 */
@Data
public class CourseDTO {

    private Long id;

    private String name;

    private String code;
    
    private Integer isDeleted;
}
