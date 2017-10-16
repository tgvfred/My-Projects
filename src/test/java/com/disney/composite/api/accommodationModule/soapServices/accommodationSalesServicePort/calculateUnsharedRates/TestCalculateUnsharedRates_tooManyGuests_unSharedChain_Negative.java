package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_tooManyGuests_unSharedChain_Negative extends AccommodationBaseTest {
    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates", "negative" })
    public void Test_CalculateUnsharedRates_tooManyGuests_unSharedChain_Negative() {

        calculate = new CalculateUnsharedRates(environment, "Main_TwoGuestRefs");
        calculate.setUnsharedChainSharedRoomDetailTCGId("0");
        calculate.setUnsharedChainSharedRoomDetailTCId("0");
        calculate.setUnsharedChainShareRoomDetailsTPSId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCGId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationTPSId("0");

        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.sendRequest();

        String faultString = "Error Invoking Packaging Service  : Party size of 6 exceeds maximum occupancy of 5 for the room type of CA.";

        validateApplicationError(calculate, AccommodationErrorCode.PACKAGING_SERVICE_FAILURE);

        TestReporter.assertEquals(calculate.getFaultString(), faultString, "Verify that the fault string [" + calculate.getFaultString() + "] is that which is expected [" + faultString + "].");

    }
}
