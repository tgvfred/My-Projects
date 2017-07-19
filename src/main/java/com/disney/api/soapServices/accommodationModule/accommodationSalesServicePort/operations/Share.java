package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class Share extends AccommodationSalesServicePort{
	public Share(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("share")));
	    generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
	public void setTravelComponentGroupingId(String tcg_id){
	setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents/travelComponentGroupingId", tcg_id);
    }
    public String getTravelComponentGroupingId(){
    return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId");
    } 
    public String getTravelComponentId(){
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentId");
        } 
}