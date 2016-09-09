package com.disney.composite.api.folioModule.folioChargeService.reverseAndPostCharges;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.FolioErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.folioModule.folioCharge.operations.PostCharges;
import com.disney.api.soapServices.folioModule.folioCharge.operations.ReverseAndPostCharges;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class Test_ReverseAndPostCharges_Negative extends BaseTest{
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_NoChargeDetailAmount(){
		TestReporter.logStep("ReverseAndPostCharge - Removed Charge Detail Amount");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/amount", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"One or more of the required fields missing");
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingChargedBy(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Charged By");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargedBy", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"ChargedBy:null ");
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingExternalReferenceName(){
		TestReporter.logStep("ReverseAndPostCharge - Missing External Reference Name");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeGroupExternalReference/referenceName", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.REQ_FIELD_MISSING,"ReferenceName and ReferenceValue are required in external reference-ExternalReferenceTO");		
	}


	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_ExternalReferenceNameNotDreams_TCG(){
		TestReporter.logStep("ReverseAndPostCharge - When External Reference Name Not DREAMS_TCG");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeGroupExternalReference/referenceName", "Dreams_TP");
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INV_CHRG_GRP_REF,"Invalid ChargeGroup external reference");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingChargeItemActualAmmount(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Charge Item Actual Amount");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/actualAmount", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"Base ChargedAmount");	
	}


	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_ChargeItemActualAmountNotMatching(){
		TestReporter.logStep("ReverseAndPostCharge - Charge Item Actual Amount is 0");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setActualAmount("0", "USD");
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.FOLIO_NOT_FOUND_FOR_TRAPPING,"BillTrap did not find a Folio to trap the Charge amount");			
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingChargeItemChargeAmmount(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Charge Item ctual Amount");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/chargedAmount", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"Base ChargedAmount");	
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_ChargeItemChargeAmountis0(){
		TestReporter.logStep("ReverseAndPostCharge - Charge Item Charge Amount is 0");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setChargedAmount("0", "USD");
		sendAndValidate(reverseAndPostCharges,LiloSystemErrorCode.UNEXPECTED_ERROR,"reverseAndPostCharges : Infinite or NaN");		
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingDepositDueDate(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Deposit Due Date");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setDepositDueDate( BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"DepositRequiredAmount and DepositDueDate are required in DepositRequirement");			
	}	

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingDepositDueAmount(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Deposit Due Amount");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositRequiredAmount", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"DepositRequiredAmount and DepositDueDate are required in DepositRequirement");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_InvalidRevenueTypeName(){
		TestReporter.logStep("ReverseAndPostCharge - Invalid Revenue Type Name");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRevenueType("1", "None");
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"RevenueType Configuration found in Accounting for None");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingRevenueTypeName(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Revenue Type Name");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/revenueType/revenueTypeName", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"RevenueTypeName : null");		
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingFulfillmentDate(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Fulfillment Date");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setFulfilmentDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"FulfilmentDateTime:null");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingPostingDate(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Posting Date");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setPostingDate(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"PostingDate:null");		
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingCommunicationChannel(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Communication Channel");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"ProductTypeName, ProductID, SalesChannel and CommunicationChannel are required for Product Charge.");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingProductID(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Product ID");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setProduct(BaseSoapCommands.REMOVE_NODE.toString(), "DiningProduct");
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"ProductTypeName, ProductID, SalesChannel and CommunicationChannel are required for Product Charge.");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingProductTypeName(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Product Type Name");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setProduct("12345", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"ProductTypeName, ProductID, SalesChannel and CommunicationChannel are required for Product Charge.");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingSalesChannel(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Sales Channel");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"ProductTypeName, ProductID, SalesChannel and CommunicationChannel are required for Product Charge.");
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingRevenueClassificationId(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Revenue Classification Id");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRevenueClassification(BaseSoapCommands.REMOVE_NODE.toString(),"Food");
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"RevenueClassificationID:null");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingRevenueStatus(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Revenue Status");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRevenueStatus(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"RevenueRecognitionStatus:null");			
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_InvalidRevenueStatus(){
		TestReporter.logStep("ReverseAndPostCharge - Invalid Revenue Status");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRevenueStatus(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"RevenueRecognitionStatus:null");			
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingSourceAccountingCenter(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Source Accounting Center");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setSourceAccountingCenterID(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"SourceAccountingCenter:null");	
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingTransactionAccountingCenterAndTransactionFacilityId(){
		TestReporter.logStep("ReverseAndPostCharge - Missing both Transaction Accounting Center and Transaction Facility ID");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setTransactionFacilityID(BaseSoapCommands.REMOVE_NODE.toString());
		reverseAndPostCharges.setTransactionAccountingCenterID(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"Both TransactionAccountingCenter and TransactionFacilityID can not be null.  TransactionFacilityID:null TransactionAccountingCenter:null");		
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingLocationId(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Location ID");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.INVALID_INPUT,"Location:null");
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763", "negative"})
	public void testReverseAndPostCharges_MissingChargeExternalReferences(){
		TestReporter.logStep("ReverseAndPostCharge - Missing Source Accounting Center");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeExternalReferences", BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(reverseAndPostCharges,FolioErrorCode.REQ_FIELD_MISSING,"ChargeRequest, ChargeExternalReferences and ChargeDetail are required.");
				
	}
	
	private void sendAndValidate(ReverseAndPostCharges request, ApplicationErrorCode error, String errorMessage){
		request.sendRequest();
		validateApplicationError(request, error);
		TestReporter.logAPI(!request.getFaultString().contains(errorMessage), request.getFaultString() ,request);
	}
}
