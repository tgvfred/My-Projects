package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.getAccommodationExternalReferences;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.GetAccommodationExternalReferences;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_GetAccommodationExternalReferences_bookingWithNoAccommExtRef extends AccommodationBaseTest {

    private String extNum;
    private String extSrc;

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
        setSendRequest(false);
        bookReservation();

        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences" })
    public void testGetAccommodationExternalReferences_bookingWithNoAccommExtRef() {

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        get.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        get.sendRequest();
        TestReporter.logAPI(!get.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", get);

        // Validate that no external references are returned
        TestReporter.assertTrue(get.getResponseNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse").isEmpty(), "No External References Found!");

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            GetAccommodationExternalReferences clone = (GetAccommodationExternalReferences) get.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(get, true), "Validating Response Comparison");
        }

    }

}
