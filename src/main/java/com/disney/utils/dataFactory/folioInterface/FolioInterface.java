package com.disney.utils.dataFactory.folioInterface;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.RetrieveFolioBalanceDue;
import com.disney.utils.Datatable;
import com.disney.utils.GenerateCard;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

/**
 * This class serves as the superclass for other folio classes such as FolioPayment and FolioSettlement
 * @author Waightstill W Avery
 *
 */
public class FolioInterface {
	private String environment;	// Current environment under test
	private Map<String, String> conversationIDs = new HashMap<String, String>();	//A map of conversation ID from various API calls
	private Datatable datatable = new Datatable();	// Datatable instance to retrieve scenarios from the virtual tables
	private String travelComponentId;	// Travel Component ID for the current reservation
	private String travelComponentGroupId; // Travel Component Group ID for the current reservation
	private String travelPlanId;	// Travel Plan ID for the current reservation
	private String travelPlanSegmentId;	// Travel Plan Segment ID for the current reservation
	private String folioId;	// Folio ID for the current reservation	
	private String cardPaymentMethod;	// Current card payment method
	private String cardPaymentType;	// Current card payment type
	private String cardNumber;	// Current card number
	private String cardExpirationDate;	// Current card expiration date, month and year
	private String cardExpirationMonth;	// Current card expiration month
	private String cardExpirationYear;	// Current card expiration year
	private String cardHolderName;	// Current card holder name
	private String cardCcvCode;	// Current card CCV
	private String locationId;	// Contains the location ID from the reservation
	private String depositDue;	// Contains the current deposit due amount
	private String terminalID;	// Terminal ID from a payment or settlement process
	private String cardStatus;	// desired status of the card (APPROVED, DEVLINED, etc.)
	private String cardDelay;	// value of credit card delay, used to emulate real-world timing for authorizing cards, and can affect the backend path of the authorization process
	private String isNegative = "";	// Flag to determine if a negative scenario is being tested
	private String errorType = "";	// Expected error type
	private String errorMessage = "";	// Expected error message
	private boolean incidentals = true;	// Flag to determine if a card is to be used for incidentals
	private String amountToPay;	// Contains the amount to pay
	private boolean existingCard = false;	// Flag to determine if a card on file is to be used
	private RetrieveFolioBalanceDue retrieveBalance;	// RetrieveFolioBalanceDue instance
	private String balanceDue;	// Contains the current folio balance due
	private String cardAddressLine1; // Contains the card address line 1
	private String cardAddressLine2; // Contains the card address line 2
	private String cardCity;	// Contains the card city
	private String cardState;	// Contains the card state
	private String cardPostalCode;	// Contains the card postal 
	private HouseHold party;	// HouseHold containing all guests in the reservation, most important to payment is the primary guest
	
	/**
	 * Retrieves the current environment under test
	 * @return String, current environment under test
	 */
	protected String getEnvironment() {return environment;}
	/**
	 * Sets the current environment under test
	 * @param env - current environment under test
	 */
	protected void setEnvironment(String env){environment = env;}	
	/**
	 * Sets the current datatable instance
	 * @param dt - Datatable, current datatable instance
	 */
	protected void setDatatable(Datatable dt){datatable = dt;}
	/**
	 * Retrieves the current datatable instance
	 * @return Datatable, current datatable instance
	 */
	protected Datatable getDatatable(){return datatable;}	
	/**
	 * Sets the conversation ID map
	 * @param map - Map<String, String>, a map of the conversation IDs for all API calls
	 */
	protected void setConversationIdMap(Map<String, String> map){conversationIDs = map;}
	/**
	 * Retrieves the conversation ID map
	 * @return Map<String, String>, a map of the conversation IDs for all API calls
	 */
	protected Map<String, String> getConversationIdMap(){return conversationIDs;}
	/**
	 * Sets the travel component ID for the current reservation
	 * @param tc_id - String, travel component ID for the current reservation
	 */
	protected void setTravelComponentId(String tc_id){travelComponentId = tc_id;}
	/**
	 * Gets the travel component ID for the current reservation
	 * @return String, travel component ID for the current reservation
	 */
	protected String getTravelComponentId(){return travelComponentId;}	
	/**
	 * Sets the travel component group ID for the current reservation
	 * @param tc_id - String, travel component group ID for the current reservation
	 */
	protected void setTravelComponentGroupId(String tcg_id){travelComponentId = tcg_id;}
	/**
	 * Gets the travel component group ID for the current reservation
	 * @return String, travel component group ID for the current reservation
	 */
	protected String getTravelComponentGroupId(){return travelComponentGroupId;}	
	/**
	 * Retrieves the travel plan ID for the current reservation
	 * @return String, travel plan ID for the current reservation
	 */
	protected String getTravelPlanId(){return travelPlanId;}
	/**
	 * Sets the travel plan ID for the current reservation
	 * @param tp_id - String, travel plan ID for the current reservation
	 */
	protected void setTravelPlanId(String tp_id){travelPlanId = tp_id;}	
	/**
	 * Retrieves the travel plan segment ID for the current reservation
	 * @return String, travel plan segment ID for the current reservation
	 */
	protected String getTravelPlanSegmentId() {return travelPlanSegmentId;}
	/**
	 * Sets the travel plan segment ID for the current reservation
	 * @param tps_id - String, travel plan segment ID for the current reservation
	 */
	protected void setTravelPlanSegmentId(String tps_id) {travelPlanSegmentId = tps_id;}	
	/**
	 * Retrieves the folio ID for the current reservation
	 * @return String, folio ID for the current reservation
	 */
	protected String getFolioId(){return folioId;}
	/**
	 * Retrieves the folio ID for the current reservation
	 * @param folId - folio ID for the current reservation
	 */
	protected void setFolioId(String folId){folioId = folId;}
	/**
	 * Sets the card payment method
	 * @param paymentMethod - String, card payment method
	 */
	protected void setCardPaymentMethod(String paymentMethod){cardPaymentMethod = paymentMethod;}
	/**
	 * Retrieves the card payment method
	 * @return String, card payment method
	 */
	protected String getCardPaymentMethod(){return cardPaymentMethod;}
	/**
	 * Sets the card payment type
	 * @param cardPaymentType - String, card payment type
	 */
	protected void setCardPaymentType(String paymentType){cardPaymentType = paymentType;}
	/**
	 * Retrieves the card payment type
	 * @return String, card payment type
	 */
	protected String getCardPaymentType(){return cardPaymentType;}
	/**
	 * Sets the current card number
	 * @param cardNumber - String, card number
	 */
	protected void setCardNumber(String number){cardNumber = number;}
	/**
	 * Retrieves the current card number
	 * @return String, card payment type
	 */
	protected String getCardNumber(){return cardNumber;}
	/**
	 * Sets the current card expiration date, month and year
	 * @param cardExpirationDate - String, card expiration date, month and year
	 */
	protected void setCardExpirationDate(String date){cardExpirationDate = date;}
	/**
	 * Retrieves the current card expiration date, month and year
	 * @return String, card expiration date, month and year
	 */
	protected String getCardExpirationDate(){return cardExpirationDate;}
	/**
	 * Sets the current card expiration month
	 * @param cardExpirationMonth - String, card expiration month
	 */
	protected void setCardExpirationMonth(String month){cardExpirationMonth = month;}
	/**
	 * Retrieves the current card expiration month
	 * @return String, card expiration month
	 */
	protected String getCardExpirationMonth(){return cardExpirationMonth;}
	/**
	 * Sets the current card expiration year
	 * @param cardExpirationYear - String, card expiration year
	 */
	protected void setCardExpirationYear(String year){cardExpirationYear = year;}
	/**
	 * Retrieves the current card expiration year
	 * @return String, card expiration year
	 */
	protected String getCardExpirationYear(){return cardExpirationYear;}
	/**
	 * Sets the current card holder name
	 * @param name - String, current card holder name
	 */
	protected void setCardHolderName(String name){ cardHolderName = name;}
	/**
	 * Retrieves the current card holder name
	 * @return String, current card holder name
	 */
	protected String getCardHolderName(){return cardHolderName;}
	/**
	 * Sets the current card CCV value
	 * @param ccv - String, current card CCV value
	 */
	protected void setCardCCV(String ccv){cardCcvCode = ccv;}
	/**
	 * Retrieves the current card CCV value
	 * @return String, current card CCV value
	 */
	protected String getCardCCV(){return cardCcvCode;}
	/**
	 * Sets the value for the current reservation's location ID
	 * @param id - String, current reservation's location ID
	 */
	protected void setLocationId(String id) {locationId = id;}
	/**
	 * Gets the value for the current reservation's location ID
	 * @return String, current reservation's location ID
	 */
	protected String getLocationId() {return locationId;}
	/**
	 * Sets the current deposit due amount
	 * @param deposit - String, current deposit due amount
	 */
	protected void setDepositDue(String deposit){depositDue = deposit;}
	/**
	 * Gets the current deposit due amount
	 * @return String, current deposit due amount
	 */	
	protected String getDepositDue(){return depositDue;}
	/**
	 * Sets the value for the terminal ID for a payment or settlement process
	 * @param value - terminal ID value
	 */
	protected void setTerminalID(String value){terminalID = value;}
	/**
	 * Gets the value for the terminal ID for a payment or settlement process
	 * @return String, terminal ID
	 */
	protected String getTerminalID(){return terminalID;}
	/**
	 * Sets the value for the credit card status
	 * @param status - credit card status
	 */
	protected void setCardStatus(String status){cardStatus = status;}
	/**
	 * Gets the credit card status
	 * @return String, credit card status
	 */
	protected String getCardStatus(){return cardStatus;}
	/**
	 * Sets the value for the credit card delay
	 * @param delay - credit card delay
	 */
	protected void setCardDelay(String delay){cardDelay = delay;}
	/**
	 * Gets the credit card delay
	 * @return String, credit card delay
	 */
	protected String getCardDelay(){return cardDelay;}	
	/**
	 * Sets flag used to determine is a negative scenario is being tested
	 * @param neg - flag used to determine is a negative scenario is being tested
	 */
	protected void setIsNegativeScenario(String neg){isNegative = neg;}
	/**
	 * Gets flag used to determine is a negative scenario is being tested
	 * @return String, flag used to determine is a negative scenario is being tested
	 */
	protected String getIsNegativeScenario(){return isNegative;}
	/**
	 * Sets negative scenario error type
	 * @param type - negative scenario error type
	 */
	protected void setNegativeScenarioErrorType(String type){errorType = type;}
	/**
	 * Gets negative scenario error type
	 * @return String, negative scenario error type
	 */
	protected String getNegativeScenarioErrorType(){return errorType;}
	/**
	 * Sets negative scenario error message
	 * @param type - negative scenario error message
	 */
	protected void setNegativeScenarioErrorMessage(String msg){errorMessage = msg;}
	/**
	 * Gets negative scenario error message
	 * @return String, negative scenario error message
	 */
	protected String getNegativeScenarioErrorMessage(){return errorMessage;}
	/**
	 * Sets the incidentals flag
	 * @param inc - incidentals flag
	 */
	protected void setIncidentals(boolean inc){incidentals = inc;}
	/**
	 * Gets the incidentals flag
	 * @return String, incidentals flag
	 */
	protected boolean getIncidentals(){return incidentals;}	
	/**
	 * Set the amount to pay
	 * @param amount - String, amount to pay
	 */
	protected void setAmountToPay(String amount){amountToPay = amount;}
	/**
	 * Gets the amount to pay
	 * @return - String, amount to pay
	 */
	protected String getAmountToPay(){return amountToPay;}
	/**
	 * Sets the flag for using an existing card
	 * @param exist - flag for using an existing card
	 */
	protected void setExistingCard(boolean exist){existingCard = exist;}
	/**
	 * Gets the flag for using an existing card
	 * @return boolean, flag for using an existing card
	 */
	protected boolean getExistingCard(){return existingCard;}
	/**
	 * Sets the value for the current folio balance due
	 * @param balanceDue - String, current folio balance due
	 */
	protected void setBalanceDue(String balanceDue){this.balanceDue = balanceDue;}
	/**
	 * Gets the value for the current folio balance due
	 * @return String, current folio balance due
	 */
	protected String getBalanceDue(){return balanceDue;}
	/**
	 * Sets the card address line 1
	 * @param line1 - card address line 1
	 */
	protected void setCardAddressLine1(String line1){cardAddressLine1 = line1;}
	/**
	 * Gets the card address line 1
	 * @return String card address line 1
	 */
	protected String getCardAddressLine1(){return cardAddressLine1;}
	/**
	 * Sets the card address line 2
	 * @param line1 - card address line 2
	 */
	protected void setCardAddressLine2(String line2){cardAddressLine2 = line2;}
	/**
	 * Gets the card address line 2
	 * @return String card address line 2
	 */
	protected String getCardAddressLine2(){return cardAddressLine2;}
	/**
	 * Sets the card address city
	 * @param line1 - card address city
	 */
	protected void setCardCity(String city){cardCity = city;}
	/**
	 * Gets the card address city
	 * @return String card address city
	 */
	protected String getCardCity(){return cardCity;}
	/**
	 * Sets the card address state
	 * @param line1 - card address state
	 */
	protected void setCardState(String state){cardState = state;}
	/**
	 * Gets the card address state
	 * @return String card address state
	 */
	protected String getCardState(){return cardState;}
	/**
	 * Sets the card address postal code
	 * @param line1 - card address postal code
	 */
	protected void setCardPostalCode(String code){cardPostalCode = code;}
	/**
	 * Gets the card address postal code
	 * @return String card address postal code
	 */
	protected String getCardPostalCode(){return cardPostalCode;}
	/**
	 * Sets the HouseHold instance the contains all guests on the reservation
	 * @param hh - HouseHold instance the contains all guests on the reservation
	 */
	protected void setParty(HouseHold hh){party = hh;}
	/**
	 * Gets the HouseHold instance the contains all guests on the reservation
	 * @return HouseHold, instance the contains all guests on the reservation
	 */
	protected HouseHold getParty(){return party;}
	
	/**
	 * Generates a card to use for payment or settlement
	 * @param status - desired status of the card (APPROVED, DEVLINED, etc.)
	 * @param delay - value of credit card delay, used to emulate real-world timing 
	 * for authorizing cards, and can affect the backend path of the authorization process
	 */
	protected void generateCard(String status, String delay){
		GenerateCard card = new GenerateCard();
		Map<String, String> cardInfo = null;
		try {cardInfo = card.getCardInfo(status, delay, card.getCradTypeEnum(getCardPaymentMethod()));}
		catch (Exception e) {
			TestReporter.assertNotNull(cardInfo, "An error occurred retrieving a valid card. "
					+ "\nSTATUS: " + status
					+ "\nDELAY:  " + delay
					+ "\nMETHOD: " + getCardPaymentMethod()
					+ "\nStacktrace: \n" + e.getMessage());
		}

		setCardNumber(cardInfo.get("AccountNumber").replace("-", ""));
		setCardExpirationMonth(cardInfo.get("ExpMonth"));
		if(getCardExpirationMonth().length() == 1) setCardExpirationMonth(0 + getCardExpirationMonth());
		setCardExpirationYear(cardInfo.get("ExpYear"));
		setCardExpirationDate(getCardExpirationMonth() + "/" + getCardExpirationYear());
		setCardCCV(cardInfo.get("CVV2"));
		setCardHolderName(cardInfo.get("NameOnCard"));		
		setCardAddressLine1(cardInfo.get("BillingStreet"));
		setCardAddressLine2(cardInfo.get("BillingStreet2"));
		setCardCity(cardInfo.get("BillingCity"));
		setCardState(cardInfo.get("BillingState"));
		setCardPostalCode(cardInfo.get("BillingZip"));
	}
	/**
	 * Retrieves from the virtual tables
	 * @param scenario - Payment UI Virtual Table scenario
	 */
	protected void getDatatableValues(String scenario){
		setVirtualTable(scenario);
		
		if (getDatatable().getDataParameter("Incidentals").equalsIgnoreCase("false")) {incidentals = false;}
		if(incidentals) TestReporter.log("Applying incidentals");
		
		try{
			if(getAmountToPay().contains("override")){setAmountToPay(getAmountToPay().split(":")[1]);}
			else{setAmountToPay(getDatatable().getDataParameter("Amount"));}
		}
		catch(NullPointerException e){setAmountToPay(getDatatable().getDataParameter("Amount"));}
		
		if(!getExistingCard()){
			setCardPaymentType(getDatatable().getDataParameter("PaymentType"));
			setCardPaymentMethod(getDatatable().getDataParameter("PaymentMethod"));
			setCardStatus(getDatatable().getDataParameter("Status"));
			setCardDelay(getDatatable().getDataParameter("Delay"));
			setCardCCV(getDatatable().getDataParameter("EnterCCV"));
		}
		TestReporter.log("Payment Type: " + getCardPaymentType());
		TestReporter.log("Payment Method: " + getCardPaymentMethod());
		TestReporter.log("Status: " + getCardStatus());
		TestReporter.log("Delay: " + getCardDelay());
		TestReporter.log("Enter CCV: " + getCardCCV());
	}
	/**
	 * Sets the virtual table path, page and scenario
	 * @param scenario - virtual table scenario
	 */
	protected void setVirtualTable(String scenario){
		getDatatable().setVirtualtablePath(Datatable.PAYMENTUI_MASTER_DATA_PATH);
		getDatatable().setVirtualtablePage("PaymentUI");
		getDatatable().setVirtualtableScenario(scenario);
	}
	/**
	 * Method that retrieves the folio balance due, and sets some critical values that are captured from the RetrieveFolioBalanceDue response
	 */
	protected void retrieveFolioBalanceDue(){
		retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
		retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getTravelPlanId());
		retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
		try{retrieveBalance.setLocationId(getLocationId());}
		catch(XPathNullNodeValueException e){retrieveBalance.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());}
		retrieveBalance.sendRequest();
		TestReporter.logAPI(!retrieveBalance.getResponseStatusCode().equals("200"), "An error occurred retrieving the folio balance due.", retrieveBalance);	
		setBalanceDue(retrieveBalance.getPaymentRequired());
		setDepositDue(retrieveBalance.getDepositRequired());
		setFolioId(retrieveBalance.getFolioId());
	}
}