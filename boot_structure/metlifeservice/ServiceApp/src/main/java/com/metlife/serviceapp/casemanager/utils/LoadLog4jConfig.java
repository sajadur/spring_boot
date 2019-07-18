package com.metlife.serviceapp.casemanager.utils;


import org.apache.log4j.PropertyConfigurator;

public class LoadLog4jConfig {
	
	public static void loadLog4jConfig(String path) throws Exception{
		if(path!=null && !path.equals("")){
			PropertyConfigurator.configure(path);
		}else{
			throw new Exception("Path is null");
		}
		
	}

}
