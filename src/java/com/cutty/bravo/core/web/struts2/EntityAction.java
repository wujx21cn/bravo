/* com.cutty.bravo.core.web.struts2.EntityAction.java
{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-28 上午02:14:32, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.
*/
package com.cutty.bravo.core.web.struts2;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.util.ClassUtils;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.utils.GenericsUtils;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * <p>
 * <a href="EntityAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class EntityAction<T> extends BaseActionSupport  implements ModelDriven  {

	private static final long serialVersionUID = -7557820411455226124L;

	protected static final String ADD = "add";
	
	protected static final String LIST = "list";

	protected static final String EDIT = "edit";

	protected static final String VIEW = "view";
	
	protected static final String QUERY = "query";
	
	protected static final String FIND = "find";
	
	protected static final String JSON_DATA_RENDER_CHAIN = "jsonDataRenderChain";

	public Class<T> entityClass; // Action所管理的Entity类型.

	public String idName; // Action所管理的Entity的主键名.

	protected T model;

	protected BaseDao  baseDao; 
    

    /**
	 * @param baseDao the baseDao to set
	 */
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
        if (entityClass == null) {
            entityClass = getEntityClass();
        }
	}

	/**
     * 获取当前Action对应的实体模型.
     * @return
     */
    public Class<T> getEntityClass() {
        if (entityClass == null) {
            entityClass = GenericsUtils.getGenericClass(getClass(),0);
        }
        return entityClass;
    }

    /**
     * 获取当前实体模型的主键名
	 * @return the idName
	 */
	public String getIdName() {
		idName = baseDao.getIdName(getEntityClass()) ;
		return idName;
	}

	/**
	 * 获得当前模型对象,如果参数中带主键,则直接从数据库中获取并填充.
	 */
	public T getModel() {
        if (model == null) {
            try {
                //用于create方法，需生成model实例用于之后parameter的填充
                model = getEntityClass().newInstance();
            } catch (InstantiationException e) {
            	logger.error(e);
            } catch (IllegalAccessException e) {
            	logger.error(e);
            }
        }
        return model;
    }

	/**
	 * 返回新增页面定义
	 * @return
	 */
	public String add() {
		return ADD;
	}
	
	/**
	 * 返回编辑页面定义
	 * @return
	 */
	public String edit() throws Exception {
		renderModel();
		return EDIT;
	}
	
	/**
	 * 返回查看页面定义
	 * @return
	 */
    public String view() throws Exception{
    	renderModel();
        return VIEW;
    }
	
	/**
	 * 返回查询页面
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		return QUERY;
	}
	/**
	 * 根据查询实体自动查询
	 * @return
	 */
    public String find() throws Exception {
    	Page page = Page.getInstance(ServletActionContext.getRequest());
    	try {
    		baseDao.findByPage(new QueryParameterWrapper(getEntityClass()), page,true);
		} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
		renderContextForFind(page);
        return FIND;
    }
    

    /**
     * 根据查询实体自动查询,并把处理结果以json格式返回
     * @return
     * @throws Exception
     */
    public String findAndRendJsonData() throws Exception {
    	find();
    	return "jsonDataRenderChain";
    }
    
    
    /**
     * 保存当前模型
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
    	//如果进入save的页面请求未带任何参数，则让其直接进入view页面即可
    	if(false == ServletActionContext.getRequest().getParameterNames().hasMoreElements()){
    		return VIEW;
    	}
		renderModel(model);
    	try {
    		T saveEntity ;
    		String id = BeanUtils.getProperty(model, this.getIdName());
    		if (StringUtils.isNotEmpty(id)){
    			saveEntity = getEntityClass().newInstance();
    			T pe= baseDao.get(getEntityClass(),(Serializable)ConvertUtils.convert(id, baseDao.getIdType(getEntityClass())));
    			Set<String> parameterKeySet = ServletActionContext.getRequest().getParameterMap().keySet();
    			PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(model.getClass());
    			for (int i=0;i<propertyDescriptors.length;i++){
    				String propertyName = propertyDescriptors[i].getName();
        			//去除Object类的属性:class,如果model中含有的字段或者参数中有的字段都附加到entity中。
        			if ("class".equals(propertyName)) continue;
        			if ( null != BeanUtils.getProperty(model, propertyName)){
        				PropertyUtils.setProperty(pe, propertyName, PropertyUtils.getProperty(model, propertyName));
        			}
        		}
    			baseDao.evict(pe);
    			PropertyUtils.copyProperties(saveEntity, pe);
    		} else {
    			saveEntity = model;
    		}
    		baseDao.save(saveEntity);
		} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
		renderModel();
        return VIEW;
    }
    public String saveAndRendJsonData() throws Exception {
    	ServletActionContext.getRequest().setAttribute(Constants.FORM_AJAX_SUBMIT_KEY, Constants.FORM_AJAX_SUBMIT_VALUE);
    	try {
    		save();
		} catch (Exception e) {
			logger.error(e);
	    	ServletActionContext.getRequest().setAttribute(Constants.FORM_AJAX_SUBMIT_STATUS, Constants.FORM_AJAX_SUBMIT_FAILURE);			
	    	ServletActionContext.getRequest().setAttribute(Constants.FORM_AJAX_SUBMIT_MSG, e.getMessage());
	    	return JSON_DATA_RENDER_CHAIN;
		}
		ServletActionContext.getRequest().setAttribute(Constants.FORM_AJAX_SUBMIT_STATUS, Constants.FORM_AJAX_SUBMIT_SUCCESS);
		ServletActionContext.getRequest().setAttribute(Constants.FORM_AJAX_SUBMIT_MSG,"保存成功!!!");
        return JSON_DATA_RENDER_CHAIN;
    }
    /**
     * 批量保存,传如的数据格式必须是select_record_num_1....select_record_num_2....
     * 其中1,2....代表记录行数
     * 实体其他字段为select_1_xxx,select_1_yyy,select_2_xxx,select_2_yyy.....
     * @return
     * @throws Exception
     */
    public String batchSave() throws Exception {
    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_SAVE_KEY, Constants.GRID_BATCH_SAVE_VALUE);
    	try {
	    	Map<String,String> parameterMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), "select_record_num_", "");
	    	Iterator<String> selectNumIT = parameterMap.keySet().iterator();
	    	List<T> saveEntitys = new ArrayList<T>();
	    	while (selectNumIT.hasNext()){
	    		String selectNumKey = selectNumIT.next();
	    		String fieldPrefix = "select_"+selectNumKey+"_";
	    		Map<String,String> recordParamMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), fieldPrefix, "");
	    		T saveEntity = getEntityClass().newInstance();
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
    
    /**
     * 删除当前模型实体
     */
    public String remove() throws Exception {
    	try {
    		baseDao.remove(model);
    		//this.list();
    	} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
		return LIST;
    }
    /**
     * 删除当前模型实体
     */
    public String batchRemove() throws Exception {    	
    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_REMOVE_KEY, Constants.GRID_BATCH_REMOVE_VALUE);
    	String[] ids = ServletActionContext.getRequest().getParameterValues("ids");
    	try {
	    	if (null != ids && 0 < ids.length){
	    		Class IdType = baseDao.getIdType(getEntityClass());
	    		Serializable[] idValues = new Serializable[ids.length];
	    		for (int i=0;i<ids.length;i++){
	    			if (null == ids[i] || (ids[i] instanceof String && StringUtils.isEmpty(ids[i]))) continue;
	    			idValues[i] = (Serializable)ConvertUtils.convert(ids[i], IdType);
	    		}
	    		baseDao.removeByIds(getEntityClass(),idValues);
	    	}
    	} catch (Exception e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_REMOVE_STATUS, Constants.GRID_BATCH_REMOVE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_REMOVE_MSG, e.getMessage());
			return JSON_DATA_RENDER_CHAIN;
		}

    	ServletActionContext.getRequest().setAttribute(Constants.GRID_BATCH_REMOVE_STATUS, Constants.GRID_BATCH_REMOVE_STATUS_SUCCESS);
		return JSON_DATA_RENDER_CHAIN;
    }
    
    /**
     * 获取默认实体对应列表名.
     * @return
     */
	protected String getEntityListName() {
		return getEntityName() + "s";
	}
	
    /**
     * 获取默认实体对应名.
     * @return
     */
	protected String getEntityName() {
		return StringUtils.uncapitalize(ClassUtils.getShortName(getEntityClass())) ;
	}
	
	/**
	 * 获取业务对象列表的函数.
	 */
	protected List<T> doListEntity() {
		return baseDao.getAll(getEntityClass());
	}
	
	/**
	 * @return the RequestHandler
	 */
	public RequestHandler getRequestHandler() {
		if (null == RequestHandler.getContextRequestHandler())
			return RequestHandler.getRequestHandler(ServletActionContext.getRequest());
		return RequestHandler.getContextRequestHandler();
	}
	
    /**
     * 根据参数中是否有主键,渲染模型.
     */
    protected void renderModel() throws Exception {
    	try {
			if (StringUtils.isEmpty(BeanUtils.getProperty(model, getIdName()))) {
	    		throw new Exception("you should pass the id Value of the model!!!\n你必须传入查询主键!!!");
			}
	    	model = baseDao.get(getEntityClass(),baseDao.getId(getEntityClass(),model));
	    	String contextDataName = ServletActionContext.getRequest().getParameter("contextDataName");
			if (StringUtils.isEmpty(contextDataName)){
				contextDataName = getEntityName();
			}
	    	ServletActionContext.getRequest().setAttribute(contextDataName,model);
		} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
    }
    
    /**
     * 去除实体无用的关系类型属性值,只删除一级无用关系属性。使用该函数将导致与hibernate耦合
     * @param entity
     */
    @Deprecated
    protected void renderModel(Object entity){
    	ClassMetadata  metaData = baseDao.getHibernate().getSessionFactory().getClassMetadata(entity.getClass());
    	if (null == metaData) return ;
    	String[] propertyNames = metaData.getPropertyNames();
    	Type[] propertyTypes = metaData.getPropertyTypes();
    	for (int i=0;i<propertyNames.length;i++){
    		if (propertyTypes[i].isAssociationType() && !propertyTypes[i].isCollectionType()){
    			try {
					Object property = PropertyUtils.getProperty(entity, propertyNames[i]);
					if (null == property) continue;
					if (null == baseDao.getId(getEntityClass(),property)) PropertyUtils.setProperty(entity, propertyNames[i], null);
				} catch (Exception e) {
					logger.error(e);
				}
    		}
    	}
    }
    
    /**
     * 根据page对服务器返回参数进行预处理
     * @param page
     */
    protected void renderContextForFind(Page page){
		//先按照contextDataName获取,如果没有,则取getEntityListName();
		String contextDataName = ServletActionContext.getRequest().getParameter("contextDataName");
		if (StringUtils.isEmpty(contextDataName)){
			contextDataName = getEntityListName();
			ServletActionContext.getRequest().setAttribute("contextDataName", contextDataName);
		}
		ServletActionContext.getRequest().setAttribute(contextDataName,page.getResult());
		ServletActionContext.getRequest().setAttribute("totalCount",String.valueOf(page.getTotalCount()));
    }
}
