/* com.cutty.bravo.core.manager.BaseHttpTest.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-2-5 上午10:01:37, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.manager;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpNotFoundException;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import junit.framework.TestCase;

/**
 *
 * <p>
 * <a href="BaseHttpTest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BaseHttpTest extends TestCase{
	
	/**
	 * 
	 * @param conversation
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public WebResponse tryGetResponse(WebConversation conversation,WebRequest request) throws Exception {
		WebResponse response=null;
		try {
			response = conversation.getResponse( request );
		} catch (HttpNotFoundException nfe) {
			System.err.println("The URL '"+request.getURL()+"' is not active any more");
			throw nfe;
		}
		return response;
	}
	  public void testWelcomePage() throws Exception {
		WebConversation     conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest( "http://jason:8080/bravo/" );
		WebResponse response = tryGetResponse(conversation, request );
		WebForm forms[] = response.getForms();
	  
	  }
}
