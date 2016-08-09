package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveRates extends AccommodationSalesServicePort{
	
	public RetrieveRates(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveRates")));
        generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
		removeWhiteSpace(); 
	}
	public void setTravelComponentGroupingId(String tcg_id){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveRates/travelComponentGroupingId", tcg_id);
	    }
	    public String getroomTypeCode(){
	    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/roomTypeCode");
	    }
	    public String getadditionalChargeOverridden(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/additionalChargeOverridden");
	    }
}