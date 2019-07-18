package com.metlife.serviceapp.casemanager.methods;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.ibm.casemgmt.api.Case;
import com.ibm.casemgmt.api.CaseType;
import com.ibm.casemgmt.api.constants.ModificationIntent;
import com.ibm.casemgmt.api.context.CaseMgmtContext;
import com.ibm.casemgmt.api.context.P8ConnectionCache;
import com.ibm.casemgmt.api.context.SimpleVWSessionCache;
import com.ibm.casemgmt.api.objectref.ObjectStoreReference;
import com.ibm.casemgmt.api.properties.CaseMgmtProperties;
import com.ibm.casemgmt.api.properties.CaseMgmtProperty;
import com.ibm.json.java.JSONObject;
import com.metlife.serviceapp.casemanager.dto.CaseDocument;
import com.metlife.serviceapp.casemanager.dto.CaseInput;
import com.metlife.serviceapp.casemanager.utils.CEConnectionUtility;
import com.metlife.serviceapp.casemanager.utils.CaseUtils;
import com.metlife.serviceapp.casemanager.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CaseOperation {
	CEConnectionUtility ceConnection ;
	private static Logger logger = Logger.getLogger(CaseOperation.class);
	public CaseOperation(CEConnectionUtility ceConnection){
		this.ceConnection=ceConnection;
	}
	public JSONObject createCaseUsingSpecifiedCaseType(CaseInput input) throws Exception {
			String caseTypeName = input.getCaseTypeName();
			String[] customPropertyNames = input.getCustomPropertyNames();
			String[] customPropertyValues = input.getCustomPropertyValues();
			
		logger.debug("createCaseUsingSpecifiedCaseType ENTER...");
		logger.info("caseTypeName :"+caseTypeName +" customPropertyNames" +customPropertyNames+" customPropertyValues:"+customPropertyValues);
		
		CaseMgmtContext cmc =null;
		JSONObject object = new JSONObject();
		try {
			P8ConnectionCache connCache = ceConnection.connect();			
			ObjectStore targetOS = ceConnection.fetchTOS();

			cmc = CaseMgmtContext.set(new CaseMgmtContext( new SimpleVWSessionCache(),connCache) );
			

			ObjectStoreReference targetOsRef = new ObjectStoreReference(targetOS);

			CaseType caseType = CaseType.fetchInstance(targetOsRef, caseTypeName);
			Case pendingCase = Case.createPendingInstanceFetchDefaults(caseType);
			CaseMgmtProperties properties = pendingCase.getProperties();

			int index = 0;
			boolean hasDependentProperties = false;
			while (index < customPropertyNames.length) {
				if (customPropertyNames[index].isEmpty()) {
					index++;
				} else {
					CaseMgmtProperty prop = properties.get(customPropertyNames[index]);
					Cardinality c = prop.getCardinality();

					hasDependentProperties = (hasDependentProperties) || (prop.hasDependentProperties());
					if (c == Cardinality.SINGLE) {
						TypeID type = prop.getPropertyType();
						if (type == TypeID.STRING) {
							if (customPropertyValues[index].contentEquals("") != true) {
								properties.putObjectValue(customPropertyNames[index], customPropertyValues[index]);
							}
						} else if (type == TypeID.DOUBLE) {
							if (customPropertyValues[index].contentEquals("") != true) {
								properties.putObjectValue(customPropertyNames[index],
										Double.valueOf(Double.valueOf(customPropertyValues[index]).doubleValue()));
							}
						} else if (type == TypeID.BOOLEAN) {
							if (customPropertyValues[index].contentEquals("") != true) {
								properties.putObjectValue(customPropertyNames[index],
										Boolean.valueOf(Boolean.valueOf(customPropertyValues[index]).booleanValue()));
							}
						} else if (type == TypeID.DATE) {
							if (customPropertyValues[index].contentEquals("") != true) {
								try {
									properties.putObjectValue(customPropertyNames[index], customPropertyValues[index]);
								} catch (Exception ee) {
									Exception e = new Exception("Invalid date type format found in case operations");
									throw e;
								}
							}
						} else if (type == TypeID.LONG) {
							if (customPropertyValues[index].contentEquals("") != true) {
								properties.putObjectValue(customPropertyNames[index],
										Long.valueOf(Long.valueOf(customPropertyValues[index]).longValue()));
							}
						} else {
							Exception e = new Exception("Invalid type: " + customPropertyNames[index]);
							throw e;
						}
					} else {
						TypeID type = prop.getPropertyType();
						if (type == TypeID.STRING) {
							List<String> stringValues = CaseUtils.parseMultiValuesForTypeString(customPropertyValues[index]);
							List<String> dataList = new ArrayList();
							if (stringValues != null) {
								Iterator iter = stringValues.iterator();
								while (iter.hasNext()) {
									String value = (String) iter.next();
									dataList.add(value);
								}
								properties.putObjectValue(customPropertyNames[index], dataList);
							}
						} else if (type == TypeID.DOUBLE) {
							List<String> stringValues = CaseUtils.parseMultiValues(customPropertyValues[index]);
							List<Double> dataList = new ArrayList();
							if (stringValues != null) {
								Iterator iter = stringValues.iterator();
								while (iter.hasNext()) {
									String value = (String) iter.next();
									dataList.add(Double.valueOf(value));
								}
								properties.putObjectValue(customPropertyNames[index], dataList);
							}
						} else if (type == TypeID.BOOLEAN) {
							List<String> stringValues = CaseUtils.parseMultiValues(customPropertyValues[index]);
							List<Boolean> dataList = new ArrayList();
							if (stringValues != null) {
								Iterator iter = stringValues.iterator();
								while (iter.hasNext()) {
									String value = (String) iter.next();
									dataList.add(Boolean.valueOf(value));
								}
								properties.putObjectValue(customPropertyNames[index], dataList);
							}
						} else if (type == TypeID.DATE) {
							List<String> stringValues = CaseUtils.parseMultiValues(customPropertyValues[index]);
							List<String> dataList = new ArrayList();
							if (stringValues != null) {
								Iterator iter = stringValues.iterator();
								while (iter.hasNext()) {
									String value = (String) iter.next();
									dataList.add(value);
								}
								properties.putObjectValue(customPropertyNames[index], dataList);
							}
						} else if (type == TypeID.LONG) {
							List<String> stringValues = CaseUtils.parseMultiValues(customPropertyValues[index]);
							List<Long> dataList = new ArrayList();
							if (stringValues != null) {
								Iterator iter = stringValues.iterator();
								while (iter.hasNext()) {
									String value = (String) iter.next();
									dataList.add(Long.valueOf(value));
								}
								properties.putObjectValue(customPropertyNames[index], dataList);
							}
						} else {
							Exception e = new Exception("Invalid type: " + customPropertyNames[index]);
							throw e;
						}
					}
					index++;
				}
			}
			if (hasDependentProperties) {
				pendingCase.fetchDependentPropertyChanges();
			}
			pendingCase.save(RefreshMode.REFRESH, null, ModificationIntent.MODIFY);
			String pathName = (String) pendingCase.getProperties().get("PathName").getValue();
			logger.info("pathName::"+pathName);
			Folder destFolder = Factory.Folder.fetchInstance(targetOsRef.getFetchlessCEObject(), pathName, null);
		    UpdatingBatch batch = UpdatingBatch.createUpdatingBatchInstance(ceConnection.getDomain(), RefreshMode.NO_REFRESH);
		    
		    CaseDocument[]caseDocuments = input.getCaseDocument();
		    if(caseDocuments!=null && caseDocuments.length>0){
		    	for(int i=0;i<caseDocuments.length;i++){
		    		CaseDocument caseDocument =caseDocuments[i];
		    		if(caseDocument!=null ){
		    			String fileName1=caseDocument.getFileName();
					    Document doc1 = getDocument(targetOS,caseDocument);
					    if(doc1!=null){
					    	DynamicReferentialContainmentRelationship drcr1 = (DynamicReferentialContainmentRelationship)destFolder.file(doc1, AutoUniqueName.AUTO_UNIQUE, fileName1, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
					    	batch.add(drcr1, null);
					    }	
		    		}
		    		
		    	}
		    }else{
		    	logger.info("CaseDocuments array is null..");
		    }
           
            batch.updateBatch();
			object.put("code", Constants.SUCCESS_CODE);
			object.put("guid", pendingCase.getId().toString());
			return object ;
		} catch (Exception e) {
			throw e;
		} finally {
			CaseMgmtContext.set(cmc);
		}
	}
	public Document getDocument(ObjectStore os , CaseDocument caseDocument) throws Exception{
		logger.info("Inside getDocument.........caseDocument:"+caseDocument.toString());
		String classId = caseDocument.getDocClass();
		String baseStr=caseDocument.getFileBaseStr();
		String fileName=caseDocument.getFileName();
		String[] customPropertyNames=caseDocument.getCustomPropertyNames();
		String[] customPropertyValues=caseDocument.getCustomPropertyValues();
		if(classId==null || "".equals(classId) || baseStr==null){
			return null;
		}
		Document doc = Factory.Document.createInstance(os, classId);
		doc = setDocumentProps(doc,customPropertyNames,customPropertyValues);
		byte [] inputDataByte = Base64.decode(baseStr);
		InputStream fileIS = new ByteArrayInputStream(inputDataByte);
		ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
		ctObject.setCaptureSource(fileIS);
		//ctObject.set_ContentType(MimeType);
		ctObject.set_RetrievalName(fileName);
		ContentElementList contentList = Factory.ContentTransfer.createList();
		contentList.add(ctObject);
		doc.set_ContentElements(contentList);
		//doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		doc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		doc.save(RefreshMode.REFRESH);
		fileIS.close();
		logger.info("Exit getDocument.......");
		return doc;
	}
	public Document setDocumentProps(Document doc,String[] customPropertyNames,String[] customPropertyValues){
		int index=0;
		Properties props = doc.getProperties();
		while (index < customPropertyNames.length) {
			if (customPropertyNames[index].isEmpty()) {
			} else {
				props.putValue(customPropertyNames[index], customPropertyValues[index]);
			}
			index++;
		}
		return doc;
	}
}
