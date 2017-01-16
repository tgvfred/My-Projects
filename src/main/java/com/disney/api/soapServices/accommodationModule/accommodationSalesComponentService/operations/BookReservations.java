package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations.SearchOrganizationByMembershipId;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
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
	public void setBookingDate(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/bookingDate", value);
	}
	
	public void setFreezeId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/freezeId", value);
	}
	
	public void setFreezeId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/freezeId", value);
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
	
	public void setGuarenteeStatus(String value, String index){
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
		
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/guaranteeStatus", value);
	}
	
	public void setPackageCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/packageCode", value);
	}
	
	public void setPackageCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/packageCode", value);
	}
	
	public void setResortCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortCode", value);
	}
	
	public void setResortCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/resortCode", value);
	}
	
	public void setResortAreaEndDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/endDate", value);
	}
	
	public void setResortAreaEndDate(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/resortPeriod/endDate", value);
	}
	
	public void setResortAreaStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/startDate", value);
	}
	
	public void setResortAreaStartDate(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/resortPeriod/startDate", value);
	}
	
	public void setRoomTypeCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomTypeCode", value);
	}
	
	public void setRoomTypeCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomTypeCode", value);
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
	
	public void setGuestReservationDetailsAge(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/age", value);
	}
	
	public void setGuestReservationDetailsAgeType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/ageType", value);
	}
	
	public void setGuestReservationDetailsGuestFirstName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/firstName", value);
	}
	
	public void setGuestReservationDetailsGuestLastName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/lastName", value);
	}
	
	public void setGuestReservationDetailsGuestPartyId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
	}
	
	public void setGuestReservationDetailsGuestLocatorId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/locatorId", value);
	}
	
	public void setGuestReservationDetailsGuestGuestLocatorId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/guestLocatorId", value);
	}
	
	public void setGuestReservationDetailsGuestLocatorUseType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/locatorUseType", value);
	}
	
	public void setGuestReservationDetailsGuestPrimary(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/primary", value);
	}
	
	public void setGuestReservationDetailsGuestAddress1(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", value);
	}
	
	public void setGuestReservationDetailsGuestCity(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", value);
	}
	
	public void setGuestReservationDetailsGuestCountry(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", value);
	}
	
	public void setGuestReservationDetailsGuestPostalCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", value);
	}
	
	public void setGuestReservationDetailsGuestState(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", value);
	}
	
	public void setGuestReservationDetailsGuestDoNotMailIndicator(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", value);
	}
	
	public void setGuestReservationDetailsGuestDoNotPhoneIndicator(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", value);
	}
	
	public void setGuestReservationDetailsGuestPreferredLanguage(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage", value);
	}
	
	public void setGuestReservationDetailsGuestDclGuestId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", value);
	}
	
	public void setGuestReservationDetailsGuestGuestId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
	}
	
	public void setGuestReservationDetailsGuestActive(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/guest/active", value);
	}
	
	public void setGuestReservationDetailsPurposeOfVisit(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/purposeOfVisit", value);
	}
	
	public void setGuestReservationDetailsRole(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/role", value);
	}
	
	public void setGuestReservationDetailsCorrelationID(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/roomReservationDetail/guestReferenceDetails/correlationID", value);
	}
	
	public void setRsrReservation(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/rsrReservation", value);
	}
	
	public void setTravelComponentGroupingId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/travelComponentGroupingId", value);
	}
	
	public void setTravelComponentId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/travelComponentId", value);
	}
	
	public void setTravelStatus(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/travelStatus", value);
	}
	
	public void setShared(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/shared", value);
	}
	
	public void setSpecialNeedsRequested(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/specialNeedsRequested", value);
	}

	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentGroupingId");
	}
	public String getTravelComponentGroupingId(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails["+index+"]/travelComponentGroupingId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentId");
	}
	
	public String getTravelComponentId(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails["+index+"]/travelComponentId");
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
	
	public void setOverrideFreeze(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails["+index+"]/overideFreeze", value);
	}
	
	
	
	
	
	
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
		int maxTries = 10;
		boolean success = false;
        SearchOrganizationByMembershipId search = new SearchOrganizationByMembershipId(getEnvironment(), "Main");
        
        for(int tries = 0; tries < maxTries; tries++){
            try{search.setOrganizationMembershipName(BaseSoapCommands.REMOVE_NODE.toString());}
            catch(XPathNotFoundException e){}
            search.setOrganizationMembershipValue(agencyId);
            search.sendRequest();
            if(search.getResponseStatusCode().equals("200")){
            	success = true;
            	break;
            }else{
            	Sleeper.sleep(Randomness.randomNumberBetween(3, 7)*1000);
            }
        }
        if(!success){
        	TestReporter.log("An error occurred searching for an organization by ID ["+agencyId+"].");
        	TestReporter.log("\nREQUEST: " + search.getRequest());
        	TestReporter.log("\nRESPONSE: " + search.getRequest());
        }
        
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
//        System.out.println(getRequest());
//        System.out.println();
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/confirmationType
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/addressLine2
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/primaryAddress/regionName
//    	/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgency/status
        
        return agencyDetails;
    }
}
