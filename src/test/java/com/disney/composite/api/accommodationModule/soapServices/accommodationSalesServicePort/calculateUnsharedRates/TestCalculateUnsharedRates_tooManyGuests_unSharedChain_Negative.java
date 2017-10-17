package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Regex;
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

        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.sendRequest();

        String faultString = "Error Invoking Packaging Service  : Party size of [0-9]+ exceeds maximum occupancy.*";
        boolean contains = Regex.match(faultString, calculate.getFaultString());

        validateApplicationError(calculate, AccommodationErrorCode.PACKAGING_SERVICE_FAILURE);

        TestReporter.assertTrue(contains, "Verify that the fault string [" + calculate.getFaultString() + "] contains that which is expected [" + faultString + "].");

    }
}
