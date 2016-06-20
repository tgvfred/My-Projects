package com.disney.api.soapServices.accommodationAssignmentServicePort.operations;

import com.disney.api.soapServices.accommodationAssignmentServicePort.AccommodationAssignmentServicePort;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class AssignRoomForReservation extends AccommodationAssignmentServicePort{
	public AssignRoomForReservation(String environment, String scenario) {
		super(environment);
		//UI Booking
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("assignRoomsForReservation")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setRoomNumber(String roomNumber){
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/roomNumber", roomNumber);
	}
	
	public void setAssignmentOwnerNumber(String assignmentOwnerNumber){
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/assgnOwnrDtlId", assignmentOwnerNumber);
	}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/facilityId", facilityId);
	}	
	
	public void setRoomResourceNumber(String roomResourceNumber){
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/resourceId", roomResourceNumber);
	}	
	
	public void setArrivalAndDepartureDaysOut(String arrivalDaysOut, String departureDaysOut){
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/effectiveDate", Randomness.generateCurrentXMLDatetime(Integer.parseInt(arrivalDaysOut)));
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/expirationDate", Randomness.generateCurrentXMLDatetime(Integer.parseInt(departureDaysOut)));
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/occupancydetail/ownerPeriod/startDate", Randomness.generateCurrentXMLDatetime(Integer.parseInt(arrivalDaysOut)));
		setRequestNodeValueByXPath("/Envelope/Body/assignRoomsForReservation/assignReq/roomsWithReservations/occupancydetail/ownerPeriod/endDate", Randomness.generateCurrentXMLDatetime(Integer.parseInt(departureDaysOut)));
	}
	
}
