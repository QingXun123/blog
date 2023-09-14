package com.qxbase.blog.common.aspect.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface PageResponse {

	String service() default "";

	Class<?> serviceClazz() default Void.class;

	Class<?> aliaClass() default Void.class;

}

