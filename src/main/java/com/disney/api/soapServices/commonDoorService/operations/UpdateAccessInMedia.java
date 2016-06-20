package com.disney.api.soapServices.commonDoorService.operations;

import com.disney.api.soapServices.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class UpdateAccessInMedia extends CommonDoorService{

	public UpdateAccessInMedia(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateAccessInMedia")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
