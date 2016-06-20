package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class BookForShare extends AccommodationSalesServicePort{
	
	public BookForShare(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookForShare")));
        generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
		removeWhiteSpace(); 
	}
	
	public String getlocationId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookForShareResponse/reservationResponse/roomDetails/locationId");
	}
   public String getshared(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookForShareResponse/reservationResponse/roomDetails/shared");
   }
}