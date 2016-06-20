package com.disney.api.soapServices.scheduledEventsComponentService.operations;

import com.disney.api.soapServices.scheduledEventsComponentService.ScheduledEventsComponentService;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForReservationMassProcess extends ScheduledEventsComponentService{
	public GetStagedRecordsForReservationMassProcess(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getStagedRecordsForReservationMassProcess")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setProcessDataId(String value){setRequestNodeValueByXPath("/Envelope/Body/getStagedRecordsForReservationMassProcess/processDataId", value);}
	
	public String getProcessIndicator(){return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReservationMassProcessResponse/return/processIndicator");}
	public String getReason(){return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReservationMassProcessResponse/return/reason");}
	public String getTravelPlanSegmentId(){return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReservationMassProcessResponse/return/travelPlanSegmentId");}
}
