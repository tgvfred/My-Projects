package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetrieveGuestContactDetails  extends AccommodationFulfillmentServicePort{

	public RetrieveGuestContactDetails(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestContactDetails")));
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public RetrieveGuestContactDetails(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestContactDetails")));
		generateServiceContext();	
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTpId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetails/request/travelPlanId", value);}
	
	public String getTpId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/travelPlanId");}
	public String getOnSiteMessaging(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/onSiteMessaging");}
	public int getNumberOfOnsiteContactDetails(){
		int num = 0;
		try{
			num = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestContactDetailsResponse/response/onsiteContactDetails").getLength();
		}catch(XPathNotFoundException e){}
		return num;
	}
	public String getContactType(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/onsiteContactDetails["+index+"]/contactType");}
	public String getContactValue(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/onsiteContactDetails["+index+"]/contactValue");}
	public int getNumberOfCastConfirmedContactDetails(){
		int num = 0;
		try{
			num = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestContactDetailsResponse/response/castConfirmedContactDetails").getLength();
		}catch(XPathNotFoundException e){}
		return num;
	}
	public String getRequired(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/castConfirmedContactDetails["+index+"]/required");}
	public String getDeliveryMethodValue(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/castConfirmedContactDetails["+index+"]/deliveryMethodValue");}
	public String getDeliveryMethod(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGuestContactDetailsResponse/response/castConfirmedContactDetails["+index+"]/deliveryMethod");}
	
	public boolean isDeliveryMethodAndValueReturned(String method, String value){
		boolean found = false;
		
		for(int i = 1; i <= getNumberOfCastConfirmedContactDetails(); i++){
			if(getDeliveryMethod(String.valueOf(i)).equalsIgnoreCase(method)){
				if(getDeliveryMethodValue(String.valueOf(i)).equalsIgnoreCase(value)){
					found = true;
					break;
				}
			}
		}
		
		return found;
	}
	public boolean isContactTypeAndValueReturned(String type, String value){
		boolean found = false;
		
		for(int i = 1; i <= getNumberOfCastConfirmedContactDetails(); i++){
			if(getContactType(String.valueOf(i)).equalsIgnoreCase(type)){
				if(getContactValue(String.valueOf(i)).equalsIgnoreCase(value)){
					found = true;
					break;
				}
			}
		}
		
		return found;
	}
}