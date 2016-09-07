package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class UpdateAuthorizedAccessGroup extends CommonDoorService{

	public UpdateAuthorizedAccessGroup(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateAuthorizedAccessGroup")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
