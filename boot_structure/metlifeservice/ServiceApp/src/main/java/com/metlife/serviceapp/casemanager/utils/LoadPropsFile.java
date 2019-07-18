package com.metlife.serviceapp.casemanager.utils;


import com.metlife.serviceapp.casemanager.exception.RestException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropsFile {
	public static Properties loadFile(String filePath){
		InputStream  input =null;
		Properties prop = null;
		try{
			System.out.println("loading props files ....................");
			input = new FileInputStream(filePath);
			prop.load(input);
		}catch(IOException e){
			throw new RestException(e,"Error while Loading property file in CE Connection:");
		}finally{
			try {
				if(null!=input)input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prop;
		}
	}
}
