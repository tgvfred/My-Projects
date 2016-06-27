package com.disney.api.soapServices.paymentService.operations;

import com.disney.api.soapServices.paymentService.PaymentService;
import com.disney.utils.XMLTools;

public class PostCheckPayment extends PaymentService{
	public PostCheckPayment(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("postCheckPayment")));

		generateServiceContext();	
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setBankingAccountName(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='bankingACName'][1]", value);
	}
	
	public void setTenderedAmount(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='tenderedAmount'][1]/*[local-name(.)='amount'][1]", value);
	}
	
	public void setConvertedAmount(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='convertedAmount'][1]/*[local-name(.)='amount'][1]", value);
	}
	
	public void setLocationId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='locationId'][1]", value);
	}
	
	public void setPostingDate(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='postingDate'][1]", value);
	}
	
	public void setFolioId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='responsibleParty'][1]/*[local-name(.)='folioId'][1]", value);
	}
	
	public void setDocumentOriginator(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='documentOriginator'][1]", value);
	}
	
	public void setCheckNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='checkNumber'][1]", value);
	}
	
	public void setAuthorizationNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPayment'][1]/*[local-name(.)='pmtInfo'][1]/*[local-name(.)='authorizationNumber'][1]", value);
	}
	
	public String getFolioBalance(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPaymentResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='folioBalance'][1]/*[local-name(.)='amount'][1]");
	}
	
	public String getOriginalTransactionId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPaymentResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='origTransactionId'][1]");
	}
	
	public String getTransactionId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPaymentResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='transactionId'][1]");
	}
	
	public String getPaymentId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='postCheckPaymentResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='paymentId'][1]");
	}
	
	/**
	 * Sets the tendered amount in the SOAP request
	 * @param value - tendered amount
	 */
	public void setTenderedAmountAmount(String value){setRequestNodeValueByXPath("/Envelope/Body/postCheckPayment/pmtInfo/tenderedAmount/amount", value);}
	/**
	 * Sets the converted amount in the SOAP request
	 * @param value - converted amount
	 */
	public void setConvertedAmountAmount(String value){setRequestNodeValueByXPath("/Envelope/Body/postCheckPayment/pmtInfo/convertedAmount/amount", value);}
	/**
	 * Sets the 3-letter party name in the SOAP request.  The 3 letters are the first 3 letters of the primary guest's last name
	 * @param value - 3-letter party name
	 */
	public void setThreeLetterPartyName(String value){setRequestNodeValueByXPath("/Envelope/Body/postCheckPayment/pmtInfo/responsibleParty/threeLetterPartyName", value);}
	/**
	 * Sets external reference number in the SOAP request, obtained by the reservation
	 * @param value - external reference number
	 */
	public void setExternalReferenceNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/postCheckPayment/pmtInfo/responsibleParty/externalReferenceNumber", value);}
	/**
	 * Sets the username in the SOAP request
	 * @param value - username
	 */
	public void setUser(String value){setRequestNodeValueByXPath("/Envelope/Body/postCheckPayment/context/userName", value);}
}
