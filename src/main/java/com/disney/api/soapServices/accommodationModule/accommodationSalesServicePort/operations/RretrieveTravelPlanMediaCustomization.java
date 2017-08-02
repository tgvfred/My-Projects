package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RretrieveTravelPlanMediaCustomization extends AccommodationSalesServicePort {


	public RretrieveTravelPlanMediaCustomization(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTravelPlanMediaCustomization")));
		//System.out.println(getRequest());
		generateServiceContext();	
		removeComments() ;
		removeWhiteSpace();
	}

	//Setters
	public void setEnterpriseFacilityId(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/enterpriseFacilityId",value);}
	public void setTravelPlanId(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/travelPlanId",value);}
	public void setResortCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/resortCode",value);}
	public void setLocationId(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/locationId",value);}
	public void setParty1Id(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party1Id",value);}
	public void setParty1Type(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party1Type",value);}
	public void setParty2Id(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party2Id",value);}
	public void setParty2Type(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party2Type",value);}
	
	//Getters
	public String getIsFacilityEligible() {return getResponseNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/response/isFacilityEligible");}
}

//<serviceContext>
//<addressRole>?</addressRole>
//<conversationId>?</conversationId>
//<messageId>?</messageId>
//<party1Id>273236743</party1Id>
//<party1Type>true</party1Type>
//<party2Id>273236775</party2Id>
//<party2Type>false</party2Type>
