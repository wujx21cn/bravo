package com.cutty.bravo.components.development;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.utils.ZipUtil;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

@Namespace("/common")
@ParentPackage("bravo")
public class InitProjectAction extends BaseActionSupport{


	private static final long serialVersionUID = -7243852554183970780L;

	public String definition(){
		return "definition";
	}
	
	public String generate(){
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);	
		String path = ServletActionContext.getRequest().getRealPath("/");
		String appFileNameFromView = ServletActionContext.getRequest().getParameter("fileName");
		File fileSrc = new File(path);
		String appFileName = appFileNameFromView.substring(0, appFileNameFromView.indexOf("."));
		String DesPath = path + "..\\..\\" + "appRelease\\" + appFileName;
		File fileDes = new File(DesPath);
		try {
			FileUtils.copyDirectory(fileSrc, fileDes);
			//
			deleteSVNFile(fileDes);
			String zipResPath = fileDes.getPath() + "\\WEB-INF\\classes";
			String zipDesPath = fileDes.getPath() + "\\WEB-INF\\lib\\bravo.jar";
			//将com文件里的class压成bravo.zip改后缀为jar,放入lib目录下.
			//ZipUtil.zip(zipDesPath, "", zipResPath);
			//将项目打包，并下载到客户端
			String zipAppDesPath = path + "uploadTemp/" + appFileName + ".zip";
		//	ZipUtil.decompression(zipFile, destination)(zipAppDesPath, "", fileDes.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
		}
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
	}

	//递归删除svn文件
	private void deleteSVNFile(File fileDes) throws IOException {
		if(fileDes.isDirectory()){
			FileUtils.deleteDirectory(new File(fileDes.getPath()+"\\.SVN"));
			File [] childFiles = fileDes.listFiles();
			for(int i = 0 ; i < childFiles.length; i ++){
				deleteSVNFile(childFiles[i]);
			}
		}
		
		
	}
}
