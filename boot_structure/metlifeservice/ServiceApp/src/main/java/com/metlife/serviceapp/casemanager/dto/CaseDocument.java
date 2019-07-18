package com.metlife.serviceapp.casemanager.dto;

import java.util.Arrays;

public class CaseDocument {
	private String docClass;
	private String fileBaseStr;
	private String fileName;
	private String []customPropertyNames;
	private String []customPropertyValues;
	
	
	public String getDocClass() {
		return docClass;
	}
	public void setDocClass(String docClass) {
		this.docClass = docClass;
	}
	public String getFileBaseStr() {
		return fileBaseStr;
	}
	public void setFileBaseStr(String fileBaseStr) {
		this.fileBaseStr = fileBaseStr;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String [] getCustomPropertyNames() {
		return customPropertyNames;
	}
	public void setCustomPropertyNames(String [] customPropertyNames) {
		this.customPropertyNames = customPropertyNames;
	}
	public String [] getCustomPropertyValues() {
		return customPropertyValues;
	}
	public void setCustomPropertyValues(String [] customPropertyValues) {
		this.customPropertyValues = customPropertyValues;
	}
	@Override
	public String toString() {
		return "CaseDocument [docClass=" + docClass + ", fileName=" + fileName + ", customPropertyNames="
				+ Arrays.toString(customPropertyNames) + ", customPropertyValues="
				+ Arrays.toString(customPropertyValues) + "]";
	}
	
}
