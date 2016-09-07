package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrievePostedCancellationFee extends AccommodationSalesServicePort{
	public RetrievePostedCancellationFee(String environment, String scenario) {
		
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePostedCancellationFee")));
		generateServiceContext();
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/retrievePostedCancellationFee/retrievePostedCancellationFeeInputs.xls", scenario));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setid(String Value) {
		setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/identityDetails/id",Value);
		
	}
	public String getWaived(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/waived");
	}
	
	
	public String getOverridden(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/overridden");
	}
}
