package com.disney.api.soapServices.folioModule.chargeGroup.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.folioModule.chargeGroup.ChargeGroup;
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
		setRootStartDateDaysOut(startDate);
		setGuestContainerStartDateDaysOut(startDate);
		setGuestContainedStartDateDaysOut(startDate);
	}
	public void setRootStartDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}
	public void setGuestContainerStartDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}
	public void setGuestContainedStartDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/startDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}

	public void setStartDate(String startDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/startDate", startDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/startDate", startDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/startDate", startDate);
	}
	public void setRootStartDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/startDate", startDate);}
	public void setGuestContainerStartDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/startDate", startDate);}
	public void setGuestContainedStartDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/startDate", startDate);}

	public void setEndDateDaysOut(String endDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(endDate));
	}
	public void setRootEndDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}
	public void setGuestContainerEndDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}
	public void setGuestContainedEndDateDaysOut(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", BaseSoapCommands.GET_DATE_TIME.commandAppend(startDate));}

	public void setEndDate(String endDate){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", endDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", endDate);
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", endDate);
	}
	public void setRootEndDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period/endDate", startDate);}
	public void setGuestContainerEndDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period/endDate", startDate);}
	public void setGuestContainedEndDate(String startDate){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period/endDate", startDate);}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/transactionFacility", facilityId);
	}
	
	public void setTravelPlanId(String value){
		setRootPrimaryReferenceValue(value);
		setGuestContainerRootReferenceValue(value);
		setGuestContainedRootReferenceValue(value);
	}

	public void setTravelPlanSegmentId(String value){
		setGuestContainerPrimaryReferenceValue(value);
		setGuestContainedAncestorReferenceValue(value);
	}

	public void setTravelPlanComponentGroupId(String value){
		setGuestContainedPrimaryReferenceValue(value);
	}
	
	public void setRootPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/primaryReference/referenceName", value);}
	public void setRootPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/primaryReference/referenceValue", value);}
	public void setGuestContainerPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/primaryReference/referenceName", value);}
	public void setGuestContainerPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/primaryReference/referenceValue", value);}
	public void setGuestContainerRootReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/rootReference/referenceName", value);}
	public void setGuestContainerRootReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/rootReference/referenceValue", value);}
	public void setGuestContainedPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference/referenceName", value);}
	public void setGuestContainedPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference/referenceValue", value);}
	public void setGuestContainedAncestorReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/ancestor/referenceName", value);}
	public void setGuestContainedAncestorReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/ancestor/referenceValue", value);}
	public void setGuestContainedRootReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/root/referenceName", value);}
	public void setGuestContainedRootReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/root/referenceValue", value);}	

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
	

	public void setRootDecription(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/description", value);}
	public void setGuestContainerDecription(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/description", value);}
	public void setGuestContainedDecription(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/description", value);}
	
	
	
	
	public void setRootSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/sourceAccountingCenterID", value);}
	public void setGuestContainerSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/sourceAccountingCenterID", value);}
	public void setGuestContainedSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/sourceAccountingCenterID", value);}
}
