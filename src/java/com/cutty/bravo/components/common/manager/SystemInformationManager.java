/* com.cutty.bravo.components.common.manager.SystemInformationManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-19 下午02:49:47, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.SystemInformation;
import com.cutty.bravo.core.manager.BaseManager;

/**
 *
 * <p>
 * <a href="SystemInformationManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */

@Service("systemInformationManager")
public class SystemInformationManager extends BaseManager<SystemInformation> {

	/**
	 * 根据发布日期先后得到系统最新版本信息
	 */
	public SystemInformation findLatestSystemInfo(){

		SystemInformation systemInformation = null;
		
		List<SystemInformation> systemInformations = baseDao.find(null, "from SystemInformation order by id asc",true);
		
		if( null!=systemInformations && 0<systemInformations.size())
		{
			systemInformation = systemInformations.get(0);
			
			for( int i=0; i<systemInformations.size(); i++)
			{
				if(null!=systemInformation.getReleaseDate() && null!=systemInformations.get(i).getReleaseDate() )
				{
					if( systemInformation.getReleaseDate().before(systemInformations.get(i).getReleaseDate()) )
					{
						systemInformation = systemInformations.get(i);
					}
				}
			}
		}
		
		return systemInformation;
	}
}
