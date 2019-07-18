package com.metlife.serviceapp.casemanager.utils;

import java.util.ArrayList;
import java.util.List;

public class CaseUtils {
	public static List<String> parseMultiValuesForTypeString(String paramString) {
		String key = "ZM$()#ZMQR";
		paramString = paramString.replace("\\,", "ZM$()#ZMQR");

		List<String> list = null;
		if ((paramString != null) && (!paramString.equalsIgnoreCase(""))) {
			String[] tokens = paramString.split(",");
			list = new ArrayList();
			for (int i = 0; i < tokens.length; i++) {
				tokens[i] = tokens[i].replace("ZM$()#ZMQR", ",");
				list.add(tokens[i]);
			}
		}
		return list;
	}
	public static  List<String> parseMultiValues(String paramString)
	  {
	    List<String> list = null;
	    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
	    {
	      String[] tokens = paramString.split(",");
	      list = new ArrayList();
	      for (int i = 0; i < tokens.length; i++) {
	        list.add(tokens[i]);
	      }
	    }
	    return list;
	  }
}
