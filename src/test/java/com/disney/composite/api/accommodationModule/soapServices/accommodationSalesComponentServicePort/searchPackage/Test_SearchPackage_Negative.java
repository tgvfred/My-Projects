package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.exceptions.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_Negative extends AccommodationBaseTest{

	private String environment;
	
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void testBefore(String environment) {
        this.environment = environment;
        
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_emptyRequest(){
		
		String faultString = "Validation Failed. : Result size too large. 3125 rows selected, which exceeds the maximum of 500";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.RESULT_SIZE_TOO_LARGE_EXCEPTION);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_nullSalesChannel(){
		
		String faultString = "Data not found. : No Packages could be found for channelIDs='[0]' and bookDate='Tue Jul 18 00:00:00 EDT 2017' and arriveDate='null' and packageCode='null' and packageDescription='null' and roomOnly=null";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.setSalesChannelIDs(" ");
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.DATA_NOT_FOUND_EXCEPTION);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_nullPackageCode(){
		
		String faultString = "Data not found. : No Packages could be found for channelIDs='[1]' and bookDate='Tue Jul 18 00:00:00 EDT 2017' and arriveDate='Tue Jul 18 00:00:00 EDT 2017' and packageCode=' ' and packageDescription='R Room Only' and roomOnly=null";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.setBookingDate(Randomness.generateCurrentXMLDate());
		search.setPackageDescription("R Room Only");
		search.setPackageCode(" ");
		search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
		search.setSalesChannelIDs("1");
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.DATA_NOT_FOUND_EXCEPTION);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_resortArrivalDateOnly(){
		
		String faultString = "Validation Failed. : Result size too large. 718 rows selected, which exceeds the maximum of 500";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.RESULT_SIZE_TOO_LARGE_EXCEPTION);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_bookingDateOnly(){
		
		String faultString = "Validation Failed. : Result size too large. 3125 rows selected, which exceeds the maximum of 500";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.setBookingDate(Randomness.generateCurrentXMLDate());
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.RESULT_SIZE_TOO_LARGE_EXCEPTION);
	}
	
}
