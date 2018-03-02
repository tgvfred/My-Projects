package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_shareAccommodations_emptyRoomReservationDetail_Negative extends AccommodationBaseTest {
    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting

        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        // guestId = getBook().getGuestId();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_emptyRoomReservationDetail_Negative() {

        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);

        ShareAccommodations share = new ShareAccommodations(environment);

        // share.setAccommodation(BaseSoapCommands.REMOVE_NODE.toString());
        share.setBookingDate(getBook().getStartDate());
        share.setFreezeId("0");
        share.setGuaranteeStatus("None");
        share.setInventoryStatus("Unassigned");
        share.setOverrideFreeze("false");
        share.setPackageCode(getPackageCode());
        share.setResortCode(getResortCode());
        share.setResortEndDate(getBook().getEndDate());
        share.setResortStartDate(getBook().getStartDate());
        share.setRoomTypeCode(getRoomTypeCode());
        share.setRSRReservations("false");
        share.setTcgId(getBook().getTravelComponentGroupingId());
        share.setTcId(getBook().getTravelComponentId());
        share.setTravelStatus("Booked");
        share.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());

        share.setShared("false");
        share.setSpecialNeedsRequested("0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/blockCode", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/checkOutDateTime", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/externalReferences", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/fplosId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/InventoryTrackingId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/modificationDate", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateCategoryCode", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/additionalCharge", retrieveRates.getAdditionalCharge());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/additionalChargeOverridden", "false");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/basePrice", retrieveRates.getBasePrice());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/rackRate", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/date", getBook().getStartDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/dayCount", retrieveRates.getDayCount());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/overidden", retrieveRates.getRateDetailsOveridden());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/shared", retrieveRates.getShared());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/netPrice", "0.0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/pointsValue", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.REMOVE_NODE.toString());
        //
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/comments", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/profiles", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomNumber", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/roomModificationContactName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/roomModificationReason", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/additionalOccupants", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/salesChannelId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/sourceExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/teamName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketGroup", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations//inventoryOverrideReason", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/inventoryOverrideContactName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/componentDetail", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/exchangeFee", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/reservationType", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/pointsPaymentReferences", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/offerIdentifiers", BaseSoapCommands.REMOVE_NODE.toString());

        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second reservation: " + share.getFaultString(), share);

        // validateApplicationError(share, MISSING_REQUIRED_PARAM_EXCEPTION);

    }
}
