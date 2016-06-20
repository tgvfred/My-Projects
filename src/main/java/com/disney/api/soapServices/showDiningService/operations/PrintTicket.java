package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class PrintTicket extends ShowDiningService {
	public PrintTicket(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("printTicket")));
		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationnumber(String value){setRequestNodeValueByXPath("/Envelope/Body/printTicket/printShowDiningTicketRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/printTicket/printShowDiningTicketRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/printTicket/printShowDiningTicketRequest/communicationChannel", value);}
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/printShowDiningResponse/status");}
}