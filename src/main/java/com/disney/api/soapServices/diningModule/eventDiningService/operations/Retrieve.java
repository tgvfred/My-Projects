package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;

public class Retrieve extends EventDiningService {
	public Retrieve(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveEventDiningRequest/reservationNumber", value);
	}
	
	public String getReservationNumber(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveEventDiningResponse'][1]/*[local-name(.)='EventDiningReservation'][1]/*[local-name(.)='reservationNumber'][1]");    	
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveEventDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveEventDiningRequest/communicationChannel", value);
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/partyId");
	}
	
	public String getInternalCommentsText(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/internalComments/commentText");
	}
	
	public String getInternalCommentsType(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/internalComments/commentType");
	}
	
	public String getInternalCommentText(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/internalComments["+index+"]/commentText");
	}
	
	public String getInternalCommentType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/internalComments["+index+"]/commentType");
	}
	
	public String getProfileDetailType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/profileDetails["+index+"]/type");
	}
	
	public String getProfileDetailId(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/profileDetails["+index+"]/id");
	}
	
	public String getAllergies(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/allergies["+index+"]");
	}
	
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/status");}
	/**
	 * Gets the total number of guests from the SOAP response
	 * @return total number of guests 
	 */
	public int getNumberOfGuests(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/partyRoles/guest").getLength();}
	/**
	 * Gets the facility ID from the SOAP response
	 * @return facility ID 
	 */
	public String getResponseFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/facilityId");}	
	/**
	 * Gets the primary guest age from the SOAP response
	 * @return primary guest age 
	 */
	public String getPrimaryGuestAge(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/eventDiningPackage/partyRoles[1]/age");}
}