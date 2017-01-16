package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class SearchResortReservationsByGuest extends AccommodationSalesServicePort{
	
	public SearchResortReservationsByGuest(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchResortReservationsByGuest")));
		
		generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
		
	public void setreservationNum(String reservationNumber){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='searchResortReservationsByGuest'][1]/*[local-name(.)='searchResortReservationsRequest'][1]/*[local-name(.)='reservationNumber'][1]", reservationNumber);
	}
	
}
