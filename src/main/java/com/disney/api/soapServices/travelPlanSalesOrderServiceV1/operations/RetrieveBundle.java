package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveBundle extends TravelPlanSalesOrderService{

	public RetrieveBundle(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveBundle")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	//*****************************************
	//*****************************************
	//		Request Methods
	//*****************************************
	//*****************************************
	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/salesOrderId", value);}
	public void setPackageBundleId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/packageBundleId", value);}
	public void setBusinessContextCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/businessContext/@communicationsChannel", value);}
	public void setBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/businessContext/@destination", value);}
	public void setBusinessContextPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/businessContext/@pointOfOrigin", value);}
	public void setBusinessContextSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveBundle/businessContext/@salesChannel", value);}	
	//*****************************************
	//*****************************************
	//		Response Methods
	//*****************************************
	//*****************************************
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse
	 */
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/@travelPlanId");}
	/*
	 * 
	 */
	public String getSalesOrderSummaryDetailsArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@arrivalDate");}
	public String getSalesOrderSummaryDetailsContactName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@contactName");}
	public String getSalesOrderSummaryDetailsCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@currencyCode");}
	public String getSalesOrderSummaryDetailsDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@depatureDate");}
	public String getSalesOrderSummaryDetailsGuaranteed(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@guaranteed");}
	public String getSalesOrderSummaryDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@id");}
	public String getSalesOrderSummaryDetailsPrimaryGuestResferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@primaryGuestReferenceId");}
	public String getSalesOrderSummaryDetailsSourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@sourceAccountingCenterId");}
	public String getSalesOrderSummaryDetailsStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@status");}
	public String getSalesOrderSummaryDetailsVipLevel(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/@vipLevel");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/creationHistory
	 */
	public String getSalesOrderSummaryDetailsCreationHistoryCreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/creationHistory/@createdBy");}
	public String getSalesOrderSummaryDetailsCreationHistoryCreationDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/creationHistory/@creationDate");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/creationHistory/modificationHistories
	 */
	public NodeList getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesNodes(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/salesOrderSummaryDetails/creationHistory/modificationHistories");}
	public Node getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesNodeByIndex(int index){return getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesNodes().item(index);}
	public Node getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesNodeByIndex(String index){return getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesNodeByIndex(Integer.valueOf(index));}
	public String getSalesOrderSummaryDetailsCreationHistoryModificationHistoriesAttributeValues(Node node, String attribute){return node.getAttributes().getNamedItem(attribute).getTextContent();}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests
	 */
	public String getGuestsAtLeastThisAge(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@atLeastThisAge");}
	public String getGuestsDoNotContact(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@doNotContact");}
	public String getGuestsDoNotEmail(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@doNotEmail");}
	public String getGuestsDoNotPhone(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@doNotPhone");}
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@id");}
	public String getGuestsPreferredLanguage(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/@preferredLanguage");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/guestName/@lastName");}
	public String getGuestsGuestNamePrefix(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/guestName/@prefix");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/externalReferenceIds
	 */
	public String getGuestsExternalReferenceIdsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/externalReferenceIds/@id");}
	public String getGuestsExternalReferenceIdsType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/externalReferenceIds/@type");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses
	 */
	public String getGuestsPostalAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@addressLine1");}
	public String getGuestsPostalAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@addressId");}
	public String getGuestsPostalAddressType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@addressType");}
	public String getGuestsPostalAddressCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@city");}
	public String getGuestsPostalAddressPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@preferred");}
	public String getGuestsPostalAddressStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@stateCode");}
	public String getGuestsPostalAddressZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/postalAddresses/@zipCode");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses
	 */
	public String getGuestsTelecomAddressesExtension(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@extension");}
	public String getGuestsTelecomAddressesNumber(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@number");}
	public String getGuestsTelecomAddressesPhoneId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@phoneId");}
	public String getGuestsTelecomAddressesPhoneTechType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@phoneTechType");}
	public String getGuestsTelecomAddressesPhoneUseType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@phoneUseType");}
	public String getGuestsTelecomAddressesPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/telecomAddresses/@preferred");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/emailAddresses
	 */
	public String getGuestsEmailAddressesEmailAddress(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/emailAddresses/@emailAddress");}
	public String getGuestsEmailAddressesEmailId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/emailAddresses/@emailId");}
	public String getGuestsEmailAddressesPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/guests/emailAddresses/@preferred");}
	/*
	 * 
	 */
	public String getPackageBundleDetailsBookDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@bookDate");}
	public String getPackageBundleDetailsCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@code");}
	public String getPackageBundleDetailsDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@description");}
	public String getPackageBundleDetailsEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@endDate");}
	public String getPackageBundleDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@id");}
	public String getPackageBundleDetailsName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@name");}
	public String getPackageBundleDetailsPlannedEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@plannedEndDate");}
	public String getPackageBundleDetailsPlannedStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@plannedStartDate");}
	public String getPackageBundleDetailsRoomOnlyFlag(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@roomOnlyFlag");}
	public String getPackageBundleDetailsSpecialReservationFlag(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@specialReservationFlag");}
	public String getPackageBundleDetailsStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@startDate");}
	public String getPackageBundleDetailsStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/@status");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemGuests
	 */
	public String getPackageBundleDetailsSalesOrderItemGuestsAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemGuests/@ageType");}
	public String getPackageBundleDetailsSalesOrderItemGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemGuests/@guestReferenceId");}
	/*
	 * 
	 */
	public String getPackageBundleDetailsSalesOrderItemPriceDetailCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemPriceDetail/@currencyCode");}
	public String getPackageBundleDetailsSalesOrderItemPriceDetailDiscount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemPriceDetail/@discount");}
	public String getPackageBundleDetailsSalesOrderItemPriceDetailNet(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemPriceDetail/@net");}
	public String getPackageBundleDetailsSalesOrderItemPriceDetailPrice(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemPriceDetail/@price");}
	public String getPackageBundleDetailsSalesOrderItemPriceDetailTax(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/salesOrderItemPriceDetail/@tax");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails
	 */
	public String getPackageBundleDetailsComponentDetailsBookDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@bookDate");}
	public String getPackageBundleDetailsComponentDetailsEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@endDate");}
	public String getPackageBundleDetailsComponentDetailsEnterpriseId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@enterpriseProductId");}
	public String getPackageBundleDetailsComponentDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@id");}
	public String getPackageBundleDetailsComponentDetailsName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@name");}
	public String getPackageBundleDetailsComponentDetailsProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@productId");}
	public String getPackageBundleDetailsComponentDetailsStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/@startDate");}
	/*
	 * /Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail
	 */
	public String getPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail/@currencyCode");}
	public String getPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailDiscount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail/@discount");}
	public String getPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailNet(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail/@net");}
	public String getPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailPrice(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail/@price");}
	public String getPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetail(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveBundleResponse/retrieveBundleResponse/packageBundleDetails/componentDetails/salesOrderItemPriceDetail/@tax");}
}
