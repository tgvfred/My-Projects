package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveDetailsBySalesOrderId extends TravelPlanSalesOrderService{

	public RetrieveDetailsBySalesOrderId(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDetailsBySalesOrderId")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}	

	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderId/salesOrderId", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderId/businessContext/@communicationsChannel", value);}
	public void setDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderId/businessContext/@destination", value);}
	public void setPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderId/businessContext/@pointOfOrigin", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderId/businessContext/@salesChannel", value);}
	
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests
	 */
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/@id");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/guestName/@lastName");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses
	 */
	public String getGuestsPostalAddressAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressId");}
	public String getGuestsPostalAddressAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressLine1");}
	public String getGuestsPostalAddressAddressLine2(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressLine2");}
	public String getGuestsPostalAddressCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@city");}
	public String getGuestsPostalAddressRegionName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@regionName");}
	public String getGuestsPostalAddressStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@stateCode");}
	public String getGuestsPostalAddressZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@zipCode");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders
	 */
	public String getSalesOrdersArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@arrivalDate");}
	public String getSalesOrdersCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@currencyCode");}
	public String getSalesOrdersDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@depatureDate");}
	public String getSalesOrdersId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@id");}
	public String getSalesOrdersPrimaryGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@primaryGuestReferenceId");}
	public String getSalesOrdersSourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@sourceAccountingCenterId");}
	public String getSalesOrdersStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/@status");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups
	 */
	public String getSalesOrdersSalesOrderItemGroupsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/@id");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses
	 */
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesBaseProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@baseProductId");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@code");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesDayCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@dayCount");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@description");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@endDate");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@id");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesPrice(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@price");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@startDate");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest
	 */
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesSalesOrderItemGuestAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest/@ageType");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesSalesOrderItemGuestGuestRegerenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest/@guestReferenceId");}
	/*
	 * /Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/travelPlan
	 */
	public String getTravelPlanContactStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/travelPlan/@contactStatus");}
	public String getTravelPlanEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/travelPlan/@endDate");}
	public String getTravelPlanStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/travelPlan/@startDate");}
	public String getTravelPlanTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsBySalesOrderIdResponse/retrieveTravelPlanResponse/travelPlan/@travelPlanId");}
}
