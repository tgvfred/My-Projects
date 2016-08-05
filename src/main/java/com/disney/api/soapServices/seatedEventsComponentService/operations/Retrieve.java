package com.disney.api.soapServices.seatedEventsComponentService.operations;

import com.disney.api.soapServices.seatedEventsComponentService.SeatedEventsComponentService;
import com.disney.utils.XMLTools;

public class Retrieve extends SeatedEventsComponentService{
	public Retrieve(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/reservationNumber", value);}
	public String getPrimaryGuestPartyId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/seatedEventRetrieveResponseTO/reservationInfo/primaryGuest/partyId");}
	public String getPrimaryGuestGuestId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/seatedEventRetrieveResponseTO/reservationInfo/primaryGuest/guestId");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/seatedEventRetrieveResponseTO/reservationInfo/travelPlanId");}
	public String getReservationStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/seatedEventRetrieveResponseTO/reservationInfo/status");}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/seatedEventRetrieveResponseTO/reservationInfo/reservationNumber");}
	
}