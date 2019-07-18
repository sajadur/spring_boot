package com.metlife.serviceapp.casemanager.dto;

import java.util.Arrays;


public class CaseInput {
	private String caseTypeName;
	private String []customPropertyNames;
	private String []customPropertyValues;
	private CaseDocument []caseDocument;
	
	
	public String getCaseTypeName() {
		return caseTypeName;
	}
	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}
	public String[] getCustomPropertyNames() {
		return customPropertyNames;
	}
	public void setCustomPropertyNames(String[] customPropertyNames) {
		this.customPropertyNames = customPropertyNames;
	}
	public String[] getCustomPropertyValues() {
		return customPropertyValues;
	}
	public void setCustomPropertyValues(String[] customPropertyValues) {
		this.customPropertyValues = customPropertyValues;
	}
	public CaseDocument [] getCaseDocument() {
		return caseDocument;
	}
	public void setCaseDocument(CaseDocument [] caseDocument) {
		this.caseDocument = caseDocument;
	}
	@Override
	public String toString() {
		return "CaseInput [caseTypeName=" + caseTypeName + ", customPropertyNames="
				+ Arrays.toString(customPropertyNames) + ", customPropertyValues="
				+ Arrays.toString(customPropertyValues) + ", caseDocument=" + Arrays.toString(caseDocument) + "]";
	}
	
}
