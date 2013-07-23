/* com.cutty.bravo.core.ui.tags.button.component.UploadDialogButtonBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Oct 10, 2008 1:41:15 PM, Created kukuxia.kevin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.button.component;


/**
 *<p> EXT标签UploadDialogButton属性集合类 </p>
 * <p>
 * <a href="UploadDialogButtonBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin</a>
 */
public class UploadDialogButtonBean extends ButtonBean  {

	private static final long serialVersionUID = 1L;
	private String dataProxy;//上传调用时间url
	private String uploadType;//上传类型，'pic'为图片上传，控制上传文件的后缀类型，且一次只能上传一个文件
	private String onUploadSuccess;
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getDataProxy() {
		return dataProxy;
	}
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}
	public String getOnUploadSuccess() {
		return onUploadSuccess;
	}
	public void setOnUploadSuccess(String onUploadSuccess) {
		this.onUploadSuccess = onUploadSuccess;
	}
	
}
