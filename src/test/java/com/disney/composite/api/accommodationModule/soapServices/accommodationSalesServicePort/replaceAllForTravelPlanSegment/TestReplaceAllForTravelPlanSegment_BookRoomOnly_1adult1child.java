package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment_BookRoomOnly_1adult1child extends AccommodationBaseTest {
    private String odsGuestId = null;
    private String tpPtyId = null;
    @SuppressWarnings("unused")
    private String assignmentOwnerId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookRoomOnly_1adult1child() {
        setSendRequest(false);
        setAddGuest(true);
        bookReservation();
        getHouseHold().sendToApi(Environment.getBaseEnvironmentName(getEnvironment()));
        getAdditionalGuests().get(1).setAge("3");
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails[2]/age", getAdditionalGuests().get(1).getAge());
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails[2]/ageType", getAgeTypeByAge(getAdditionalGuests().get(1).getAge()));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        odsGuestId = getHouseHold().primaryGuest().getOdsId();

        tpPtyId = getBook().getGuestId();
        assignmentOwnerId = getAssignmentOwnerId(getBook().getTravelPlanId());

        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        validations.validateModificationBackend(2, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 1, getBook().getTravelPlanId());
        validations.verifyChargeDetail(4, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "1", true);
        System.out.println();

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyGoMasterInfoForNewGuest(getHouseHold().primaryGuest(), odsGuestId);
    }
}
