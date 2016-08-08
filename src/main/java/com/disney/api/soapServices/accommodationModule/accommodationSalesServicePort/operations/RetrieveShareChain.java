package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveShareChain extends AccommodationSalesServicePort{
    public RetrieveShareChain(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveShareChain")));
		generateServiceContext();
		//	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
     removeComments() ;
		removeWhiteSpace(); 
     }
    public void setTravelComponentGroupingId(String tcg_id){
	   setRequestNodeValueByXPath("/Envelope/Body/retrieveShareChain/request/travelComponentGroupingId", tcg_id);
     }
   
   public String gettravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelComponentId");
}
   public String gettravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/travelPlanSegmentId"); 

}
}