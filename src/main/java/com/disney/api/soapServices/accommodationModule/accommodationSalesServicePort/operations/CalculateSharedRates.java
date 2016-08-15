package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CalculateSharedRates extends AccommodationSalesServicePort{

	public CalculateSharedRates(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateSharedRates")));
		
		generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public String getpackageCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/packageCode");
	}
	
	public String getguaranteeStatus(){
		return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/guaranteeStatus");
	}
}
