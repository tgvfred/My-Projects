package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class BookWithPrice extends AccommodationSalesServicePort{
	public BookWithPrice(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookWithPrice")));
		generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public String getGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPriceResponse/response/roomDetails[2]/roomReservationDetail/guestReferenceDetails/guest/guestId");
	}
	public String getReservationType(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPriceResponse/response/roomDetails[2]/reservationType");
	}
}
