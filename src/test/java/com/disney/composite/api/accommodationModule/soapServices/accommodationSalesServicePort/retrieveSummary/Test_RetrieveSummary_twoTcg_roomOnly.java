package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_twoTcg_roomOnly extends AccommodationBaseTest{

	private String environment;
	
	private ReplaceAllForTravelPlanSegment book;
	private ReplaceAllForTravelPlanSegment book1;
	
	private Integer two = 2;
	
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
        
        book = new ReplaceAllForTravelPlanSegment(environment, "RoomOnlyNoTickets");
        book.sendRequest();
        
        book1 = new ReplaceAllForTravelPlanSegment(environment, "RoomOnlyNoTickets");
        book1.sendRequest();
        
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary"})
	public void testRetrieveSummary_twoTcg_roomOnly(){
		
		RetrieveSummary retrieve = new RetrieveSummary(environment, "Main2TCG");
		retrieve.setRequestTravelComponentGroupingIdIndex("1", book.getTravelPlanSegmentId());
		retrieve.setRequestTravelComponentGroupingIdIndex("2", book1.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping ["+book.getTravelComponentGroupingId()+"]", retrieve);
		
		TestReporter.logStep("Verify two AccommodationsSummaryDetails node is found & that two different TPS IDs are found.");
		TestReporter.assertTrue(retrieve.getAccommodationsSummaryDetails().equals(two), "Number of AccommodationsSummaryDetails nodes found is ["+retrieve.getAccommodationsSummaryDetails()+"]! and the two TPS IDs are ["+retrieve.getTravelPlanSegmentId("1")+"] & ["+retrieve.getTravelPlanSegmentId("2")+"]");
		
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
