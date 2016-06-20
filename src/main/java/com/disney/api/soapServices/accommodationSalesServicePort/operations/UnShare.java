package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class UnShare extends AccommodationSalesServicePort{
	public UnShare(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("unShare")));
	    generateServiceContext();
	    //setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/UnShare/UnShareInputs.xls", scenario));
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
	public void setTravelComponentGroupingId(String tcg_id){
	setRequestNodeValueByXPath("/Envelope/Body/unShare/request/unSharedComponents/travelComponentGroupingId", tcg_id);
    }

	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/travelPlanSegmentId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/travelComponentId");
	}
	
    
}