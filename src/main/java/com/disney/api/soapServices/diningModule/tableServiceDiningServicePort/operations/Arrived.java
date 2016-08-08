package com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.XMLTools;

public class Arrived extends TableServiceDiningServicePort {
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));
//		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedTableServiceRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedTableServiceRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedTableServiceRequest/communicationChannel", value);}
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedTableServiceResponse/status");}
}
