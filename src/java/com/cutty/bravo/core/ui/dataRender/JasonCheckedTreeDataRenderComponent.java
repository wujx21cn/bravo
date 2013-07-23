/* com.cutty.bravo.core.ui.dataRender.JasonCheckedTreeDataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 4, 2008 11:11:11 PM, Created kukuxia.kevin.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ui.tags.tree.TreeNode;

/**
 * <p> 返回Tree数据格式的json渲染器 </p>
 * <p>
 * <a href="JasonCheckedTreeDataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class JasonCheckedTreeDataRenderComponent implements DataRenderComponent {
	private static final Log logger = LogFactory.getLog(JasonCheckedTreeDataRenderComponent.class);

    /**
     * 实现 DataRenderComponent 接口 rend(HttpServletRequest request, HttpServletResponse response)方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return json格式返回信息 String
     */
	public String rend(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		StringBuffer jsonDataSB = new StringBuffer("");
		String contextDataName = request.getParameter("contextDataName");
		if (StringUtils.isNotEmpty(contextDataName)){
			jsonDataSB.append("[");
			List<TreeNode> contextData = (List<TreeNode>)request.getAttribute(contextDataName);
			boolean hasNotPaserData = true;
			if (null != contextData){
//				Iterator<TreeNode> contextDataIT = contextData.iterator();
//				while (contextDataIT.hasNext()){
//    				TreeNode treeNode = contextDataIT.next();
//				}
				for(int i=0;i<contextData.size();i++){
					TreeNode treeNode = contextData.get(i);
    				if (hasNotPaserData){
		    			hasNotPaserData = false;
		    		} else {
		    			jsonDataSB.append(",");
		    		}
		    		jsonDataSB.append("{");
		    		//以下为树数据解释,目前只定义部分必须的属性,可以扩充com.cutty.bravo.core.ui.tags.tree.TreeNode
		    		if (StringUtils.isNotEmpty(treeNode.getId())){
		    			jsonDataSB.append("\"id\":\"").append(treeNode.getId()).append("\",");
		    		}
		    		if (StringUtils.isNotEmpty(treeNode.getText())){
		    			jsonDataSB.append("\"text\":\"").append(treeNode.getText()).append("\",");
		    		}		
		    		if (StringUtils.isNotEmpty(treeNode.getCls())){
		    			jsonDataSB.append("\"cls\":\"").append(treeNode.getCls()).append("\",");
		    		}
		    		if (StringUtils.isNotEmpty(treeNode.getHref())){
		    			jsonDataSB.append("\"href\":\"").append(treeNode.getHref()).append("\",");
		    		}	
		    		if (StringUtils.isNotEmpty(treeNode.getIconCls())){
		    			jsonDataSB.append("\"iconCls\":\"").append(treeNode.getIconCls()).append("\",");
		    		}	    		
		    		if (StringUtils.isNotEmpty(treeNode.getLeaf())){
		    			jsonDataSB.append("\"leaf\":\"").append(treeNode.getLeaf()).append("\",");
		    		}
		    		if (StringUtils.isNotEmpty(treeNode.getExpanded())){
		    			jsonDataSB.append("\"expanded\":").append(treeNode.getExpanded()).append(",");
		    		}
		    		if (StringUtils.isNotEmpty(treeNode.getChecked())&&treeNode.getChecked().equals("true")){
		    			jsonDataSB.append("\"checked\":\"").append(treeNode.getChecked()).append("\",");
		    		}
		    		if(treeNode.getChildTreeNodeList()!=null&&treeNode.getChildTreeNodeList().size()>0){
		    			jsonDataSB.append(addChildMenu(treeNode.getChildTreeNodeList()));
		    			jsonDataSB.append(",");
		    		}
		    		jsonDataSB.delete(jsonDataSB.length()-1, jsonDataSB.length());
		    		jsonDataSB.append("}");
				}
    			jsonDataSB.append("]");
			} else {
        		logger.error("you should define the contextData for tree!!!\n你必须定义树的数据源!!!");
        		throw new ServletException("you should define the contextData for tree!!!\n你必须定义树的数据源!!!");
    		}
		} else {
    		logger.error("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
    		throw new ServletException("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
    	}
		return jsonDataSB.toString();
	}

	/**
	 *  递归组装有子节点的父节点，组装形如如下的格式：
	 *   [{id: 1,text: 'A leaf Node',leaf: true},
	 *    {id: 2,text: 'A folder Node',children: [{id: 3,text: 'A child Node',leaf: true}]
     *   }]
     *   @param TreeNodeList List<TreeNode>
     *   @return String
	 */
	
	public String addChildMenu(List<TreeNode> TreeNodeList){
		StringBuffer sb = new StringBuffer();
		sb.append("children:[");
		boolean hasNotPaserData = true;
		for(int i=0;i<TreeNodeList.size();i++){
			TreeNode treeNode = TreeNodeList.get(i);
			if (hasNotPaserData){
    			hasNotPaserData = false;
    		} else {
    			sb.append(",");
    		}
			sb.append("{");	
    		if (StringUtils.isNotEmpty(treeNode.getId())){
    			sb.append("\"id\":\"").append(treeNode.getId()).append("\",");
    		}
    		if (StringUtils.isNotEmpty(treeNode.getText())){
    			sb.append("\"text\":\"").append(treeNode.getText()).append("\",");
    		}	
    		if (StringUtils.isNotEmpty(treeNode.getCls())){
    			sb.append("\"cls\":\"").append(treeNode.getCls()).append("\",");
    		}
    		if (StringUtils.isNotEmpty(treeNode.getHref())){
    			sb.append("\"href\":\"").append(treeNode.getHref()).append("\",");
    		}	
    		if (StringUtils.isNotEmpty(treeNode.getIconCls())){
    			sb.append("\"iconCls\":\"").append(treeNode.getIconCls()).append("\",");
    		}	    		
    		if (StringUtils.isNotEmpty(treeNode.getLeaf())){
    			sb.append("\"leaf\":\"").append(treeNode.getLeaf()).append("\",");
    		}
    		if (StringUtils.isNotEmpty(treeNode.getExpanded())){
    			sb.append("\"expanded\":").append(treeNode.getExpanded()).append(",");
    		}
    		if (StringUtils.isNotEmpty(treeNode.getChecked())&&treeNode.getChecked().equals("true")){
    			sb.append("\"checked\":\"").append(treeNode.getChecked()).append("\",");
    		}
    		if(treeNode.getChildTreeNodeList()!=null&&treeNode.getChildTreeNodeList().size()>0){
    			sb.append(addChildMenu(treeNode.getChildTreeNodeList()));
    			sb.append(",");
    		}
    		sb.delete(sb.length()-1, sb.length());
    		sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}
