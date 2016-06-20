package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class ReplaceGuestId extends AccommodationSalesServicePort{
	
	public ReplaceGuestId(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("replaceGuestId")));
        generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
		removeWhiteSpace();
		}
	public void setguestId(String guestId){
		setRequestNodeValueByXPath("/Envelope/Body/replaceGuestId/transactionGuestId",guestId );
	}
}