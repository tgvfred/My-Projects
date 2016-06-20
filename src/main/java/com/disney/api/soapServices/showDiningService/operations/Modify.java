package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class Modify extends ShowDiningService {
	public Modify(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setComponentUnitPriceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setAddOnServiceStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/serviceStartDate", value);
	}	
	
	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/travelPlanId", value);
	}
	
	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/reservationNumber", value);
	}
	
	public void setFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/facilityId", value);
	}
	
	public void setServicePeriosId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/signInLocation", value);
	}
	
	public void setAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/productId", value);
	}
	
	public void setAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/productType", value);
	}
	
	public void setAddOnFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/facilityId", value);
	}
	
	public void setAddOnFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/facilityName", value);
	}
	
	public String getResponseStatus(){
		return getResponseNodeValueByXPath("/Envelope/Body/modifyShowDiningResponse/status");
	}
	
	public void addInternalComments(String text, String type){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentType", type);
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments", "fx:AddNode;Node:commentType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentType", type);
		}
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/communicationChannel", value);
	}
	
	public void setReservationnumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/reservationNumber", value);
	}
	
	public void setProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/productId", value);
	}
	
	public void setProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/productType", value);
	}
	
	public void setServiceStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setFulfillmentDate(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/charges["+index+"]/fulfillmentDate", value);
	}
	
	public void setProfileDetailIdAndType(String id, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:profileDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:id");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:type");
		}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/type", type);
	}
	
	public void setComments(String text, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+index+"]/commentText");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentType");
		}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+index+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+index+"]/commentType", type);
	}
	
	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/allergies["+index+"]");
		}catch(Exception e){
			e.printStackTrace();
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:allergies");
		}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/allergies["+index+"]", value);
	}
	
	public void setEnterpriseProductid(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/enterpriseProductId", value);}
	
	
	
	
	

	public int getNumberOfComponentIds(){
		int numNodes = 0;
		try{numNodes = getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/componentPrices");}
		catch(RuntimeException e){}
		return numNodes;
	}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}	
}