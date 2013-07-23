package com.cutty.bravo.components.development;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WriteException;

 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
 
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import org.springframework.beans.BeanUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.ui.tags.tree.TreeNode;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.DataExportUtils;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/common")
@ParentPackage("bravo")
public class InitDataAction extends BaseActionSupport {
	private static final Log logger = LogFactory.getLog(InitDataAction.class);

	private static final long serialVersionUID = -5417360104493098777L;
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/*
	 * 列出当前所有的实体树,类似于实体操作权限树,但是没有CRUD节点
	 */
	public String entityView() {
		return "entityView";
	}

	public String entityTree() {
		Map meta = baseDao.getHibernate().getSessionFactory()
				.getAllClassMetadata();
		Set entityNameSet = meta.keySet();
		Iterator itSet = entityNameSet.iterator();
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<TreeNode> leavesList = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setText("All Entity:");
		rootNode.setExpanded("true");
		/*
		 * //将所有的实体名作为叶子节点进行添加一棵树 while(itSet.hasNext()){ TreeNode leaf= new
		 * TreeNode(); String entityName = (String)itSet.next();
		 * leaf.setText(entityName); leaf.setLeaf("true"); leavesList.add(leaf);
		 * }
		 */

		while (itSet.hasNext()) {

			String entityName = (String) itSet.next();

			insertTreeNode(rootNode, entityName, entityName);

		}
		for (TreeNode node : rootNode.getChildTreeNodeList()) {
			treeList.add(node);
		}

		ServletActionContext.getRequest().setAttribute("initDataTreeData",
				treeList);
		return "jsonDataRenderChain";
	}

	void insertTreeNode(TreeNode parentNode, String entityName,
			String fullEntityName) {

		if (entityName.indexOf(".") > -1) {

			String nodeText = entityName.substring(0, entityName.indexOf("."));// ...
			List<TreeNode> childTreeNodeList = parentNode
					.getChildTreeNodeList();
			if (childTreeNodeList == null) {
				childTreeNodeList = new ArrayList<TreeNode>();
				TreeNode treeNode = new TreeNode();
				treeNode.setText(nodeText);
				childTreeNodeList.add(treeNode);
				parentNode.setChildTreeNodeList(childTreeNodeList);

				insertTreeNode(treeNode, entityName.substring(entityName
						.indexOf(".") + 1, entityName.length()), fullEntityName);
			} else {
				boolean tag = true;
				for (TreeNode node : childTreeNodeList) {
					if (nodeText.equals(node.getText())) {
						insertTreeNode(node, entityName.substring(entityName
								.indexOf(".") + 1, entityName.length()),
								fullEntityName);
						tag = false;
					}
				}
				if (tag) {
					TreeNode treeNode = new TreeNode();
					treeNode.setText(nodeText);
					childTreeNodeList.add(treeNode);
					// parentNode.setChildTreeNodeList(childTreeNodeList);
					insertTreeNode(treeNode, entityName.substring(entityName
							.indexOf(".") + 1, entityName.length()),
							fullEntityName);
				}

			}
		} else {

			List<TreeNode> childTreeNodeList = parentNode
					.getChildTreeNodeList();

			TreeNode treeNode = new TreeNode();
			treeNode.setText(entityName);
			treeNode.setLeaf("true");

			if (childTreeNodeList == null) {
				childTreeNodeList = new ArrayList<TreeNode>();
			}
			childTreeNodeList.add(treeNode);
			parentNode.setChildTreeNodeList(childTreeNodeList);
		}
	}

	/**
	 * 点击"导出数据"按钮时,响应本方法，用于导出数据
	 */
	public String exportData() {
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);	
		String[] entityNames = ServletActionContext.getRequest()
				.getParameterValues("initDataCheckedTreeNames");
		String fileName = ServletActionContext.getRequest().getParameter("fileName");
		// 找出从UI传来的实体名的全名,保存成List再用DataExportUtils导出数据,需要改进
		List<String> entityFullNames = new ArrayList<String>();
		Map meta = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
		Set entityNameSet = meta.keySet();
		for (int i = 0; i < entityNames.length; i++) {
			String name = entityNames[i];
			// 传过来""时说明无选择任何节点
			if ("".equals(name)) {
				entityNames = null;
				break;
			}

			Iterator itSet = entityNameSet.iterator();

			while (itSet.hasNext()) {
				String fullEntityName = (String) itSet.next();
				int lastIndexOfdo = fullEntityName.lastIndexOf(".");
				int last2IndexOfdo = fullEntityName.lastIndexOf(".", lastIndexOfdo-1);
  				if (fullEntityName.substring(last2IndexOfdo + 1, fullEntityName.length()).equals(name)) {
					entityFullNames.add(fullEntityName);
				}

			}
		}
		try {
			DataExportUtils.exportData(fileName, entityFullNames);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
		} catch (WriteException e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
	}
	
	/**
	 * 点击"导入数据"按钮时,响应本方法，用于导入数据
	 */
	public String importData(){
		
		/*加载所有Excel文件中待导入数据的信息，用一个Map来管理，键为实体全名，值为FileEntity对象*/
		Map<String,FileEntity> entityFileMap = new HashMap<String,FileEntity>();
		//该路径需手动调整
		ServletContext servletContext = ApplicationContextKeeper.getServletContext();
		String dataTemplatePath = servletContext.getRealPath("/") +ConfigurableConstants.getProperty("data.template.path","WEB-INF/template/data/");
		File dir = new File(dataTemplatePath);
        File file[] = dir.listFiles();

        for (int i = 0; i < file.length; i++) {
        	String fileType = (file[i].getName()).substring( (file[i].getName()).lastIndexOf(".")+1, (file[i].getName()).length());
            if ( ! file[i].isDirectory() && fileType.equals("xls") ){
            	/*遍历其sheet，获得sheet的名称*/
                jxl.Workbook rwb = null;
                try {
        			InputStream is = new FileInputStream(file[i].getAbsolutePath());
        			rwb = Workbook.getWorkbook(is);
        			/*遍历所有的sheet*/
        			for(int s = 0; s < rwb.getNumberOfSheets(); s++ )
        			{
        				Sheet rs = rwb.getSheet(s);
        				String entityName = rs.getCell(0,0).getContents();
        				if( null!=entityName ){
        					FileEntity fileEntity = new FileEntity();
        					fileEntity.setFullFileName(file[i].getAbsolutePath());
            				fileEntity.setFileName(file[i].getName());
            				fileEntity.setEntityName(entityName);
            				fileEntity.setSheetNo(s);
            				entityFileMap.put(entityName, fileEntity);
        				}
        			}
                }catch (Exception e) {
        				e.printStackTrace();
        			} finally {
        				rwb.close();
        			}
            }
        }
		
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);
		
		/*根据页面传入的数据生成fileEntitys*/
		Map<String,String> parameterMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), "select_record_num_", "");
		Iterator<String> selectNumIT = parameterMap.keySet().iterator();
		ArrayList<FileEntity> fileEntitys = new ArrayList<FileEntity>();
		while (selectNumIT.hasNext()){
			String selectNumKey = selectNumIT.next();
    		String fieldPrefix = "select_"+selectNumKey+"_";
    		Map<String,String> recordParamMap = RequestHandler.getMapWithPrefix(ServletActionContext.getRequest().getParameterMap(), fieldPrefix, "");
    		
    		FileEntity fileEntity = new FileEntity();
			fileEntity.setFullFileName(recordParamMap.get("fullFileName"));
			fileEntity.setFileName(recordParamMap.get("fileName"));
			fileEntity.setEntityName(recordParamMap.get("entityName"));
			fileEntity.setSheetNo(Integer.parseInt(recordParamMap.get("sheetNo")));
			fileEntitys.add(fileEntity);
		}
		try {
			
			/*遍历上面的fileEntitys，逐个逐个导入数据*/
			for(int i=0; i<fileEntitys.size(); i++){
				importFromExcel( fileEntitys.get(i), entityFileMap );
			}
			
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
			e.printStackTrace();
			return "jsonDataRenderChain";
		}
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
	}
	
	/**
	 * 根据实体信息插入到数据库中
	 * @param fileEntity
	 * @throws Exception
	 */
	public void importFromExcel( FileEntity fileEntity, Map<String,FileEntity> entityFileMap ) throws Exception{
		/*已导入过的数据表勿需重复导入*/
		if( true == fileEntity.isImported() ) return;
		
		/*对当前正在导入的实体加锁，防止被外键导入进入死循环*/
		if( false == fileEntity.isLocked() ){
			fileEntity.setLocked(true);
			entityFileMap.put(fileEntity.getEntityName(), fileEntity);
		}
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(fileEntity.getFullFileName());
			rwb = Workbook.getWorkbook(is);

			Sheet rs = rwb.getSheet(fileEntity.getSheetNo());
			String entityName = fileEntity.getEntityName();
			/*获取Sheet表中所包含的总列数*/
			int rsColumns = rs.getColumns();
			/*获取Sheet表中所包含的总行数*/
			int rsRows = rs.getRows();
			/*aliases用于存放实体的属性名，从第一行读取到的*/
			ArrayList<String> aliases = new ArrayList<String>();
			for (int i = 1; i < rsRows; i++) {
				/*tuple用于存放待放入到pojo各属性的值，从第二行开始读取到的*/
				ArrayList<Object> tuple = new ArrayList<Object>();
				Long entityId = null;
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = rs.getCell(j, i);
					if (i == 1) {
						/*读取第一行的属性名，放入aliases中去*/
						aliases.add(cell.getContents());
					}else if( i>1 && StringUtils.isNotEmpty(rs.getCell(0,i).getContents()) ){
						/*读取第一行以后的值，放入tuple中去*/
						tuple.add(cell.getContents());
						/*读取该行数据的id*/
						entityId = new Long(rs.getCell(0,i).getContents());
					}
				}
				/*从第二行开始，把数据组装成对应的pojo然后插入数据库表去*/
				if( 1 < i && 1 < tuple.size() )
				{
					Class resultEntityClass ;
					EntityPersister meta = ((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory()).getEntityPersister(entityName);
					PropertyDescriptor[] propertyDescriptors ;
					try {
						resultEntityClass = Class.forName(entityName);
						propertyDescriptors = BeanUtils.getPropertyDescriptors(resultEntityClass);
						Object returnResult = null;
						if (null == resultEntityClass) return;
						if (null != aliases && 1 <aliases.size() && null != propertyDescriptors && 1 < propertyDescriptors.length){
							/*按给定的类名构造一个空pojo*/
							returnResult = resultEntityClass.newInstance();
							/*根据字段名和读取到的数据给pojo注值*/
							for (int x=0 ; x< aliases.size(); x++){
								
								/*对于Excel中为空的，跳过此次注值*/
								if( ((String)tuple.get(x)).equals("") ) continue;
								
								for (int y=0;y<propertyDescriptors.length;y++){
									if (propertyDescriptors[y].getName().equalsIgnoreCase(aliases.get(x))){
										Class propertyClass = org.apache.commons.beanutils.PropertyUtils.getPropertyType(returnResult, propertyDescriptors[y].getName());
										
										/*对于单一外键属性，需构建对应外键的pojo后再注入*/
										if( meta.getPropertyType(propertyDescriptors[y].getName()).isEntityType() ){
											/*从所有Excel文件中查找有没有该外键对应的实体信息，有则导入，无则不注入此实体*/
											FileEntity fe = entityFileMap.get(propertyClass.getName());
											
											/*如果该外键实体已加锁，则跳过对此属性的注入*/
											if( null != fe && true == fe.isLocked() ) break;
											
											if( null != fe && false == fe.isImported() ){
												importFromExcel(fe,entityFileMap);
											}
											/*导入完成后，再到数据库中查找该外键类是否存在，有则放到该外键属性中，无则不导入此实体，同时向外抛出异常*/
											String idName = baseDao.getIdName(propertyClass);
											Object propertyObj = baseDao.findUniqueBy(propertyClass, idName, Long.valueOf((String)tuple.get(x)), true);
											if( null !=propertyObj ){
												org.apache.commons.beanutils.BeanUtils.setProperty(returnResult,propertyDescriptors[y].getName(),propertyObj);
											}else{
												throw new Exception("Error in line ["+i+"] of entity ["+entityName+"] foreignKey "+propertyClass.getName()+" has not input before!");
											}
											
										}
										
										/*对于多个外键属性，需逐个构建对应外键的pojo后再注入*/
										else if( meta.getPropertyType(propertyDescriptors[y].getName()).isCollectionType() ){
											
											CollectionType collectionType = (CollectionType)meta.getPropertyType(propertyDescriptors[y].getName());
											Collection propertyCollection = (Collection)collectionType.instantiate(-1);
											Type entityType = collectionType.getElementType((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory());
											
											/*获取主键的值*/
											String[] keyValues = ((String)tuple.get(x)).split(";");
											
											for(int n=0; n<keyValues.length; n++){
												/*从所有Excel文件中查找有没有该外键对应的实体信息，有则导入，无则不注入此实体*/
												FileEntity fe = entityFileMap.get(entityType.getReturnedClass().getName());
												
												/*如果该外键实体已加锁，则跳过对此属性的注入*/
												if( null != fe && true == fe.isLocked() ) break;
												
												if( null != fe && false == fe.isImported() ){
													importFromExcel(fe,entityFileMap);
												}
												/*插入完成后，再到数据库中查找该外键类是否存在，有则放到该外键属性中，无则不注入此实体，同时向外抛出异常*/
												String idName = baseDao.getIdName(entityType.getReturnedClass());
												Object propertyObj = baseDao.get(entityType.getReturnedClass(), Long.valueOf(keyValues[n]));
												if( null !=propertyObj ){
													/*放到容器中*/
													propertyCollection.add(propertyObj);
												}else{
													throw new Exception("foreignKey has not input before!");
												}
											}
											if( null !=propertyCollection ){
												org.apache.commons.beanutils.BeanUtils.setProperty(returnResult,propertyDescriptors[y].getName(),propertyCollection);
											}else{
												throw new Exception("Error in line ["+i+"] of entity ["+entityName+"] foreignKey "+propertyClass.getName()+" has not input before!");
											}
										}
										
										/*对于非外键属性直接注入*/
										else{
											org.apache.commons.beanutils.BeanUtils.setProperty(returnResult,propertyDescriptors[y].getName(), tuple.get(x));
										}
									}
								}
							}
							
							/*保存或更新实体对象到数据库中*/
							saveOrUpdate(returnResult, resultEntityClass, "id", entityId);
							
						} else if (1 == aliases.size()){
							returnResult = ConvertUtils.convert(tuple.get(0), resultEntityClass);
						}
					} catch (InstantiationException e) {
						logger.error(e);
					} catch (IllegalAccessException e) {
						logger.error(e);
					} catch (InvocationTargetException e) {
						logger.error(e);
					} catch (ClassNotFoundException e) {
						logger.error(e);
					}					
				}
			}
			/*导入完成后设置导入完成标识，并解锁*/
			fileEntity.setImported(true);
			fileEntity.setLocked(false);
			entityFileMap.put(fileEntity.getEntityName(), fileEntity);
			
			System.out.println(fileEntity.getEntityName()+" has been imported successfully!======================================================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("file:"+fileEntity.getFileName()+"  sheet:"+fileEntity.getSheetNo()+"  entity:"+fileEntity.getEntityName()+"\n"+ e.getMessage());
		} finally {
			rwb.close();
		}
	}
	
	public String dataFileEntityView(){	
		ArrayList<FileEntity> fileEntitys = new ArrayList<FileEntity>();
		
		 /*获取过滤条件*/
		String fileNameFilter = ServletActionContext.getRequest().getParameter("fileNameFilter");
		String entityNameFilter = ServletActionContext.getRequest().getParameter("entityNameFilter");
		
		/*遍历文件夹内所有xls文件，对每个文件遍历其sheet，获得sheet名称后构建出实体对象全名*/
		//该路径需手动调整
		ServletContext servletContext = ApplicationContextKeeper.getServletContext();
		String dataTemplatePath = servletContext.getRealPath("/") +ConfigurableConstants.getProperty("data.template.path","WEB-INF/template/data/");
		File dir = new File(dataTemplatePath);
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
        	
        	/*如果当前文件被过滤掉，则跳过*/
        	if( null!=fileNameFilter && 0>file[i].getName().indexOf(fileNameFilter) ){
        		continue;
        	}
        	
        	String fileType = (file[i].getName()).substring( (file[i].getName()).lastIndexOf(".")+1, (file[i].getName()).length());
            if ( ! file[i].isDirectory() && fileType.equals("xls") ){
            	/*遍历其sheet，获得sheet的名称*/
                jxl.Workbook rwb = null;
                try {
        			InputStream is = new FileInputStream(file[i].getAbsolutePath());
        			rwb = Workbook.getWorkbook(is);
        			/*遍历所有的sheet*/
        			for(int s = 0; s < rwb.getNumberOfSheets(); s++ )
        			{
        				Sheet rs = rwb.getSheet(s);
        				String entityName = rs.getCell(0,0).getContents();
        				
        				/*如果当前实体被过滤掉，则跳过*/
        				if( null!=entityName && null!=entityNameFilter && 0>entityName.indexOf(entityNameFilter) ){
        					continue;
        				}
        				
        				if( null!=entityName ){
        					FileEntity fileEntity = new FileEntity();
        					fileEntity.setFullFileName(file[i].getAbsolutePath());
            				fileEntity.setFileName(file[i].getName());
            				fileEntity.setEntityName(entityName);
            				fileEntity.setSheetNo(s);
            				fileEntitys.add(fileEntity);
        				}
        			}
                }catch (Exception e) {
        				e.printStackTrace();
        			} finally {
        				rwb.close();
        			}
            }
        }
		
		Page page = new Page(0,100,100,fileEntitys);
		renderContextForFind(page);
		return "jsonDataRenderChain";
	}
	
	/**
     * 根据page对服务器返回参数进行预处理
     * @param page
     */
    protected void renderContextForFind(Page page){
		//先按照contextDataName获取,如果没有,则取getEntityListName();
		String contextDataName = ServletActionContext.getRequest().getParameter("contextDataName");
		if (StringUtils.isEmpty(contextDataName)){
			contextDataName = "FileEntity";
			ServletActionContext.getRequest().setAttribute("contextDataName", contextDataName);
		}
		ServletActionContext.getRequest().setAttribute(contextDataName,page.getResult());
		ServletActionContext.getRequest().setAttribute("totalCount",String.valueOf(page.getTotalCount()));
    }
    
    /**
     * 如果待插入数据库的实体对象在数据库中已存在则更新，若不存在则新增
     * @param entity        待插入实体
     * @param entityClass   实体类型
     * @param propertyName  主键名称
     * @param value         主键值
     */
    private void saveOrUpdate(Object entity, Class entityClass, String propertyName, Object value){
		/*如果该实体已在数据库中,则更新该实体数据*/
		if( null!=baseDao.findUniqueBy(entityClass, propertyName, value, true) ){
			baseDao.clear();
			baseDao.save(entity);
			baseDao.evict(entity);
		}
		/*如果该实体不在数据库中,则插入该实体数据*/
		else
		{
			baseDao.saveWithId(entity, (Serializable)value);
			baseDao.evict(entity);
		}
	}
    
    /**
     * 根据实体对象名返回去全名
     * @param entityName 实体对象名
     */
	private String entityFullName(String entityName){
		BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		Map classMetadataMap = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
		/*如果只传实体名,不传包名,则循环处理,选择出该实体的包名。*/
		if (entityName.indexOf(".") < 0) {
			entityName = "." + entityName;
			Iterator classMetadataKeyIT = classMetadataMap.keySet().iterator();
			while (classMetadataKeyIT.hasNext()) {
				String className = (String) classMetadataKeyIT.next();
				int entityNameLen = (entityName.length()+className.indexOf(entityName));
				/*该方法需优化，或需确保不同类包下的类名不会重复*/
				if (className.indexOf(entityName) > -1 && entityNameLen == className.length()) {
					return className;
				}
			}
		}	
		return null;
	}

}
