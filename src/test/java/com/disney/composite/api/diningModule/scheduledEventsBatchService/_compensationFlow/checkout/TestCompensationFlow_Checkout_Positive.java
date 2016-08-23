package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow.checkout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.Checkout;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveReservationsForAutoCheckout;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

public class TestCompensationFlow_Checkout_Positive extends BaseTest{
	private String date;
	private Map<String, String> reservations = new HashMap<String, String>();
	private String reservation;
	private Checkout checkout;
	private int maxDaysOut = 45;
	private String rrId;
	private String startDateTime;
	
	@Override
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout");
		RetrieveReservationsForAutoCheckout retrieve = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		int daysOut = 0;
		do{
			daysOut++;
			date = Randomness.generateCurrentXMLDatetime(daysOut);
			retrieve.setProcessDate(date);
			retrieve.sendRequest();
			TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto checkout: " + retrieve.getFaultString(), retrieve);
			reservations = retrieve.getAllReservationNumbers();
		}while(reservations.size() == 0 && daysOut <= maxDaysOut);
		TestReporter.assertTrue(reservations.size() > 0, "Verify reservations were returned between ["+Randomness.generateCurrentXMLDatetime(0)+"] and ["+Randomness.generateCurrentXMLDatetime(maxDaysOut)+"].");
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "compensation"})
	public void testCompensationFlow_Checkout_Positive(){
		TestReporter.logScenario("Checkout");
		if(reservations.size() == 0) throw new SkipException("No reservations were returned for the date ["+date+"].");
		Iterator<String> it = reservations.values().iterator();
		while(it.hasNext()){
			reservation = it.next().toString();
			if(retrieveResourceReservationId(retrieveTcgType(reservation))) break;
		}
		checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest(rrId, startDateTime);
		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking out reservation ["+reservation+"]: " + checkout.getFaultString(), checkout);
		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Verify the reservation is successfully checked out.");
		TestReporter.assertEquals(reservation, checkout.getReservationNumber(), "Verify the actual reservation number ["+checkout.getReservationNumber()+"] matches the expected reservation number ["+reservation+"].");
		TestReporter.assertTrue(Integer.parseInt(checkout.getInventoryCountAfter()) == Integer.parseInt(checkout.getInventoryCountBefore()), "Verify the booked inventory ["+checkout.getInventoryCountAfter()+"] is equal to the previous value ["+checkout.getInventoryCountBefore()+"].");
	}
	
	private String retrieveTcgType(String tps){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(tps)));
		return rs.getValue("TC_GRP_TYP_NM");
	}
	
	private boolean retrieveResourceReservationId(String tcgType){
		boolean tcgTypeFound = true;
		
		switch (tcgType.toUpperCase()) {
		case "SHOWDINING":
			Retrieve retrieveShow = new Retrieve(environment, "RetrieveDiningEvent"); 
			retrieveShow.setReservationNumber(reservation);
			retrieveShow.sendRequest();
			rrId = retrieveShow.getReservableResourceId();
			tcgTypeFound = !retrieveShow.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveShow.getServiceStartDate().replace("T", " ");
			break;
		case "TABLESERVICEDINING":
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve retrieveTable = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve(environment, "Main");
			retrieveTable.setReservationNumber(reservation);
			retrieveTable.sendRequest();
			rrId = retrieveTable.getReservableResourceId();
			tcgTypeFound = !retrieveTable.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveTable.getServiceStartDate().replace("T", " ");
			break;
		case "EVENTDINING":
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve retrieveEvent = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve(environment, "RetrieveDiningEvent");
			retrieveEvent.setReservationNumber(reservation);
			retrieveEvent.sendRequest();
			rrId = retrieveEvent.getReservableResourceId();
			tcgTypeFound = !retrieveEvent.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveEvent.getServiceStartDate().replace("T", " ");
			break;
		default:
			tcgTypeFound = false;
			break;
		}
		return tcgTypeFound;
	}
}
