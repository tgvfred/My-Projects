package com.disney.api.soapServices.admissionSalesServicePort.operations;

import com.disney.api.soapServices.admissionSalesServicePort.AdmissionSalesServicePort;
import com.disney.utils.XMLTools;

public class CancelKeyToTheWorldTickets extends AdmissionSalesServicePort {

	public CancelKeyToTheWorldTickets(String environment, String scenario) {
		super(environment);

		// Generate a request from a project xml file
		setRequestDocument(XMLTools
				.loadXML(buildRequestFromWSDL("cancelKeyToTheWorldTickets")));

		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments();
		removeWhiteSpace();
	}

	public void setAdmissionComponecntId(String value) {
		setRequestNodeValueByXPath(
				"/Envelope/Body/cancelKeyToTheWorldTickets/request/cancelTicketTO/ticketComponentId",
				value);
	}

	public void setTravelComponentGroupingId(String value) {
		setRequestNodeValueByXPath(
				"/Envelope/Body/cancelKeyToTheWorldTickets/request/cancelTicketTO/travelComponentGroupingId",
				value);
	}

	public void setTravelPlanId(String value) {
		setRequestNodeValueByXPath(
				"/Envelope/Body/cancelKeyToTheWorldTickets/request/cancelTicketTO/travelPlanId",
				value);
	}

	public void setTravelPlanSegmentId(String value) {
		setRequestNodeValueByXPath(
				"/Envelope/Body/cancelKeyToTheWorldTickets/request/cancelTicketTO/travelPlanSegmentId",
				value);
	}

}
