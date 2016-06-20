package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class AssignTableNumbers extends ShowDiningService {
	public AssignTableNumbers(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("assignTableNumbers")));
		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanSegmentId(String value){setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/travelPlanSegmentId", value);}
	public void setPartySize(String value){setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/partySize", value);}
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/inventoryDetails/reservableResourceId", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/salesChannel", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/communicationChannel", value);}
	public String getStatus(){return getResponseNodeValueByXPath("Envelope/Body/assignTableNumbersResponse/status");}
}