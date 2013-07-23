package com.cutty.bravo.core.ui.dataRender;



import java.awt.Color;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;


import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.HtmlUtils;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.rtf.RtfWriter2;


	public class ExprtWordRenderComponent implements DataRenderComponent{ 
		
		
		private static final Log logger = LogFactory.getLog(ExprtWordRenderComponent.class);
	
		 public void writeWORD(String fileName) {
		 }
		
		@SuppressWarnings("deprecation")
		public String rend(HttpServletRequest request,HttpServletResponse response) 
        throws IOException, ServletException {			
			String contextDataName = request.getParameter("contextDataName");
			String contextDataFieldName = request.getParameter("contextDataFieldName");
			String contextDataTitle = request.getParameter("contextDataTitle");
			String columnWidth = request.getParameter("wordColumnWidth");
			String fileName = request.getParameter("fileName");
			//分解contextDataFieldName 其结构类似于“id_@_name”
			String[] fields = contextDataFieldName.split("_@_");
			String[] titles = contextDataTitle.split("_@_");
			String[] widths = columnWidth.split("_@_");
			//判断contextDataName是否存在查询参数中，如果不存在,则直接从request attribute中获取.
			if (StringUtils.isEmpty(contextDataName)){
				contextDataName = (String) request.getAttribute("contextDataName");
			}
			if (StringUtils.isEmpty(contextDataName)) throw new ServletException("you shold defined the contextDataName!!!\n你必须定义contextDataName");
			//获得查询结果集
			ArrayList contextData = (ArrayList)request.getAttribute(contextDataName);
			Iterator dataIt = contextData.iterator();
			String path = ServletActionContext.getRequest().getRealPath("/")+"uploadTemp/";
			File file = new File(path+fileName);
		    FileOutputStream out = null;
		  
		      
		    try {          
		    	// 定义输出位置并把文档对象装入输出对象中    
		 	out = new FileOutputStream(file);	    	
		    Document doc = new Document(); 
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();   
		    RtfWriter2.getInstance(doc, out);            // 打开文档对象       
		    doc.open();         
		    com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 
		   	     18, Font.BOLD, new Color(0, 0, 255));
		    
		    if (StringUtils.isEmpty(contextDataName)){
				contextDataName = (String) request.getAttribute("contextDataName");
			}
		  //创建表格对象
			   Table table = new Table(titles.length, contextData.size() + 1);
			   //设置表格边框颜色
			  // table.setBorderColor(new Color(220, 255, 100));
			   //设置单元格的边距间隔等
			   table.setDefaultHorizontalAlignment(Element.ALIGN_MIDDLE);
			   table.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);


			   table.setPadding(1);
			   table.setSpacing(0);
			   table.setBorderWidth(1);
			   //单元格对象
			   Cell cell = null;
			   //添加表头信息
				   //允许在PDF中写入中文，将字体文件放在classPath中。
			   //simfang.ttf是仿宋的字体文件
			   BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			  

			   //中文大小为10
			   font = new Font(bfChinese, 10);
			   for (int colNum=0; colNum<titles.length; colNum++){
			    cell = new Cell(new Chunk(titles[colNum],font));
			    cell.setBackgroundColor(Color.LIGHT_GRAY);
			    
			    cell.setHeader(true);
			    table.addCell(cell);
			   }
			   table.endHeaders();
			   //添加表的内容
				for(int i = 1; dataIt.hasNext(); i ++){
					Object data = dataIt.next();
					String [] dataArray = dateConvWord(fields, data);
					//下面的for语句判断字段名是否为"reason",是则过滤其html的语句
					for(int k = 0; k < fields.length; k ++){
						if("reason".equalsIgnoreCase(fields[k]))
								dataArray[k] = HtmlUtils.filter(HtmlUtils.html2Text(dataArray[k])); 
					}
					for(short j = 0; j < dataArray.length; j ++){
						cell = new Cell(new Chunk(dataArray[j],font));
						table.addCell(cell);
					}
				}   
		      
		    doc.add(table);           
		    // 关闭文档对象，释放资源        
		    doc.close();     
		    System.out.println("WORD文件生成成功，WORD文件名：" + file.getAbsolutePath());
			  } catch (DocumentException e) {
			   System.out.println("WORD文件"+ file.getAbsolutePath() + "生成失败！" + e);
			   e.printStackTrace();
			  } catch (IOException ee) {
			   System.out.println("WORD文件"+ file.getAbsolutePath() + "生成失败！" + ee);
			   ee.printStackTrace();
			  } finally {
			   if (out != null){
			    try {
			     //关闭输出文件流
			     out.close();
			    } catch (IOException e1) {
			    }
			   }
			  }
			return null;
		}

		public String[] dateConvWord(String[] fields, Object value ){
			String displayString = "";
			String[] wordData = new String[fields.length];
			for(int i=0;i<fields.length;i++){
				//将所有---置换为.符合javaBean格式.jason 2008-10-8
				String dataIndexName = fields[i];
				String filedName = fields[i].replace("___", ".");
				//如果参数中带"_@@_"则表明该列存在自定义值需要做特殊处理.jason 2008-10-8
				if (filedName.indexOf("_@@_") > -1){
					String[] filedNameAarray = filedName.split("_@@_");
					dataIndexName = filedNameAarray[0];
					String currentContextDataFieldValueKey = filedNameAarray[1];
					Map<String,Object> context = new HashMap<String,Object>();
					context.put("rowValue", value);
					context.put("requestHandler", RequestHandler.getContextRequestHandler());
					FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
					displayString = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+currentContextDataFieldValueKey.replace("@{", "${")+"</#escape>",context);
				} else {
					try {
						Object propertyValue = PropertyUtils.getProperty(value, filedName);
						if (propertyValue instanceof Date){
							displayString = (new SimpleDateFormat(ConfigurableConstants.getProperty("convert.format.date","yyyy-MM-dd"))).format((Date)propertyValue);
						} else {
							displayString = propertyValue.toString();
						}
					} catch(Exception e) {
						displayString = null;//kukuxia.kevin 2008-10-31
						logger.warn(e);
					}
					if (null == displayString) displayString = "";
					wordData[i] = displayString;
					
				}
				
			 
			}
			 
			return wordData;
		} 	
		}
