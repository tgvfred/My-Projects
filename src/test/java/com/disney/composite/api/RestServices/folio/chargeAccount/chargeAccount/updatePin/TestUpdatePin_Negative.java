package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.updatePin;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.RetreiveResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request.UpdatePinRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.response.UpdatePinResponse;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestUpdatePin_Negative {
private String environment = "Bashful";	
private String caChargeAccount;
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//this.environment = environment;
		this.environment = "Bashful";
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
		CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse){
			caChargeAccount = chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId();
		}
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
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_SequesntialPinNumber()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("1234");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number cannot consist of sequential digits."), "Update Pin status of [Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number cannot consist of sequential digits.] was received");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_4DigitsSamePinNumber()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId(caChargeAccount);
		//Update value for Pin
		request.setPinNumber("1111");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : All four digits of the Pin Number cannot be same."), "Update Pin status of [Pin must be 4 nonsequential digits in length and all digits cannot be the same : All four digits of the Pin Number cannot be same.] was received");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_NoPinNumber()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Pin number and ChargeAccount Ids to be updated are mandatory fields"), "Invalid input fields. : Pin number and ChargeAccount Ids to be updated are mandatory fields] was received");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_InvaidPinNumberLength_1Digit()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("1");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length."), "Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length.");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_InvaidPinNumberLength_2Digits()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("19");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length."), "Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length.");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_InvaidPinNumberLength_3Digits()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("196");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length."), "Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length.");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_InvaidPinNumberLength_5Digits()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("19638");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length."), "Pin must be 4 nonsequential digits in length and all digits cannot be the same : Pin Number should be four digits in length.");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testupdatePin_Negative_NoChargeAcct()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("");
		//Update value for Pin
		request.setPinNumber("6175");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Charge account not found. : For ChageAccount Id = null ChargeAccountGuestExternalReferenceName: null ChargeAccountGuestExternalReferenceValue: null"), "Update Pin status of [Charge account not found. : For ChageAccount Id = null ChargeAccountGuestExternalReferenceName: null ChargeAccountGuestExternalReferenceValue: null] was received");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_InvalidChargeAcct()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("9999");
		//Update value for Pin
		request.setPinNumber("6175");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
	TestReporter.assertTrue(response.getResponse().contains("Charge account not found. : For ChageAccount Id = 9999 ChargeAccountGuestExternalReferenceName: null ChargeAccountGuestExternalReferenceValue: null"), "Update Pin status of [Charge account not found. : For ChageAccount Id = 9999 ChargeAccountGuestExternalReferenceName: null ChargeAccountGuestExternalReferenceValue: null] was received");
		
	}
	
	@Test(groups={"api","rest", "regression", "negative","folio", "chargeAccountV2", "updatePin"})
	public void testUpdatePin_Negative_NoAuthorization()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("1694");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequestWithMissingAuthToken(request);
	TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");	
	}
}
