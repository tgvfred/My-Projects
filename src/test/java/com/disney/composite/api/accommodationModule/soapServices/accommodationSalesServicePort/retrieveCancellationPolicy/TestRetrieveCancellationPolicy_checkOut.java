package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationPolicy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckOut;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy_checkOut extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate());
        departureDate.set(Randomness.generateCurrentXMLDate());
        isComo.set("true");
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_checkOut() {

        String faultString = "Cannot calculate Cancel fee";

        CheckIn checkIn = new CheckIn(environment, "Main");
        try {
            checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } catch (XPathNotFoundException e) {
            checkIn.setRequestNodeValueByXPath("/Envelope/Body/checkIn/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
            checkIn.setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/travelComponentGroupingId", getBook().getTravelComponentGroupingId());
        }
        checkIn.setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/checkInGuestDetails/guestId", BaseSoapCommands.REMOVE_NODE.toString());
        checkIn.sendRequest();

        CheckOut check = new CheckOut(environment, "Check Out");
        check.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        check.setRequestNodeValueByXPath("/Envelope/Body/checkOut/request/locationId", BaseSoapCommands.REMOVE_NODE.toString());
        check.sendRequest();

        TestReporter.logAPI(!check.getResponseStatusCode().equals("200"), "An error occurred when sending request", check);

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }
}