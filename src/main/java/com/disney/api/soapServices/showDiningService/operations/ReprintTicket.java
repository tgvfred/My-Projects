package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class ReprintTicket extends ShowDiningService {
	public ReprintTicket(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reprintTicket")));
		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/reprintTicket/reprintShowDiningTicketRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/reprintTicket/reprintShowDiningTicketRequest/salesChannel", value);}
	public void setReprintReasonId(String value){setRequestNodeValueByXPath("/Envelope/Body/reprintTicket/reprintShowDiningTicketRequest/reprintReasonId", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/reprintTicket/reprintShowDiningTicketRequest/communicationChannel", value);}
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/reprintShowDiningResponse/status");}
}