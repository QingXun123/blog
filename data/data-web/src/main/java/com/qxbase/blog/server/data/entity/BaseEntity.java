package com.qxbase.blog.server.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-02
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField(fill = FieldFill.INSERT)
	private Timestamp createtime;
	@TableField(fill = FieldFill.UPDATE)
	private Timestamp updatetime;
	
}
