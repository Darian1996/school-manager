package com.darian.schoolmanager.teacher.DTO;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  14:09
 */
@Data
public class CourseSpecialRuleDTO {

    private Long id;

    private String bizType;

    private Long bizId;

    private String ruleString;

    public String extStr;

    private String bizIdDesc;

    private String ruleStringDesc;

    private String extStrDesc;

    private Integer isDeleted;

}
