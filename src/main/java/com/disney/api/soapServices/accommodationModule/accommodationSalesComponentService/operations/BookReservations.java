package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations.SearchOrganizationByMembershipId;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class BookReservations extends AccommodationSalesComponentService{
	public BookReservations(String environment, String scenario) {
		super(environment);
		
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookReservations")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setAreaPeriodEndDate(String value){ 
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/areaPeriod/endDate", value);
	}
	
	public void setAreaPeriodStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/areaPeriod/startDate", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/communicationChannel", value);
	}
	
	public void setExternalReferenceNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/externalReference/externalReferenceNumber", value);
	}
	
	public void setExternalReferenceSource(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/externalReference/externalReferenceSource", value);
	}
	
	public void setBlockCode(String value){
		switch (value) {
		case "1905":
			value = "01905";
			break;
		case "1825":
			value = "01825";
			break;
		case "4250":
			value = "04250";
			break;
		case "4268":
			value = "04268";
			break;
		default:
			break;
		}
		
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/blockCode", value);
	}
	
	public void setBookingDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/bookingDate", value);
	}
	
	public void setFreezeId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/freezeId", value);
	}
	
	public void setGuarenteeStatus(String value){
		switch (value) {
		case "1905":
			value = "01905";
			break;
		case "1825":
			value = "01825";
			break;
		case "4250":
			value = "04250";
			break;
		case "4268":
			value = "04268";
			break;
		default:
			break;
		}
		
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/guaranteeStatus", value);
	}
	
	public void setPackageCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/packageCode", value);
	}
	
	public void setResortCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortCode", value);
	}
	
	public void setResortAreaEndDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/endDate", value);
	}
	
	public void setResortAreaStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/startDate", value);
	}
	
	public void setRoomTypeCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomTypeCode", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/salesChannel", value);
	}
	
	public void setTravelPlanGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", value);
	}
	
	public void setTravelPlanGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", value);
	}
	
	public void setTravelPlanGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", value);
	}
	
	public void setTravelPlanGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", value);
	}
	
	public void setTravelPlanGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", value);
	}
	
	public void setTravelPlanGuestZip(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", value);
	}
	
	public void setTravelPlanGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", value);
	}
	
	public void setGuestReservationDetailsGuestFirstName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/firstName", value);
	}
	
	public void setGuestReservationDetailsGuestLastName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/lastName", value);
	}
	
	public void setGuestReservationDetailsGuestAddress1(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/addressLine1", value);
	}
	
	public void setGuestReservationDetailsGuestCity(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/city", value);
	}
	
	public void setGuestReservationDetailsGuestCountry(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/country", value);
	}
	
	public void setGuestReservationDetailsGuestPostalCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/postalCode", value);
	}
	
	public void setGuestReservationDetailsGuestState(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/state", value);
	}
	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentGroupingId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentId");
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/travelPlanId");
	}
	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/travelPlanSegmentId");
	}
	
	public void setProfileCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/code", value);
	}
	public void setProfileCode(String value, int index){setProfileCode(value, String.valueOf(index));}
	
	public void setProfileDescription(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/description", value);
	}
	public void setProfileDescription(String value, int index){setProfileDescription(value, String.valueOf(index));}
	
	public void setProfileType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/profileType", value);
	}
	public void setProfileType(String value, int index){setProfileType(value, String.valueOf(index));}
	
	public void setProfileRountingsName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/routings/name", value);
	}
	public void setProfileRountingsName(String value, int index){setProfileRountingsName(value, String.valueOf(index));}
	
	
	
	
	
	
	public String getProfileCode(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/code");
	}
	public String getProfileCode(int index){return getProfileCode(String.valueOf(index));}
	
	public String getProfileDescription(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/description");
	}
	public String getProfileDescription(int index){return getProfileDescription(String.valueOf(index));}
	
	public String getProfileType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/profileType");
	}
	public String getProfileType(int index){return getProfileType(String.valueOf(index));}
	
	public String getProfileRountingsName(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/routings/name");
	}
	public String getProfileRountingsName(int index){return getProfileRountingsName(String.valueOf(index));}
	
	public void setTicketGroup(String value){setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketGroup", value);}
	public void setTicketDetailsBaseAdmissionProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/baseAdmissionProductId", value);}
	public void setTicketDetailsCode(String value){setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/code", value);}
	public void setTicketDetailsTicketDescription(String value){setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/ticketDescription", value);} 
	public void setLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/locationId", value);}
	
	
	public Map<String, String> setAgency(String agencyId){
        SearchOrganizationByMembershipId search = new SearchOrganizationByMembershipId(getEnvironment(), "Main");
        search.setOrganizationMembershipName(BaseSoapCommands.REMOVE_NODE.toString());
        search.setOrganizationMembershipValue(agencyId);
        search.sendRequest();
        
        Map<String, String> agencyDetails = new HashMap<String, String>();
        agencyDetails.put("iataNumber", agencyId);
        agencyDetails.put("agencyName", search.getName());
        agencyDetails.put("agencyOdsId", search.getId());
        agencyDetails.put("guestTravelAgencyId", "0");
        agencyDetails.put("agentId", search.getFirstAgentId());
        agencyDetails.put("guestAgentId", "0");
        agencyDetails.put("confirmationLocatorValue", "0");
        agencyDetails.put("guestConfirmationLocationId", "0");
        agencyDetails.put("locatorId", search.getAddressLocatorId());
        agencyDetails.put("guestLocatorId", "0");
        agencyDetails.put("locatorUseType", "UNKNOWN");
        agencyDetails.put("primary", "true");
        agencyDetails.put("addressLine1", search.getAddress1());
        agencyDetails.put("city", search.getCity());
        agencyDetails.put("country", search.getCountry());
        agencyDetails.put("postalCode", search.getPostalCode());
        agencyDetails.put("state", search.getState());
        
        try{
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/agencyIataNumber", agencyId);
        }catch(XPathNotFoundException e){
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest", BaseSoapCommands.ADD_NODE.commandAppend("travelAgency"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("agencyIataNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("agencyName"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("agencyOdsId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("guestTravelAgencyId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("agentId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("guestAgentId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("confirmationLocatorValue"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("guestConfirmationLocationId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency",  BaseSoapCommands.ADD_NODE.commandAppend("primaryAddress"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("locatorUseType"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("primary"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("city"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("country"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress",  BaseSoapCommands.ADD_NODE.commandAppend("state"));
            
            setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/agencyIataNumber", agencyId);
        }
        
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/agencyName", search.getName());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/agencyOdsId", search.getId());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/guestTravelAgencyId", "0");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/agentId", search.getFirstAgentId());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/guestAgentId", "0");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/confirmationLocatorValue", "0");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/guestConfirmationLocationId", "0");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/locatorId", search.getAddressLocatorId());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/guestLocatorId", "0");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/locatorUseType", "UNKNOWN");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/primary", "true");
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/addressLine1", search.getAddress1());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/city", search.getCity());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/country", search.getCountry());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/postalCode", search.getPostalCode());
        setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/state", search.getState());
        System.out.println(getRequest());
        System.out.println();
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/confirmationType
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/addressLine2
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/regionName
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/status
        
        return agencyDetails;
    }
}