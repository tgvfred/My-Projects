package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_roomOnlyShared extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);

        setIsShared(true);
        bookReservation();

        Share share = new Share(Environment.getBaseEnvironmentName(environment));
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        bookReservation();
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.setRoomNumber(BaseSoapCommands.REMOVE_NODE.toString());
        share.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing a res: " + share.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_roomOnlyShared() {

        // Flips correctly while booking, but the it isn't flipped in RetrieveSummary RS
        RetrieveSummary retrieve = new RetrieveSummary(Environment.getBaseEnvironmentName(environment), "Main");
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            try {
                retrieve.sendRequest();
                tries++;
                retrieve.getShared();
                success = true;
            } catch (XPathNotFoundException e) {
            }
        } while (tries < maxTries && !success);

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", retrieve);
        TestReporter.assertTrue(success, "Verify that a shared node was found in the response.");
        TestReporter.assertTrue(retrieve.getShared().equals("true"), "Shared Successfully flipped! ");

    }

}
