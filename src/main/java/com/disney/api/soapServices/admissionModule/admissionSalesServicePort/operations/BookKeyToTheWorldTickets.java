package com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations;

import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.AdmissionSalesServicePort;
import com.disney.utils.XMLTools;

public class BookKeyToTheWorldTickets extends AdmissionSalesServicePort{
	public BookKeyToTheWorldTickets(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookKeyToTheWorldTickets")));
	
		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setGuestId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookKeyToTheWorldTickets/request/tickets/guestReference/guest/guestId", value);
	}
	
	public void setTravelSegmentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookKeyToTheWorldTickets/request/travelPlanSegmentId", value);
	}
	
	public void setTravelComponentGroupingId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookKeyToTheWorldTickets/request/travelComponentGroupingId", value);
	}
	
	public void setLocationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookKeyToTheWorldTickets/request/locationId", value);
	}
}
