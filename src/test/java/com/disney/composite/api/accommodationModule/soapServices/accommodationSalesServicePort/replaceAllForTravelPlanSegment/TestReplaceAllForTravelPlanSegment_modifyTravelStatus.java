package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment_modifyTravelStatus extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;

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
        isComo.set("true");
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
    }

    // Invalid case
    // @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_modifyTravelStatus() {

        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setTravelComponentId(tcId);
        // getBook().setRoomDetailsTravelStatus("Checked In");
        getBook().setReplaceAll("true");
        // getBook().sendRequest();

        getBook().sendRequest();

        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying to a group booking: " + getBook().getFaultString(), getBook());
        tpPtyId = getBook().getGuestId();

        validations();
    }

    private void validations() {
        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())));
        // Validate reservation
        validations.validateModificationBackend(2, "Checked In", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyUpgradeIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked", "PackageTravelComponent");
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Checked In", "AccommodationComponent");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("Earned", 1, getBook().getTravelPlanId());
        validations.verifyChargeDetail(4, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("Earned", 3, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());

        validations.validateResortAndRoomType(getBook().getTravelPlanId(), getFacilityId(), getRoomTypeCode());
        validations.validateAreaPeriod(getBook().getTravelPlanId(), getArrivalDate(), getDepartureDate());
    }
}