package com.disney.api.soapServices.chargeGroup.operations;

import com.disney.api.soapServices.chargeGroup.ChargeGroup;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class CreateChargeGroupsAndPostCharges extends ChargeGroup{
	public CreateChargeGroupsAndPostCharges(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createChargeGroupsAndPostCharges")));
	
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setStartDateDaysOut(String startDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));
	}

	public void setStartDate(String startDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/startDate", startDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/startDate", startDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/startDate", startDate);
	}

	public void setEndDateDaysOut(String endDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
	}

	public void setEndDate(String endDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", endDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", endDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", endDate);
	}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/transactionFacility", facilityId);
	}
	
	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/primaryReference/referenceValue", value);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/rootReference/referenceValue", value);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/root/referenceValue", value);
	}

	public void setTravelPlanSegmentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/primaryReference/referenceValue", value);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/ancestor/referenceValue", value);
	}

	public void setTravelPlanComponentGroupId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference/referenceValue", value);
	}

	public void setGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/responsiblePartyFirstName", value);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/responsiblePartyFirstName", value);
	}

	public void setGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/responsiblePartyLastName", value);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/responsiblePartyLastName", value);
	}
	
	public boolean isSuccessful(){
		return getResponseNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostChargesResponse/returnParameter").equals("success");
	}
	
	
	

	
	public String getRequestPrimaryReferenceName(){
		return getRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference/referenceName");
	}
	public String getRequestPrimaryReferenceValue(){
		return getRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference/referenceValue");
	}
}
