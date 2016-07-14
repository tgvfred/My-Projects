package com.disney.api.soapServices.accommodationInventoryRequestComponentServicePort.operations;

import com.disney.api.soapServices.accommodationInventoryRequestComponentServicePort.AccommodationInventoryRequestComponentServicePort;
import com.disney.utils.XMLTools;

public class UpdateInventoryForScheduledEvents extends AccommodationInventoryRequestComponentServicePort{
	public UpdateInventoryForScheduledEvents(String environment, String scenario) {
		super(environment);
		
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateInventoryForScheduledEvents")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets assignment owner type
	 * @param value - assignment owner type
	 */
	public void setAssignmentOwnerType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/assignmentOwnerType", value);}
	/**
	 * Sets assignment request details date
	 * @param value - assignment request details date
	 */
	public void setAssignmentRequestDetailsDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/assignmentRequestDetails/date", value);}
	/**
	 * assignment request details is all day flag
	 * @param value - request details is all day flag
	 */
	public void setAssignmentRequestDetailsIsAllDay(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/assignmentRequestDetails/isAllDay", value);}
	/**
	 * Sets facility ID
	 * @param value - facility ID
	 */
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/facilityId", value);}
	/**
	 * Sets freeze ID
	 * @param value - freeze ID
	 */
	public void setFreezeId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/freezeId", value);}
	/**
	 * Sets is guaranteed flag
	 * @param value - is guaranteed flag
	 */
	public void setIsGuaranteed(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/isGuaranteed", value);}
	/**
	 * Sets is VIP flag
	 * @param value - is VIP flag
	 */
	public void setIsVip(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/isVIP", value);}
	/**
	 * Sets assignment owner ID
	 * @param value - assignment owner ID
	 */
	public void setAssignmentOwnerId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/assignmentOwnerId", value);}
	/**
	 * Sets the reservation type
	 * @param value - reservation type
	 */
	public void setReservationType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/reservationType", value);}
	/**
	 * Sets the resource type
	 * @param value - resource type
	 */
	public void setResourceType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/resourceType", value);}
	/**
	 * Sets the resource type code
	 * @param value - resource type code
	 */
	public void setResourceTypeCode(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/resourceTypeCode", value);}
	/**
	 * Sets the TCG ID
	 * @param value - TCG ID
	 */
	public void setTcgId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/tcgId", value);}
	/**
	 * Sets the transfer in flag
	 * @param value - transfer in flag
	 */
	public void setTransferIn(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/transferIn", value);}
	/**
	 * Sets the wholesaler indicator flag
	 * @param value - wholesaler indicator flag
	 */
	public void setWholesaleIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/wholeSaleIndicator", value);}
	/**
	 * Sets the TP ID
	 * @param value - TP ID
	 */
	public void setTpId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/tpId", value);}
	
	
	//***************************
	//***************************
	//     Owner Details Node
	//***************************
	//***************************
	
	
	/**
	 * Sets owner details booking date
	 * @param value - owner details booking date
	 */
	public void setOwnerDetailsBookingDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/bookingDate", value);}
	/**
	 * Sets owner details name
	 * @param value - owner details name 
	 */
	public void setOwnerDetailsOwnerName(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/ownerName", value);}
	/**
	 * Sets owner details owner reference number
	 * @param value - owner details owner reference number 
	 */
	public void setOwnerDetailsOwnerReferenceNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/ownerReferenceNumber", value);}
	/**
	 * Sets owner details owner reference type
	 * @param value - owner details owner reference type 
	 */
	public void setOwnerDetailsOwnerReferenceType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/ownerReferenceType", value);}
	/**
	 * Sets owner details parent owner reference type
	 * @param value - owner details parent owner reference type 
	 */
	public void setOwnerDetailsTravelPlanSegmentId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/parentOwnerReferences[2]/referenceNumber", value);}
	/**
	 * Sets owner details parent owner reference number
	 * @param value - owner details parent owner reference number 
	 */
	public void setOwnerDetailsTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/parentOwnerReferences[1]/referenceNumber", value);}
	/**
	 * Sets owner details period end date
	 * @param value - owner details period end date 
	 */
	public void setOwnerDetailsPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/period/endDate", value);}
	/**
	 * Sets owner details period start date
	 * @param value - owner details period start date 
	 */
	public void setOwnerDetailsPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/period/startDate", value);}
	

	//***************************
	//***************************
	//     Period Node
	//***************************
	//***************************
	
	
	/**
	 * Sets the period end date
	 * @param value - period end date
	 */
	public void setPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/period/endDate", value);}
	/**
	 * Sets the period start date
	 * @param value - period start date
	 */
	public void setPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/period/startDate", value);}
	

	//************************************
	//************************************
	//    Update Inventory Type Info  Node
	//************************************
	//************************************
	
	
	/**
	 * Sets the update inventory type info new duration
	 * @param value - update inventory type info new duration
	 */
	public void setUpdateInventoryTypeInfoNewDuration(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/updateInventoryTypeInfo/newDuration", value);}
	/**
	 * Sets the update inventory type info reservable resource ID
	 * @param value - update inventory type info reservable resource ID
	 */
	public void setUpdateInventoryTypeInfoReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/updateInventoryTypeInfo/newInventoryDetails/reservableResourceId", value);}
	/**
	 * Sets the update inventory type info new service start date
	 * @param value - update inventory type info new service start date
	 */
	public void setUpdateInventoryTypeInfoNewServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/updateInventoryTypeInfo/newServiceStartDate", value);}
	/**
	 * Sets the update inventory type info old duration
	 * @param value - update inventory type info old duration
	 */
	public void setUpdateInventoryTypeInfoOldDuration(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/updateInventoryTypeInfo/oldDuration", value);}
	/**
	 * Sets the update inventory type info old service start date
	 * @param value - update inventory type info old service start date
	 */
	public void setUpdateInventoryTypeInfoOldServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/updateInventoryTypeInfo/oldServiceStartDate", value);}
	
	//******************************
	//******************************
	//  Response values
	//******************************
	//******************************
	/**
	 * Gets the new Assignement Owner Id generated by the service
	 */
	public String getNewAssignmentOwnerId(){return getResponseNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEventsResponse/return/updateScheduleEventInventoryResponseDetails/assignmentOwnerId");}

	/**
	 * Gets the Travel Component Id returned by the service. 
	 * Should be the same id passed in the request
	 */
	public String getResponseTravelComponentId(){return getResponseNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEventsResponse/return/updateScheduleEventInventoryResponseDetails/tcId");}
	
	
}