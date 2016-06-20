package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveResortReservations extends AccommodationSalesServicePort{
	public RetrieveResortReservations(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveResortReservations")));
	    generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		} 
	
	public void setreservationNumber(String tps_id){
	setRequestNodeValueByXPath("/Envelope/Body/retrieveResortReservations/retrieveResortReservationsRequest/reservationNumber", tps_id);
	}
	public String getTravelPlanSegmentId(){
    return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/travelPlanSegmentId");
	}
	public String getpurposeOfVisit(){
	return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles/purposeOfVisit");
	}
} 