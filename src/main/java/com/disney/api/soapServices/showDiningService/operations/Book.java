package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class Book extends ShowDiningService {
	public Book(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
//		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelPlanId", value);
	}

	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookShowDiningResponse/bookResponse/travelPlanId");
	}
	
	public String getSalesChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/salesChannel");
	}
	
	public String getCommunicationsChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/communicationChannel");
	}
	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookShowDiningResponse/bookResponse/confirmationNumber");
	}
	
	public void setComponentUnitPriceDepositDueDateTime(String value, String index){
		System.out.println(getRequest());
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/deposit/dueDate", value);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/date", value);
	}
	
	public void setServiceDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setAddOnComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setAddOnServiceDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/serviceStartDate", value);
	}
	
	public void setComponentUnitPriceDateTime(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/date", value);
	}
	
	public void setComponentPriceComponentType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentType", value);
	}
	
	public void setComponentPriceComponentId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentId", value);
	}
	
	public void setComponentPriceRevenueClassificationId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/revenueClassification/id", value);
	}
	
	public void setComponentPriceRevenueClassificationName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/revenueClassification/name", value);
	}
	
	public void setFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId", value);
	}
	
	public String getFacilityId(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId");
	}
	
	public void setFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName", value);
	}
	
	public String getFacilityName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName");
	}
	
	public void setProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productId", value);
	}
	
	public void setProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productType", value);
	}
	
	public void setServicePeriosId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/signInLocation", value);
	}
	
	public void setAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/productId", value);
	}
	
	public void setAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/productType", value);
	}
	
	public void setAddOnFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/facilityId", value);
	}
	
	public void setAddOnFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/facilityName", value);
	}
	
	public void setPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/addressLine1", value);
	}
	
	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/city", value);
	}
	
	public void setPrimaryGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/country", value);
	}
	
	public void setPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/postalCode", value);
	}
	
	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/state", value);
	}
	
	public void setSourceAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/sourceAccountingCenter", value);
	}
	
	public void setServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/communicationChannel", value);
	}
	
	public void setProfileDetailIdAndType(String id, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:profileDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:id");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:type");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/type", type);
	}
	
	public void setComments(String text, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentText");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentType");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentType", type);
	}
	
	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/allergies["+index+"]");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:allergies");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/allergies["+index+"]", value);
	}
	
	
	

	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices");}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}
}
