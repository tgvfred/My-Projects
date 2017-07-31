package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCPointsHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestRetrieveRates_DVC_Points extends BookDVCPointsHelper {
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(String environment) {
		setEnvironment(environment);

		setUseDvcResort(true);
		setUseNonZeroPoints(true);
		setBook(bookDvcReservation("testBookWithPay_MP", 1));
		setTpId(getFirstBooking().getTravelPlanId());
	}
	 @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveRates" })
		public void TestRetrieveRates_dvcPointsReservation() {
		 String tcgId = getBook().getTravelComponentGroupingId();
			TestReporter.logScenario("Retieve DVC Points");
			RetrieveRates retrieve = new RetrieveRates(environment, "retrieveRates");
			retrieve.setTravelComponentGroupingId(tcgId);
			retrieve.sendRequest();
			TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getFirstBooking().getTravelComponentGroupingId() + "]", retrieve);
			
			// Old vs New Validation
			if (Environment.isSpecialEnvironment(environment)) {
				RetrieveRates clone = (RetrieveRates) retrieve.clone();
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