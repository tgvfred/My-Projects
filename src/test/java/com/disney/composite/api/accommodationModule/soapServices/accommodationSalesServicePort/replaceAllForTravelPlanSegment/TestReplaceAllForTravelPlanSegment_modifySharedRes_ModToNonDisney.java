package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment_modifySharedRes_ModToNonDisney extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcgId2 = null;
    private Map<String, String> tcgs = new HashMap<>();
    private String resortCode;
    private String roomTypeCode;
    private String locationId;
    private String facilityId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(4);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcgs.put(tcgId, tcgId);
        tpPtyId = getBook().getGuestId();

        Sleeper.sleep(3000);
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelPlanGuestPartyAndGuestIds(tpPtyId, tpPtyId);
        getBook().setReplaceAll("true");
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        tcgId2 = getBook().getTravelComponentGroupingId();
        tcgs.put(getBook().getTravelComponentGroupingId(), getBook().getTravelComponentGroupingId());
        Sleeper.sleep(3000);
        Share share = new Share(getEnvironment());
        share.setTravelComponentGroupingId(tcgId);
        share.addSharedComponent();
        share.setLocationId(getLocationId());
        share.setRoomNumber(BaseSoapCommands.REMOVE_NODE.toString());
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing a reservation: " + share.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_modifySharedRes_ModToNonDisney() {
        setSendRequest(false);
        setResortCode("SWN");
        setRoomTypeCode("SA");
        setFacilityId("80069789");
        Sleeper.sleep(3000);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setReplaceAll("true");
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying a shared res: " + getBook().getFaultString(), getBook());
        tpPtyId = getBook().getGuestId();
        tcgs.put(getBook().getTravelComponentGroupingId(), getBook().getTravelComponentGroupingId());

        validations();

        // Test validations
        TestReporter.logStep("Validating ExperienceMediaDetails Node Found");
        TestReporter.assertTrue(getBook().getNumberOfResponseNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/experienceMediaDetails") == 1, "Verify an ExperienceMediaDetails Node was found in the Response.");

    }

    private void validations() {
        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())));

        // Validate reservation
        validations.validateModificationBackendMultiAccomm(6, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgs);
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId(), 3);
        validations.verifyTcStatusByTcg(tcgId, "Cancelled");
        validations.verifyTcStatusByTcg(tcgId2, "Booked");
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgId, getHouseHold().primaryGuest());
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgId2, getHouseHold().primaryGuest());
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 8, getBook().getTravelPlanId());
        validations.verifyChargeDetail(48, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 4, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(tcgId2, 1, getBook().getTravelPlanId());
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.validateAreaPeriod(getBook().getTravelPlanId(), getArrivalDate(), getDepartureDate());
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(tcgId);
            cancel(getBook().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
    }
}
