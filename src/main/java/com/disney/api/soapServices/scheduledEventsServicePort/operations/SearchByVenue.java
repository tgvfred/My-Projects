package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class SearchByVenue extends ScheduledEventsServicePort{
	public SearchByVenue(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByVenue")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setFacilityId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/facilityId", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:facilityId");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/facilityId", value);
		}
	}
	public void setProductIds(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/productIds", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:productIds");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/productIds", value);
		}
	}
	public void setReservationStatus(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/reservationStatus", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:reservationStatus");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/reservationStatus", value);
		}
	}
	public void setServiceDate(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceDate", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:serviceDate");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceDate", value);
		}
	}
	public void setServiceWindowEnd(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceWindowEnd", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:serviceWindowEnd");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceWindowEnd", value);
		}
	}
	public void setServiceWindowStart(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceWindowStart", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:serviceWindowStart");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/serviceWindowStart", value);
		}
	}
	public void setSourceAccountingCenter(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/sourceAccountingCenter", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest", "fx:addnode;node:sourceAccountingCenter");
			setRequestNodeValueByXPath("/Envelope/Body/searchByVenue/searchByVenueRequest/sourceAccountingCenter", value);
		}
	}
	
	public String getCancellationNumberByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/cancellationNumber");}
	public String getFacilityIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/facilityId");}
	public String getPrimaryGuestFirstNameByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/primaryGuestFirstName");}
	public String getPrimaryGuestLastNameByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/primaryGuestLastName");}
	public String getProductIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/productId");}
	public String getEnterpriseProductIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/enterpriseProductId");}
	public String getReservationNumberByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/reservationNumber");}
	public String getReservationStatusByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/reservationStatus");}
	public String getTicketIssuedByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/ticketIssued");}
	public String getVipLevelByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/vipLevel");}
	public String getServicePeriodIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/servicePeriodId");}
	public String getSpecialDietaryNeedsByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/specialDietaryNeeds");}
	public String getExtraCareRequiredByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/extraCareRequired");}
	public String getPaidInFullByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/paidInFull");}
	public String getPartySizeByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByVenueResponse/eventReservations["+index+"]/partySize");}
	public NodeList getEventReservations(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchByVenueResponse/eventReservations");}
}