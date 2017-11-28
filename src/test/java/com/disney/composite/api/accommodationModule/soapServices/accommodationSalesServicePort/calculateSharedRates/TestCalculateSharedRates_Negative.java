package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateSharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates", "negative" })
    public void testCalculateSharedRates_nullRequest() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "Main");
        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.sendRequest();

        String faultString = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(calculate, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

        TestReporter.assertEquals(calculate.getFaultString(), faultString, "Verify that the fault string [" + calculate.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates", "negative" })
    public void testCalculateSharedRates_nullAccommodations() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "Main");
        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.sendRequest();

        String faultString = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(calculate, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

        TestReporter.assertEquals(calculate.getFaultString(), faultString, "Verify that the fault string [" + calculate.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates", "negative" })
    public void testCalculateSharedRates_emptyRoomReservationDetail() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "Main");

        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/rateDetails", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/ticketDetails", BaseSoapCommands.REMOVE_NODE.toString());

        calculate.sendRequest();

        String faultString = "Guest is Required : GuestReferenceDetails cannot be empty";

        validateApplicationError(calculate, AccommodationErrorCode.GUEST_REQUIRED);

        TestReporter.assertEquals(calculate.getFaultString(), faultString, "Verify that the fault string [" + calculate.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}
