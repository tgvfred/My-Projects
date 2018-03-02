package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.unshareAccommodations;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.UnshareAccommodations;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestUnshareAccommodations_Negative extends AccommodationBaseTest {
private String faultString = "Required parameters are missing";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations", "negative" })
    public void testUnshareAccommodations_NullRequest() {

        UnshareAccommodations unshare = new UnshareAccommodations(environment);
        unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request", BaseSoapCommands.REMOVE_NODE.toString());
        unshare.sendRequest();

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations", "negative" })
    public void testUnshareAccommodations_NoAccommFound() {

        // first unshare
        UnshareAccommodations unshare = new UnshareAccommodations(environment, "AccomNotFound");
        unshare.setBookingDate("1", Randomness.generateCurrentXMLDatetime());
        unshare.setOverrideFreeze("1", "false");
        unshare.setPackageCode("1", "Q747P");
        unshare.setResortCode("1", "1S");
        unshare.setAdditionalChargeOverride("1", "false");
        unshare.setRateDetailsBasePrice("1", "152.0");
        unshare.setRateDetailsDate("1", date(2));
        unshare.setRateDetailsOverriden("1", "false");
        unshare.setRateDetailsShared("1", "false");
        unshare.setResortEndDate("1", date(5));
        unshare.setResortStartDate("1", date(4));
        unshare.setGuestAge("1", "49");
        unshare.setGuestType("1", "ADULT");
        unshare.setGuestFirstName("1", "Elijah");
        unshare.setGuestLastName("1", "Holland");
        unshare.setGuestMiddleName("1", "Automation");
        unshare.setGuestPartyId("1", "0");
        unshare.setGuestDoNotMailIndicator("1", "false");
        unshare.setGuestDoNotPhoneIndicator("1", "false");
        unshare.setGuestId("1", "355553099");
        unshare.setGuestDclGuestId("1", "0");
        unshare.setGuestActive("1", "false");
        unshare.setGuestPurposeOfVisit("1", "Leisure");
        unshare.setCorrelationId("1", "0");
        unshare.setRoomTypeCode("1", "SA");
        unshare.setRsrReservation("1", "false");
        unshare.setTcgId("1", getBook().getTravelComponentGroupingId());
        unshare.setTcId("1", getBook().getTravelComponentId());
        unshare.setTravelStatus("1", "BOOKED");
        unshare.setShared("1", "false");
        unshare.setUnsharedRoomTpsId("1", "480511386857");

        // second unshare
        unshare.setBookingDate("2", Randomness.generateCurrentXMLDatetime());
        unshare.setOverrideFreeze("2", "false");
        unshare.setPackageCode("2", "Q747P");
        unshare.setResortCode("2", "1S");
        unshare.setAdditionalChargeOverride("2", "false");
        unshare.setRateDetailsBasePrice("2", "152.0");
        unshare.setRateDetailsDate("2", date(2));
        unshare.setRateDetailsOverriden("2", "false");
        unshare.setRateDetailsShared("2", "false");
        unshare.setResortEndDate("2", date(5));
        unshare.setResortStartDate("2", date(4));
        unshare.setGuestAge("2", "49");
        unshare.setGuestType("2", "ADULT");
        unshare.setGuestFirstName("2", "Elijah");
        unshare.setGuestLastName("2", "Holland");
        unshare.setGuestMiddleName("2", "Automation");
        unshare.setGuestPartyId("2", "0");
        unshare.setGuestDoNotMailIndicator("2", "false");
        unshare.setGuestDoNotPhoneIndicator("2", "false");
        unshare.setGuestId("2", "355553099");
        unshare.setGuestDclGuestId("2", "0");
        unshare.setGuestActive("2", "false");
        unshare.setGuestPurposeOfVisit("2", "Leisure");
        unshare.setCorrelationId("2", "0");
        unshare.setRoomTypeCode("2", "SA");
        unshare.setRsrReservation("2", "false");
        unshare.setTcgId("2", getBook().getTravelComponentGroupingId());
        unshare.setTcId("2", getBook().getTravelComponentId());
        unshare.setTravelStatus("2", "BOOKED");
        unshare.setShared("2", "false");
        unshare.setUnsharedRoomTpsId("2", "480511386569");
        System.out.println(unshare.getRequest());
        unshare.sendRequest();

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    public String date(int daysOut) {
        return Randomness.generateCurrentXMLDatetime(daysOut);
    }

}
