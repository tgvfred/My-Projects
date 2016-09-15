package com.disney.composite.api.RestServices.folio.paymentv2.foliopaymentV2;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.EstablishSettlementMethodRequest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.GenerateCard;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestEstablishSettlementMethod_Negative {
	private String environment = "Bashful";
	private String TPId;
	private String TPSId;
	private String LastName;
	private String ccNum;
	private String ExpMonth;
	private String ExpYear;
	private String Expiration;
	private String CVV2;
	private String NameOnCard;

		/**
		 * This will always be used as is. TestNG will pass in the Environment used
		 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
		 */
		
		@BeforeMethod(alwaysRun = true)
		@Parameters({  "environment" })
		public void setup(@Optional String environment) {
			//this.environment = environment;
			this.environment = "Bashful";
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			//generate accommodation booking
			Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
			book.sendRequest();
			TPId = book.getTravelPlanId();
			TPSId = book.getTravelPlanSegmentId();
			LastName = book.getPrimaryGuestLastName();
			//Generate Credit Card
			GenerateCard card = new GenerateCard();
	        Map<String, String> cardInfo = null;
			try {cardInfo = card.getCardInfo("Approved", "0", card.getCradTypeEnum("VISA"));}
	        catch (Exception e) {
//	            TestReporter.assertNotNull(cardInfo, "An error occurred retrieving a valid card. "
//	                    + "\nSTATUS: " + status
//	                    + "\nDELAY:  " + delay
//	                    + "\nMETHOD: " + getCardPaymentMethod()
//	                    + "\nStacktrace: \n" + e.getMessage());
	        }
			ccNum = cardInfo.get("AccountNumber").replace("-", "");
			ExpMonth = cardInfo.get("ExpMonth");
			ExpYear = cardInfo.get("ExpYear");
			Expiration = ExpMonth+"/"+ExpYear;
			CVV2 = cardInfo.get("CVV2");
			NameOnCard = cardInfo.get("NameOnCard");
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
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoAuthorization () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber("4012000033330026");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber("367");
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName("veg");
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequestWithMissingAuthToken(request);
			TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoCreditCardNumber () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber("");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber("CVV2");
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("DREAMS_TP");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue(TPId);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("Credit Card Number is not Valid : null"), "Proper error message of Credit Card Number is not Valid : null is received");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_InvalidCVV2 () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber("1234");
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("DREAMS_TP");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue(TPId);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("Credit Card was not approved : CVV2/CVC2 Mismatch"), "Proper error message of Credit Card was not approved : CVV2/CVC2 Mismatch");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoExpiration () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate("");
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("DREAMS_TP");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue(TPId);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("General Error"), "Proper error message of General Error is received");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoExternalReferenceSource () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue(TPId);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("No External References found : Reference Value: "), "No External References found : Reference Value: ");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoExternalReferenceValue () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("For input string:"), "For input string: \"\"");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoPaymentMethod () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().setPaymentMethod("");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("Required field invalid or missing in the Request : Credit Card Type cannot be null"), "Required field invalid or missing in the Request : Credit Card Type cannot be null");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoPaymentMethodType () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().setPaymentMethodType("");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("applicationErrorCode"), "applicationErrorCode");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_InvalidDeviceTerminal () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().setDeviceTerminalId("123456abc");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("applicationErrorCode"), "applicationErrorCode");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_InvalidStratusTerminalNumber () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().setStratusTerminalNumber("abcd123456");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("applicationErrorCode"), "applicationErrorCode");
		}
		@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
		public void testestablishSettlementMethod_Negative_NoMerchantName() throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
					
			//create new request file
			EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
			
			//Create request
			request.getSettlementMethodRequest().getMerchantInfo().setName("");
			request.getSettlementMethodRequest().getCreditCardInfo().setCardNumber(ccNum);
			request.getSettlementMethodRequest().getCreditCardInfo().setCardSecurityNumber(CVV2);
			request.getSettlementMethodRequest().getCreditCardInfo().setExpirationDate(Expiration);
			request.getSettlementMethodRequest().getCreditCardInfo().getCardHolderInfo().setName(NameOnCard);
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceSource("FOLIO");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue("138855643");
			request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName(LastName);
			
			//submit request
			RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			TestReporter.assertTrue(response.getResponse().contains("applicationErrorCode"), "applicationErrorCode");
		}
}
