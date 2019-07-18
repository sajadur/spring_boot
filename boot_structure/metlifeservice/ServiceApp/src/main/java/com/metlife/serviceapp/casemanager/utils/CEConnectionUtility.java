package com.metlife.serviceapp.casemanager.utils;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.ibm.casemgmt.api.context.P8ConnectionCache;
import com.ibm.casemgmt.api.context.SimpleP8ConnectionCache;
import com.metlife.serviceapp.casemanager.exception.ConnectionException;
import com.metlife.serviceapp.casemanager.exception.RestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//@Component(value="cEConnectionUtility")

public class CEConnectionUtility {


	private static Logger logger = Logger.getLogger(CEConnectionUtility.class);
	private Connection conn;
	private Domain dom;
	private String domainName;
	private boolean isConnected;
	private String objectStore;
	private UserContext uc;
	public static Properties prop = new Properties();
	
	static {
		InputStream input = null;
		try {
			System.out.println("loading props files ....................");
			input = new FileInputStream(Constants.BASE_PATH + Constants.FN_CONFIG_FILE);
			prop.load(input);
			System.out.println("end props file");
		}
		catch(IOException e){
			System.out.println("Error while loading props files ....................");
			throw new RestException(e,"Error while Loading property file in CE Connection:");
		} catch(Exception e){e.printStackTrace();
		}

		finally{
			try {
				if(null!=input)input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public CEConnectionUtility(){
		conn = null;
		uc = UserContext.get();
		dom = null;
		domainName = null;
		isConnected = false;
	}


	public P8ConnectionCache connect() throws ConnectionException, Exception{
		logger.debug("connect():: enter");
		long startTime = System.currentTimeMillis();
		
		String uri = prop.getProperty(Constants.FN_URI);
		this.objectStore = prop.getProperty(Constants.OBJECT_STORE);
		
		logger.debug("Filenet uri:"+uri +" objectStore:"+this.objectStore);
		String jaasStanza= AntiXSS.HtmlAttributeEncode(prop.getProperty(Constants.STANZA));		
		String p8_user = new String(Base64.decodeBase64(CEConnectionUtility.prop.getProperty(Constants.P8_USER)));
		String p8_pass = new String(Base64.decodeBase64(CEConnectionUtility.prop.getProperty(Constants.P8_PASS)));
		logger.info("connect():: p8_user :"+p8_user+" p8_pass:"+p8_pass);
				
		P8ConnectionCache connCache = new SimpleP8ConnectionCache();
	    conn = connCache.getP8Connection(uri);
	    if(null == conn){
			throw new ConnectionException("Connection to CE failed. with uri"+uri);
		}
	    Subject subject = UserContext.createSubject(conn, p8_user, p8_pass, jaasStanza);
	    uc.pushSubject(subject);
		dom = fetchDomain();
		if(null ==uc || null == dom ){
			throw new ConnectionException("Failed to get user context and domain. Connection to CE failed.");
		}
		this.domainName = dom.get_Name();
		
		isConnected = true;
		logger.debug("Time taken for Connection ::"+(System.currentTimeMillis()-startTime)+" ms");
		logger.debug("connect():: EXIT");
		return connCache;
	}


	public Domain fetchDomain(){
		dom = Factory.Domain.fetchInstance(conn, null, null);
		return dom;
	}

	
	public boolean isConnected() {
		return isConnected;
	}

	
	public ObjectStore fetchOS(String objectStoreName) throws ConnectionException{
		if("".equals(objectStoreName)){
			throw new ConnectionException("Failed to get Object Store Name Since Object store name is blank. Connection to CE failed.");
		}
		ObjectStore os = Factory.ObjectStore.fetchInstance(dom, objectStoreName, null);
		return os;
	}
	public ObjectStore fetchTOS() throws ConnectionException{
		if("".equals(this.objectStore)){
			throw new ConnectionException("Failed to get Object Store Name Since Object store name is blank. Connection to CE failed.");
		}
		ObjectStore os = Factory.ObjectStore.fetchInstance(dom, this.objectStore, null);
		return os;
	}
	
	public String getDomainName(){
		return domainName;
	}
	public Domain getDomain(){
		return dom;
	}
	public void popUserContext(){
		if(null!=uc){
			logger.info("popUserContext():: successful");
			uc.popSubject();
		}else{
			logger.info("popUserContext():: UC is null ");
		}
	}
}


