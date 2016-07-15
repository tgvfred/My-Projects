
package com.disney.api.soapServices.accommodationSalesServicePort.operations;
import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;


public class BuyPoints extends AccommodationSalesServicePort{

	public BuyPoints(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("buyPoints")));
		
		generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/buyPoints/buyPoints.xls", scenario));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	public String getTravelPlanID(){
		return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelPlanId");
	}
	
	public String gettravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentId");
	}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelPlanId");}
	public String getTravelComponentId(){return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentId");}
	public String getTravelComponentGroupingId(){return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentGroupingId");}
}
