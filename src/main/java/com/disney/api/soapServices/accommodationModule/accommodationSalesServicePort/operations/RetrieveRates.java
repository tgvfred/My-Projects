package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
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
	    /*public String getRate(){
	    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/rate");
	    }*/
	    
	   // int index2 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate") + 1;
	    
	    public void setRequestTravelComponentGroupingIdIndex(String index3, String tcgId){
			setRequestNodeValueByXPath("/Envelope/Body/retrieveRates/travelComponentGroupingId["+index3+"]", tcgId);
	    }
	  
	 //Getters
	    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail") + 1;
		   public String getAccommodationRateDetails(String index) {return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail["+index+"]");}
		   public Integer getAccommodationRateDetails(){return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail");}
	    
	   int index1 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails") + 1;
	   public String getRateDetails(String index) {return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails["+index+"]");}
	   public Integer getRateDetails(){return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails");}
	   public String getRate(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/rate");
		    }
	    
}