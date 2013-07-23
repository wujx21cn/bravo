/* com.cutty.bravo.core.utils.AutoLoadSessionFactoryBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-22 上午09:27:31, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.NetworkInfo;
/**
 *
 * <p>
 * <a href="AutoLoadSessionFactoryBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class AutoLoadSessionFactoryBean  extends AnnotationSessionFactoryBean{
	   private List<String> mappingClassPathJars;
	
	  	public List<String> getMappingClassPathJars() {
			return mappingClassPathJars;
		}
	
		public void setMappingClassPathJars(List<String> mappingClassPathJars) {
			this.mappingClassPathJars = mappingClassPathJars;
		}

		@Override
		public void postProcessAnnotationConfiguration(AnnotationConfiguration config) {
		//加序列号与日期
		boolean sdalfjodf = false;
		try {
			InputStreamReader ir =new InputStreamReader(this.getClass().getResourceAsStream("/ca"));
			BufferedReader br = new BufferedReader(ir);
			String Xsixx = null;
			while ((Xsixx = br.readLine()) != null) {
				String code =CryptUtils.decrypt(Xsixx, "IFUCKYOU,shitibm");
				String nksui = code.substring(0, code.indexOf("["));
				String xcxjk = code.substring(code.indexOf("[")+1,code.length()-1);
				if (!nksui.equals(NetworkInfo.getMacAddress()) ) continue;
				if (!(new Date()).after(DateUtil.parseToDate(xcxjk, DateUtil.yyyyMMdd)))sdalfjodf = true;
			}
		} catch (Exception e) {
			return;
		}
		
		if (!sdalfjodf) return;
	     ResolverUtil<Object> resolver = new ResolverUtil<Object>(mappingClassPathJars); 
	     resolver.findAnnotated(Entity.class,ConfigurableConstants.getProperty("entity.package.name", "com.cutty"));
	     Set<Class<? extends Object>> valueObjectSet = resolver.getClasses();
	     for(Iterator<Class<? extends Object>> iterator = valueObjectSet.iterator();iterator.hasNext();){
	       config.addAnnotatedClass(iterator.next());
	     } 
	   }
}
