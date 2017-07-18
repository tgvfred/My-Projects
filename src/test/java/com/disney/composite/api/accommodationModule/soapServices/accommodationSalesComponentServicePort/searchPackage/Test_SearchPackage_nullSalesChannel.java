package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.exceptions.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_nullSalesChannel extends AccommodationBaseTest{

	private String environment;
	
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void testBefore(String environment) {
        this.environment = environment;
        
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
}
