package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class NoShow extends ShowDiningService {
	public NoShow(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/reservationNumber", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/communicationChannel", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/noShowShowDiningResponse/cancellationNumber");
	}
}