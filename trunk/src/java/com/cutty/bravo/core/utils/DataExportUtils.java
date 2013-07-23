package com.cutty.bravo.core.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.*;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.ui.dataRender.web.ExportFileComponentAction;

public class DataExportUtils {
	private static final Log logger = LogFactory.getLog(DataExportUtils.class);
	/*
	 * 根据传入的list做循环,并把实体的数据根据
	 * D:\bravo\trunk\webapp\WEB-INF\template\data\initData.xlsx 文件 中定义的格式导出数据
	 */
	public static String exportData(String fileName, List<String> entityNameList) throws IOException, WriteException {
		
		if(entityNameList.size() <=0 ){
			logger.warn("No entityName for export!/n需要导出的实体列表为空!");
			return "";
		}
		BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx()
				.getBean("baseDao");
		String path = ServletActionContext.getRequest().getRealPath("/")
				+ "uploadTemp/";
		FileOutputStream fileOut = new FileOutputStream(path + fileName);
		jxl.write.WritableWorkbook writableWorkbook = Workbook.createWorkbook(fileOut);
		try {
			for (String entityName : entityNameList) {
						
				EntityPersister meta = ((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory()).getEntityPersister(entityName);
				//获得实体的字段名称与字段类型
				String[] propertyNames = meta.getPropertyNames();
				Type[] propertyTypes = meta.getPropertyTypes();	
				Class entityClass;
				entityClass = Class.forName(entityName);
				List entityList = baseDao.getAll(entityClass);
				if(entityList.size() <= 0) continue;
				 
				// 生成sheet 操作第一张工作表
				String sheetName = entityName.substring(entityName.lastIndexOf(".")+1, entityName.length());
				//sheetName太长
				if(sheetName.length() > 20) sheetName = sheetName.substring(0,20);
				jxl.write.WritableSheet sheet = writableWorkbook.createSheet(sheetName, 0);
				
				// 定义表头样式
				WritableFont times12ptBold2 = new WritableFont(
						WritableFont.TIMES, 12, WritableFont.BOLD);
				WritableCellFormat times12BoldFormat2 = new WritableCellFormat(
						times12ptBold2);
				times12BoldFormat2.setAlignment(Alignment.CENTRE);
				times12BoldFormat2.setBackground(Colour.GRAY_25);
				times12BoldFormat2.setBorder(Border.TOP, BorderLineStyle.THIN);
				times12BoldFormat2
						.setBorder(Border.RIGHT, BorderLineStyle.THIN);
				times12BoldFormat2.setBorder(Border.BOTTOM,
						BorderLineStyle.THIN);
				times12BoldFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN);
				
				jxl.write.Label labelTitle = null;
 				
				//定义列宽
				for (int i = 0; i <= propertyNames.length; i++) {
					sheet.setColumnView(i, 25);
				}
				//第一行填上表的全名
				labelTitle = new Label(0, 0, entityName, times12BoldFormat2);
				sheet.addCell(labelTitle);
				// 生成表头
				labelTitle = new Label(0, 1, meta.getIdentifierPropertyName(), times12BoldFormat2);
				sheet.addCell(labelTitle);
				for (int i = 0; i < propertyNames.length; i++) {
					//byte[]数组类型不导出
					if("binary".equals(propertyTypes[i].getName())) continue;
					labelTitle = new Label(i+1, 1, propertyNames[i], times12BoldFormat2);
					sheet.addCell(labelTitle);
				}

				// 定义一种字体及单元格样式
				WritableFont wfc = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

				WritableCellFormat wcfFContent = new WritableCellFormat(wfc);
				wcfFContent.setAlignment(Alignment.CENTRE);
				wcfFContent.setWrap(true);
				wcfFContent.setBorder(Border.RIGHT, BorderLineStyle.THIN);
				wcfFContent.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
				wcfFContent.setBorder(Border.LEFT, BorderLineStyle.THIN);

				// 开始为每行的单元cell填充数据
				Iterator dataIt = entityList.iterator();
				fillContentCell(baseDao, meta, propertyNames, propertyTypes,
						sheet, wcfFContent, dataIt);
				

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		// 写入数据并关闭文件
		writableWorkbook.write();
		writableWorkbook.close();
		return "";
	}

	//填充内容
	static void fillContentCell(BaseDao baseDao, EntityPersister meta,
			String[] propertyNames, Type[] propertyTypes,
			jxl.write.WritableSheet sheet, WritableCellFormat wcfFContent,
			Iterator dataIt) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, WriteException,
			RowsExceededException {
		jxl.write.Label labelTitle;
		Session session = ((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory()).getCurrentSession();
		EntityMode entityMode = session.getEntityMode();
		for (int i = 0; dataIt.hasNext(); i++) {
			Object data = dataIt.next();
			//id先添加
			
			labelTitle = new Label(0, i + 2,meta.getIdentifier(data, entityMode).toString(),wcfFContent);
			sheet.addCell(labelTitle);
			//当获得的属性是一个BaseDomain的子类时，拿其ID进行存储
			for (int j = 0; j < propertyNames.length; j++) {
				//byte[]数组类型不导出
				if("binary".equals(propertyTypes[j].getName())) continue;
				Object propertyObj;
				 
				propertyObj = meta.getPropertyValue(data, propertyNames[j], entityMode);

				//如果propertyObj为null，直接下一个
				if(propertyObj == null){
					sheet.addCell(new Label(j+1, i + 2, "", wcfFContent));
					continue;
				}
				if(propertyTypes[j].isEntityType()){
					//many to one存放在excel中的字段为被引用实体的主键值. 
					//强制转化取得EntityType，然后拿到returnedClass,即可获得主键名
					EntityType entityType = (EntityType)propertyTypes[j];
					String manyToOneEntityName = entityType.getReturnedClass().getName();
					EntityPersister manyToOneMeta = ((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory()).getEntityPersister(manyToOneEntityName);
					labelTitle = new Label(j+1, i + 2,session.getIdentifier(propertyObj).toString(),wcfFContent);
				}else if(propertyTypes[j].isCollectionType()){
					//m2m导出的数据格式,类似于1;3;4;5 
					StringBuffer ids = new StringBuffer("");
					
					CollectionType collectionType = (CollectionType)propertyTypes[j];
					Type type = collectionType.getElementType((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory());
					//目的是取得主键名称
					String manyToManyEntityName = type.getReturnedClass().getName();
					EntityPersister manyToManyMeta = ((SessionFactoryImplementor)baseDao.getHibernate().getSessionFactory()).getEntityPersister(manyToManyEntityName);
					Iterator it = collectionType.getElementsIterator(propertyObj, (SessionImpl)(baseDao.getHibernate().getSessionFactory().getCurrentSession()));
					//遍历set或list里的数据，取得各自的主键的值,作;分开
					while(it.hasNext()){
						if(! "".equals(ids.toString())) ids.append(";");
						ids.append(session.getIdentifier(it.next()).toString());
					}
					labelTitle = new Label(j+1, i + 2, ids.toString(), wcfFContent);
				}else{
					
					labelTitle = new Label(j+1, i + 2,meta.getPropertyValue(data, propertyNames[j], entityMode).toString(),wcfFContent);
				}
				
				
				sheet.addCell(labelTitle);
			}
		}
	}
}
