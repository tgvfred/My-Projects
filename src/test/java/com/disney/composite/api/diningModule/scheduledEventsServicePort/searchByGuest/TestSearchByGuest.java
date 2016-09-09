package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByGuest;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByGuest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByGuest extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setLastName(hh.primaryGuest().getLastName() + Randomness.randomString(5));
		hh.primaryGuest().primaryAddress().setZipCode("90001");
		hh.primaryGuest().primaryAddress().setState("California");
		hh.primaryGuest().primaryPhone().setNumber(String.valueOf(Randomness.randomNumberBetween(10000, 99999)) + String.valueOf(Randomness.randomNumberBetween(10000, 99999)));
		hh.primaryGuest().primaryEmail().setEmail(hh.primaryGuest().getFullName() + String.valueOf(Randomness.randomNumberBetween(10000, 99999)) + "@automationTesting.com");
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterClass(alwaysRun = true)
	public synchronized void closeSession() {
		try{res.cancel();}
		catch (Exception e){}
	}


	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_ReservationNumber(){
		TestReporter.logStep("Search By Guest Reservation Number");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(res.getConfirmationNumber());
		sendAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_CancellationNumber(){
		TestReporter.logStep("Search By Guest Reservation Number");
		ScheduledEventReservation localRes = preReq();
		localRes.cancel();
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(localRes.getCancellationNumber());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_LastNameAndPostalCode(){
		TestReporter.logStep("Search By Guest Last Name and Postal Code");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLastName(res.party().primaryGuest().getLastName());
		search.setPostalCode(res.party().primaryGuest().primaryAddress().getZipCode());
		sendAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_LastNameAndPhoneNumber(){
		TestReporter.logStep("Search By Guest Last Name and Phone Number");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLastName(res.party().primaryGuest().getLastName());
		search.setPhoneNumber(res.party().primaryGuest().primaryPhone().getNumber());
		sendAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_LastNameAndEmail(){
		TestReporter.logStep("Search By Guest Last Name and Email");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLastName(res.party().primaryGuest().getLastName());
		search.setEmail(res.party().primaryGuest().primaryEmail().getEmail());
		sendAndValidate(search);
	}
	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_EmailOnly(){
		TestReporter.logStep("Search By Guest Email Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setEmail(res.party().primaryGuest().primaryEmail().getEmail());
		sendAndValidate(search);
	}


	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByGuest_OdsId(){
		TestReporter.logStep("Search By Guest ODS Id");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByGuestId(res.party().primaryGuest().getGuestId())));
		search.setGuestOdsIds(rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		sendAndValidate(search);
	}
	
	private void sendAndValidate(SearchByGuest search){
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search: " + search.getFaultString(), search);
		TestReporter.assertEquals(search.getReservationNumber(), res.getConfirmationNumber(), "Verify that the actual reservation number ["+search.getReservationNumber()+"] matches the expected reservation number ["+res.getConfirmationNumber()+"].");
	
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByGuest", false);
		logItems.addItem("PartyIF", "retrieveParty", false);		
		validateLogs(search, logItems);
	}
	
	
	private ScheduledEventReservation preReq(){
		ScheduledEventReservation localRes = new EventDiningReservation(environment, new HouseHold(1));
		localRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		return res;
	}
}
