package com.disney.api.soapServices.folioModule.folioServicePort.operations;

import com.disney.api.soapServices.folioModule.cardAuthorizationServiceV2.operations.RequestCreditCardRRN;
import com.disney.api.soapServices.folioModule.folioServicePort.FolioService;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class CreateSettlementMethod extends FolioService {
	
	private String rrn;
	private String rrnKey;

	/**
	* @throws 			NA
	* @Summary:			Builds the request for the modifyResponsiblePartyAccount operation.  Contains setters to
	* 					set the xpath nodes and getters to get xpath values from the response
	* 					There are serveral dynamic xpath nodes you must set, like the travel plan ID, folio ID, etc
	* @Precondition:
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	*/
	public CreateSettlementMethod(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createSettlementMethod")));
//		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setRetreivalReferenceNumber(String cardNumber, String expire){
		RequestCreditCardRRN requestRRN = new RequestCreditCardRRN(getEnvironment(), "Main");
		requestRRN.setCardNumber(cardNumber);
		String expireFullDate = expire;
		String expireMonth = expireFullDate.substring(0,2);
		String expireYear = expireFullDate.substring(3,5);
		requestRRN.setExpirationDate(expireYear+expireMonth);
		requestRRN.sendRequest();
		TestReporter.assertEquals(requestRRN.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertEquals(requestRRN.getStatusCode(), "APPROVED", "The response code was not 200");
		TestReporter.assertNotNull(requestRRN.getRetrievalReferenceNumber(), "The response contains a RRN");
		TestReporter.assertNotNull(requestRRN.getRetrievalReferenceNumberKey(),  "The response contains a Travel Component ID");
		rrn =  requestRRN.getRetrievalReferenceNumber();
		rrnKey = requestRRN.getRetrievalReferenceNumberKey();
		
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/retrievalReferenceNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/retrievalReferenceNumberKey", requestRRN.getRetrievalReferenceNumberKey());
	}
	
	public void setRetreivalReferenceNumber(String cardNumber, String expire, String cardType){
		RequestCreditCardRRN requestRRN = new RequestCreditCardRRN(getEnvironment(), "Main");
		requestRRN.setCardNumber(cardNumber);
		String expireFullDate = expire;
		String expireMonth = expireFullDate.split("/")[0].trim();
		if(expireMonth.toCharArray().length == 1){
			expireMonth = "0" + expireMonth;
			System.out.println();
		}
		String expireYear = expireFullDate.split("/")[1];
		requestRRN.setExpirationDate(expireYear+expireMonth);
		requestRRN.setCardType(cardType);
		requestRRN.sendRequest();
		TestReporter.logAPI(!requestRRN.getResponseStatusCode().equals("200"), requestRRN.getResponse(), requestRRN);
		rrn =  requestRRN.getRetrievalReferenceNumber();
		rrnKey = requestRRN.getRetrievalReferenceNumberKey();
		
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/retrievalReferenceNumber", requestRRN.getRetrievalReferenceNumber());
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/retrievalReferenceNumberKey", requestRRN.getRetrievalReferenceNumberKey());
	}
	
	public String getRrn(){return rrn;}
	public String getRrnKey(){return rrnKey;}
	
	public void setFolioId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/folioID", value);
	}
	
	public void setExpressCheckout(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/expressCheckOut", value);
	}
	
	public void setSettlementMethod(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/mop", value);		
	}
	
	public void setCardNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/creditCardNumber", value);
	}
	
	public void setCardName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/name", value);
	}
	
	public void setCardAddressLine1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/addressLineOne", value);
	}
	
	public void setCardAddressLine2(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/addressLineTwo", value);
	}
	
	public void removeAddressLine2(){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/addressLineTwo", "fx:RemoveNode");
	}
	
	public void setCardCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/city", value);
	}
	
	public void setCardPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/postalCode", value);
	}
	
	public void setCardState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/address/state", value);
	}
	
	public void setCardExpirationMonth(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/expirationMonth", value);
	}
	
	public void setCardExpirationYear(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createSettlementMethod/settlementMethod/card/cardAuthorizationInfo/expirationYear", value);
	}
}
