package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class RetrieveServiceVersion extends CommonDoorService{

	public RetrieveServiceVersion(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveServiceVersion")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	
	public String getServiceVersion(){
/*    	String[][] namespaces = {
    			{"soapenv", "http://schemas.xmlsoap.org/soap/envelope/"},
    			{"val", "http://valueobjects.commondoorv1.bussvcs.wdpr.com/"},
    			{"oas1", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"},
    			{"ser", "http://servicecontextv1.service.wdpr.com"},
    			{"oas", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"},
    			{"ser1", "http://service.commondoorv1.bussvcs.wdpr.com/"},
    	};*/
    	//<ns2:version major="1" minor="0" revision="0"/>
//		return getResponseNodeValueByXPath("//ns2:version@major", namespaces);
    	return getResponseNodeValueByXPath("//ns2:version@major");
	}
}
