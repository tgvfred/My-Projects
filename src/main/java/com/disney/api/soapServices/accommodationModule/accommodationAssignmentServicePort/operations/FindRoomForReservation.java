package com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.AccommodationAssignmentServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class FindRoomForReservation extends AccommodationAssignmentServicePort {
	public FindRoomForReservation(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("findRoomsForReservation")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments();
		removeWhiteSpace();
	}

	public void setTravelPlanId(String travelPlanId) {setRequestNodeValueByXPath("//findRoomsForReservation/roomSearch/reservationNumber",travelPlanId);}

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
		return roomNumber;
	}
	
	public void setNumberOfResponseRows(String value){setRequestNodeValueByXPath("/Envelope/Body/findRoomsForReservation/roomSearch/maxRowsPerPage", value);}
	public String getAssignmentOwnerNumber() {return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/assgnOwnDtlList/assignmentOwnerId");}
	
	public String getRoomResourceNumber(){
		String resourceId = "";
		try {
			resourceId = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms[1]/resourceId");
		}catch(RuntimeException ex){
			resourceId = getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms[1]/resourceId");
		}
		return resourceId;
	}	
	
	public String[] getTwoBedroomLockOffChildRoomsIDs(){
		String[] childRoomIDs = new String[2];
		childRoomIDs[0] = getResponseNodeValueByXPath("//matchingRooms/childRoomIds[1]");
		childRoomIDs[1] = getResponseNodeValueByXPath("//matchingRooms/childRoomIds[2]");
		return childRoomIDs;
	}
	
	
	
	

	
	public int getNumberOfRoomsReturned(){
		int matchingRooms = 0;
		int alternateRooms = 0;
		matchingRooms = getNumberOfMatchingRooms();
		alternateRooms = getNumberOfAlternateRooms();
		return matchingRooms + alternateRooms; 
	}
	public int getNumberOfMatchingRooms(){
		try{return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms").getLength();}
		catch(XPathNotFoundException e){return 0;}		
	}
	public int getNumberOfAlternateRooms(){
		try{return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms").getLength();}
		catch(XPathNotFoundException e){return 0;}
	}
	public String getMatchingRoomNumber(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms["+index+"]/statusdetails/statusName[text()='VACANT']/../../roomNumber");
	}
	public String getAlternateRoomNumber(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms["+index+"]/statusdetails/statusName[text()='VACANT']/../../roomNumber");
	}
	public String getMatchingRoomResourceNumber(String index){return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/matchingRooms["+index+"]/resourceId");}
	public String getAlternateRoomResourceNumber(String index){return getResponseNodeValueByXPath("/Envelope/Body/findRoomsForReservationResponse/return/alternateRooms["+index+"]/resourceId");}
	
	public Map<String, String> getAllRoomAndResourceIds(){
		Map<String, String> values = new HashMap<String, String>();
		String roomNumber = "";
		String resourceId = "";
		for(int i = 1; i <= getNumberOfMatchingRooms(); i++){
			try{
				roomNumber = getMatchingRoomNumber(String.valueOf(i));
				resourceId = getMatchingRoomResourceNumber(String.valueOf(i));
				values.put(roomNumber, resourceId);
			}catch(Exception e){}
		}
		for(int i = 1; i <= getNumberOfAlternateRooms(); i++){
			try{
				roomNumber = getAlternateRoomNumber(String.valueOf(i));
				resourceId = getAlternateRoomResourceNumber(String.valueOf(i));
				values.put(roomNumber, resourceId);
			}catch(Exception e){}
		}
		
		return values;
	}
}