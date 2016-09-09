package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.create;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.RetreiveResponse;
import com.disney.test.utils.Randomness;

@SuppressWarnings("unused")

public class TestCreate {
private String environment;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
	this.environment = environment;
	//this.environment = "Bashful";
}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 */
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "create"})
	public void testcreate_SingleChargeAcct(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Create new request
		CreateRequest request = new CreateRequest();
		// Charge Account Type	
		request.getChargeAccountRequests().get(0).setChargeAccountType("GUEST_ACCOUNT");
		//Complete the Charge Account Common Request 
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setActive("true");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setDescription("From Booking");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setTxnFacilityId("80010401");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setSrcAcctCenterId("2");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setDefaultFolioRequired("false");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setFirstName("Marisol");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setLastName("Centeno");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setTxnGuestId("0");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).setReferenceName("SWID");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).getReferenceValue();	
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setActive("true");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodName("Visa");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodTypeName("CreditCard");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setIsSubAccountPaymentMethod("false");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setChargingPrivilegesIndicator("false");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setCampusId("1");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setReservationTxnGuestId("238431649");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setKttwNumber("991946168311680202");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCardStatus("Valid");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCreditCardNumber("xxxxxxxxxxxx7840");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCvvNumber("423");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setName("Marisol Centeno");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setAddressLineOne("3395 NE 9th Dr");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCity("Homestead");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setPostalCode("33033");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCountry("US");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setState("FL");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationMonth("6");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationYear("20");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumber("423730003693");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumberKey("HGBkD8");
		
		
        RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
        TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
        CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse){
			 System.out.println(chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId());
		}
    //End single test case    	
	}
	
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "create"})
	public void testcreate_TwoChargeAcct(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Create new request
		CreateRequest request = new CreateRequest();
		// Charge Account Type	
		request.getChargeAccountRequests().get(0).setChargeAccountType("GUEST_ACCOUNT");
		//Complete the Charge Account Common Request 
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setActive("true");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().setDescription("From Booking");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setFirstName("Marisol");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setLastName("Centeno");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setTxnGuestId("0");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).setReferenceName("SWID");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).getReferenceValue();
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setActive("true");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodName("Visa");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodTypeName("CreditCard");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setIsSubAccountPaymentMethod("false");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setChargingPrivilegesIndicator("false");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setCampusId("1");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setReservationTxnGuestId("238431649");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setKttwNumber("991946168311680202");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCardStatus("Valid");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCreditCardNumber("xxxxxxxxxxxx7840");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCvvNumber("423");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setName("Marisol Centeno");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setAddressLineOne("3395 NE 9th Dr");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCity("Homestead");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setPostalCode("33033");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCountry("US");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setState("FL");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationMonth("6");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationYear("20");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumber("423730003693");
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumberKey("HGBkD8");
		// Charge Account Type	
		request.addChargeAccountRequest();
		request.getChargeAccountRequests().get(1).setChargeAccountType("GUEST_ACCOUNT");
		//Complete the Charge Account Common Request 
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().setActive("true");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().setDescription("From Booking");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getPeriod().setEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setFirstName("Michael");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setLastName("Centeno");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setTxnGuestId("0");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).setReferenceName("SWID");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).getReferenceValue();	
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setActive("true");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodName("Visa");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodTypeName("CreditCard");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodStartDate(Randomness.generateCurrentXMLDatetime(10)+"-04:00");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setPaymentMethodEndDate(Randomness.generateCurrentXMLDatetime(15)+"-04:00");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setIsSubAccountPaymentMethod("false");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).setChargingPrivilegesIndicator("false");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCardStatus("Valid");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCreditCardNumber("xxxxxxxxxxxx7840");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setCvvNumber("423");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().setName("Michael Centeno");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setCampusId("1");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setReservationTxnGuestId("238431649");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setKttwNumber("991946168311680202");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setAddressLineOne("3395 NE 9th Dr");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCity("Homestead");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setPostalCode("33033");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setCountry("US");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getAddress().setState("FL");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationMonth("6");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setExpirationYear("20");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumber("423730003693");
		request.getChargeAccountRequests().get(1).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getCardDetailTO().getCardAuthorizationDetailTO().setRetrievalReferenceNumberKey("HGBkD8");
		
        RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
        TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
        CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse){
			 System.out.println(chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId());
		}
    //End single test case    	
	}
}
