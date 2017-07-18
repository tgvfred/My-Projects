package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.exceptions.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_emptyRequest extends AccommodationBaseTest{

	private String environment;
	
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void testBefore(String environment) {
        this.environment = environment;
        
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage"})
	public void testSearchPackage_packageCodeOnly(){
		
		String faultString = "Validation Failed. : Result size too large. 3125 rows selected, which exceeds the maximum of 500";
		
		SearchPackage search = new SearchPackage(environment, "Main");
		search.setPackageCode("X697A");
		search.sendRequest();
		
		TestReporter.assertEquals(search.getFaultString().replaceAll("\\s", ""), faultString.replaceAll("\\s", ""), "Verify that the fault string [" + search.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(search, AccommodationErrorCode.RESULT_SIZE_TOO_LARGE_EXCEPTION);
	}
}
