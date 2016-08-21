package com.disney.utils.dataFactory.folioInterface;

import com.disney.AutomationException;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.folioModule.folioBankServicesV2.operations.IsUserBankedIn;
import com.disney.api.soapServices.folioModule.folioBankServicesV2.operations.UserBankIn;
import com.disney.api.soapServices.folioModule.folioBankServicesV2.operations.UserBankOut;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCardPayment;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCheckPayment;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveTravelComponentId;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This class extends the superclass Folio, and further defines fields and methods specific to making payments on reservations.
 * @author Waightstill W Avery
 *
 */
public class FolioInterfacePayment extends FolioInterface{
	private String partyId;	// Contains the partyID from the current reservation
	private String primaryGuestFirstName;	// Primary guest first name from the current reservation
	private String primaryGuestLastName;	// Primary guest last name from the current reservation
	private String originalTransactionId;	// Original transaction ID from the first payment
	private String latestTransactionId;	// Current transaction ID from the most recent payment
	private String paymentId;	// Payment ID from the most recent payment
	private String rrnNumber;	// Retrieval Reference Number from the PostCardPayment response 
	private String rrnKey;	// Retrieval Reference Key from the PostCardPayment response
	private String facilityId;	// Contains the facility ID for the reservation 
	private PostCardPayment postPayment;	// PostCardPayment instance
	private String defaultCheckPaymentScenario = "Main";	// Default scenario for making a check payment
	private String defaultCardPaymentScenario = "Pay total amount due with valid visa with incidentals";	// Default scenario for making a check payment
	private String scenario;	// Scenario for the next occurring process
	private String liloUser;	// Lilo user, used to ensure a user is banked-in to accept payments such as check, cash, etc.
	private OracleDatabase odb;	// Oracle database to be used to query databases
	private Recordset resultSet;	// Recordset from a database query
	private String bankingAccountingCenterName;	// Banking Accounting Center Name, used for check payments, and possibly others
	private String checkNumber;	// Document number for making a check payment
	private double paidAmount = 0.0;	// Accrues the total amount paid
	/**
	 * Dummy constructor
	 */
	public FolioInterfacePayment(){}
	/**
	 * Constructor intended for use with an instance of the ScheduledEventReservation which contains a booked Scheduled Event reservation
	 * @param seRes - ScheduledEventReservation, an instance of the ScheduledEventReservation which should contain a booked Scheduled Event reservation
	 */
	
	public FolioInterfacePayment(ScheduledEventReservation seRes){
		setEnvironment(seRes.getEnvironment());
		setTravelPlanSegmentId(seRes.getConfirmationNumber());
		setTravelPlanId(seRes.getTravelPlanId());
		setTravelComponentId(new RetrieveTravelComponentId(getEnvironment(), getTravelPlanSegmentId()).searchForReservationInformationByTravelPlanSegment());
		setFacilityId(seRes.getFacilityId());
		setLocationId("9");  // This is the location ID for "System-WDW Scheduled Events - Guest  Facing" as it is found in the Dreams.RSRC_INV.WRK_LOC table
		setParty(seRes.party());
		setPrimaryGuestFirstName(getParty().primaryGuest().getFirstName());
		setPrimaryGuestLastName(getParty().primaryGuest().getLastName());
		setPartyId(getParty().primaryGuest().getPartyId());
	}

	public FolioInterfacePayment(ScheduledEventReservation seRes, String environment){
		setEnvironment(environment);
		setTravelPlanSegmentId(seRes.getConfirmationNumber());
		setTravelPlanId(seRes.getTravelPlanId());
		setTravelComponentId(new RetrieveTravelComponentId(getEnvironment(), getTravelPlanSegmentId()).searchForReservationInformationByTravelPlanSegment());
		setFacilityId(seRes.getFacilityId());
		setLocationId("9");  // This is the location ID for "System-WDW Scheduled Events - Guest  Facing" as it is found in the Dreams.RSRC_INV.WRK_LOC table
		setParty(seRes.party());
		setPrimaryGuestFirstName(getParty().primaryGuest().getFirstName());
		setPrimaryGuestLastName(getParty().primaryGuest().getLastName());
		setPartyId(getParty().primaryGuest().getPartyId());
	}
	/**
	 * Constructor intended for use with an instance of the ReservationDecorator which contains a booked Resort reservation
	 * @param resDec - ReservationDecorator, an instance of the ReservationDecorator which should contain a booked Resort reservation
	 */
	public FolioInterfacePayment(Reservation res){
		setTravelPlanSegmentId(res.getTravelPlanId());
		setTravelPlanId(res.getTravelPlanId());
		setTravelComponentId(res.getTravelComponentId());
		setTravelComponentGroupId(res.getTravelComponentGroupingId());
		setEnvironment(res.getEnvironment());
		setFacilityId(res.getFacilityId());
		setLocationId(res.getLocationId());
		setFolioId(res.getFacilityId());
		setPrimaryGuestFirstName(res.getPrimaryGuestFirstName());
		setPrimaryGuestLastName(res.getPrimaryGuestLastName());
		try{setPartyId(res.getPrimaryGuestPartyId());}
		catch(NullPointerException e){setPartyId(res.getPartyId());}
		setPartyId(res.getPartyId());
	}
	
	/**
	 * Sets the party ID for the current reservation
	 * @param id - String, party ID for the current reservation
	 */
	protected void setPartyId(String id) {partyId = id;}
	/**
	 * Gets the party ID for the current reservation
	 * @return String, party ID for the current reservation
	 */
	protected String getPartyId() {return partyId;}
	/**
	 * Sets the primary guest last name for the current reservation
	 * @param name - String, primary guest last name for the current reservation
	 */
	protected void setPrimaryGuestLastName(String name) {primaryGuestLastName = name;}
	/**
	 * Gets the primary guest last name for the current reservation
	 * @return String, primary guest last name for the current reservation
	 */
	protected String getPrimaryGuestLastName() {return primaryGuestLastName;}
	/**
	 * Sets the primary guest first name for the current reservation
	 * @param name - String, primary first last name for the current reservation
	 */
	protected void setPrimaryGuestFirstName(String name) {primaryGuestFirstName = name;}
	/**
	 * Gets the primary guest first name for the current reservation
	 * @return String, primary guest first name for the current reservation
	 */
	protected String getPrimaryGuestFirstName() {return primaryGuestFirstName;}	
	/**
	 * Gets the original transaction ID from the first payment
	 * @return String, original transaction ID from the first payment
	 */
	protected String getOriginalTransactionId() {return originalTransactionId;}	
	/**
	 * Sets the original transaction ID from the first payment
	 * @param originalTransactionId - String, original transaction ID from the first payment
	 */
	protected void setOriginalTransactionId(String originalTransactionId) {this.originalTransactionId = originalTransactionId;}	
	/**
	 * Gets the latest transaction ID from the most recent payment
	 * @return String, latest transaction ID from the most recent payment
	 */
	protected String getLatestTransactionId() {return latestTransactionId;}	
	/**
	 * Sets the latest transaction ID from the most recent payment
	 * @param latestTransactionId - String, latest transaction ID from the most recent payment
	 */
	protected void setLatestTransactionId(String latestTransactionId) {this.latestTransactionId = latestTransactionId;}
	/**
	 * Gets the current payment ID
	 * @return String, current payment ID
	 */
	protected String getPaymentId() {return paymentId;}
	/**
	 * Sets the current payment ID
	 * @return String, current payment ID
	 */
	protected void setPaymentId(String id) {this.paymentId = id;}
	/**
	 * Gets the current Retrieval Reference Number
	 * @return String, current Retrieval Reference Number
	 */
	protected String getRRNNumber() {return rrnNumber;}
	/**
	 * Sets the current Retrieval Reference Number
	 * @param number - String, current Retrieval Reference Number
	 */
	protected void setRRNNumber(String number) {this.rrnNumber = number;}
	/**
	 * Gets the current Retrieval Reference Key
	 * @return String, current Retrieval Reference Key
	 */
	protected String getRRNKey() {return rrnKey;}
	/**
	 * Sets the current Retrieval Reference Key
	 * @param key - String, current Retrieval Reference Key
	 */
	protected void setRRNKey(String key) {this.rrnKey = key;}
	/**
	 * Sets the facility id from the reservation
	 * @param id - String, facility id from the reservation
	 */
	protected void setFacilityId(String id) {facilityId = id;}
	/**
	 * Overloaded method: Sets the facility id from the reservation
	 * @param id - int, facility id from the reservation
	 */
	protected void setFacilityId(int id) {setFacilityId(String.valueOf(id));}
	/**
	 * Gets the facility id that was used for the reservation
	 * @return String, facility id that was used for the reservation
	 */
	protected String getFacilityId() {return facilityId;}
	/**
	 * Sets the scenario for the next method
	 * @param sce - scenario for the next method
	 */
	protected void setScenario(String sce){scenario = sce;}
	/**
	 * Gets the scenario for the next method
	 * @return String, scenario for the next method
	 */
	protected String getScenario(){return scenario;}
	/**
	 * Sets the lilo user
	 * @param user - lilo user
	 */
	protected void setLiloUser(String user){liloUser = user;}
	/**
	 * Gets the lilo user
	 * @return - lilo user
	 */
	protected String getLiloUser(){return liloUser;}
	/**
	 * Sets the Oracle database instance to be used to query databases
	 * @param o - current Oracle database instance
	 */
	protected void setDatabase(OracleDatabase o){odb = o;}
	/**
	 * Returns the current Oracle database instance
	 * @return OracleDatabase, current Oracle database instance
	 */
	protected OracleDatabase getDatabase(){return odb;}
	/**
	 * Sets the current Recordset instance
	 * @param rs - current Recordset instance
	 */
	protected void setRecordset(Recordset rs){resultSet = rs;}
	/**
	 * Defines the current Recordset instance
	 * @return Recordset, current Recordset instance
	 */
	protected Recordset getRecordset(){return resultSet;}
	/**
	 * Sets the banking accounting center name
	 * @param name - banking accounting center name
	 */
	protected void setBankingAccountingCenterName(String name){bankingAccountingCenterName = name;}
	/**
	 * Gets the banking accounting center name
	 * @return String, banking accounting center name
	 */
	protected String getBankingAccountingCenterName(){return bankingAccountingCenterName;}
	/**
	 * Sets the check number from a check payment
	 * @param number - check number
	 */
	protected void setCheckNumber(String number) {checkNumber = number;}
	/**
	 * Gets the check number from a check payment
	 * @return - String, check number
	 */
	protected String getCheckNumber(){return checkNumber;}
	/**
	 * Sets the total amount paid for a given instance of the folioInterface
	 * @param amount - total amount paid for a given instance of the folioInterface
	 */
	protected void setPaidAmount(double amount){paidAmount = amount;}
	/**
	 * Gets the total amount paid for a given instance of the folioInterface
	 * @return double, total amount paid for a given instance of the folioInterface
	 */
	protected double getPaidAmount(){return paidAmount;}
	
	/**
	 * Method that invoke helper methods that retrieves the balance due on the folio, posts the card payment, and capture values from the PostCardPayment response
	 * @param scenario - String, PaymentUI VirtualTable scenario
	 */
	public void makeCardPayment(String scenario){
		TestReporter.logStep("Make a card Payment For Payment Scenario ["+scenario+"]");
		// Retrieve Folio Balance Due
		retrieveFolioBalanceDue();
		// Post Card Payment
		setExistingCard(false);
		postCardPayment(scenario);
	}	
	/**
	 * Method that invoke helper methods that posts the card payment, and captures values from the PostCardPayment response
	 * @param scenario - String, PaymentUI VirtualTable scenario
	 * @param creditAmount - String, amount to credit back to an existing card
	 */
	public void applyCreditToExistingCard(String scenario, String creditAmount){
		TestReporter.logStep("Apply a Credit ["+creditAmount+"] to an Existing Card For Payment Scenario ["+scenario+"]");
		//Post Card payment
		postCardPayment(scenario, creditAmount, true);
	}
	/**
	 * Makes a payment to an existing card on file
	 * @param scenario - String, PaymentUI VirtualTable scenario
	 */
	public void makeCardPaymentCardOnFile(String scenario){	
		TestReporter.logStep("Make a Card Payment to an Existing Card For Payment Scenario ["+scenario+"]");
		// Retrieve folio balance due
		retrieveFolioBalanceDue();	
		// Post card payment
		setExistingCard(true);
		postCardPayment(scenario);	
	}
	/**
	 * Attempts to make a payment using a negative scenario that is intended to invoke an error
	 * @param scenario - negative scenario that is intended to invoke an error
	 */
	public void makeCardPaymentNegative(String scenario){
		setIsNegativeScenario(getDatatable().getDataParameter("Negative"));
		setIsNegativeScenario(getDatatable().getDataParameter("ErrorType"));
		setIsNegativeScenario(getDatatable().getDataParameter("ErrorMessage"));
		retrieveFolioBalanceDue();
		setExistingCard(false);
		postCardPayment(scenario);			
	}
	/**
	 * Method that calls a series of setter methods that capture values from the PostCardPayment response
	 */
	private void setValuesFromPostPaymentResponse(Object postPayment){
		if(postPayment instanceof PostCardPayment){
			setBalanceDue(((PostCardPayment)postPayment).getFolioBalance());
			setOriginalTransactionId(((PostCardPayment)postPayment).getOriginalTransactionId());
			setLatestTransactionId(((PostCardPayment)postPayment).getTransactionId());
			setRRNKey(((PostCardPayment)postPayment).getRRNKey());
			setRRNNumber(((PostCardPayment)postPayment).getRRNNumber());
			setPaymentId(((PostCardPayment)postPayment).getPaymentId());
		}else if(postPayment instanceof PostCheckPayment){
			setBalanceDue(((PostCheckPayment)postPayment).getFolioBalance());
			setOriginalTransactionId(((PostCheckPayment)postPayment).getOriginalTransactionId());
			setLatestTransactionId(((PostCheckPayment)postPayment).getTransactionId());
			setPaymentId(((PostCheckPayment)postPayment).getPaymentId());
		}
		
	}	
//	/**
//	 * Method that retrieves the folio balance due, and sets some critical values that are captured from the RetrieveFolioBalanceDue response
//	 */
//	private void retrieveFolioBalanceDue(){
//		retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
//		retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getTravelPlanId());
//		retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
//		try{retrieveBalance.setLocationId(getLocationId());}
//		catch(XPathNullNodeValueException e){retrieveBalance.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());}
//		retrieveBalance.sendRequest();
//		TestReporter.logAPI(!retrieveBalance.getResponseStatusCode().equals("200"), "An error occurred retrieving the folio balance due.", retrieveBalance);	
//		setBalanceDue(retrieveBalance.getPaymentRequired());
//		setDepositDue(retrieveBalance.getDepositRequired());
//		setFolioId(retrieveBalance.getFolioId());
//	}	
	/**
	 * Method that invokes methods to set the amount to pay, and posts the card payment
	 * 
	 * @param scenario - String, PaymentUI VirtualTable scenario
	 * @param amount - String, amount to pay
	 * @param existingCard - boolean, used to determine if an existing card is to be
	 *            used. This method will fail if a previous payment has not been
	 *            made for a given instance of this class
	 */
	private void postCardPayment(String scenario, String amount, boolean existingCard){
		setExistingCard(existingCard);
		if(amount == null){setAmountToPay("-" + String.valueOf(getPaidAmount()));}
		else setAmountToPay("override:" + amount);
		postCardPayment(scenario);
	}
	/**
	 * Method to post a card payment
	 * @param scenario - String, PaymentUI VirtualTable scenario
	 * @param existingCard - boolean, used to determine if an existing card is to be
	 *            used. This method will fail if a previous payment has not been
	 *            made for a given instance of this class
	 */
	private void postCardPayment(String scenario){
		String convoMapKey;
		
		// Split payment scenarios are anticipated to have previously been set
		try{
			getScenario().equalsIgnoreCase("split");
			if(!getScenario().equalsIgnoreCase("split"))getDatatableValues(scenario);
		}
		catch(NullPointerException e){getDatatableValues(scenario);}
		
		//Generate a card
		if(!getExistingCard()) generateCard(getCardStatus(), getCardDelay());
		
		if(getAmountToPay() == null || getAmountToPay().isEmpty()) setAmountToPay(getDatatable().getDataParameter("Amount"));		
		
		postPayment = new PostCardPayment(getEnvironment(),"Visa-CreditCard");
		if(getAmountToPay().equalsIgnoreCase("total")){
			setAmountToPay(getBalanceDue());
			postPayment.setAmount(getAmountToPay());
		}
		else{
			setAmountToPay(getAmountToPay().replace("$", "").replace(",", ""));
			postPayment.setAmount(getAmountToPay());
		}
		// If making a payment to an existing card then at this point the folio ID should have been set by a previous transaction. 
		// If no folio ID exists, throw an AutomationException
		try{postPayment.setFolioId(getFolioId());}
		catch(NullPointerException e){throw new AutomationException("No folio ID exists from a previous transaction.");}	
		postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TC, getTravelPlanId());
		postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, getTravelComponentId());
		postPayment.setLocationId(getLocationId());
		postPayment.setPartyId(getPartyId());
		postPayment.setPrimaryLastname(getPrimaryGuestLastName());
		postPayment.setTravelPlanId(getTravelPlanId());
		postPayment.setTravelPlanSegmentId(getTravelPlanSegmentId());		
		postPayment.setCardNumber(getCardNumber());
		postPayment.setExpirationDate(getCardExpirationDate());
		//If using a prepaid card, the expiration date, month and year nodes need to be removed from the SOAP request
		if(getCardPaymentType().equalsIgnoreCase("PrePaidCard")){
			postPayment.setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/expirationDate", "fx:RemoveNode");
			postPayment.setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/expirationMonth", "fx:RemoveNode");
			postPayment.setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/expirationYear", "fx:RemoveNode");
		}else{
			postPayment.setExpirationDate(getCardExpirationDate());
			postPayment.setExpriationMonth(getCardExpirationMonth());
			postPayment.setExpirationYear(getCardExpirationYear());
		}
		postPayment.setCardHolderName(getCardHolderName());
		if(getCardCCV().equalsIgnoreCase("true")){postPayment.setAuthorizationCode(getCardCCV());}
		else{postPayment.setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/authorizationCode", "fx:RemoveNode");}
		postPayment.setPaymentMethod(getCardPaymentMethod());
		postPayment.setPaymentType(getCardPaymentType().replace(" ", ""));
		
		if(getExistingCard()){
			String method = getCardPaymentMethod();
			if(getCardPaymentMethod().equalsIgnoreCase("DINERS CLUB")) method = "DINERS_CLUB";
			else if(getCardPaymentMethod().equalsIgnoreCase("American Express")) method = "AMEX";
			postPayment.setRetreivalReferenceNumber(getCardNumber(), method, getCardExpirationDate());
			convoMapKey = "creditPayment";
		}else{
			postPayment.setRetreivalReferenceNumber();
			convoMapKey = "payment";
		}	
		TestReporter.logStep("Make card payment on Folio [" + getFolioId() +"] for the amount of [" + getBalanceDue() +"]");
		postPayment.sendRequest();
		getConversationIdMap().put(convoMapKey, postPayment.getConversationID());
		if(getIsNegativeScenario().equalsIgnoreCase("true")){
			TestReporter.log(postPayment.getFaultString());
			TestReporter.assertTrue(postPayment.getFaultString().contains(getNegativeScenarioErrorMessage()), "The expected error message ["+getNegativeScenarioErrorMessage()+"] was not found in the fault string ["+postPayment.getFaultString()+"]");
		}else{TestReporter.logAPI(!postPayment.getResponseStatusCode().equals("200"), "An error occurred while attempting to post a card payment.", postPayment);}		
		//Set payment metadata from the post payment response
		setValuesFromPostPaymentResponse(postPayment);
		setPaidAmount(getPaidAmount() + Double.parseDouble(getAmountToPay()));
	}
	/**
	 * Sets the user-defined payment scenario, and invokes a method to make a check payment
	 * @param scenario - user-defined payment scenario
	 */
	public void makeCheckPayment(String scenario){
		setScenario(scenario);
		makeCheckPayment();
	}
	/**
	 * Sets the payment scenario if one is not set, and invokes a method to make a check payment
	 */
	public void makeCheckPayment(){	
		if(getScenario() == null || getScenario().isEmpty()) setScenario(defaultCheckPaymentScenario);
		// Retrieve folio balance due
		retrieveFolioBalanceDue();
		postCheckPayment();
	}
	/**
	 * Posts a check payment for the current reservation
	 * @return PostCheckPayment, current instance of the FolioInterface
	 */
	private PostCheckPayment postCheckPayment(){
		PostCheckPayment postPayment = new PostCheckPayment(getEnvironment(), getScenario());
		postPayment.setAuthorizationNumber(Randomness.randomNumber(4).toString());
		setCheckNumber(Randomness.randomNumber(4).toString());
		postPayment.setCheckNumber(getCheckNumber());
		setAmountToPay(getBalanceDue());
		postPayment.setConvertedAmountAmount(getAmountToPay());
		postPayment.setTenderedAmountAmount(getAmountToPay());
		postPayment.setDocumentOriginator(getPrimaryGuestFirstName() + " " + getPrimaryGuestLastName());
		postPayment.setFolioId(getFolioId());
		postPayment.setLocationId(getLocationId());
		postPayment.setPostingDate(Randomness.generateCurrentXMLDate());
		postPayment.setThreeLetterPartyName(getPrimaryGuestLastName().substring(0, 3));
		
		determineBankedInUser_BankingAccountingCenterName();
		postPayment.setUser(getLiloUser());
		postPayment.setBankingAccountName(getBankingAccountingCenterName());
		
		postPayment.sendRequest();
		TestReporter.logAPI(!postPayment.getResponseStatusCode().equals("200"), "An error occurred make a check payment", postPayment);		
		//Set payment metadata from the post payment response
		setValuesFromPostPaymentResponse(postPayment);
		setPaidAmount(getPaidAmount() + Double.parseDouble(getAmountToPay()));		
		TestReporter.logStep("Make check payment on Folio [" + getFolioId() +"] for the amount of [" + getBalanceDue() +"]");
		return postPayment;
	}
	/**
	 * Grabs all banked-in lilo users with the manager role, and uses the resulting data to populate the postCheckPayment request
	 */
	private void determineBankedInUser_BankingAccountingCenterName(){
		UserBankOut bankOut;
		UserBankIn bankIn = null;
		String defaultUser = "test5109.use";
		setLiloUser(defaultUser);
		String query = Dreams.getBankAccountcEnterNameByUserName(getLiloUser());
		setDatabase(new OracleDatabase(getEnvironment(), "Dreams"));
		TestReporter.log("LILO User Query: " + query);
		setRecordset(new Recordset(odb.getResultSet(query)));
		
		if(getRecordset().getRowCount() == 0){
			setLiloUser(defaultUser);
			if(isUserBankedIn()){
				TestReporter.log("Default LILO user ["+getLiloUser()+"] was banked-in.  Attemeting to bank-out the defaulut user.");
				bankOut = new UserBankOut(getEnvironment());
				bankOut.setBagnumber(Randomness.randomNumber(4));
				bankOut.setUser(getLiloUser());
				bankOut.sendRequest();
			}
			bankInLiloUser(bankIn);
			setRecordset(new Recordset(odb.getResultSet(query)));
		}
		setRecordset(new Recordset(odb.getResultSet(query)));
		setBankingAccountingCenterName(getRecordset().getValue(1, 1));
	}
	/**
	 * Banks-in a LILO user with the 'Manager' role
	 * @param bankIn - UserBankIn instance from a calling method
	 */
	private void bankInLiloUser(UserBankIn bankIn){
		TestReporter.log("Banking user " + getLiloUser() + " into location '" + getLocationId() +"'");
		bankIn = new UserBankIn(getEnvironment());
		bankIn.setUser(getLiloUser());
		bankIn.setLocationId(getLocationId());
		bankIn.setTillType("Manager");
		bankIn.sendRequest();
	}
	/**
	 * Determines if a LILO user is banked-in
	 * @return - boolean, true if the user is banked-in, false otherwise
	 */
	private boolean isUserBankedIn(){
		TestReporter.log("Check if " +getLiloUser()+" is banked in");		
		IsUserBankedIn isUserBankedIn = new IsUserBankedIn(getEnvironment());
		isUserBankedIn.setUser(getLiloUser());
		isUserBankedIn.sendRequest();
		try {
			isUserBankedIn.getLocationId();
			return true;
		}
		catch (XPathNotFoundException xpnfe) {return false;}
	}
	/**
	 * Makes the first night's deposit
	 */
	public void makeFirstNightDeposit(){
		if(getScenario() == null || getScenario().isEmpty()) setScenario(defaultCardPaymentScenario);
		retrieveFolioBalanceDue();
		postCardPayment(getScenario(), getDepositDue(), false);	
	}
	/**
	 * Makes a payment in full using a credit card
	 */
	public void makeFullCardPayment(){
		retrieveFolioBalanceDue();
		setAmountToPay("total");
		postCardPayment(defaultCardPaymentScenario);		
	}
	/**
	 * Makes a payment in full using a check
	 */
	public void makeFullCheckPayment(){
		if(getScenario() == null || getScenario().isEmpty()) setScenario(defaultCheckPaymentScenario);
		retrieveFolioBalanceDue();
		setAmountToPay("total");
		postCheckPayment();	
	}	
	/**
	 * Makes a split card payment
	 * @param scenario - virtual table scenario
	 */
	public void makeSplitCardPayment(String scenario){		
		setVirtualTable(scenario);
		String[] cardPaymentTypes = getDatatable().getDataParameter("PaymentType").split(";");
		String[] cardPaymentMethods = getDatatable().getDataParameter("PaymentMethod").split(";");
		String[] status = getDatatable().getDataParameter("Status").split(";");
		String[] delay = getDatatable().getDataParameter("Delay").split(";");
		String[] ccv = getDatatable().getDataParameter("EnterCCV").split(";");
		String[] incidentals = getDatatable().getDataParameter("Incidentals").split(";");
		String[] amounts = getDatatable().getDataParameter("Amount").split(";");
		
		for(int payments = 0; payments < cardPaymentTypes.length; payments++){
			setCardPaymentType(cardPaymentTypes[payments]);
			setCardPaymentMethod(cardPaymentMethods[payments]);
			setCardStatus(status[payments]);
			setCardDelay(delay[payments]);
			setCardCCV(ccv[payments]);
			setIncidentals(Boolean.parseBoolean(incidentals[payments]));
			setAmountToPay(amounts[payments]);
			TestReporter.log("Payment Type [" + payments + "]: " + getCardPaymentType());
			TestReporter.log("Payment Method [" + payments + "]: " + getCardPaymentMethod());
			TestReporter.log("Status [" + payments + "]: " + getCardStatus());
			TestReporter.log("Delay [" + payments + "]: " + getCardDelay());
			TestReporter.log("Enter CCV [" + payments + "]: " + getCardCCV());
			
			retrieveFolioBalanceDue();
			setScenario("split");
			postCardPayment(getScenario());
		}		
	}
}