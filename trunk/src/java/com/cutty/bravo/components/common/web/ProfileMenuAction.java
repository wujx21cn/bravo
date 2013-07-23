package com.cutty.bravo.components.common.web;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.EnumerationType;
import com.cutty.bravo.components.common.domain.ProfileMenu;
import com.cutty.bravo.components.common.manager.ProfileMenuManager;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

@Namespace("/common")
@ParentPackage("bravo")
public class ProfileMenuAction extends EntityAction<ProfileMenu> {
	private static final long serialVersionUID = 1687057917465396530L;
	
	private ProfileMenuManager profileMenuManager;

	public ProfileMenuManager getProfileMenuManager() {
		return profileMenuManager;
	}

	public void setProfileMenuManager(ProfileMenuManager profileMenuManager) {
		this.profileMenuManager = profileMenuManager;
	}
	
	public String viewProfileMenu() throws ServletException, IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		User user = RequestHandler.getContextRequestHandler().getCurrentUser();
		List<ProfileMenu> profileMenus = profileMenuManager.findBy(null, "user.id", user.getId(),true);
		ServletActionContext.getRequest().setAttribute("profileMenus", profileMenus);
		return "show";
	}
	
	@Override
	public String query() throws Exception {
		BaseDao baseDao= (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		String sql = "from EnumerationType where name=\'快捷图标\'";
		List<EnumerationType> dataList = (List)baseDao.find(null, sql,true);
		ServletActionContext.getRequest().setAttribute("iconTypeId", dataList.get(0).getId());
		return QUERY;
	}

	@Override
	public String batchSave() throws Exception {
		ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_SAVE_KEY, Constants.GRID_BATCH_SAVE_VALUE);
    	try {
	    	Map<String,String> parameterMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), "select_record_num_", "");
	    	Iterator<String> selectNumIT = parameterMap.keySet().iterator();
	    	List<ProfileMenu> saveEntitys = new ArrayList<ProfileMenu>();
	    	while (selectNumIT.hasNext()){
	    		String selectNumKey = selectNumIT.next();
	    		String fieldPrefix = "select_"+selectNumKey+"_";
	    		Map<String,String> recordParamMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), fieldPrefix, "");
	    		ProfileMenu saveEntity = getEntityClass().newInstance();
	    		//判断主键是否存在,作出不同处理.
	    		if (recordParamMap.containsKey(this.getIdName()))  {
	    			if (StringUtils.isEmpty(recordParamMap.get(this.getIdName()))){
	    				recordParamMap.remove(this.getIdName());
	    			} else {
		    			Serializable id = recordParamMap.get(this.getIdName());
		    			Object pe= baseDao.get(getEntityClass(),(Serializable)ConvertUtils.convert(id, baseDao.getIdType(getEntityClass())));
		    			BeanUtils.copyProperties(saveEntity, pe);
		    			baseDao.evict(pe);
	    			}
	    		}
	    		Iterator<String> fieldNameIT = recordParamMap.keySet().iterator();
	    		while (fieldNameIT.hasNext()){
	    			String fieldName = fieldNameIT.next();
	    			if (fieldName.indexOf(".") > -1){
	    				String[] fieldNameArray = fieldName.split("\\.");
	    				String propertyName = "";
	    				for (int i=0;i<fieldNameArray.length-1;i++){
	    					if (i==0){
	    						PropertyUtils.setProperty(saveEntity, fieldNameArray[0],null ) ;
	    					}
	    					if (propertyName.length() > 0) propertyName = propertyName + ".";
	    					propertyName = propertyName + fieldNameArray[i];
	    					if (null == BeanUtils.getProperty(saveEntity, propertyName)){
		    					PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(saveEntity.getClass(), propertyName);
		    					Object fieldValue = propertyDescriptor.getPropertyType().newInstance();
		    					BeanUtils.setProperty(saveEntity, propertyName,fieldValue );
	    					}
	    				}
	    			}
	    		}
	    		BeanUtils.populate(saveEntity, recordParamMap);
	    		saveEntity.setUser(RequestHandler.getContextRequestHandler().getCurrentUser());
	    		saveEntitys.add(saveEntity);
	    	}
	    	baseDao.batchSave(saveEntitys);
    	} catch (Exception e) {
			logger.error(e);
	    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_SAVE_STATUS, Constants.GRID_BATCH_SAVE_STATUS_FAILURE);			
	    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_SAVE_MSG, e.getMessage());
	    	return JSON_DATA_RENDER_CHAIN;
		}
    	
    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_SAVE_STATUS, Constants.GRID_BATCH_SAVE_STATUS_SUCCESS);
    	return JSON_DATA_RENDER_CHAIN;
	}
}
