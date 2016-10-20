package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetrieveGuestSummary extends AccommodationFulfillmentServicePort{

	public RetrieveGuestSummary(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestSummary")));		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public RetrieveGuestSummary(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestSummary")));		
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelPlanId(String tcgId){setRequestNodeValueByXPath("/Envelope/Body/retrieveGuestSummary/request/travelPlanId",tcgId);}
	
	public String getKttwId(String index){
//		return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestSummaryResponse/guestReservationInfo/guestAccommodationInfoList/accommodationGuestInfoList[1]/guestKttwInfoList["+index+"]/kttwNumber");
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestSummaryResponse/guestReservationInfo/guestAccommodationInfoList/accommodationGuestInfoList["+index+"]/guestKttwInfoList/kttwNumber");
	}
	public int getNumberOfKttwIds(){
		int numKttw =0;
		try{numKttw = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestSummaryResponse/guestReservationInfo/guestAccommodationInfoList/accommodationGuestInfoList/guestKttwInfoList/kttwNumber").getLength();}
		catch(XPathNotFoundException e){}
		return numKttw;
	}
}
