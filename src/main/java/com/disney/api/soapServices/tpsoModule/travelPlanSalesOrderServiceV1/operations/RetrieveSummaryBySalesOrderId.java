package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveSummaryBySalesOrderId extends TravelPlanSalesOrderService{

	public RetrieveSummaryBySalesOrderId(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSummaryBySalesOrderId")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}	

	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderId/salesOrderId", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderId/businessContext/@communicationsChannel", value);}
	public void setDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderId/businessContext/@destination", value);}
	public void setPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderId/businessContext/@pointOfOrigin", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderId/businessContext/@salesChannel", value);}
	
	/*
	 * /Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests
	 */
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/@id");}
	public String getGuestsPreferredLanguage(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/@preferredLanguage");}
	/*
	 * /Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/guestName/@lastName");}
	/*
	 * /Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses
	 */
	public String getGuestsPostalAddressesAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@addressId");}
	public String getGuestsPostalAddressesAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@addressLine1");}
	public String getGuestsPostalAddressesAddressLine2(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@addressLine2");}
	public String getGuestsPostalAddressesAddressType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@addressType");}
	public String getGuestsPostalAddressesCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@city");}
	public String getGuestsPostalAddressesCountry(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@country");}
	public String getGuestsPostalAddressesDoNotMail(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@doNotMail");}
	public String getGuestsPostalAddressesPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@preferred");}
	public String getGuestsPostalAddressesRegionName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@regionName");}
	public String getGuestsPostalAddressesStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@stateCode");}
	public String getGuestsPostalAddressesZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/guests/postalAddresses/@zipCode");}
	/*
	 * /Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary
	 */	
	public String getSalesOrderSummaryArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@arrivalDate");}
	public String getSalesOrderSummaryCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@currencyCode");}
	public String getSalesOrderSummaryDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@depatureDate");}
	public String getSalesOrderSummaryGuaranteed(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@guaranteed");}
	public String getSalesOrderSummaryId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@id");}
	public String getSalesOrderSummaryPrimaryGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@primaryGuestReferenceId");}
	public String getSalesOrderSummarySourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@sourceAccountingCenterId");}
	public String getSalesOrderSummaryStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/@status");}
	/*
	 * 
	 */
	public String getSalesOrderSummaryCreationHistoryCreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/creationHistory/@createdBy");}
	public String getSalesOrderSummaryCreationHistoryCreationDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryBySalesOrderIdResponse/retrieveSummaryResponse/salesOrderSummary/creationHistory/@creationDate");}
}
