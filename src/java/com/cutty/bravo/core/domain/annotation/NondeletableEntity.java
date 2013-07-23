package com.cutty.bravo.core.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用来标志一个实体不可删除，有该标识的实体当出现删除操作时都会删除
 * 不成功，保障数据的安全性。
 *
 * <p>
 * <a href="UndeletableEntity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface NondeletableEntity {
	String fieldName() default "status";
	String value() default "0"; //"1"表示不可删除
}
