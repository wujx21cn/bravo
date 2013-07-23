 /* com.cutty.bravo.core.dao.support.listener.PostDeleteEventListener.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-21 下午02:10:45, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support.listener;


import java.lang.reflect.InvocationTargetException;
import java.util.Set;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.hibernate.HibernateException;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.def.DefaultDeleteEventListener;

import com.cutty.bravo.core.domain.annotation.NondeletableEntity;



/**
 * 删除操作的监听器,用于根据实体上的标签确定该表的某些字段是否可以被删除,
 * 当实体有undeletable标签时为不可删除标志
 *
 * <p>
 * <a href="PostDeleteEventListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 
 */
public class DeleteListener extends DefaultDeleteEventListener {
	/**
	 * Logger for this class
	 */
	protected static final Log logger = LogFactory.getLog(DeleteListener.class);

	private static final long serialVersionUID = -668572544557041229L;
	/* (non-Javadoc)
	 * @see org.hibernate.event.def.DefaultDeleteEventListener#onDelete(org.hibernate.event.DeleteEvent, java.util.Set)
	 */
	@Override
	public void onDelete(DeleteEvent event, Set transientEntities)
			throws HibernateException {
		
		Object entityObject = event.getObject();
		
		Class entityClass = entityObject.getClass();
		if (entityObject.getClass().getName().indexOf("$$") > -1){
			try {
				entityClass = Class.forName(event.getSession().getEntityName(event.getObject()));
			} catch (ClassNotFoundException e) {
				logger.error(e);
				throw new HibernateException(e.getMessage());
				 
			}
		}
		//取得实体的undeletable标签，有该标签的实体为不可删除的实体，改其状态属性为"1"
		NondeletableEntity undeletable = (NondeletableEntity)entityClass.getAnnotation(NondeletableEntity.class);
		if(undeletable != null){
			try {
				//用该实体的undeletable标签的value值来设置该实体的status属性（默认是status）,并保存到数据库
				BeanUtils.setProperty(entityObject, undeletable.fieldName(), undeletable.value());
				 
			} catch (IllegalAccessException e) {
				logger.error(e); 
				throw new HibernateException(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e);  
				throw new HibernateException(e.getMessage());
			}

			return ;
		}

		super.onDelete(event, transientEntities);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.event.def.DefaultDeleteEventListener#onDelete(org.hibernate.event.DeleteEvent)
	 */
	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		super.onDelete(event);
	}
}


