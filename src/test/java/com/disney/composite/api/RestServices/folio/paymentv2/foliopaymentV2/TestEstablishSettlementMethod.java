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
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.RetrieveSettlementMethodsResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.ModifyGuestsRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.RetrieveDetailsResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByAgency.Reservation;
import com.disney.utils.GenerateCard;
//import com.disney.test.utils.GenerateCard;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.FolioInterfacePayment;
import com.disney.utils.dataFactory.staging.GenerateReservation;

@SuppressWarnings("unused")
public class TestEstablishSettlementMethod {
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
private String folioId;

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
//            TestReporter.assertNotNull(cardInfo, "An error occurred retrieving a valid card. "
//                    + "\nSTATUS: " + status
//                    + "\nDELAY:  " + delay
//                    + "\nMETHOD: " + getCardPaymentMethod()
//                    + "\nStacktrace: \n" + e.getMessage());
        }
		ccNum = cardInfo.get("AccountNumber").replace("-", "");
		ExpMonth = cardInfo.get("ExpMonth");
		ExpYear = cardInfo.get("ExpYear");
		if (ExpMonth.length()!=2){
			ExpMonth = "0"+ExpMonth;
		}
		Expiration = ExpMonth+"/"+ExpYear;
		CVV2 = cardInfo.get("CVV2");
		NameOnCard = cardInfo.get("NameOnCard");
		
		//Get Folio Id
		RestResponse response= Rest.folio(this.environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("DREAMS_TP",TPId,"INDIVIDUAL","","true");
		//Get the returned FolioID
		RetrieveSettlementMethodsResponse [] retrieveSettlementMethodsResponse = response.mapJSONToObject(RetrieveSettlementMethodsResponse[].class);
		for( RetrieveSettlementMethodsResponse folio:retrieveSettlementMethodsResponse){
			folioId = Long.toString(folio.getFolioID());
			
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
	//@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
	public void testestablishSettlementMethod () throws IOException{
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
		request.getSettlementMethodRequest().getFolioIdentifierInfo().setExternalReferenceValue(folioId);
		request.getSettlementMethodRequest().getFolioIdentifierInfo().setPartyLastName("aut");
		
		//submit request
		RestResponse response = Rest.folio(environment).paymentV2().folioPaymentV2().establishSettlementMethod().sendPutRequest(request);
	}
}
