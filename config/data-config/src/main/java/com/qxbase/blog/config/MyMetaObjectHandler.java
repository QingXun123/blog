package com.qxbase.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author 青旬
 * @version 1.0.0
 * @since 2023-06-02
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("checkTime",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("handleTime",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
//        this.setFieldValByName("createtime",new Date(System.currentTimeMillis()), metaObject); // java.sql.Date;
//        metaObject.setValue("createtime", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("revocationTime", Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("updateTime", Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
	    //        this.setFieldValByName("revocationTime",new Date(System.currentTimeMillis()), metaObject); // java.sql.Date;
//        metaObject.setValue("revocationTime", Timestamp.valueOf(LocalDateTime.now()));
    }

}

