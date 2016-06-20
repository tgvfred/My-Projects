package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class SearchForLinking extends AccommodationSalesServicePort{
	public SearchForLinking(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchForLinking")));
		generateServiceContext();
		//	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setTravelComponentGroupingId(String tcg_id){
		setRequestNodeValueByXPath("/Envelope/Body/searchForLinking/travelComponentGroupingId", tcg_id);
	    }
}
