package com.darian.schoolmanager.common.modle;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.darian.schoolmanager.configuration.serializer.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/***
 * 继承这个 {@link DataBaseModel} 数据库基础属性封装
 * 数据库基础必须有的字段
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:27
 */
@Getter
@Setter
@ToString
public class DataBaseModel<T extends Model<?>> extends Model<T> {

    @TableId
    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "gmt_created", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreated;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * {@link DeleteEnums}
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;
}
