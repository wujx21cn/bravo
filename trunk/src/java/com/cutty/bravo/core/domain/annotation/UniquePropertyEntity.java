package com.cutty.bravo.core.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 存储实体的某个唯一性的属性，可多个，当更新或插入时，系统会检测该实体是否有该
 * 标识，有则检测里面的属性是否已经存在，已经存在说明不唯一，插入或更新数据库失败。
 *
 * <p>
 * <a href="UniquePropertyEntity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniquePropertyEntity {
	String[] fieldNames();//存储的字段表示在表中必需唯一
}
