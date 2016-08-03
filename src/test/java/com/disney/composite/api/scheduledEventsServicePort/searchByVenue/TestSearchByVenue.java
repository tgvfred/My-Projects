package com.disney.composite.api.scheduledEventsServicePort.searchByVenue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestSearchByVenue extends BaseTest{
	protected ScheduledEventReservation res = null;
	private String[] facilityIds = {"354261", "90001724", "178451", "90001744", "354279", "90002012", "90001778", "90001786",
			"349059", "348023", "15525573", "90001798", "90001819", "90001833", "90001865", "17364609", "90001879",
			"90001951", "90001296", "354372", "354378", "90001983", "90001985", "90002606", "90002006", "343233", "90002032",
			"90002041", "354414", "90002053", "90002044", "354432", "90001873", "90002440", "354435", "17564465", "90002068",
			"90002086", "90002084", "90002100", "293704", "90002114", "90002122", "90002126", "90002124", "90002156",
			"17720675", "17134590", "354468", "354474", "221625", "90002660", "247904", "90002402", "364242", "90002237",
			"90002245", "90002670", "98575", "266442", "7813806", "90002274", "221626", "90002668", "90002678", "354528",
			"17346592", "90001473", "90002287", "293563", "90002686", "16526362", "220739", "15525574", "90002336", "90002376",
			"354555", "23170", "90002412", "215686"};
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
		res = new TableServiceDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun = true)
	public void closeSession(ITestResult test) {
		if(res != null)
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue(){
		boolean reservationsReturned = false;
		TestReporter.logStep("Search By Venue");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search = new SearchByVenue(environment, "Main");
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(Integer.parseInt("90")));
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(Integer.parseInt("-90")));
		try{search.setSourceAccountingCenter(res.getSourceAccountingCenter());}
		catch(XPathNullNodeValueException e){search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());}
		for(String facility : facilityIds){	
			search.setFacilityId(facility);
			search.sendRequest();
			try{
				reservationsReturned = search.getEventReservations().getLength() > 0;
				break;
			}catch(Exception e){}
		}
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search."+ search.getFaultString(), search);
		TestReporter.assertTrue(reservationsReturned, "No reservations were returned for facility id ["+res.getFacilityId()+"].");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByVenue", false);
		logItems.addItem("FolioServiceIF", "retrieveFolioPaymentSummary", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);		
		validateLogs(search, logItems, 10000);
	}
}