package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class RetrieveServiceOptions extends CommonDoorService{

	public RetrieveServiceOptions(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveServiceOptions")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
