package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class Book extends ActivityService{
	
	public Book(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	/*
	 * Activity Event Getters and Setters
	 */
	
	public void setActivityTravelPlanId(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelPlanId", value);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest", "fx:AddNode;Node:travelPlanId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelPlanId", value);
		}
	}
	
	public void setActivitySalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/salesChannel", value);
	}
	
	public void setActivityCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/communicationChannel", value);
	}
	
	public void setActivityUnitPriceDateTime(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/unitPrices/date", value);
	}
	
	public void setActivityComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices/unitPrices/date", value);
	}
	
	public void setActivityServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/serviceStartDate", value);
	}
	
	public void setActivityAddOnComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setActivityAddOnServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/serviceStartDate", value);
	}
	
	public void setActivitySourceAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/sourceAccountingCenter", value);
	}
	
	public String getActivitySalesChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/salesChannel");
	}
	
	public String getActivityCommunicationsChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/communicationChannel");
	}
	
	public String getActivityPrimaryGuestFirstName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/firstName");
	}
	
	public String getActivityPrimaryGuestLastName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/lastName");
	}

	public String getActivityTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookActivityComponentResponse/bookResponse/travelPlanId");
	}
	
	public String getActivityTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookActivityComponentResponse/bookResponse/confirmationNumber");
	}		
	
	public void setActivityComponentPriceComponentType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentType", value);
	}
	
	public void setActivityComponentPriceComponentId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentId", value);
	}
	
	public void setActivityComponentPriceRevenueClassificationId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/revenueClassification/id", value);
	}
	
	public void setActivityComponentPriceRevenueClassificationName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/revenueClassification/name", value);
	}
	
	public void setActivityFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityId", value);
	}
	
	public void setActivityFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityName", value);
	}
	
	public void setActivityProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productId", value);
	}
	
	public void setActivityProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productType", value);
	}
	
	public void setActivityServicePeriosId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/servicePeriodId", value);
	}
	
	public void setActivitySignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/signInLocation", value);
	}
	
	public void setActivityAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setActivityAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setActivityAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setActivityAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setActivityAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/productId", value);
	}
	
	public void setActivityAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/productType", value);
	}
	
	public void setActivityPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);
	}
	
	public void setActivityPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/city", value);
	}
	
	public void setActivityPrimaryGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/country", value);
	}
	
	public void setActivityPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);
	}
	
	public void setActivityPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/state", value);
	}
	
	public void setPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);
	}
	
	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/city", value);
	}
	
	public void setPrimaryGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/country", value);
	}
	
	public void setPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);
	}
	
	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/state", value);
	}
	
	public String getPartyId(){return getResponseNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/partyId");}
	
	
	

	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices/componentId");}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}
}
