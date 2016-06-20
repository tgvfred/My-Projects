package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;


public class RetrieveSummary extends AccommodationSalesServicePort{

	public RetrieveSummary(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSummary")));
		generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setRequestTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("//Envelope/Body/retrieveSummary/request/travelComponentGroupingIds", tcgId);
	}
	
	
	/*public String getRequestTravelComponentGroupingId(){
		return getRequestNodeValueByXPath("//Envelope/Body/retrieveSummary/request/travelComponentGroupingIds");
	}
	
	public void setRequestTravelComponentId(String tcId){
		setRequestNodeValueByXPath("//Envelope/Body/retrieveSummary/request/travelComponentIds", tcId);
	}
	
	public String getRequestTravelComponentId(){
		return getRequestNodeValueByXPath("//Envelope/Body/retrieveSummary/request/travelComponentIds");
	}*/
}
