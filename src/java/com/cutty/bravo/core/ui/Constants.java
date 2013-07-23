/* com.cutty.bravo.core.ui.Constants.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 23, 2008 1:16:56 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui;

/**
 * <p> UI模块所有静态字段集合 </p>
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public interface Constants {
	//表单保存状态及返回信息 start------------------------------------------------------------------	
	/**
	 * 当request存在该SUBMIT_KEY,json渲染器将做处理 
	 */
	public static final String FORM_AJAX_SUBMIT_KEY = "jasonFormSubmit";
	/**
	 * 是否调用json渲染器做处理 Y：是 
	 */
	public static final String FORM_AJAX_SUBMIT_VALUE = "Y";
	/**
	 * Form表单Ajax提交状态
	 */
	public static final String FORM_AJAX_SUBMIT_STATUS = "FORM_AJAX_SUBMIT_STATUS";
	/**
	 * 提交返回信息
	 */
	public static final String FORM_AJAX_SUBMIT_MSG = "FORM_AJAX_SUBMIT_MSG";
	
	/**
	 * 提交返回参数
	 */
	public static final String FORM_AJAX_SUBMIT_PARAM = "FORM_AJAX_SUBMIT_PARAM";
	
	/**
	 * 表单保存状态_成功
	 */
	public static final String FORM_AJAX_SUBMIT_SUCCESS = "true";
	/**
	 * 表单保存状态_失败
	 */
	public static final String FORM_AJAX_SUBMIT_FAILURE = "false";
	//表单保存状态及返回信息 end------------------------------------------------------------------

	//通用Ajax处理数据保存及返回信息 start------------------------------------------------------------------
	/**
	 * 通用Ajax处理数据保存及返回信息
	 * 当request存在该AJAX_HANDLE_KEY,json渲染器将做处理 
	 */
	public static final String AJAX_HANDLE_KEY = "ajaxHandleResponse";
	/**
	 * 是否调用json渲染器做处理 Y：是
	 */
	public static final String AJAX_HANDLE_VALUE = "Y";
	/**
	 * Ajax提交状态
	 */
	public static final String AJAX_HANDLE_STATUS = "AJAX_HANDLE_STATUS";
	/**
	 * Ajax处理返回信息
	 */
	public static final String AJAX_HANDLE_MSG = "AJAX_HANDLE_MSG";
	/**
	 * 提交状态返回_成功
	 */
	public static final String AJAX_HANDLE_STATUS_SUCCESS = "success";
	/**
	 * 提交状态返回_失败
	 */
	public static final String AJAX_HANDLE_STATUS_FAILURE = "failure";
	//通用Ajax提交数据保存及返回信息 end-----------------------------------------------------------------------	
	
	//表格批量删除状态及返回信息 start------------------------------------------------------------------
	/**
	 * 表格批量删除
	 * 当request存在该GRID_BATCH_REMOVE_KEY,json渲染器将做处理 
	 */
	public static final String GRID_BATCH_REMOVE_KEY = "jasonBatchRemove";
	/**
	 * 是否调用json渲染器处理 Y：是
	 */
	public static final String GRID_BATCH_REMOVE_VALUE = "Y";
	/**
	 * 批量删除状态
	 */	
	public static final String GRID_BATCH_REMOVE_STATUS = "GRID_BATCH_REMOVE_STATUS";
	/**
	 * 批量删除反馈信息
	 */
	public static final String GRID_BATCH_REMOVE_MSG = "GRID_BATCH_REMOVE_MSG";
	/**
	 * 表格批量删除状态_成功
	 */
	public static final String GRID_BATCH_REMOVE_STATUS_SUCCESS = "success";
	/**
	 * 表格批量删除状态_失败
	 */
	public static final String GRID_BATCH_REMOVE_STATUS_FAILURE = "failure";
	//表格批量删除状态及返回信息 end-----------------------------------------------------------------------
	

	//表格批量保存状态及返回信息 start------------------------------------------------------------------
	/**
	 * 表格批量保存
	 * 当request存在该GRID_BATCH_SAVE_KEY,json渲染器将做处理 
	 */
	public static final String GRID_BATCH_SAVE_KEY = "jasonBatchSave";
	/**
	 * 是否调用json渲染器处理 Y：是
	 */
	public static final String GRID_BATCH_SAVE_VALUE = "Y";
	/**
	 * 批量保存状态
	 */		
	public static final String GRID_BATCH_SAVE_STATUS = "GRID_BATCH_SAVE_STATUS";
	/**
	 * 批量保存反馈信息
	 */	
	public static final String GRID_BATCH_SAVE_MSG = "GRID_BATCH_SAVE_MSG";
	/**
	 * 表格批量保存状态_成功
	 */	
	public static final String GRID_BATCH_SAVE_STATUS_SUCCESS = "success";
	/**
	 * 表格批量保存状态_失败
	 */	
	public static final String GRID_BATCH_SAVE_STATUS_FAILURE = "failure";
	//表格批量删除状态及返回信息 end-----------------------------------------------------------------------
	
	//表单获取数据返回信息 start---------------------------------------------------------------------------
	/**
	 * 表单自动加载数据
	 */
	public static final String FORM_LOAD_STATUS = "FORM_LOAD_STATUS";
	/**
	 * 表单自动加载数据反馈信息
	 */
	public static final String FORM_LOAD_MSG = "FORM_LOAD_MSG";
	/**
	 * 表单自动加载数据_成功
	 */
	public static final String FORM_LOAD_STATUS_SUCCESS = "success";
	/**
	 * 表单自动加载数据_失败
	 */
	public static final String FORM_LOAD_STATUS_FAILURE = "failure";
	
	//表单获取数据返回信息end---------------------------------------------------------------------------
	
	//多对多选择新增时传递的参数start------------------------------------------------------------------
	
	public static final String M2M_SELECT_IDS = "_M2M_SELECT_IDS";
	/**
	 * 目标grid的ID
	 */	
	public static final String M2M_TARGET_GRID = "_M2MSelect_targetGridName";
	/**
	 * 初始grid的ID
	 */	
	public static final String M2M_ORIGIN_GRID = "_M2MSelect_originGridName";
	/**
	 * 查询实体名
	 */	
	public static final String M2M_ENTITY_NAME = "_M2MSelect_entityName";
	/**
	 * 该多对多字段名
	 */	
	public static final String M2M_FIELD_NAME = "_M2MSelect_fieldName";
	/**
	 * 该实体ID
	 */	
	public static final String M2M_ENTITY_ID = "_M2M_ENTITY_ID";
	//多对多选择新增时传递的参数end------------------------------------------------------------------
	
	
	//Popu选择新增时传递的参数start------------------------------------------------------------------
	/**
	 * Popu选择时控件名称
	 */	
	public static final String POPU_SELECT_NAME = "_PopuSelect_name";
	/**
	 * 目标Gird名称
	 */	
	public static final String POPU_TARGET_GRID = "_PopuSelect_targetGridName";
	/**
	 * Popu选择时隐藏控件名称
	 */	
	public static final String POPU_HIDDEN_NAME = "_PopuSelect_hiddenName";
	/**
	 * Popu选择时返回的隐藏值
	 */	
	public static final String POPU_VALUE_FIELD = "_PopuSelect_valueField";
	/**
	 * Popu选择时返回的显示值
	 */	
	public static final String POPU_DISPLAY_FIELD = "_PopuSelect_displayField";

	//多对多选择新增时传递的参数end------------------------------------------------------------------

	
	//工作流按钮动态产生时传递的参数start------------------------------------------------------------------
	public static final String WORKFLOW_BUTTON_KEY = "jsonWorkFlowButtonData";
	public static final String WORKFLOW_BUTTON_VALUE = "Y";

	public static final String WORKFLOW_BUTTON_CONTEXT_NAME = "jsonWorkFlowButtonContextData";
	//工作流按钮动态产生时传递的参数end------------------------------------------------------------------
	
}
