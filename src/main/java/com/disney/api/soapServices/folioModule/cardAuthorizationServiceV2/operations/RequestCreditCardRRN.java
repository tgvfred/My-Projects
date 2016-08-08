package com.disney.api.soapServices.folioModule.cardAuthorizationServiceV2.operations;

import com.disney.api.soapServices.folioModule.cardAuthorizationServiceV2.CardAuthorizationServiceV2;
import com.disney.utils.XMLTools;

public class RequestCreditCardRRN extends CardAuthorizationServiceV2{
	public RequestCreditCardRRN(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("requestCreditCardRRN")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setHotelStayDuration(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/@hotelStayDuration", value);
	}
	
	public void setCardNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/creditCardInfo/@cardNumber", value);
	}
	
	public void setExpirationDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/creditCardInfo/@expirationDate", value);
	}
	
	public void setBillingZipCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/creditCardInfo/@billingZipCode", value);
	}

	public void setCardType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/creditCardInfo/@creditCardType", value);
	}

	public void setAmount(String value){
		setRequestNodeValueByXPath("/Envelope/Body/requestCreditCardRRN/processCreditCardRequest/authorizationRequestInfo/@amount", value);
	}
	
	public String getRetrievalReferenceNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/requestCreditCardRRNResponse/returnResultParameter1/authorizationResponse/@retrievalReferenceNumber");
	}
	
	public String getRetrievalReferenceNumberKey(){
		return getResponseNodeValueByXPath("/Envelope/Body/requestCreditCardRRNResponse/returnResultParameter1/authorizationResponse/@retrievalReferenceNumberKey");
	}
	
	public String getStatusCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/requestCreditCardRRNResponse/returnResultParameter1/authorizationResponse/@statusCode");
	}
}
