package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Modify extends ActivityService {
	public Modify(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/travelPlanId", value);}
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/partyId", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/communicationChannel", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/sourceAccountingCenter", value);}
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/reservationNumber", value);}
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/facilityId", value);}
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/productId", value);}
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/modifyActivityComponentResponse/status");}
}