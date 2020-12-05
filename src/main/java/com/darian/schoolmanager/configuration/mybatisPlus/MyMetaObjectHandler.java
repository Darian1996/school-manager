package com.darian.schoolmanager.configuration.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  0:42
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("gmtCreated") &&
                getFieldValByName("gmtCreated", metaObject) == null) {
            setFieldValByName("gmtCreated", LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter("gmtModified") &&
                getFieldValByName("gmtModified", metaObject) == null) {
            setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter("isDeleted") &&
                getFieldValByName("isDeleted", metaObject) == null) {
            setFieldValByName("isDeleted", 0, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
    }
}
