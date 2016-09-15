package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class Book extends AccommodationSalesServicePort{
	
	public Book(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
		generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/book/bookInput.xls", scenario));
		   	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
		 
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("//travelPlanId");
	}

	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("//travelPlanSegmentId");
	}
	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("//travelComponentGroupingId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("//travelComponentId");
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("//roomDetails[1]/roomReservationDetail/guestReferenceDetails/guest/partyId");
	}
	
	public void setExternalRefNum(String externalRefNum){
		setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceNumber", externalRefNum);
	}
	
	public void setExternalRefSource(String externalRefSource){
		setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceSource", externalRefSource);
	}
	
	public void setBlockCode(String blockCode){
		switch (blockCode) {
		case "1905":
			blockCode = "01905";
			break;
		case "1825":
			blockCode = "01825";
			break;
		case "4250":
			blockCode = "04250";
			break;
		case "4268":
			blockCode = "04268";
			break;
		default:
			break;
		}
		try{
			setRequestNodeValueByXPath("//book/request/roomDetail/blockCode", blockCode);
		}catch(XPathNotFoundException xnfe){
			setRequestNodeValueByXPath("//book/request/roomDetail", "fx:addnode; Node:blockCode");
			setRequestNodeValueByXPath("//book/request/roomDetail/blockCode", blockCode);
		}
	}
	
	public void setBookingDate(String bookingDate){
		setRequestNodeValueByXPath("//book/request/roomDetail/bookingDate", bookingDate);
	}
	
	public void setRoomDetailExtRefNum(String externalRefNum){
		setRequestNodeValueByXPath("//book/request/roomDetail/externalReferences/externalReferenceNumber", externalRefNum);
	}
	
	public void setRoomDetailExtRefSrc(String externalRefSource){
		setRequestNodeValueByXPath("//book/request/roomDetail/externalReferences/externalReferenceSource", externalRefSource);
	}
	
	public void setInventoryTrackingID(String inventoryID){
		setRequestNodeValueByXPath("//book/request/roomDetail/InventoryTrackingId", inventoryID);
	}

	/**
	 * 
	 * @summary Method to set the request departure date - modified to handle 2 scenarios
	 * 	1) a string-number is passed as the days out (EX: "0")
	 *  2) a string-function is passed as the days out (EX: "fx:GetDate; Daysout:0")
	 * @version Created 11/11/2014
	 * @author Waightstill W Avery
	 * @param deptDateDaysOut - string used to define the days out in the request; see summary for more details
	 * @throws NA
	 * @return NA
	 * 
	 */
	public void setDeptDate(String deptDateDaysOut){
		if(deptDateDaysOut.contains("fx:GetDate; Daysout:")){
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/endDate", deptDateDaysOut);
		}else{
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/endDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
		}
	}
	public void setExplicitDeptDate(String deptDateDaysOut){
		setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/endDate", deptDateDaysOut);
	}

	/**
	 * 
	 * @summary Method to set the request arrival date - modified to handle 2 scenarios
	 * 	1) a string-number is passed as the days out (EX: "0")
	 *  2) a string-function is passed as the days out (EX: "fx:GetDate; Daysout:0")
	 * @version Created 11/11/2014
	 * @author Waightstill W Avery
	 * @param arrivalDaysOut - string used to define the days out in the request; see summary for more details
	 * @throws NA
	 * @return NA
	 * 
	 */
	public void setArrivalDate(String arrivalDaysOut){
		if(arrivalDaysOut.contains("fx:GetDate; Daysout:")){
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/startDate", arrivalDaysOut);
		}else{
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/startDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
		}
	}	
	
	public void setExplicitArrivalDate(String arrivalDaysOut){
		setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/startDate", arrivalDaysOut);
	}
	
	public void setPackageCode(String packageCode){
		setRequestNodeValueByXPath("//book/request/roomDetail/packageCode", packageCode);
	}
	
	public void setResortCode(String resortCode){
		setRequestNodeValueByXPath("//book/request/roomDetail/resortCode", resortCode);
	}
	
	public void setRoomTypeCode(String roomTypeCode){
		setRequestNodeValueByXPath("//book/request/roomDetail/roomTypeCode", roomTypeCode);
	}

	public void setLocationID(String locationID){
		setRequestNodeValueByXPath("//book/request/locationId", locationID);
	}
	
	public void setReservationType(String reservationType){
		setRequestNodeValueByXPath("//book/request/roomDetail/reservationType", reservationType);
	}
	
	public void setSalesChannel(String salesChannel){
		setRequestNodeValueByXPath("//book/request/salesChannel", salesChannel);
	}
	
	public void setMembershipID(String membershipID){
		setRequestNodeValueByXPath("//book/request/travelPlanGuest/membershipDetail/membershipId", membershipID);
	}

	//Guest first & last names.  Have to enter in 2 places, under the Guest reference details node & the travel plan guest node
	public void setPrimaryGuestFirstNameGuestRefDetails(String firstName){
		setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/firstName", firstName);
	}
		
	public void setPrimaryGuestLastNameGuestRefDetails(String lastName){
		setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/lastName", lastName);
	}
	
	public void setPrimaryGuestFirstNameTravelPlan(String firstName){
		setRequestNodeValueByXPath("//book/request/travelPlanGuest/firstName", firstName);
	}
	
	public void setPrimaryGuestLastNameTravelPlan(String lastName){
		setRequestNodeValueByXPath(""
				+ "//book/request/travelPlanGuest/lastName", lastName);
	}
	
	public String getPrimaryGuestFirstName(){
		return getResponseNodeValueByXPath("//bookResponse/bookResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails/guest/firstName");
	}
	
	public String getPrimaryGuestLastName(){
		return getResponseNodeValueByXPath("//bookResponse/bookResponse/roomDetails[2]/roomReservationDetail/guestReferenceDetails/guest/lastName");
	}
	public String getGuestId(){
		return getResponseNodeValueByXPath("//guestId");
	}
	
	public void setPrimaryGuestFirstName(String name){
		setRequestNodeValueByXPath("//guest/firstName", name);
		setRequestNodeValueByXPath("//travelPlanGuest/firstName", name);
	}
	
	public void setPrimaryGuestLastName(String name){
		setRequestNodeValueByXPath("//guest/lastName", name);
		setRequestNodeValueByXPath("//travelPlanGuest/lastName", name);

	}
	
	public void setPhoneNumber(String phone){
		setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number", phone);

	}
	
	
	public void setEmail(String email){
		setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address", email);
	}
	public String getPrimaryPhoneNumber(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");
	}
	
	public String getBookingDate(){
		return getRequestNodeValueByXPath("//book/request/roomDetail/bookingDate");
	}
	
	public void setRoomReservationDetailsGuestId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
	}
	
	public void setTravelPlanGuestid(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/guestId", value);
	}
	
	public void setRoomReservationDetailsPartyId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
	}
	
	public void setTravelPlanPartyId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/partyId", value);
	}
}