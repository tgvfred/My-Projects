package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class SearchByGuest extends ScheduledEventsServicePort{
	public SearchByGuest(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByGuest")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setCancellationNumber(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/cancellationNumber", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:cancellationNumber");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/cancellationNumber", value);
		}
	}
	public void setEmail(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/email", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:email");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/email", value);
		}
	}	
	public void setFirstName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/firstName", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/firstName", value);
		}
	}
	public void setLastName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/lastName", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/lastName", value);
		}
	}
	public void setPhoneNumber(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/phoneNumber", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:phoneNumber");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/phoneNumber", value);
		}
	}
	public void setPostalCode(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/postalCode", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/postalCode", value);
		}
	}
	public void setReservationNumber(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/reservationNumber", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:reservationNumber");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/reservationNumber", value);
		}
	}
	public void setReservationStatus(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/reservationStatus", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:reservationStatus");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/reservationStatus", value);
		}
	}
	public void setGuestOdsIds(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/guestOdsIds", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:guestOdsIds");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/guestOdsIds", value);
		}
	}
	public void setServiceDate(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceDate", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:serviceDate");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceDate", value);
		}
	}
	public void setServiceWindowEnd(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceWindowEnd", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:serviceWindowEnd");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceWindowEnd", value);
		}
	}
	public void setServiceWindowStart(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceWindowStart", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:serviceWindowStart");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/serviceWindowStart", value);
		}
	}
	public void setSourceAccountingCenter(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/sourceAccountingCenter", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest", "fx:addnode;node:sourceAccountingCenter");
			setRequestNodeValueByXPath("/Envelope/Body/searchByGuest/searchByGuestRequest/sourceAccountingCenter", value);
		}
	}
	
	public String getCancellationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/cancellationNumber");}
	public String getFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/facilityId");}
	public String getPrimaryGuestFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/primaryGuestFirstName");}
	public String getPrimaryGuestLastName(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/primaryGuestLastName");}
	public String getProductTypeName(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/productTypeName");}
	public String getProductId(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/productId");}
	public String getEnterpriseProductId(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/enterpriseProductId");}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/reservationNumber");}
	public String getReservationStatus(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/reservationStatus");}
	public String getServiceDate(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/serviceDate");}
	public String getTicketIssued(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/ticketIssued");}
	public String getVipLevel(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/vipLevel");}
	public String getServicePeriodId(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/servicePeriodId");}
	public String getBookDate(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/bookDate");}
	public String getSpecialDietaryNeeds(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/specialDietaryNeeds");}
	public String getExtraCardRequired(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/extraCareRequired");}
	public String getPaidInFull(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/paidInFull");}
	public String getPartySize(){return getResponseNodeValueByXPath("/Envelope/Body/searchByGuestResponse/eventReservations/partySize");}
}