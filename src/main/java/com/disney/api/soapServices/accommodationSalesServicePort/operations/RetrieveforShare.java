package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;


public class RetrieveforShare extends AccommodationSalesServicePort{

	public RetrieveforShare(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveForShare")));
		
		generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	
	public void settravelComponentGroupingId(String Tcg_ID){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveForShare/request/travelComponentGroupingId", Tcg_ID);
	}
	
	public void settravelPlanSegmentId(String tps_ID){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveForShare/request/travelPlanSegmentId", tps_ID);
	}
}
