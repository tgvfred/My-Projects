package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class SearchByTag extends CommonDoorService{

	public SearchByTag(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByTag")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
