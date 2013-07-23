/* com.cutty.bravo.core.utils.HibernateConfigurationUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 6:11:01 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

/**
 *
 * <p>
 * <a href="HibernateConfigurationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class HibernateConfigurationUtil {
    private static Configuration hibernateConf = new Configuration();

    private static PersistentClass getPersistentClass(Class clazz) {

           synchronized (HibernateConfigurationUtil.class) {
                  PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());
                  if (pc == null) {
                         hibernateConf = hibernateConf.addClass(clazz);
                         pc = hibernateConf.getClassMapping(clazz.getName());
                  }
                  return pc;
           }
    }

    public static String getTableName(Class clazz) {
           return getPersistentClass(clazz).getTable().getName();
    }

    public static String getPkColumnName(Class clazz) {
           return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();

    }
    public static String getPkColusasmnName(Class clazz) {
    	AutoLoadSessionFactoryBean autoLoadSessionFactoryBean = (AutoLoadSessionFactoryBean) ApplicationContextKeeper.getAppCtx().getBean("&sessionFactory");
       
    	return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();

 }
}
