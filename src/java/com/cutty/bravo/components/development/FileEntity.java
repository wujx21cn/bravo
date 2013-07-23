package com.cutty.bravo.components.development;
/**
 * 该类用于存放实体所在的文件名及实体类的全名
 */
public class FileEntity {
	private String fullFileName;
	private String fileName;
	private String entityName;
	private int    sheetNo;
	private boolean imported=false; 
	private boolean locked=false;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	public int getSheetNo() {
		return sheetNo;
	}
	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}
	public String getFullFileName() {
		return fullFileName;
	}
	public void setImported(boolean imported) {
		this.imported = imported;
	}
	public boolean isImported() {
		return imported;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isLocked() {
		return locked;
	}
}
