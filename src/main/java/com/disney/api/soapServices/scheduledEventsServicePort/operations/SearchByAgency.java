package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class SearchByAgency extends ScheduledEventsServicePort {
	private Integer numberOfReservations = null;
	
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
		numberOfReservations = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchByAgencyResponse/eventReservations").getLength(); 
		return numberOfReservations;
	}
	
	
	public String getIataNumber(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/agencyIataNumber");}
	public String getAgencyName(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/agencyName");}
	public String getCancellationNumber(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/cancellationNumber");}
	public String getFacilityId(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/facilityId");}
	public String getPrimaryGuestFirstName(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/primaryGuestFirstName");}
	public String getPrimaryGuestLastName(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/primaryGuestLastName");}
	public String getProductTypeName(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/productTypeName");}
	public String getProductId(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/productId");}
	public String getEnterpriseProductId(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/enterpriseProductId");}
	public String getReservationNumber(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/reservationNumber");}
	public String getReservationStatus(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/reservationStatus");}
	public String getServiceDate(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/serviceDate");}
	public String getTicketIssued(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/ticketIssued");}
	public String getVipLevel(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/vipLevel");}
	public String getServicePeriodId(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/servicePeriodId");}
	public String getBookDate(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/bookDate");}
	public String getSpecialDietaryNeeds(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/specialDietaryNeeds");}
	public String getExtraCareRequired(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/extraCareRequired");}
	public String getPaidInFull(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/paidInFull");}
	public String getPartySize(String index){return getResponseNodeValueByXPath("/Envelope/Body/searchByAgencyResponse/eventReservations["+index+"]/partySize");}
	
	
	/**
	 * Captures and returns all reservations in the response
	 * @return
	 */
	public List<Reservation> getAllReservations(){
		List<Reservation> reservations = new ArrayList<Reservation>();
		
		if(numberOfReservations == null) getNumberOfReservation();
		
		for(int i = 1; i <= numberOfReservations; i++){
			Reservation res = new Reservation();
			res.setAgencyIataNumber(getIataNumber(String.valueOf(i)));
			res.setAgencyName(getAgencyName(String.valueOf(i)));
			res.setBookDate(getBookDate(String.valueOf(i)));
			res.setCancellationNumber(getCancellationNumber(String.valueOf(i)));
			res.setEnterpriseProductId(getEnterpriseProductId(String.valueOf(i)));
			res.setExtraCareRequired(getExtraCareRequired(String.valueOf(i)));
			res.setFacilityId(getFacilityId(String.valueOf(i)));
			res.setPaidInFull(getPaidInFull(String.valueOf(i)));
			res.setPartySize(getPartySize(String.valueOf(i)));
			res.setPrimaryGuestFirstName(getPrimaryGuestFirstName(String.valueOf(i)));
			res.setPrimaryGuestLastName(getPrimaryGuestLastName(String.valueOf(i)));
			res.setProductId(getProductId(String.valueOf(i)));
			res.setProductTypeName(getProductTypeName(String.valueOf(i)));
			res.setReservationNumber(getReservationNumber(String.valueOf(i)));
			res.setReservationStatus(getReservationStatus(String.valueOf(i)));
			res.setServiceDate(getServiceDate(String.valueOf(i)));
			res.setServicePeriodId(getServicePeriodId(String.valueOf(i)));
			res.setSpecialDietaryNeeds(getSpecialDietaryNeeds(String.valueOf(i)));
			res.setTicketIssued(getTicketIssued(String.valueOf(i)));
			res.setVipLevel(getVipLevel(String.valueOf(i)));
			reservations.add(res);
		}
		
		return reservations;
	}

	
	/**
	 * Subclass that contains all data points for a reservation from the response
	 * @author AutoXP
	 *
	 */
	public class Reservation{
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

		
		public void setReservationStatus(String value){reservationStatus = value;}
		public String getReservationStatus(){return reservationStatus;}

		
		public void setServiceDate(String value){serviceDate = value;}
		public String getServiceDate(){return serviceDate;}

		
		public void setTicketIssued(String value){ticketIssued = value;}
		public String getTicketIssued(){return ticketIssued;}

		
		public void setVipLevel(String value){vipLevel = value;}
		public String getVipLevel(){return vipLevel;}

		
		public void setServicePeriodId(String value){servicePeriodId = value;}
		public String getServicePeriodId(){return servicePeriodId;}

		
		public void setBookDate(String value){bookDate = value;}
		public String getBookDate(){return bookDate;}

		
		public void setSpecialDietaryNeeds(String value){specialDietaryNeeds = value;}
		public String getSpecialDietaryNeeds(){return specialDietaryNeeds;}

		
		public void setExtraCareRequired(String value){extraCareRequired = value;}
		public String getExtraCareRequired(){return extraCareRequired;}

		
		public void setPaidInFull(String value){paidInFull = value;}
		public String getPaidInFull(){return paidInFull;}

		
		public void setPartySize(String value){partySize = value;}
		public String getPartySize(){return partySize;}		
	}
}