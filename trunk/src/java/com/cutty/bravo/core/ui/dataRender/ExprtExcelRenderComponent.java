package com.cutty.bravo.core.ui.dataRender;

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

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.HtmlUtils;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;

public class ExprtExcelRenderComponent implements DataRenderComponent {

	private static final long serialVersionUID = 6875908748696205686L;
	private static final Log logger = LogFactory
			.getLog(ExprtExcelRenderComponent.class);

	public String rend(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String contextDataName = request.getParameter("contextDataName");
		String contextDataFieldName = request
				.getParameter("contextDataFieldName");
		String contextDataTitle = request.getParameter("contextDataTitle");
		String columnWidth = request.getParameter("excelColumnWidth");
		String fileName = request.getParameter("fileName");
		// 分解contextDataFieldName 其结构类似于“id_@_name”
		String[] fields = contextDataFieldName.split("_@_");
		String[] titles = contextDataTitle.split("_@_");
		String[] widths = columnWidth.split("_@_");
		// 判断contextDataName是否存在查询参数中，如果不存在,则直接从request attribute中获取.
		if (StringUtils.isEmpty(contextDataName)) {
			contextDataName = (String) request.getAttribute("contextDataName");
		}
		// sheetName设置
		if (StringUtils.isEmpty(contextDataName))
			throw new ServletException(
					"you shold defined the contextDataName!!!\n你必须定义contextDataName");
		// 获得查询结果集
		ArrayList contextData = (ArrayList) request
				.getAttribute(contextDataName);
		Iterator dataIt = contextData.iterator();

		String path = ServletActionContext.getRequest().getRealPath("/")+"uploadTemp/";
		FileOutputStream fileOut = new FileOutputStream(path + fileName);
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(fileOut);
		// 生成sheet 操作第一张工作表
		try {
			jxl.write.WritableSheet sheet = wwb.createSheet("sheet1", 0);
			// 定义列宽
			for (int i = 0; i < widths.length; i++) {
				sheet.setColumnView(i, Integer.valueOf(widths[i]) / 5 + 2);
			}
			// 定义表头样式
			WritableFont times12ptBold2 = new WritableFont(WritableFont.TIMES,
					12, WritableFont.BOLD);
			WritableCellFormat times12BoldFormat2 = new WritableCellFormat(
					times12ptBold2);
			times12BoldFormat2.setAlignment(Alignment.CENTRE);
			times12BoldFormat2.setBackground(Colour.GRAY_25);
			times12BoldFormat2.setBorder(Border.TOP, BorderLineStyle.THIN);
			times12BoldFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			times12BoldFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			times12BoldFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN);
			// 生成表头
			jxl.write.Label labelTitle = null;
			for (int i = 0; i < titles.length; i++) {
				labelTitle = new Label(i, 0, titles[i], times12BoldFormat2);
				sheet.addCell(labelTitle);
			}

			// 定义一种字体及单元格样式
			WritableFont wfc = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);

			WritableCellFormat wcfFContent = new WritableCellFormat(wfc);
			wcfFContent.setAlignment(Alignment.CENTRE);
			wcfFContent.setWrap(true);
			wcfFContent.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			wcfFContent.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			wcfFContent.setBorder(Border.LEFT, BorderLineStyle.THIN);

			// 开始为每行的单元cell填充数据
			for (int i = 0; dataIt.hasNext(); i++) {
				Object data = dataIt.next();
				String[] dataArray = dateConvExcel(fields, data);
				//下面的for语句判断字段名是否为"reason",是则过滤其html的语句
				for(int k = 0; k < fields.length; k ++){
					if("reason".equalsIgnoreCase(fields[k]))
							dataArray[k] = HtmlUtils.filter(HtmlUtils.html2Text(dataArray[k])); 
				}
				for (int j = 0; j < dataArray.length; j++) {
					labelTitle = new Label(j, i + 1, dataArray[j], wcfFContent);
					sheet.addCell(labelTitle);
				}
			}
			// 写入数据并关闭文件
			wwb.write();
			wwb.close();

		} catch (Exception e) {
			logger.debug(e);
		}

		return "";
	}

	public String[] dateConvExcel(String[] fields, Object value) {
		String displayString = "";
		String[] excelData = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			// 将所有---置换为.符合javaBean格式.jason 2008-10-8
			String dataIndexName = fields[i];
			String filedName = fields[i].replace("___", ".");
			// 如果参数中带"_@@_"则表明该列存在自定义值需要做特殊处理.jason 2008-10-8
			if (filedName.indexOf("_@@_") > -1) {
				String[] filedNameAarray = filedName.split("_@@_");
				dataIndexName = filedNameAarray[0];
				String currentContextDataFieldValueKey = filedNameAarray[1];
				Map<String, Object> context = new HashMap<String, Object>();
				context.put("rowValue", value);
				context.put("requestHandler", RequestHandler
						.getContextRequestHandler());
				FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine
						.getInstance();
				displayString = freemarkerTemplateEngine.renderFtl(
						ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"
								+ currentContextDataFieldValueKey.replace("@{",
										"${") + "</#escape>", context);
			} else {
				try {
					Object propertyValue = PropertyUtils.getProperty(value,
							filedName);
					if (propertyValue instanceof Date) {
						displayString = (new SimpleDateFormat(
								ConfigurableConstants.getProperty(
										"convert.format.date", "yyyy-MM-dd")))
								.format((Date) propertyValue);
					} else {
						displayString = propertyValue.toString();
					}
				} catch (Exception e) {
					displayString = null;// kukuxia.kevin 2008-10-31
					logger.warn(e);
				}
				if (null == displayString)
					displayString = "";
				excelData[i] = displayString;

			}

		}

		return excelData;
	}
}
