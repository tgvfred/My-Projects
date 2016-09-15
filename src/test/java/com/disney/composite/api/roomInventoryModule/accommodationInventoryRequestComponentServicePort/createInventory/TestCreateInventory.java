package com.disney.composite.api.roomInventoryModule.accommodationInventoryRequestComponentServicePort.createInventory;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.roomInventoryModule.accommodationInventoryRequestComponentServicePort.operations.CreateInventory;
import com.disney.api.soapServices.roomInventoryModule.accommodationInventoryRequestComponentServicePort.operations.UpdateInventoryForScheduledEvents;
import com.disney.test.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestCreateInventory extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	//@Test(groups = {"api","presmoke",  "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort"})
	public void Presmoke_testCreateInventory(){
		CreateInventory create = new CreateInventory(environment, "Default");
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), create.getFaultString(), create);
	}
}