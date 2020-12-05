package com.darian.schoolmanager.common.modle;

import lombok.Getter;
import lombok.ToString;

/***
 * 枚举 是否删除
 * {@link DataBaseModel#getIsDeleted()}
 * {@link DataBaseModel#setIsDeleted(Integer)}
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:34
 */
@Getter
@ToString
public enum DeleteEnums {
    DELETE(1, "已删除"),

    NOT_DELETE(0, "未删除"),

    ;

    private int code;
    private String desc;

    DeleteEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (DeleteEnums deleteEnums : DeleteEnums.values()) {
            if (deleteEnums.code == code) {
                return deleteEnums.getDesc();
            }
        }
        return "";
    }
}
