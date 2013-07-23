package com.cutty.bravo.core.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用来存放需要记录修改的前后值的实体属性（可多个属性），实体打上本标签后在经过
 * 修改后将往数据库保存一些修改的前后信息的表.以便今后查阅使用等，保证信息的安全.
 *
 * <p>
 * <a href="AuditEntity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditEntity {
	String[] fieldNames();
}
