package com.metlife.serviceapp.casemanager.dto;

import com.ibm.json.java.JSONObject;
import com.metlife.serviceapp.casemanager.utils.CEConnectionUtility;

public class Test {
	public static void main(String [] args){
		CEConnectionUtility ceConn = new CEConnectionUtility();
		JSONObject resp = new JSONObject();
		try{
			/*String []popsName ={"TEST_name","TEST_TyreType"};
			String []popsvalue ={"Kumar","xyz"};
			String []caseStr ={""};
			CaseOperation cops= new CaseOperation(ceConn);
			resp = cops.createCaseUsingSpecifiedCaseType("TEST_Test", popsName, popsvalue, caseStr);*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ceConn.popUserContext();
		}
	}
}
