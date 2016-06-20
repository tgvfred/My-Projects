package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveGatedAdmission extends TravelPlanSalesOrderService{

	public RetrieveGatedAdmission(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGatedAdmission")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setGatedAdmissionId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveGatedAdmission/gatedAdmissionId", value);}
	public void setGatedAdmissionBusinessContextCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveGatedAdmission/businessContext/@communicationsChannel", value);}
	public void setGatedAdmissionBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveGatedAdmission/businessContext/@destination", value);}
	public void setGatedAdmissionBusinessContextSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveGatedAdmission/businessContext/@salesChannel", value);}
	
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests
	 */
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/@id");}
	public String getGuestsPreferredLanguage(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/@preferredLanguage");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/guestName/@lastName");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/guestName/postalAddresses
	 */
	public String getGuestsPostalAddressesAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@addressId");}
	public String getGuestsPostalAddressesAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@addressLine1");}
	public String getGuestsPostalAddressesAddressLine2(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@addressLine2");}
	public String getGuestsPostalAddressesAddressType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@addressType");}
	public String getGuestsPostalAddressesCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@city");}
	public String getGuestsPostalAddressesCountry(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@country");}
	public String getGuestsPostalAddressesDoNotMail(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@doNotMail");}
	public String getGuestsPostalAddressesPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@preferred");}
	public String getGuestsPostalAddressesRegionName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@regionName");}
	public String getGuestsPostalAddressesStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@stateCode");}
	public String getGuestsPostalAddressesZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/guests/postalAddresses/@zipCode");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails
	 */
	public String getGatedAdmissionDetailsAdultTicket(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@adultTicket");}
	public String getGatedAdmissionDetailsBaseProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@baseProductId");}
	public String getGatedAdmissionDetailsCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@code");}
	public String getGatedAdmissionDetailsDayCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@dayCount");}
	public String getGatedAdmissionDetailsDesciption(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@description");}
	public String getGatedAdmissionDetailsEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@endDate");}
	public String getGatedAdmissionDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@id");}
	public String getGatedAdmissionDetailsPrice(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@price");}
	public String getGatedAdmissionDetailsSelectable(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@selectable");}
	public String getGatedAdmissionDetailsStartDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@startDate");}
	public String getGatedAdmissionDetailsStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/@status");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/salesOrderItemGuest
	 */
	public String getGatedAdmissionDetailsSalesOrderItemGuestAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/salesOrderItemGuest/@ageType");}
	public String getGatedAdmissionDetailsSalesOrderItemGuestGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails/salesOrderItemGuest/@guestReferenceId");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary
	 */
	public String getSalesOrderSummaryArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@arrivalDate");}
	public String getSalesOrderSummaryCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@currencyCode");}
	public String getSalesOrderSummaryDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@depatureDate");}
	public String getSalesOrderSummaryGuaranteed(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@guaranteed");}
	public String getSalesOrderSummaryId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@id");}
	public String getSalesOrderSummaryPrimaryGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@primaryGuestReferenceId");}
	public String getSalesOrderSummarySourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@sourceAccountingCenterId");}
	public String getSalesOrderSummaryStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/@status");}
	/*
	 * /Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/creationHistory
	 */
	public String getSalesOrderSummaryCreationHistoryCreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/creationHistory/@createdBy");}
	public String getSalesOrderSummaryCreationHistoryCreationDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/salesOrderSummary/creationHistory/@creationDate");}
}
