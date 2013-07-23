/* DataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-1    2008, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2007 bullshit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> json渲染器接口 </p>
 * <p>
 * <a href="DataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface DataRenderComponent {
	public String rend(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException;
}
