package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveTeamsByGroupCode extends AccommodationSalesServicePort{
	public RetrieveTeamsByGroupCode(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTeamsByGroupCode")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();

	}

	public void setgroupcode(String Value) {
		setRequestNodeValueByXPath("/Envelope/Body/retrieveTeamsByGroupCode/groupCode",Value);
		
	}
	
	
}
