package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class RetrieveStagedReservationsForMassCancellation  extends ScheduledEventsBatchService{
	public RetrieveStagedReservationsForMassCancellation(String environment) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveStagedReservationsForMassCancellation")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}

	public void setProcessType(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveStagedReservationsForMassCancellation/processType", value);}

	
	public int getNumberOfReservationProcessesReturned(){
		return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveStageRecordsResp/retrieveStageRecordsResponse/massReservationProcessTOList");
	}
}