package com.disney.utils.dataFactory.folioInterface;

import com.disney.api.soapServices.folioServicePort.operations.CreateSettlementMethod;
import com.disney.utils.TestReporter;

public class FolioInterfaceSettlement extends FolioInterface{
	private String expressCheckout;	// Flag to determine if Express Checkout should be used for the settlement method
	
	/**
	 * Sets the express checkout flag
	 * @param checkout - express checkout flag
	 */
	protected void setExpressCheckout(String checkout){expressCheckout = checkout;}
	/**
	 * Gets the express checkout flag
	 * @return String, express checkout flag
	 */
	protected String getExpressCheckout(){return expressCheckout;}
	
	/**
	 * Creates a settlement method using a predefined scenario
	 */
	public void createSettlementMethod(){
		CreateSettlementMethod settlement = new CreateSettlementMethod(getEnvironment(), "Main");
		settlement.setFolioId(getFolioId());
		settlement.sendRequest();
		TestReporter.logAPI(!settlement.getResponseStatusCode().equals("200"), "An error occurred creating a settlement method.", settlement);
	}
	/**
	 * Creates a settlement method using a predefined scenario
	 * @param scenario - payment UI virtual table
	 */
	public void createSettlementMethod(String scenario){
		getDatatableValues(scenario);
		setExpressCheckout(getDatatable().getDataParameter("ExpressCheckout"));
		generateCard(getCardStatus(), getCardDelay());
		retrieveFolioBalanceDue();
		
		CreateSettlementMethod settlement = new CreateSettlementMethod(getEnvironment(), "Main");
		settlement.setFolioId(getFolioId());
		if(getExpressCheckout().equalsIgnoreCase("true"))settlement.setExpressCheckout("true");
		settlement.setSettlementMethod(getCardPaymentMethod());
		settlement.setCardNumber(getCardNumber());
		settlement.setCardName(getCardHolderName());
		settlement.setCardAddressLine1(getCardAddressLine1());
		String method = getCardPaymentMethod();
		if(getCardPaymentMethod().equalsIgnoreCase("DINERS CLUB")) method = "DINERS_CLUB";
		if(getCardPaymentMethod().equalsIgnoreCase("American Express")) method = "AMEX";
		settlement.setRetreivalReferenceNumber(getCardNumber(),getCardExpirationMonth()+"/"+getCardExpirationYear(), method.replace(" ", "").toUpperCase());
		if(getCardAddressLine2().isEmpty() || getCardAddressLine2() == null || getCardAddressLine2().equalsIgnoreCase("null")){settlement.removeAddressLine2();}
		else{settlement.setCardAddressLine2(getCardAddressLine2());}
		settlement.setCardPostalCode(getCardPostalCode());
		settlement.setCardState(getCardState());
		settlement.setCardExpirationMonth(getCardExpirationMonth());
		settlement.setCardExpirationYear(getCardExpirationYear());		
		settlement.sendRequest();
		TestReporter.logAPI(!settlement.getResponseStatusCode().equals("200"), "An error occurred creating a settlement method.", settlement);
	}
}
