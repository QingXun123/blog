package com.qxbase.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class MyBatisPlusConfig {

	@Bean
	public PaginationInnerInterceptor paginationInnerInterceptor() {
		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		paginationInterceptor.setDbType(DbType.MYSQL);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setOptimizeJoin(true);
		return paginationInterceptor;
	}

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
		return mybatisPlusInterceptor;
	}

}
