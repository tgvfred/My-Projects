package com.disney.utils.dataFactory.folioInterface;

import com.disney.api.soapServices.folioServicePort.operations.CreateSettlementMethod;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class FolioInterfaceSettlement extends FolioInterface{
	private String expressCheckout;	// Flag to determine if Express Checkout should be used for the settlement method
	public String defaultSettlementScenario = "Main";
	
	/**
	 * Dummy constructor
	 */
	public FolioInterfaceSettlement(){}
	/**
	 * Constructor intended for use with an instance of the ScheduledEventReservation which contains a booked Scheduled Event reservation
	 * @param seRes - ScheduledEventReservation, an instance of the ScheduledEventReservation which should contain a booked Scheduled Event reservation
	 */
	public FolioInterfaceSettlement(ScheduledEventReservation seRes){
		setEnvironment(seRes.getEnvironment());
		setTravelPlanId(seRes.getTravelPlanId());
		setLocationId("9");  // This is the location ID for "System-WDW Scheduled Events - Guest  Facing" as it is found in the Dreams.RSRC_INV.WRK_LOC table
	}
	/**
	 * Constructor intended for use with an instance of the ReservationDecorator which contains a booked Resort reservation
	 * @param resDec - ReservationDecorator, an instance of the ReservationDecorator which should contain a booked Resort reservation
	 */
	public FolioInterfaceSettlement(Reservation res){
		setEnvironment(res.getEnvironment());
		setTravelPlanId(res.getTravelPlanId());
		setLocationId(res.getLocationId());
	}
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
