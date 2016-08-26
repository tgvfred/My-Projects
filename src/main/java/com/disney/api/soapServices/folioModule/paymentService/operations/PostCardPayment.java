package com.disney.api.soapServices.folioModule.paymentService.operations;

import com.disney.AutomationException;
import com.disney.api.soapServices.folioModule.cardAuthorizationServiceV2.operations.RequestCreditCardRRN;
import com.disney.api.soapServices.folioModule.paymentService.PaymentService;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class PostCardPayment extends PaymentService{	
	
	private String rrn;
	private String rrnKey;
	
	public PostCardPayment(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("postCardPayment")));

		generateServiceContext();	
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setAmount(String amount){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/tenderedAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/convertedAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/authAmount/amount", amount);		
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/locationId", locationId);		
	}

	public void setBookingReference(String bookingSource, String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/bookingSource", bookingSource);
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/externalReferenceNumber", value);		
	}
	
	public void setFolioId(String folioId){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/folioId", folioId);		
	}
	
	public void setPartyId(String partyId){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/responsibleParty", partyId);		
	}
	
	public void setPrimaryLastname(String lastname){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/threeLetterPartyName", lastname.substring(0, 3));		
	}
	
	public void setTravelPlanSegmentId(String tpsId){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/tpsId", tpsId);		
	}
	
	public void setTravelPlanId(String tpId){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/responsibleParty/tpId", tpId);		
	}
	
	public void setExternalReference(String externalReferenceName, String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/externalReferenceTOs/referenceName", externalReferenceName);
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/externalReferenceTOs/referenceValue", value);		
	}
	
	public void setRetreivalReferenceNumber(){
		String amount = "";
		TestReporter.logStep("Generate RRN for Card Payment");
		RequestCreditCardRRN requestRRN = new RequestCreditCardRRN(getEnvironment(), "Main");
		// Negative amounts are not allowed for the RequestCreditCardRRN
		// operation. Therefore, make the amount positive in order to retrieve a
		// RRN
//		requestRRN.setAmount(getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/tenderedAmount/amount"));
		amount = getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/tenderedAmount/amount").replace("-", "");
		requestRRN.setAmount(amount);
		requestRRN.setCardNumber(getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardNumber"));
		requestRRN.setCardType(getRequestNodeValueByXPath("	/Envelope/Body/postCardPayment/pmtInfo/paymentMethodInfo/paymentMethodCd").replace(" ", ""));
		String expireFullDate = getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/expirationDate");
		String expireMonth = null;
		String expireYear = null;
		try{
			if(expireFullDate.contains("/")){
				expireMonth = expireFullDate.split("/")[0];
			}else{
				expireMonth = expireFullDate.substring(0, 2);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			try {
				throw new AutomationException("Exception with expiration date month: " + e.getMessage());
			} catch (AutomationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try{
			if(expireFullDate.contains("/")){
				expireYear = expireFullDate.split("/")[1];
			}else{
				expireYear = expireFullDate.substring(2, 4);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			try {
				throw new AutomationException("Exception with expiration date year: " + e.getMessage());
			} catch (AutomationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		requestRRN.setExpirationDate(expireYear+expireMonth);
		requestRRN.sendRequest();
        TestReporter.logAPI(!requestRRN.getResponseStatusCode().equals("200"), "Error generating RRN: "+ requestRRN.getFaultString(), requestRRN);	
		rrn =  requestRRN.getRetrievalReferenceNumber();
		rrnKey = requestRRN.getRetrievalReferenceNumberKey();
		
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/retrievalReferenceNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/retrievalReferenceNumberKey", requestRRN.getRetrievalReferenceNumberKey());

		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardRetrievalInfo/rrNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardRetrievalInfo/rrnKey", requestRRN.getRetrievalReferenceNumberKey());		
	}
	
	public void setRetreivalReferenceNumber(String cardNumber, String cardType, String expireFullDate){
		String amount = "";
		RequestCreditCardRRN requestRRN = new RequestCreditCardRRN(getEnvironment(), "Main");
		TestReporter.logStep("Generate RRN for Card Payment");
		// Negative amounts are not allowed for the RequestCreditCardRRN
		// operation. Therefore, make the amount positive in order to retrieve a
		// RRN
//		requestRRN.setAmount(getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/tenderedAmount/amount"));
		amount = getRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/tenderedAmount/amount").replace("-", "");
		requestRRN.setAmount(amount);
		requestRRN.setCardNumber(cardNumber);
		requestRRN.setCardType(cardType.replace(" ", ""));
		String expireMonth = expireFullDate.split("/")[0].trim();
		String expireYear = expireFullDate.split("/")[1].trim();
		if(expireMonth.toCharArray().length == 1){
			expireMonth = "0" + expireMonth;
			System.out.println();
		}
		requestRRN.setExpirationDate(expireYear+expireMonth);
		requestRRN.sendRequest();
		TestReporter.assertEquals(requestRRN.getResponseStatusCode(), "200", "The response code was not 200. Response message: \n" + requestRRN.getResponse());
		TestReporter.assertEquals(requestRRN.getStatusCode(), "APPROVED", "The response code was not 200");
		TestReporter.assertNotNull(requestRRN.getRetrievalReferenceNumber(), "The response contains a RRN");
		TestReporter.assertNotNull(requestRRN.getRetrievalReferenceNumberKey(),  "The response contains a Travel Component ID");
		rrn =  requestRRN.getRetrievalReferenceNumber();
		rrnKey = requestRRN.getRetrievalReferenceNumberKey();
		
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/retrievalReferenceNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/retrievalReferenceNumberKey", requestRRN.getRetrievalReferenceNumberKey());

		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardRetrievalInfo/rrNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardRetrievalInfo/rrnKey", requestRRN.getRetrievalReferenceNumberKey());		
	}
	
	public String getFolioBalance(){
		return getResponseNodeValueByXPath("/Envelope/Body/postCardPaymentResponse/returnParameter/folioBalance/amount");
	}
	
	public String getOriginalTransactionId(){
		return getResponseNodeValueByXPath("/Envelope/Body/postCardPaymentResponse/returnParameter/origTransactionId");
	}
	
	public String getPaymentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/postCardPaymentResponse/returnParameter/paymentId");
	}
	
	public String getTransactionId(){
		return getResponseNodeValueByXPath("/Envelope/Body/postCardPaymentResponse/returnParameter/transactionId");
	}
	
	public String getRRNNumber(){
		return rrn;
	}
	
	public String getRRNKey(){
		return rrnKey;
	}
	
	
	
	
	
	public void setCardNumber(String cardNumber){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardNumber",cardNumber);
	}
	
	public void setExpirationDate(String expDate){
		expDate = expDate.replace(" ", "").replace("/", "");
//		if(expDate.length() == 4) expDate = "0" + expDate;
		if(expDate.length() != 4) expDate = "0" + expDate;
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/expirationDate", expDate);
	}
	
	public void setCardHolderName(String fullName){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/name", fullName);
	}
	
	public void setAuthorizationCode(String code){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/authorizationCode", code);
	}
	
	public void setExpriationMonth(String month){
		month = month.replace(" ", "");
		if(month.length() == 1) month = "0" + month;
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/expirationMonth", month);
	}
	
	public void setExpirationYear(String year){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/expirationYear", year);
	}
	
	public void setPaymentMethod(String method){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/paymentMethodInfo/paymentMethodCd", method);
	}
	
	public void setPaymentType(String type){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/paymentMethodInfo/paymentMethodTypeCd", type);
	}
	
	public void setDeviceTerminalID(String id){
		setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/paymentMethodInfo/paymentMethodTypeCd", id);
	}
}
