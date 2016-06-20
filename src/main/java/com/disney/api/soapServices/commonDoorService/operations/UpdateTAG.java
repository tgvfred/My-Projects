package com.disney.api.soapServices.commonDoorService.operations;

import com.disney.api.soapServices.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class UpdateTAG extends CommonDoorService{

	public UpdateTAG(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateTAG")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
