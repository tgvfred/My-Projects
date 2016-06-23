package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Retrieve extends ActivityService {
	public Retrieve(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/reservationNumber", value);}	
	/**
	 * Gets the reservation number in the SOAP response
	 * @return reservation number 
	 */
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/reservationNumber");}	
	/**
	 * Sets the sales channel in the SOAP request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/salesChannel", value);}	
	/**
	 * Sets the communication channel in the SOAP request
	 * @param value - communication channel
	 */
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/communicationChannel", value);}	
	/**
	 * Gets the party ID in the SOAP response
	 * @return party ID
	 */
	public String getPartyId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/primaryGuest/partyId");}
	/**
	 * Gets the reservation status from the SOAP response
	 * @return reservation status
	 */
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/status");}
	/**
	 * Gets the number of guests in the SOAP response
	 * @return number of guests 
	 */
	public int getNumberOfGuests(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveActivityComponentResponse/activityReservation/activity/partyRoles/ageType").getLength();}
	/**
	 * Gets the facility ID in the SOAP response
	 * @return facility ID
	 */
	public String getResponseFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/activity/facilityId");}	
	/**
	 * Gets primary guest age from the SOAP response
	 * @return - primary guest age 
	 */
	public String getPrimaryGuestAge(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/activity/partyRoles[1]/age");}
}