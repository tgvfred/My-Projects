package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CalculateUnsharedRates extends AccommodationSalesServicePort{
    public CalculateUnsharedRates(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateUnsharedRates")));
		generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments() ;
		removeWhiteSpace(); 
    }
    public String getfreezeId(){
	   return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/freezeId");
    }
    public String getinventoryStatus(){
	   return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/inventoryStatus");
    }
}