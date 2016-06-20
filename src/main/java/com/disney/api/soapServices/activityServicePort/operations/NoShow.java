package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class NoShow extends ActivityService {
	public NoShow(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
//		System.out.println(getRequest());	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/communicationChannel", value);}
	public String getCancellationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/noShowActivityComponentResponse/cancellationNumber");}
}