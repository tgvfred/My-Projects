
package com.disney.api.soapServices.accommodationSalesServicePort.operations;
import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;


public class RetrieveCancellationFee extends AccommodationSalesServicePort{

	public RetrieveCancellationFee(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancellationFee")));
		
		generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	public void setTravelPlanSegmentID(String TPS_ID){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/id", TPS_ID);
	}
	
	public String getName(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/revenueType/name");
	}
	
	public String getRevenueID(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/revenueClassification/id");
	}
}
