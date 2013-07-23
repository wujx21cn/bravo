package com.cutty.bravo.core.security.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.security.domain.EntityOperatePermissionRelation;
import com.cutty.bravo.core.security.manager.EntityOperatePermissionRelationManager;
import com.cutty.bravo.core.ui.tags.tree.TreeNode;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;
import com.cutty.bravo.core.web.struts2.EntityAction;

@Namespace("/security")   
@ParentPackage("bravo")
public class EntityOperatePermissionRelationAction extends BaseActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6873134999116605010L;
	private BaseDao  baseDao; 
	private EntityOperatePermissionRelationManager entityOperatePermissionRelationManager;
	
	public EntityOperatePermissionRelationManager getEntityOperatePermissionRelationManager() {
		return entityOperatePermissionRelationManager;
	}
	public void setEntityOperatePermissionRelationManager(
			EntityOperatePermissionRelationManager entityOperatePermissionRelationManager) {
		this.entityOperatePermissionRelationManager = entityOperatePermissionRelationManager;
	}
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public String entityTree(){
		String permisId = ServletActionContext.getRequest().getParameter("permisId");
    	Map meta = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
    	Set entityNameSet = meta.keySet();
    	Iterator itSet = entityNameSet.iterator();
    	List<TreeNode> treeList = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setText("全部实体");
		rootNode.setExpanded("true");
		//根据permisId查出相应的数据，目的是将相应的节点设上check为true.
		String hql = "from EntityOperatePermissionRelation where permission.id = "+permisId;
		List<EntityOperatePermissionRelation> entityOpPermList= entityOperatePermissionRelationManager.find(null, hql,true);
		//传递给递归函数的参数，最多有增删改查四个EntityOperatePermissionRelation
		List<EntityOperatePermissionRelation> entityOpPermCRUD = new ArrayList<EntityOperatePermissionRelation>();
		while(itSet.hasNext()){
			
			entityOpPermCRUD.clear();
			String entityName = (String)itSet.next();
    		for(EntityOperatePermissionRelation entityOpPer : entityOpPermList){
    			if(entityName.equals(entityOpPer.getEntityName())){
    				entityOpPermCRUD.add(entityOpPer);
    			}
    		}
    		insertTreeNode(rootNode,entityName, entityName, entityOpPermCRUD);
    		 
    		
		}
		for(TreeNode node : rootNode.getChildTreeNodeList()){
		    treeList.add(node);
		}

		ServletActionContext.getRequest().setAttribute("entityOpPermisTreeData",treeList);
		return "jsonDataRenderChain";
    }
	//递归插入节点
    void insertTreeNode(TreeNode parentNode, String entityName, String fullEntityName, List<EntityOperatePermissionRelation> entityList){
    	if(entityList.size() > 0) {
    		parentNode.setChecked("true");
    		parentNode.setExpanded("true");
    	}
    	if(entityName.indexOf(".") > -1){
    		
    		String nodeText = entityName.substring(0,entityName.indexOf("."));//...
    		List<TreeNode> childTreeNodeList = parentNode.getChildTreeNodeList();
    		if(childTreeNodeList == null){
    			childTreeNodeList = new ArrayList<TreeNode>();
    			TreeNode treeNode = new TreeNode();
    			treeNode.setText(nodeText);
    			childTreeNodeList.add(treeNode);
    			parentNode.setChildTreeNodeList(childTreeNodeList);
    			
    			insertTreeNode(treeNode, entityName.substring(entityName.indexOf(".") + 1, entityName.length()), fullEntityName, entityList);
    		}else{
    			boolean tag = true;
    			for(TreeNode node : childTreeNodeList){
    				if(nodeText.equals(node.getText())){
    					insertTreeNode(node,  entityName.substring(entityName.indexOf(".") + 1, entityName.length()), fullEntityName, entityList);
    					tag = false;
    				}
    			}
    			if(tag){
    				TreeNode treeNode = new TreeNode();
    				treeNode.setText(nodeText);
    				childTreeNodeList.add(treeNode);
//    				parentNode.setChildTreeNodeList(childTreeNodeList);
    				insertTreeNode(treeNode,  entityName.substring(entityName.indexOf(".") + 1, entityName.length()), fullEntityName, entityList);
    			}
    			
    		}
    	}else{
    		
    		List<TreeNode> childTreeNodeList = parentNode.getChildTreeNodeList();
    		List<TreeNode> treeLeaves = new ArrayList<TreeNode>();
    		
    		TreeNode treeNodeC = new TreeNode();
    		treeNodeC.setText("Add");
    		treeNodeC.setLeaf("true");
    		treeNodeC.setFullEntityName(fullEntityName);
    		
    		TreeNode treeNodeR = new TreeNode();
    		treeNodeR.setText("Delete");
    		treeNodeR.setLeaf("true");
    		treeNodeR.setFullEntityName(fullEntityName);
    		
    		TreeNode treeNodeU = new TreeNode();
    		treeNodeU.setText("Save");
    		treeNodeU.setLeaf("true");
    		treeNodeU.setFullEntityName(fullEntityName);
    		
    		TreeNode treeNodeD = new TreeNode();
    		treeNodeD.setText("View");
    		treeNodeD.setLeaf("true");
    		treeNodeD.setFullEntityName(fullEntityName);
    		
    		//根据数据库里entityList里的内容，设置check属性
    		for(EntityOperatePermissionRelation entity : entityList){
    			if("Add".equals(entity.getOperType())){
    				treeNodeC.setChecked("true");
    			}
    			else if("Delete".equals(entity.getOperType())){
    				treeNodeR.setChecked("true");
    			}
    			else if("Save".equals(entity.getOperType())){
    				treeNodeU.setChecked("true");
    			}
    			else if("View".equals(entity.getOperType())){
    				treeNodeD.setChecked("true");
    			}
    		}
    		treeLeaves.add(treeNodeC);
    		treeLeaves.add(treeNodeR);
    		treeLeaves.add(treeNodeU);
    		treeLeaves.add(treeNodeD);
    		
    		TreeNode treeNode = new TreeNode();
    		treeNode.setText(entityName);
    		if(entityList.size() > 0){
    			treeNode.setChecked("true");
    			treeNode.setExpanded("true");
    		}
    		treeNode.setChildTreeNodeList(treeLeaves);
    		
    		if (childTreeNodeList == null) {
    			childTreeNodeList = new ArrayList<TreeNode>();
    		}
    		childTreeNodeList.add(treeNode);
    		parentNode.setChildTreeNodeList(childTreeNodeList);
    	}
    }
}
