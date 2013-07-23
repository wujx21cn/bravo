/* com.cutty.bravo.core.security.web.UserAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-19 下午02:06:19, Created by Jason.Wu
		2008-09-25 kukuxia.hw 增加Form提交的方式 ajaxSave() 以及 commSave()，前者Ajax，后者非Ajax
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.manager.UserManager;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="UserAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Namespace("/security")   
@ParentPackage("bravo")
public class UserAction extends EntityAction<User>{
	private static final long serialVersionUID = 6722962153158485213L;
	private InputStream resultStream;
    private UserManager userManager;
    public InputStream getResultStream() throws Exception {
        return resultStream;
    }

    private File file;
    private String fileFileName;
    private String fileContentType;
    

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public String getUploadFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileName) {
        this.fileFileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File upload) {
        this.file = upload;
    }

    public void setFileContentType(String contentType) {
        this.fileContentType = contentType;

    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    /**
     * 获得上传图片的数据流，并存储在数据库中，字段类型BLOB
     * @return
     * @throws Exception
     */
    public String uploadPersonPic() throws Exception {
    	//获得上传图片的数据流
        java.io.InputStream is = new java.io.FileInputStream(file);
        String userID = RequestHandler.getContextRequestHandler().getRequest().getParameter("userID");
        //存储图片的二进制流
        byte[] photoStream = new byte[is.available()];
        is.read(photoStream, 0, is.available());
        is.close();
        //取得当前用户数据
        User user = new User();
        user = userManager.get(Long.valueOf(userID));
        //更新photo字段
        user.setPhoto(photoStream);
        userManager.save(user);
        
        String msg = "{'success':true,message:'upload success!'}";
        resultStream = (new ByteArrayInputStream(msg.getBytes()));
        return "Aajx";
    }

    /**
     * 读取人员管理的photo字段，并在页面上显示
     * @throws IOException
     */
    public void showPersonPic() throws IOException{
    	HttpServletResponse response = ServletActionContext.getResponse();
        String userID = RequestHandler.getContextRequestHandler().getRequest().getParameter("userID");
        //获得当前选中人员的所有信息
        User user = new User();
        user = userManager.get(Long.valueOf(userID));
        if(user!=null && user.getPhoto()!=null){
        	//获得图片的二进制流
        	byte[] photoStream = new byte[user.getPhoto().length];
        	photoStream = user.getPhoto();
        	ServletOutputStream sout = response.getOutputStream();
        	sout.write(photoStream); 
        	sout.flush();
        	sout.close(); 
        	//设置contentType,tell browser that I'm a image
        	response.setContentType("image/jpeg");
        }
    }

    /**
     * 为了上传图片后便可以预览，在iframe里显示新的页面(转向user-personalImage页面显示图片).上传后reload该iframe便可以刷新刚上传的图片预览.
     * @return
     */
    public String redirectPersonPic(){
         	HttpServletRequest request = ServletActionContext.getRequest();
         	String userID = RequestHandler.getContextRequestHandler().getRequest().getParameter("userID");
         	request.setAttribute("userID", userID);
        	return "personalImage";
        }
    

}
