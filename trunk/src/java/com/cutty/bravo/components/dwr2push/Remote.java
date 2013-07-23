package com.cutty.bravo.components.dwr2push;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class Remote {
	public void getFlightInfo(HttpServletRequest request) {
		noticeNewFlightInfo(String.valueOf(System.currentTimeMillis()));// 获取服务器端数据
	}

	public static void noticeNewFlightInfo(String id) {
		WebContext wctx = WebContextFactory.get();
		String currentPage =  wctx.getCurrentPage();
		
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("receiveMessages(").appendData(id).appendScript(
				");");
		Collection pages = wctx.getScriptSessionsByPage(currentPage);
		System.out.println(pages.size());
		for (Iterator it = pages.iterator(); it.hasNext();) {
			ScriptSession otherSession = (ScriptSession) it.next();
			otherSession.addScript(script);
		}
	}

}