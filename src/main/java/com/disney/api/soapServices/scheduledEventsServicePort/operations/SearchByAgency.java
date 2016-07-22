package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class SearchByAgency extends ScheduledEventsServicePort {
	public SearchByAgency(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByAgency")));
		
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
	
	
	
	
	
	
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/agencyIataNumber
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/agencyName
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/cancellationNumber
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/facilityId
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/primaryGuestFirstName
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/primaryGuestLastName
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/productTypeName
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/productId
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/enterpriseProductId
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/reservationNumber
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/reservationStatus
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/serviceDate
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/ticketIssued
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/vipLevel
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/servicePeriodId
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/bookDate
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/specialDietaryNeeds
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/extraCareRequired
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/paidInFull
//	/Envelope/Body/searchByAgencyResponse/eventReservations[1]/partySize

	public class reservation{
		private String agencyIataNumber;
		private String agencyName;
		private String cancellationNumber;
		private String facilityId;
		private String primaryGuestFirstName;
		private String primaryGuestLastName;
		private String productTypeName;;
		private String productId;
		private String enterpriseProductId;
		private String reservationNumber;
		private String reservationStatus;
		private String serviceDate;
		private String ticketIssued;
		private String vipLevel;
		private String servicePeriodId;
		private String bookDate;
		private String specialDietaryNeeds;
		private String extraCareRequired;
		private String paidInFull;
		private String partySize;
		
		public void setAgencyIataNumber(String value){agencyIataNumber = value;}
		public String getAgencyIataNumber(){return agencyIataNumber;}
		
		public void setAgencyName(String value){agencyName = value;}
		public String getAgencyName(){return agencyName;}

		
		public void setCancellationNumber(String value){cancellationNumber = value;}
		public String getCancellationNumber(){return cancellationNumber;}

		
		public void setFacilityId(String value){facilityId = value;}
		public String getFacilityId(){return facilityId;}

		
		public void setPrimaryGuestFirstName(String value){primaryGuestFirstName = value;}
		public String getPrimaryGuestFirstName(){return primaryGuestFirstName;}

		
		public void setPrimaryGuestLastName(String value){primaryGuestLastName = value;}
		public String getPrimaryGuestLastName(){return primaryGuestLastName;}

		
		public void setProductTypeName(String value){productTypeName = value;}
		public String getProductTypeName(){return productTypeName;}

		
		public void setProductId(String value){productId = value;}
		public String getProductId(){return productId;}

		
		public void setEnterpriseProductId(String value){enterpriseProductId = value;}
		public String getEnterpriseProductId(){return enterpriseProductId;}

		
		public void setReservationNumber(String value){reservationNumber = value;}
		public String getReservationNumber(){return reservationNumber;}

		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
//
//		
//		public void set(String value){ = value;}
//		public String get(){return ;}
		
		
	}
}