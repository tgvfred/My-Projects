package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class RetrieveResortAccess extends CommonDoorService{

	public RetrieveResortAccess(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveResortAccess")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void reportAllResortAccess(RetrieveResortAccess retrieveResortAccess) {
		// TODO Report all access nodes in the response
	}
}
