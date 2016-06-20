package com.disney.api.soapServices.commonDoorService.operations;

import com.disney.api.soapServices.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class Search extends CommonDoorService{

	public Search(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("search")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
