/* com.cutty.bravo.core.dao.support.Page.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午06:33:57, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ConfigurableConstants;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * <p>
 * <a href="Page.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@SuppressWarnings("serial")
 public class Page implements Serializable {
		
		private static int DEFAULT_PAGE_SIZE = Integer.parseInt(ConfigurableConstants.getProperty("query.common.constant.default_page_size","20"));
		
		private static int DEFAULT_PAGE_START = Integer.parseInt(ConfigurableConstants.getProperty("query.common.constant.default_page_start","0"));//我暂时改成零

		private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

		private int start = DEFAULT_PAGE_START; // 当前页第一条数据在List中的位置,从1开始

		private Object data; // 当前页中存放的记录,类型一般为List

		private int totalCount; // 总记录数

		/**
		 * 构造方法，禁止构造空页.
		 */
		private Page() {}

		/**
		 * 默认构造方法.
		 *
		 * @param start	 本页数据在数据库中的起始位置
		 * @param totalSize 数据库中总记录条数
		 * @param pageSize  本页容量
		 * @param data	  本页包含的数据
		 */
		public Page(int start, int totalSize, int pageSize, Object data) {
			this.pageSize = pageSize;
			this.start = start;
			this.totalCount = totalSize;
			this.data = data;
		}

		/**
		 * 取总记录数.
		 */
		public int getTotalCount() {
			return this.totalCount;
		}
		
		/**
		 * 设置总记录数.
		 */
		public int setTotalCount(int totalCount) {
			return this.totalCount = totalCount;
		}
		
		/**
		 * 取当前页中的记录.
		 */
		public Object getResult() {
			return data;
		}
		
		/**
		 * 设置前页中的记录.
		 */
		public Object setResult(Object data) {
			return this.data=data;
		}
		
		/**
		 * 取总页数.
		 */
		public int getStartIndex() {
			return this.start ;
		}
		
		/**
		 * 取总页数.
		 */
		public int getTotalPageCount() {
			if (totalCount % pageSize == 0)
				return totalCount / pageSize;
			else
				return totalCount / pageSize + 1;
		}

		/**
		 * 取每页数据容量.
		 */
		public int getPageSize() {
			return pageSize;
		}

		/**
		 * 取该页当前页码,页码从1开始.
		 */
		public int getCurrentPageNo() {
			return start/ pageSize + 1;
		}

		/**
		 * 该页是否有下一页.
		 */
		public boolean hasNextPage() {
			return this.start < this.getTotalPageCount() - 1;
		}

		/**
		 * 该页是否有上一页.
		 */
		public boolean hasPreviousPage() {
			return this.start > 1;
		}

		/**
		 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
		 *
		 * @see #getStartOfPage(int,int)
		 */
		protected static int getStartOfPage(int pageNo) {
			return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
		}

		/**
		 * 获取任一页第一条数据在数据集的位置.
		 *
		 * @param pageNo   从1开始的页号
		 * @param pageSize 每页记录条数
		 * @return 该页第一条数据
		 */
		public static int getStartOfPage(int pageNo, int pageSize) {
			return (pageNo - 1) * pageSize;
		}
		
		public static Page getInstance(HttpServletRequest request) {
			Page pageObject = new Page();
			if (null == request) return pageObject;
			pageObject.reqProperty(request);
			return pageObject;
		}
		
		public void reqProperty(HttpServletRequest request) {
			if (StringUtils.isNotEmpty(request.getParameter(ConfigurableConstants.getProperty("query.common.page_start_name","start")))){
				int startValue = Integer.parseInt(request.getParameter(ConfigurableConstants.getProperty("query.common.page_start_name","start")));
				if (startValue >=0){
					this.start =  startValue;
				}
			}
			if (StringUtils.isNotEmpty(request.getParameter(ConfigurableConstants.getProperty("query.common.page_size_name","limit")))){
				int limitValue =  Integer.parseInt(request.getParameter(ConfigurableConstants.getProperty("query.common.page_size_name","limit")));
				if (limitValue>0){
					this.pageSize = limitValue;
				}
			}
		}
		
	}