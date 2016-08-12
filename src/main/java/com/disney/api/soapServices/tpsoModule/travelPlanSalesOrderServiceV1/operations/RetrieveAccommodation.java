package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class RetrieveAccommodation extends TravelPlanSalesOrderService{
	private String environment; 
	private String database = "SALES";
	final String salesOrderItemType = "ACCOMMODATION";
	final String columnName = "SALES_ORDER_ITEM_TYPE";
	private String query = "select a.sls_ord SALES_ORDER, b.sls_ord_item_id SALES_ORDER_ITEM_ID, b.sls_ord_item_typ_nm "+columnName+" "
			+ "from sales_tp.sls_ord a join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord where a.tp_id={TP_ID} "
			+ "and b.sls_ord_item_typ_nm  = '"+salesOrderItemType+"'";
	private String[] accommodationIds;
	private int column;
	private Recordset resultSet = null;
	
	public RetrieveAccommodation(String environment, String scenario) {
		super(environment);
		this.environment = environment;
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAccommodation")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setAccommodationId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveAccommodation/accommodationId", value);}
	
	
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests
	 */
	public String getGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/@guestReferenceId");}
	public String getGuestsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/@id");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/guestName
	 */
	public String getGuestsGuestNameFirstName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/guestName/@firstName");}
	public String getGuestsGuestNameLastName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/guestName/@lastName");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/externalReferenceIds
	 */
	public String getGuestsExternalReferenceIdsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/externalReferenceIds/@id");}
	public String getGuestsExternalReferenceIdsType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/externalReferenceIds/@type");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses
	 */
	public String getGuestsPostalAddressesAddressId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@addressId");}
	public String getGuestsPostalAddressesAddressLine1(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@addressLine1");}
	public String getGuestsPostalAddressesAddressType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@addressType");}
	public String getGuestsPostalAddressesCity(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@city");}
	public String getGuestsPostalAddressesCountry(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@country");}
	public String getGuestsPostalAddressesDoNotMail(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@doNotMail");}
	public String getGuestsPostalAddressesPreferred(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@preferred");}
	public String getGuestsPostalAddressesRegionName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@regionName");}
	public String getGuestsPostalAddressesStateCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@stateCode");}
	public String getGuestsPostalAddressesZipCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/@zipCode");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/externalReferenceIds
	 */
	public String getGuestsPostalAddressExternalReferenceIdsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/externalReferenceIds/@id");}
	public String getGuestsPostalAddressExternalReferenceIdsType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/guests/postalAddresses[1]/externalReferenceIds/@type");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails
	 */
	public String getAccommodationDetailsArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@arrivalDate");}
	public String getAccommodationDetailsBookingDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@bookingDate");}
	public String getAccommodationDetailsDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@departureDate");}
	public String getAccommodationDetailsEnterpriseFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@enterpriseFacilityId");}
	public String getAccommodationDetailsFacilityName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@facilityName");}
	public String getAccommodationDetailsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@id");}
	public String getAccommodationDetailsLateCheckout(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@lateCheckOut");}
	public String getAccommodationDetailsResortCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@resortCode");}
	public String getAccommodationDetailsRoomTypeCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@roomTypeCode");}
	public String getAccommodationDetailsRoomTypeDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@roomTypeDescription");}
	public String getAccommodationDetailsSpecialneedsRequested(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@specialNeedsRequested");}
	public String getAccommodationDetailsStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/@status");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments
	 */
	public String getAccommodationDetailsSalesOrderItemCommentsConfidentialFlag(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments/@confidentialFlag");}
	public String getAccommodationDetailsSalesOrderItemCommentsFreeFormText(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments/@freeFormText");}
	public String getAccommodationDetailsSalesOrderItemCommentsGuestServiceRecordFlag(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments/@guestServicesRecordFlag");}
	public String getAccommodationDetailsSalesOrderItemCommentsId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments/@id");}
	public String getAccommodationDetailsSalesOrderItemCommentsType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemComments/@type");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/accommodationVacationClubRate
	 */
	public String getAccommodationDetailsAccommodationVacationClubRateType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/accommodationVacationClubRate/@type");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemGuests
	 */
	public String getAccommodationDetailsSalesOrderItemGuestsAgeType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemGuests/@ageType");}
	public String getAccommodationDetailsSalesOrderItemGuestsGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/accommodationDetails/salesOrderItemGuests/@guestReferenceId");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary
	 */
	public String getPackageSummaryCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@code");}
	public String getPackageSummaryDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@description");}
	public String getPackageSummaryId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@id");}
	public String getPackageSummaryPlanType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@planType");}
	public String getPackageSummaryProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@productId");}
	public String getPackageSummaryResortSpecialReservation(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@resortSpecialReservation");}
	public String getPackageSummaryRoomOnly(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@roomOnly");}
	public String getPackageSummaryWholesaler(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/packageSummary/@wholesaler");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary
	 */
	public String getSalesOrderSummaryArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@arrivalDate");}
	public String getSalesOrderSummaryContactName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@contactName");}
	public String getSalesOrderSummaryCurrencyCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@currencyCode");}
	public String getSalesOrderSummaryDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@depatureDate");}
	public String getSalesOrderSummaryGuaranteed(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@guaranteed");}
	public String getSalesOrderSummaryId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@id");}
	public String getSalesOrderSummaryPrimaryGuestReferenceId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@primaryGuestReferenceId");}
	public String getSalesOrderSummarySourceAccountingCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@sourceAccountingCenterId");}
	public String getSalesOrderSummaryStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@status");}
	public String getSalesOrderSummaryVipLevel(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/@vipLevel");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory
	 */
	public String getSalesOrderSummaryCreationHistoryCreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/@createdBy");}
	public String getSalesOrderSummaryCreationHistoryCreationDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/@creationDate");}
	/*
	 * /Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/modificationHistories
	 */
	public String getSalesOrderSummaryCreationHistoryModificationHistoriesType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/modificationHistories/@type");}
	public String getSalesOrderSummaryCreationHistoryModificationHistoriesUpdatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/modificationHistories/@updatedBy");}
	public String getSalesOrderSummaryCreationHistoryModificationHistoriesUpdatedDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveAccommodationResponse/retrieveAccommodationResponse/salesOrderSummary/creationHistory/modificationHistories/@updatedDate");}	

	
	public void retrieveAccommodationSalesOrderItemIds(String TP_ID){
		query = query.replace("{TP_ID}", TP_ID);
		OracleDatabase odb = new OracleDatabase(environment, database);
		resultSet = new Recordset(odb.getResultSet(query));
		resultSet.print();
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "The sales order item query for TP_ID ["+TP_ID+"] did not return any records in ["+environment+"].");
		TestReporter.log("Record Count: " + String.valueOf(resultSet.getRowCount()));
		accommodationIds = new String[resultSet.getRowCount()];
		//Capture the accommodation sales item ids
		column = resultSet.getColumnIndex(columnName);
		for(int record = 1; record <= resultSet.getRowCount(); record++) 
			accommodationIds[record - 1] = resultSet.getValue(column - 1, record);
	}
	
	public String[] getAccommodationSalesOrderItemIds(){return accommodationIds;}
	
//	@Test
//	public static void test(){
//		RetrieveAccommodation retAcc = new RetrieveAccommodation("Sleepy", "Main");
//		retAcc.retrieveAccommodationSalesOrderItemIds("461349728321");
//	}
}