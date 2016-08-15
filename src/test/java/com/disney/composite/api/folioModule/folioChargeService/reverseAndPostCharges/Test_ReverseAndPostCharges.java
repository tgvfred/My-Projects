package com.disney.composite.api.folioModule.folioChargeService.reverseAndPostCharges;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.soapServices.folioModule.folioCharge.operations.PostCharges;
import com.disney.api.soapServices.folioModule.folioCharge.operations.ReverseAndPostCharges;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class Test_ReverseAndPostCharges extends BaseTest{
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostChargesWithAddon(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		
		reverseAndPostCharges.sendRequest();
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostChargesWithNoAddon(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_Approved(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("APPROVED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_NotApproved(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("NOT_APPROVED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_Deleted(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("DELETED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_Processed(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("PROCESSED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_ADDRESS_REQUIRED(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("ADDRESS_REQUIRED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	

	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "reverseAndPostCharges", "s140763"})
	public void testReverseAndPostCharges_Status_Failed(){
		String tcId = "";
		String tcgId = "";	
		String initialChargeAmount = "200";
		String updatedChargeAmount = "500";
		String productTypeName = "";
		TestReporter.logStep("Book an initial reservation");
		hh = new HouseHold(1);
		ScheduledEventReservation  res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = postCharges(res, initialChargeAmount, updatedChargeAmount, productTypeName, tcgId, tcId);
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		reverseAndPostCharges.setAllAmounts(updatedChargeAmount, "USD");
		reverseAndPostCharges.setProduct(res.getProductId(), productTypeName);
		reverseAndPostCharges.setTravelComponentGroupId(tcgId);
		reverseAndPostCharges.setTravelComponentId(tcId);
		reverseAndPostCharges.setLocationId(res.getFacilityId());
		reverseAndPostCharges.setRevenueStatus("FAILED");
		reverseAndPostCharges.sendRequest();
		
		String postChargeId = reverseAndPostCharges.getChargeId();
		TestReporter.logAPI(!reverseAndPostCharges.getResponseStatusCode().contains("200"), reverseAndPostCharges.getFaultString() ,reverseAndPostCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", postChargeId), "The new Charge ID ["+ postChargeId +"] is not numeric as expected.");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postCharges.getChargeId() + "] in Folio.Chrg table was DELETED");
		Recordset rsOldChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postCharges.getChargeId())));
		TestReporter.assertTrue(rsOldChargeId.getRowCount() == 0 , "Validate no Charge items returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]");

		TestReporter.logStep("Validate Charge info for Charge ID [" + postChargeId + "] in Folio.Chrg table");
		Recordset rsNewChargeId = new Recordset(db.getResultSet(Dreams.getChargeInfo(postChargeId)));
		TestReporter.assertTrue(rsNewChargeId.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(postCharges.getChargeId()) +"]. Records found [" +rsNewChargeId.getRowCount() +"]");
		TestReporter.assertTrue(rsNewChargeId.getValue("CHRG_AM").equals(updatedChargeAmount) , "Validiate Charge Amount in DB[" + rsNewChargeId.getValue("CHRG_AM")+"] equals [" + updatedChargeAmount +"]");
	}
	
	private PostCharges postCharges(ScheduledEventReservation res, String initialChargeAmount, String updatedChargeAmount,	String productTypeName, String tcgId, String tcId ){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = null;
		
		if(tcId.isEmpty()) throw new AutomationException("No TC ID found using SQL: " + Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()));
		TestReporter.logStep("Post a new charge to reservation [" + res.getConfirmationNumber() + "]");	
		PostCharges postCharges = new PostCharges(environment, "MinimalInfo");
		postCharges.setAllAmounts(initialChargeAmount, "USD");
		postCharges.setProduct(res.getProductId(), productTypeName);
		postCharges.setTravelComponentGroupId(tcgId);
		postCharges.setTravelComponentId(tcId);
		postCharges.setLocationId(res.getFacilityId());
		postCharges.sendRequest();
		
		String chargeId = postCharges.getChargeId();
		
		TestReporter.logAPI(!postCharges.getResponseStatusCode().contains("200"), postCharges.getFaultString() ,postCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", chargeId), "The Charge ID ["+ chargeId +"] is not numeric as expected.");
		
		TestReporter.logStep("Validate Charge info for Charge ID [" + chargeId + "] in Folio.Chrg table");	
		rs = new Recordset(db.getResultSet(Dreams.getChargeInfo(chargeId)));
		TestReporter.assertTrue(rs.getRowCount() > 0 , "Validate Charge items are returned in query [" + Dreams.getChargeInfo(chargeId) +"]. Records found [" +rs.getRowCount() +"]");
		TestReporter.assertTrue(rs.getValue("CHRG_AM").equals(initialChargeAmount) , "Validiate Charge Amount in DB[" + rs.getValue("CHRG_AM")+"] equals [" + initialChargeAmount +"]");
		
		return postCharges;
	}
}
