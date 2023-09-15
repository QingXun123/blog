package com.qxbase.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Fkh
 * @version 1.0.0
 * @since 2023-06-02
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("check_time",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("handle_time",Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
//        this.setFieldValByName("createtime",new Date(System.currentTimeMillis()), metaObject); // java.sql.Date;
//        metaObject.setValue("createtime", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("revocation_time", Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
        this.setFieldValByName("update_time", Timestamp.valueOf(LocalDateTime.now()), metaObject); // java.sql.Timestamp;
	    //        this.setFieldValByName("revocationTime",new Date(System.currentTimeMillis()), metaObject); // java.sql.Date;
//        metaObject.setValue("revocationTime", Timestamp.valueOf(LocalDateTime.now()));
    }

}

