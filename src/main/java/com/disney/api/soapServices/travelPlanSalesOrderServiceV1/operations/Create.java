package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class Create extends TravelPlanSalesOrderService{

	public Create(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("create")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
//		System.out.println(getRequest());
//		System.out.println();
	}
	
	//*************************************************************
	//*************************************************************
	//*************************************************************
	//				REQUEST METHODS
	//*************************************************************
	//*************************************************************
	//*************************************************************
	/*
	 * TravelPlanRequest
	 */
	public void setTravelPlanRequestStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/travelPlanRequest/@startDate", value);}
	public void setTravelPlanRequestEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/travelPlanRequest/@startDate", value);}
	public String getTravelPlanRequestStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/travelPlanRequest/@startDate");}
	public String getTravelPlanRequestEndDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/travelPlanRequest/@startDate");}
	
	/*
	 * SalesOrders
	 */
	public void setSalesOrderStatus(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@status", value);}
	public void setSalesOrderSourceAccountingCenterId(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@sourceAccountingCenterId", value);}
	public void setSalesOrderArrivalDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@arrivalDate", value);}
	public void setSalesOrderDepartureDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@depatureDate", value);}
	public void setSalesOrderLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@locationId", value);}	
	public void setSalesOrderFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@enterpriseFacilityId", value);}	
	public String getSalesOrderStatus(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@status");}
	public String getSalesOrderSourceAccountingCenterId(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@sourceAccountingCenterId");}
	public String getSalesOrderArrivalDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@arrivalDate");}
	public String getSalesOrderDepartureDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/@depatureDate");}
	public String getSalesOrderLocationId(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@locationId");}	
	public String getSalesOrderFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@enterpriseFacilityId");}	
	
	/*
	 * SalesOrders/GatedAdmissionRequests
	 */
	public void setSalesOrderGatedAdmissionRequestsCode(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@code", value);}
	public void setSalesOrderGatedAdmissionRequestsStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@startDate", value);}
	public void setSalesOrderGatedAdmissionRequestsEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@endDate", value);}
	public void setSalesOrderGatedAdmissionRequestsEnterpriseFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@enterpriseFacilityId", value);}
	public void setSalesOrderGatedAdmissionRequestsLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@locationId", value);}
	public String getSalesOrderGatedAdmissionRequestsCode(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@code");}
	public String getSalesOrderGatedAdmissionRequestsStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@startDate");}
	public String getSalesOrderGatedAdmissionRequestsEndDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@endDate");}
	public String getSalesOrderGatedAdmissionRequestsEnterpriseFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@enterpriseFacilityId");}
	public String getSalesOrderGatedAdmissionRequestsLocationId(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/@locationId");}
	
	/*
	 * SalesOrders/GatedAdmissionRequests/salesOrderItemComments
	 */
	public void setSalesOrderGatedAdmissionRequestsSalesOrderItemCommentsRequestInactiveDate(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/salesOrderItemComments/@requestInactiveDate", value);}
	public String getSalesOrderGatedAdmissionRequestsSalesOrderItemCommentsRequestInactiveDate(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/salesOrderItemComments/@requestInactiveDate");}
	
	/*
	 * SalesOrders/GatedAdmissionRequests/salesOrderItemReasons
	 */
	public void setSalesOrderGatedAdmissionRequestsSalesOrderItemReasonsType(String value){setRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/salesOrderItemReasons/@type", value);}
	public String getSalesOrderGatedAdmissionRequestsSalesOrderItemReasonsType(){return getRequestNodeValueByXPath("/Envelope/Body/create/salesOrders/gatedAdmissionRequests/salesOrderItemReasons/@type");}	

	/*
	 * Guests
	 */
	public void setGuestReferenceid(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/@guestReferenceId", value);}
	public void setGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/guestName/@firstName", value);}
	public void setGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/guestName/@lastName", value);}
	public void setGuestAddressLine1(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@addressLine1", value);}
	public void setGuestAddressCity(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@city", value);}
	public void setGuestAddressStateCode(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@stateCode", value);}
	public void setGuestAddressRegionName(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@regionName", value);}
	public void setGuestAddressZipCode(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@zipCode", value);}
	public void setGuestAddressCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@country", value);}
	public String getGuestReferenceid(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/@guestReferenceId");}
	public String getGuestFirstName(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/guestName/@firstName");}
	public String getGuestLastName(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/guestName/@lastName");}
	public String getGuestAddressLine1(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@addressLine1");}
	public String getGuestAddressCity(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@city");}
	public String getGuestAddressStateCode(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@stateCode");}
	public String getGuestAddressRegionName(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@regionName");}
	public String getGuestAddressZipCode(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@zipCode");}
	public String getGuestAddressCountry(){return getRequestNodeValueByXPath("/Envelope/Body/create/guests/postalAddresses/@country");}
	
	/*
	 * BusinessContext
	 */
	public void setBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@destination", value);}
	public void setBusinessContextSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@salesChannel", value);}
	public void setBusinessContextCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@communicationsChannel", value);}
	public void setBusinessContextPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@pointOfOrigin", value);}
	public String getBusinessContextDestination(){return getRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@destination");}
	public String getBusinessContextSalesChannel(){return getRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@salesChannel");}
	public String getBusinessContextCommunicationChannel(){return getRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@communicationsChannel");}
	public String getBusinessContextPointOfOrigin(){return getRequestNodeValueByXPath("/Envelope/Body/create/businessContext/@pointOfOrigin");}
	
	//*************************************************************
	//*************************************************************
	//*************************************************************
	//				RESPONSE METHODS
	//*************************************************************
	//*************************************************************
	//*************************************************************
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createResponse/@travelPlanId");}	
	public String getTravelPlanSegmentId(){return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createResponse/requestReferences/@id");}
	public String getSalesOrderIdId(){return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createResponse/requestReferences/createSalesOrderResponse/@salesOrderId");}
}
