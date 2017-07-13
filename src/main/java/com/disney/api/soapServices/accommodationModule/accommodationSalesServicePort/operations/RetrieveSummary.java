package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;


public class RetrieveSummary extends AccommodationSalesServicePort{
	
	public RetrieveSummary(String environment) {
        super(environment);
        
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSummary")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

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
	
	// Setters
	public void setRequestTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentGroupingIds", tcgId);
	}
	
	public void setRequestTravelComponentId(String value) {
		try{setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentIds", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentIds"));
			setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentIds", value);
		}
	}
	
	public void setRequestRetrieveTaxExempt(String value) {
		try{setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/retrieveTaxExempt", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request", BaseSoapCommands.ADD_NODE.commandAppend("retrieveTaxExempt"));
			setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/retrieveTaxExempt", value);
		}
	}
	
	// Getters
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/status");}
	
	public String getRSR(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/RSR");}
	
	public String getShared(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/shared");}
	
	//Here in case you need it
//	public void setguestEnteredInRoomIndicator(String value) {
//		try{setRequestNodeValueByXPath("/Envelope/Body/search/request/guestEnteredInRoomIndicator", value);}
//		catch(XPathNotFoundException e){
//			setRequestNodeValueByXPath("/Envelope/Body/search/request", BaseSoapCommands.ADD_NODE.commandAppend("guestEnteredInRoomIndicator"));
//			setRequestNodeValueByXPath("/Envelope/Body/search/request/guestEnteredInRoomIndicator", value);
//		}
//	}
}
