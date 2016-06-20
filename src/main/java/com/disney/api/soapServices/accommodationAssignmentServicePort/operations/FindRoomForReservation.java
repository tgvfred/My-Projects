package com.disney.api.soapServices.accommodationAssignmentServicePort.operations;

import com.disney.api.soapServices.accommodationAssignmentServicePort.AccommodationAssignmentServicePort;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class FindRoomForReservation extends AccommodationAssignmentServicePort {
	public FindRoomForReservation(String environment, String scenario) {
		super(environment);
		// UI Booking
		// Generate a request from a project xml file
		setRequestDocument(XMLTools
				.loadXML(buildRequestFromWSDL("findRoomsForReservation")));
		System.out.println(getRequest());

		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

		removeComments();
		removeWhiteSpace();
	}

	public void setTravelPlanId(String travelPlanId) {
		setRequestNodeValueByXPath(
				"//findRoomsForReservation/roomSearch/reservationNumber",
				travelPlanId);
	}

	public String getFirstRoomNumber(){
		String roomNumber = "";
		try {
			roomNumber = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms[1]/statusdetails/statusName[text()='VACANT']/../../roomNumber");
		}catch(RuntimeException ex){
			try{
				roomNumber = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms[1]/statusdetails/statusName[text()='VACANT']/../../roomNumber");
			}catch(RuntimeException ex1){
				TestReporter.logFailure("No VACANT rooms are available");
				throw new RuntimeException("No VACANT rooms are available");
			}
		}
		//return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms[1]/roomNumber");
System.out.println();
		return roomNumber;
	}

	public String getAssignmentOwnerNumber() {
		return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/assgnOwnDtlList/assignmentOwnerId");
	}
	
	public String getRoomResourceNumber(){
		String resourceId = "";
		try {
			resourceId = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms[1]/resourceId");
		}catch(RuntimeException ex){
			resourceId = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms[1]/resourceId");
		}
		//return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms[1]/resourceId");;
		return resourceId;
		
	}	
	
	public String[] getTwoBedroomLockOffChildRoomsIDs(){

		String[] childRoomIDs = new String[2];
		childRoomIDs[0] = getResponseNodeValueByXPath("//matchingRooms/childRoomIds[1]");
		childRoomIDs[1] = getResponseNodeValueByXPath("//matchingRooms/childRoomIds[2]");

		return childRoomIDs;
	}
}
