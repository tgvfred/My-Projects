package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveDetailsByTravelPlanId extends TravelPlanSalesOrderService{
	Node accommBundleNode = null;
	Node accommSalesOrderItemGroup = null;
	NodeList nonAccommodationBundleNodes = null;
	Node creationHistoryNode = null;
	Node salesOrderFinancialTransactionsNode = null;
	Node salsesOrders = null;
	
	public RetrieveDetailsByTravelPlanId(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDetailsByTravelPlanId")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}	

	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanId/travelPlanId", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanId/businessContext/@communicationsChannel", value);}
	public void setDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanId/businessContext/@destination", value);}
	public void setPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanId/businessContext/@pointOfOrigin", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanId/businessContext/@salesChannel", value);}
	
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests
	 */
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/@id");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/guestName/@lastName");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses
	 */
	public String getGuestsPostalAddressAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressId");}
	public String getGuestsPostalAddressAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressLine1");}
	public String getGuestsPostalAddressAddressLine2(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@addressLine2");}
	public String getGuestsPostalAddressCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@city");}
	public String getGuestsPostalAddressCountry(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@country");}
	public String getGuestsPostalAddressRegionName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@regionName");}
	public String getGuestsPostalAddressStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@stateCode");}
	public String getGuestsPostalAddressZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/guests/postalAddresses/@zipCode");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders
	 */
	public String getSalesOrdersArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@arrivalDate");}
	public String getSalesOrdersCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@currencyCode");}
	public String getSalesOrdersDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@depatureDate");}
	public String getSalesOrdersId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@id");}
	public String getSalesOrdersPrimaryGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@primaryGuestReferenceId");}
	public String getSalesOrdersSourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@sourceAccountingCenterId");}
	public String getSalesOrdersStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/@status");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups
	 */
	public String getSalesOrdersSalesOrderItemGroupsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/@id");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses
	 */
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesBaseProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@baseProductId");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@code");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesDayCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@dayCount");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@description");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@endDate");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@id");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesPrice(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@price");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/@startDate");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest
	 */
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesSalesOrderItemGuestAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest/@ageType");}
	public String getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesSalesOrderItemGuestGuestRegerenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/gatedAdmissionDetailsResponses/salesOrderItemGuest/@guestReferenceId");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/businessContext
	 */
	public String getBusinessContextCommunicationChannel(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/businessContext/@communicationsChannel");}
	public String getBusinessContextDestination(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/businessContext/@destination");}
	public String getBusinessContextPointOfOrigin(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/businessContext/@pointOfOrigin");}
	public String getBusinessContextSalesChannel(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/businessContext/@salesChannel");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan
	 */
	public String getTravelPlanContactStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan/@contactStatus");}
	public String getTravelPlanEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan/@endDate");}
	public String getTravelPlanHasPlanning(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan/@hasPlanning");}
	public String getTravelPlanStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan/@startDate");}
	public String getTravelPlanTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/travelPlan/@travelPlanId");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/packageBundleDetails
	 */
		//***********************************
		//***********************************
		//		Accommodation Bundle
		//***********************************
		//***********************************
	private void setAccommodationBundleList(){accommBundleNode = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/accommodationDetailsResponse/../packageBundleDetails").item(0);}
	private String setAccommodationPackageBundleDetailsNodeReturnAttribute(String attribute){
		if(accommBundleNode == null) setAccommodationBundleList();
		return accommBundleNode.getAttributes().getNamedItem(attribute).getTextContent();
	}
	public NodeList getPackageBundleDetails(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/packageBundleDetails");}
	public String getAccommodationPackageBundleDetailsCode(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("code");}
	public String getAccommodationPackageBundleDetailsDescription(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("description");}
	public String getAccommodationPackageBundleDetailsEndDate(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("endDate");}
	public String getAccommodationPackageBundleDetailsId(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("id");}
	public String getAccommodationPackageBundleDetailsName(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("name");}
	public String getAccommodationPackageBundleDetailsRoomOnlyFlag(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("roomOnlyFlag");}
	public String getAccommodationPackageBundleDetailsSpecialReservationFlag(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("specialReservationFlag");}
	public String getAccommodationPackageBundleDetailsStartDate(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("startDate");}
	public String getAccommodationPackageBundleDetailsStatus(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("status");}
	public String getAccommodationPackageBundleDetailsWholesalePackageFlag(){return setAccommodationPackageBundleDetailsNodeReturnAttribute("wholesalerPackageFlag");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/packageBundleDetails/packageBundleItemsReferenceSets
	 */
	public String getAccommodationPackageBundleDetailsItemReferenceSetsOrderItemReferenceId(Node bundleNode){return accommBundleNode.getFirstChild().getAttributes().getNamedItem("orderItemReferenceId").getTextContent();}
		//***********************************
		//***********************************
		//		Non-Accommodation Bundle
		//***********************************
		//***********************************
	/**
	 * Grabs the salesOrders XML node in the response and isolates the non-accommodation sales order item node
	 */
	private void setNonAccommodationBundleList(){
		salsesOrders = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders").item(0);
		removeExtraneousNodesFromSalesOrdersNode();
		nonAccommodationBundleNodes = salsesOrders.getChildNodes();
	}
	/**
	 * Removes only those sales order item nodes that are not accommodation sales oreder item nodes
	 */
	private void removeExtraneousNodesFromSalesOrdersNode(){
		removeAccommodationBundleNode();
		removeCreationHistoryNode();
		removeSalesOrderFinancialTransactionsNode();
	}
	/**
	 * Removes the accommodation sales order item node
	 */
	private void removeAccommodationBundleNode(){
		try{
			if(accommSalesOrderItemGroup == null) getAccommSalesOrderItemGroup();	
			salsesOrders.removeChild(accommSalesOrderItemGroup);
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Grabs the accommodation sales order item node
	 */
	private void getAccommSalesOrderItemGroup(){
		try{accommSalesOrderItemGroup = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups/accommodationDetailsResponse/..").item(0);}
		catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Removes the creation history node
	 */	
	private void removeCreationHistoryNode(){
		try{
			if(creationHistoryNode == null) getCreationHistoryNode();
			salsesOrders.removeChild(creationHistoryNode);	
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Grabs the creation history node
	 */
	private void getCreationHistoryNode(){
		try{creationHistoryNode = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/creationHistory").item(0);}
		catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Removes the sales Order Financial Transactions node
	 */
	private void removeSalesOrderFinancialTransactionsNode(){
		try{
			if(salesOrderFinancialTransactionsNode == null) getSalesOrderFinancialTransactionsNode();	
			salsesOrders.removeChild(salesOrderFinancialTransactionsNode);	
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Grabs the sales Order Financial Transactions node
	 */
	private void getSalesOrderFinancialTransactionsNode(){
		try{salesOrderFinancialTransactionsNode = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderFinancialTransactions").item(0);}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	
	
	private String getNonAccommodationPackageBundleDetailsNodeReturnAttribute(String nodeName, String attribute){
		Node node = null;
		if(nonAccommodationBundleNodes == null) setNonAccommodationBundleList();
		
		switch (nodeName) {
		case "salesOrderItemGroups":
			node = nonAccommodationBundleNodes.item(0);
			break;
		case "packageBundleDetails":
			node = getNodeByNodeName(nonAccommodationBundleNodes.item(0), nodeName);
			break;
		case "salesOrderItemGuests": 
		case "salesOrderItemPriceDetail":
		case "componentDetails":
			node = getNodeByNodeName(nonAccommodationBundleNodes.item(0), "packageBundleDetails");
			node = getNodeByNodeName(node, nodeName);
			break;
		case "componentDetailsSalesOrderItemPriceDetail":
			node = getNodeByNodeName(nonAccommodationBundleNodes.item(0), "packageBundleDetails");
			node = getNodeByNodeName(node, "componentDetails");
			node = getNodeByNodeName(node, "salesOrderItemPriceDetail");
			break;
		default:
			break;
		}
		
		return node.getAttributes().getNamedItem(attribute).getTextContent();
	}
	
	private Node getNodeByNodeName(Node node, String nodeName){
		for(int i = 0; i < node.getChildNodes().getLength(); i++){
			if(node.getChildNodes().item(i).getNodeName().toLowerCase().contains(nodeName.toLowerCase())) return node.getChildNodes().item(i);
		}
		return null;
	}
	
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups[2]/packageBundleDetails
	 */
	public NodeList getNonAccommPackageBundleDetails(){return nonAccommodationBundleNodes;}	
	public String getNonAccommodationBundleSalesOrderId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemGroups", "id");}
	public String getNonAccommodationPackageBundleDetailsBookDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "bookDate");}
	public String getNonAccommodationPackageBundleDetailsCode(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "code");}
	public String getNonAccommodationPackageBundleDetailsDescription(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "description");}
	public String getNonAccommodationPackageBundleDetailsEndDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "endDate");}
	public String getNonAccommodationPackageBundleDetailsId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "id");}
	public String getNonAccommodationPackageBundleDetailsName(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "name");}
	public String getNonAccommodationPackageBundleDetailsPlannedEndDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "plannedEndDate");}
	public String getNonAccommodationPackageBundleDetailsPlannedStartDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "plannedStartDate");}
	public String getNonAccommodationPackageBundleDetailsRoomOnlyFlag(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "roomOnlyFlag");}
	public String getNonAccommodationPackageBundleDetailsSpecialReservationFlag(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "specialReservationFlag");}
	public String getNonAccommodationPackageBundleDetailsStartDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "startDate");}
	public String getNonAccommodationPackageBundleDetailsStatus(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("packageBundleDetails", "status");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups[2]/packageBundleDetails/salesOrderItemGuests
	 */
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemGuestsAgeType(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemGuests", "ageType");}
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemGuestsGuestReferenceId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemGuests", "guestReferenceId");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups[2]/packageBundleDetails/salesOrderItemPriceDetail
	 */
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailCurrencyCode(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemPriceDetail", "currencyCode");}
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailDiscount(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemPriceDetail", "discount");}
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailNet(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemPriceDetail", "net");}
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailPrice(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemPriceDetail", "price");}
	public String getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailTax(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("salesOrderItemPriceDetail", "tax");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups[2]/packageBundleDetails/componentDetails 
	 */
	public String getNonAccommodationPackageBundleDetailsComponentDetailsBookDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "bookDate");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsEndDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "endDate");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsEnterpriseProductId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "enterpriseProductId");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "id");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsName(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "name");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsProductId(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "productId");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsStartDate(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetails", "startDate");}
	/*
	 * /Envelope/Body/retrieveDetailsByTravelPlanIdResponse/retrieveTravelPlanResponse/salesOrders/salesOrderItemGroups[2]/packageBundleDetails/componentDetails/componentDetails
	 */
	public String getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailCurrencyCode(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetailsSalesOrderItemPriceDetail", "currencyCode");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailDiscount(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetailsSalesOrderItemPriceDetail", "discount");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailNet(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetailsSalesOrderItemPriceDetail", "net");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailPrice(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetailsSalesOrderItemPriceDetail", "price");}
	public String getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailTax(){return getNonAccommodationPackageBundleDetailsNodeReturnAttribute("componentDetailsSalesOrderItemPriceDetail", "tax");}
}
