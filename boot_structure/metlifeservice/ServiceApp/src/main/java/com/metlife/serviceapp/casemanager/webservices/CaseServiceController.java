package com.metlife.serviceapp.casemanager.webservices;


import com.ibm.json.java.JSONObject;
import com.metlife.serviceapp.casemanager.dto.CaseInput;
import com.metlife.serviceapp.casemanager.exception.ConnectionException;
import com.metlife.serviceapp.casemanager.methods.CaseOperation;
import com.metlife.serviceapp.casemanager.utils.CEConnectionUtility;
import com.metlife.serviceapp.casemanager.utils.Constants;
import com.metlife.serviceapp.casemanager.utils.LoadLog4jConfig;
import filenet.vw.ntutil.security.base.AuthException;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/v1")
public class CaseServiceController {

	/*@Autowired
	@Qualifier("cEConnectionUtility")
	private CEConnectionUtility cEConnectionUtility;*/
	private static Logger logger;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss:SSS");
	
	static {
		try {
			System.out.println("Loading log4j------------"+ Constants.BASE_PATH+Constants.LOG_4J_FILE );
			LoadLog4jConfig.loadLog4jConfig(Constants.BASE_PATH+Constants.LOG_4J_FILE);
		} catch (Exception e) {
			System.out.println("error in Exception------------" + e.getMessage());
			e.printStackTrace();
		}
		logger = Logger.getLogger(CaseServiceController.class);
	}
	
	
	@SuppressWarnings("finally")
	/*@POST
	@Path("/CreateCase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) */
	@RequestMapping(value = "/create/case", method = RequestMethod.POST)
	public JSONObject createCase(@RequestBody CaseInput input, @RequestHeader("Authorization") String authString) {
		logger.info("Start CreateCase : --------------------createCase()------------------------------");
		logger.info("Input Request::"+input.toString());
		JSONObject resp = new JSONObject();
		CEConnectionUtility cEConnectionUtility = null;

		try{
			cEConnectionUtility = new CEConnectionUtility();
//			validateAuthString(authString);
			CaseOperation cops= new CaseOperation(cEConnectionUtility);
			resp = cops.createCaseUsingSpecifiedCaseType(input);
		}catch(AuthException ae){
			logger.error("Error:",ae);
			resp.put("code", "5001");
			resp.put("error", "Error in Authentication");
		}catch(ConnectionException ce){
			logger.error("Error:",ce);
			resp.put("code", "5002");
			resp.put("error", ce.getMessage());
		}catch(Exception e){
			logger.error("Error:",e);
			resp.put("code", "5003");
			resp.put("error", e.getMessage());
		}
		finally{
			cEConnectionUtility.popUserContext();
			logger.debug("End createCase(): resp::"+resp);
			logger.info("End createCase(): --------------------------------------------------");
			return resp;
		}
	}
	/*@GET
	@Path("/health")
	@Produces(MediaType.APPLICATION_JSON)*/
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public JSONObject health() throws IOException {
		logger.info("Start health:---------------------------------------health--------------------------------------------------------");
		JSONObject resp = new JSONObject();
		resp.put("code", "000");
		resp.put("msg", "Running....");
		logger.info("End health():-----------------------------------------------------------------------");
		return resp;
	}

	public boolean validateAuthString (String authString) throws AuthException{
		logger.info("authString"+authString);
		if(authString==null || "".equals(authString)){
			throw new AuthException("Auth header  is null");
		}
		String api_user = new String(Base64.decodeBase64(CEConnectionUtility.prop.getProperty(Constants.API_USER)));
		String api_pass = new String(Base64.decodeBase64(CEConnectionUtility.prop.getProperty(Constants.API_PASS)));
		
		String[] authParts = authString.split("\\s+");
		String authinfo = authParts[1];
		if (authinfo== null){ 
			throw new AuthException("Auth header  is null");
		}else{
			byte[] bytes = null;
			bytes = Base64.decodeBase64(authinfo);
			String decodedAuth = new String(bytes);
			String[] credentials=decodedAuth.split(":");
			//logger.info("validateAuthString():: api_user :"+api_user+" api_pass:"+api_pass);
			//logger.info("validateAuthString():: credentials[0] :"+credentials[0]+" credentials[1]:"+credentials[1]);
			if(!api_user.equals(credentials[0]) || !api_pass.equals(credentials[1]) ){
				throw new AuthException("User Name and Passord not varified..");
			}
		}
		return true;
	}
}
