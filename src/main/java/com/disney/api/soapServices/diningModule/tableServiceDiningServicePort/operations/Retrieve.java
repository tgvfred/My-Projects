package com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.XMLTools;

public class Retrieve extends TableServiceDiningServicePort{
	public Retrieve(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
//		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveTableServiceRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveTableServiceRequest/salesChannel", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveTableServiceRequest/communicationChannel", value);}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveTableServiceResponse'][1]/*[local-name(.)='tableServiceReservation'][1]/*[local-name(.)='reservationNumber'][1]");}
	public String getPartyId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/primaryGuest/partyId");}
	public String getGuestId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/primaryGuest/guestId");}
	public String getInternalCommentsText(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/internalComments/commentText");}
	public String getInternalCommentsType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/internalComments/commentType");}
	public String getInternalCommentText(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/internalComments["+index+"]/commentText");}
	public String getInternalCommentType(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/internalComments["+index+"]/commentType");}
	public String getProfileDetailType(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/profileDetails["+index+"]/type");}
	public String getProfileDetailId(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/profileDetails["+index+"]/id");}
	public String getAllergies(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/allergies["+index+"]");}
	/**
	 * Gets the reservation status from the SOAP response
	 * @return reservation status
	 */
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/status");}
	/**
	 * Gets the number of guests in the SOAP response
	 * @return number of guests 
	 */
	public int getNumberOfGuests(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/partyRoles/guest").getLength();}
	/**
	 * Gets the facility ID in the SOAP response
	 * @return facility ID
	 */
	public String getResponseFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/facilityId");}	
	/**
	 * Gets primary guest age from the SOAP response
	 * @return - primary guest age 
	 */
	public String getPrimaryGuestAge(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTableServiceResponse/tableServiceReservation/tableService/partyRoles[1]/age");}
}