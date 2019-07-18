package com.metlife.serviceapp.casemanager.exception;

import org.apache.log4j.Logger;

public class RestException extends RuntimeException {
		

	private static final long serialVersionUID = 6587053262428490597L;
	private static Logger logger;
	static{
        logger = Logger.getLogger(RestException.class);
    }	
	
	public RestException(Exception e)
	{
		logger.error(e);	
	}
	
	
	public RestException(Exception e,String message)
	{
		logger.error(message+e);	
	}
}
