/* com.cutty.bravo.components.common.web.AttachmentAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Oct 14, 2008 5:00:47 PM, Created kukuxia.kevin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;


import com.cutty.bravo.components.common.domain.Attachment;
import com.cutty.bravo.components.common.manager.AttachmentManager;
import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="AttachmentAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin</a>
 */
@Namespace("/common")   
@ParentPackage("bravo")
public class AttachmentAction extends EntityAction<Attachment>{

	private static final long serialVersionUID = 1905725737571720327L;
	private InputStream resultStream;
    private AttachmentManager attachmentManager;
    public InputStream getResultStream() throws Exception {
        return resultStream;
    }

    private File file;
    private String fileFileName;
    private String fileContentType;
    
    public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
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

    
    @Override
	public String execute() throws Exception {
        String msg = "{success:true,message:'No action'}";
        resultStream = (new ByteArrayInputStream(msg.getBytes()));
        return "Ajax";
    }

    public String upload() throws Exception {
    	//获得上传文件的数据流
        java.io.InputStream is = new java.io.FileInputStream(file);
        int fileSize = is.available()/1024;
//      String bravoHome =  RequestHandler.getContextRequestHandler().getRequest().getContextPath();
        //获得当前时间
        Timestamp now = new Timestamp((new java.util.Date()).getTime());  
        SimpleDateFormat fileFmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");   
        SimpleDateFormat dirFmt = new SimpleDateFormat("yyyyMMdd"); 
        String  pFileName = fileFmt.format(now).toString().trim();            
        String  pDirPath = dirFmt.format(now).toString().trim();   
        
        //获得config.properties里面的配置的上传文件存放的路径
        String uploadPath = ConfigurableConstants.getProperty("ui.uploadDialog.path","false");
        //新建文件夹，判断是否存在，不存在则新建该文件夹
        File mianUploadPath = new File(uploadPath);
        if(!mianUploadPath.exists()){
        	mianUploadPath.mkdir();
        }
        
        //为每天的上传的文件新建一个文件夹，文件夹名称为当天的时间yyyyMMdd；例如：20081016
        String relativePath = pDirPath +"\\";
        uploadPath = uploadPath + pDirPath +"\\";
          //新建文件夹，判断是否存在，不存在则新建该文件夹
        File tempUploadPath = new File(uploadPath);
        if(!tempUploadPath.exists()){
        	tempUploadPath.mkdir();
        }       
    
        //修改上传的文件名，格式为：yyyyMMddHHmmssSSS_fileFileName;例如20081016170127786_test.txt
        String originalFileName = fileFileName;
        fileFileName = pFileName + "_" + fileFileName;
        
        //写入文件
        java.io.OutputStream os = new java.io.FileOutputStream(uploadPath + fileFileName);
        byte buffer[] = new byte[8192];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            os.write(buffer, 0, count);
        }
        os.close();
        is.close();
  
        
        Attachment attachment = new Attachment();
        attachment.setName(fileFileName); //获取上传文件名；
        attachment.setRelativePath(relativePath);  //获取上传文件相对路径；
        attachment.setOriginalFilename(originalFileName);  //获取上传原文件名 ；
        attachment.setFileSize(fileSize);     //获取上传 文件 大小 ；
          User uploader = new User();
          uploader = RequestHandler.getContextRequestHandler().getCurrentUser();
        attachment.setUploader(uploader);  //获取上传文件当前用户 ；
        attachment.setUploadt(new Date()); //获取上传文件 当前时间 ；
        	attachmentManager.save(attachment);
        
        
        String msg = "{'success':true,message:'upload success!'}";
        resultStream = (new ByteArrayInputStream(msg.getBytes()));
        return "Aajx";
    }
    
   
   
    
    public String del() throws Exception {
        String msg = "{success:true,message:'del success'}";
        resultStream = (new ByteArrayInputStream(msg.getBytes()));
        return "Aajx";
    }
}
