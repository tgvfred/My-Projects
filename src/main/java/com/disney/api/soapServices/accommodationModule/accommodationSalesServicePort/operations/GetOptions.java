package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class GetOptions extends AccommodationSalesServicePort{
	public GetOptions(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptions")));
		generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public String getoptionKey(){
		return getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[1]/optionKey");
	}
	public String getoptionValue(){
		return getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[1]/optionValue");
	}
}
