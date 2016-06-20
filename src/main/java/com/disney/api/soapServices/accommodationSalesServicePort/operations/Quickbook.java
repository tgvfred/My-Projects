package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;


public class Quickbook extends AccommodationSalesServicePort{

	public Quickbook(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("quickBook")));
	//	System.out.println(getRequest());
		
		generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public String getLocationId(){
		return getRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/locationId");
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("//Envelope/Body/quickBookResponse/travelPlanId");
	}
	
	public String getPrimaryGuestFirstName(){
		return getRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/primaryGuestDetail/firstName");
	}
	
	public String getPrimaryGuestLastName(){
		return getRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/primaryGuestDetail/lastName");
	}
	
	public String getPrimaryGuestPostalCode(){
		return getRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/postalCode");
	}
	
	public String getRoomTypeCode(){
		return getRequestNodeValueByXPath("/Envelope/Body/quickBook/quickBookRequest/roomTypeCode");
	}
	
	public void setNumberOfRooms(String numberOfRooms){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/numberOfRooms", numberOfRooms);
	}
	
	public void setPrimaryGuestFirstName(String firstName){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/primaryGuestDetail/firstName", firstName);
	}
	
	public void setPrimaryGuestLastName(String lastName){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/primaryGuestDetail/lastName", lastName);
	}

	public void setPrimaryGuestId(String guestId){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/guestId", guestId);
	}
	
	public void setResortCode(String resortCode){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/resortCode", resortCode);
	}
	
	public void setRoomTypeCode(String roomTypeCode){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/roomTypeCode", roomTypeCode);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/locationId", locationId);
	}
	
	public void setNumberOfChildren(String numberOfChildren){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/numberOfChildren", numberOfChildren);
	}

	public void setPackageCode(String packageCode){
//		setRequestNodeValueByXPath("//request/packageCode", packageCode);
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/packageCode", packageCode);
		//Envelope/Body/quickBook/quickBookRequest/packageCode
	}
	public void setGroupNumber(String groupNumber){
		try{
			setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/blockCode", groupNumber);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest", "fx:AddNode;blockCode");
			setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/blockCode", groupNumber);
		}
		
	}
	
	public void setNumberOfAdults(String numberOfAdults){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/numberOfAdults", numberOfAdults);
	}
	
	public void setArrivalDaysOut(String arrivalDaysOut){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/arrivalDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
	}
	
	public void setDepartureDaysOut(String departureDaysOut){
		setRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/departureDate", "fx:GetDate; Daysout:" + departureDaysOut);
	}
	
	public String getArrivalDate(){
		return getRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/arrivalDate");
	}
	
	public String getDepartureDate(){
		return getRequestNodeValueByXPath("//Envelope/Body/quickBook/quickBookRequest/departureDate");
	}
	public void setPrimaryGuestAddressLocatorId(String value){
		try{
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/locatorId", value);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails", "fx:AddNode;locatorId");
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/locatorId", value);
		}
	}
	public void setPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/addressLine1", value);
	}
	
	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/city", value);
	}
	
	public void setPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/postalCode", value);
	}

	public void setPrimaryGuestCountry(String value){
		try{
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/country", value);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails", "fx:AddNode;country");
			setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/country", value);
		}
	}
	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/addressDetails/state", value);
	}
	public void setPrimaryGuestPhoneNumber(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/phoneDetails/number", value);
	}
	public void setPrimaryGuestEmail(String value){
		setRequestNodeValueByXPath("//quickBookRequest/primaryGuestDetail/emailDetails/address", value);
	}
}
