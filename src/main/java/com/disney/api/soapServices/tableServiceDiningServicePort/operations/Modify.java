package com.disney.api.soapServices.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.XMLTools;

public class Modify extends TableServiceDiningServicePort {
	public Modify(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/travelPlanId", value);}
	public void setPrimaryGuestPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/partyId", value);}
	public void setPrimaryGuestGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/guestId", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/communicationChannel", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/sourceAccountingCenter", value);}
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/reservationNumber", value);}
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/modifyTableServiceResponse/status");}
}
