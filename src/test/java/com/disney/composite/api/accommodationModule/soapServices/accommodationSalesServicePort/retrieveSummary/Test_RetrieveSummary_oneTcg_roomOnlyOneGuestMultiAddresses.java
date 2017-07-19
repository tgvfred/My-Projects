package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyOneGuestMultiAddresses extends AccommodationBaseTest{

	private String environment;
	
	private ReplaceAllForTravelPlanSegment book;
	
	@Override
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
		setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);
        bookReservation();
        
        book = new ReplaceAllForTravelPlanSegment(environment, "RoomOnlyNoTicketsMultiAddr");
        book.sendRequest();
        
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary"})
	public void testRetrieveSummary_oneTcg_roomOnlyOneGuestMultiAddresses(){
		
		RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
		retrieve.setRequestTravelComponentGroupingId(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping ["+book.getTravelComponentGroupingId()+"]", retrieve);
		
//		TestReporter.logStep("Verify Multiple Addresses are found.");
//		TestReporter.assertTrue(retrieve.getTaxExemptCertificateNumber().equals("1"), "Number of Addresses Found [2]! ");
		
		// Old vs New Validation
		if (Environment.isSpecialEnvironment(environment)) {
			RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
			clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
			clone.sendRequest();
			if (!clone.getResponseStatusCode().equals("200")) {
				TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
			}
			clone.addExcludedBaselineAttributeValidations("@xsi:nil");
			clone.addExcludedBaselineAttributeValidations("@xsi:type");
			clone.addExcludedBaselineXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
			clone.addExcludedXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
			clone.addExcludedBaselineXpathValidations("/Envelope/Header");
			TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
		}
		
	}
	
}
