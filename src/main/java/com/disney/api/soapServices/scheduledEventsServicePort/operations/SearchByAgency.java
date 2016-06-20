package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class SearchByAgency extends ScheduledEventsServicePort {
	public SearchByAgency(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByAgency")));
//		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setAgencyIataNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/agencyIataNumber", value);
	}
	

	public void setGuestLastName(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/guestLastName", value);
		}catch(XPathNotFoundException xpnfe){
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest", "fx:AddNode;Node:guestLastName");
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/guestLastName", value);
		}
	}
	

	public void setReservationStatus(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/reservationStatus", value);
		}catch(XPathNotFoundException xpnfe){
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest", "fx:AddNode;Node:reservationStatus");
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/reservationStatus", value);
		}
	}
	

	public void setSourceAccountingCenter(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/sourceAccountingCenter", value);
		}catch(XPathNotFoundException xpnfe){
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest", "fx:AddNode;Node:sourceAccountingCenter");
			setRequestNodeValueByXPath("/Envelope/Body/searchByAgency/searchByAgencyRequest/sourceAccountingCenter", value);
		}
	}
	
	public int getNumberOfReservation(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchByAgencyResponse/eventReservations").getLength();		    	
	}
	
	
}