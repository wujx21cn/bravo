/* com.cutty.bravo.core.ui.dataRender.web.ExportFileComponentAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Dec 19, 2008 12:06:24 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.opensymphony.xwork2.Action;

/**
 *
 * <p>
 * <a href="ExprtExcelRenderComponentAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Namespace("/ui")   
@ParentPackage("bravo")
public class ExportFileComponentAction implements Action {

	private static final long serialVersionUID = 6875908748696205686L;
	private static final Log logger = LogFactory.getLog(ExportFileComponentAction.class);
	HttpServletRequest request = ServletActionContext.getRequest();

    private String fileName;
    private String inputPath;
    private String contentType;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
    
    public InputStream getInputStream() throws Exception {
    	if(StringUtils.isNotEmpty(ServletActionContext.getRequest().getParameter("fileName")))
    		fileName = ServletActionContext.getRequest().getParameter("fileName");	
    	if(StringUtils.isNotEmpty(ServletActionContext.getRequest().getParameter("contentType")))
    		contentType = ServletActionContext.getRequest().getParameter("contentType");
    	inputPath = ServletActionContext.getRequest().getRealPath("/")+"uploadTemp/" + fileName;
    	File downLoadFile = new File(inputPath);
    	return new FileInputStream(downLoadFile);
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

}

