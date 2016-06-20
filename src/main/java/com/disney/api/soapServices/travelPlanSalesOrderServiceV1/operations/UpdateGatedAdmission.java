package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class UpdateGatedAdmission extends TravelPlanSalesOrderService{

	public UpdateGatedAdmission(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateGatedAdmission")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		removeComments() ;
		removeWhiteSpace();
	}
	
	//**********************************************
	//**********************************************
	//**********************************************
	//		Request Methods
	//**********************************************
	//**********************************************
	//**********************************************
	/*
	 * /Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests
	 */
	public void setGatedAdmissionUpdateRequestsId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@id", value);}
	public void setGatedAdmissionUpdateRequestsStatus(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@status", value);}
	public String getGatedAdmissionUpdateRequestsStatus(){return getRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@status");}
	public void setGatedAdmissionUpdateRequestsCode(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@code", value);}
	public void setGatedAdmissionUpdateRequestsDescription(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@description", value);}
	public void setGatedAdmissionUpdateRequestsStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@startDate", value);}
	public void setGatedAdmissionUpdateRequestsEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@endDate", value);}
	public void setGatedAdmissionUpdateRequestsPrice(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@price", value);}
	public void setGatedAdmissionUpdateRequestsGuestMagicPlusEligibility(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@guestMagicPlusEligibility", value);}
	public void setGatedAdmissionUpdateRequestsEnterpriseFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@enterpriseFacilityId", value);}
	public void setGatedAdmissionUpdateRequestsLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@locationId", value);}
	public void setGatedAdmissionUpdateRequestsIsElectronicEntitlement(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/@isElectronicEntitlement", value);}
	/*
	 * /Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/salesOrderItemGuest
	 */
	public void setSalesOrderItemGuestGuestReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/salesOrderItemGuest/@guestReferenceId", value);}
	public void setSalesOrderItemGuestAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/salesOrderItemGuest/@ageType", value);}
	/*
	 * /Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/creationHistory
	 */
	public void setCreationHistoryCreatedBy(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/creationHistory/@createdBy", value);}
	public void setCreationHistoryCreationDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/gatedAdmissionUpdateRequests/creationHistory/@creationDate", value);}
	/*
	 * /Envelope/Body/updateGatedAdmission/guests
	 */
	public void setGuestsId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/@id", value);}
	public void setGuestsGuestReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/@guestReferenceId", value);}
	public void setGuestsAtLeastThisAge(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/@atLeastThisAge", value);}
	public void setGuestsDoNotMail(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/@doNotMail", value);}
	public void setGuestsDoNotPhone(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/@doNotPhone", value);}
	/*
	 * /Envelope/Body/updateGatedAdmission/guests/guestName
	 */
	public void setGuestsGuestNameFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/guestName/@firstName", value);}
	public void setGuestsGuestNameLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/guests/guestName/@lastName", value);}
	/*
	 * /Envelope/Body/updateGatedAdmission/businessContext
	 */
	public void setBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/businessContext/@destination", value);}
	public void setBusinessContextSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/businessContext/@salesChannel", value);}
	public void setBusinessContextCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/businessContext/@communicationsChannel", value);}
	public void setBusinessContextPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/updateGatedAdmission/businessContext/@pointOfOrigin", value);}
	
	
	//**********************************************
	//**********************************************
	//**********************************************
	//		Response Methods
	//**********************************************
	//**********************************************
	//**********************************************
	/*
	 * /Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails
	 */
	public String getSalesOrderId(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/@salesOrderId");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/@travelPlanId");}
	/*
	 * /Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/gatedAdmissionDetails
	 */
	public String getGatedAdmissionDetailsDayCount(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/gatedAdmissionDetails/@dayCount");}
	public String getGatedAdmissionDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/gatedAdmissionDetails/@id");}
	public String getGatedAdmissionDetailsPartOfPackage(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/gatedAdmissionDetails/@partOfPackage");}
	public String getGatedAdmissionDetails(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/gatedAdmissionDetails/@price");}
	/*
	 * /Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/salesOrderItemGuests
	 */
	public String getExperienceMediasSalesOrderItemGuestsAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/salesOrderItemGuests/@ageType");}
	public String getExperienceMediasSalesOrderItemGuestsFulfillmentStatus(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/salesOrderItemGuests/@fulfillmentStatus");}
	public String getExperienceMediasSalesOrderItemGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/salesOrderItemGuests/@guestReferenceId");}
	/*
	 * /Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/magStripeMediaReference
	 */
	public String getExperienceMediasMagStripeMediaReferenceChargingPriviledges(){return getResponseNodeValueByXPath("/Envelope/Body/updateGatedAdmissionResponse/updateGatedAdmissionResponse/updateGatedAdmissionDetails/experienceMedias/magStripeMediaReference/@chargingPrivileges");}
}
