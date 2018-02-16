package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestModify_Negative_ModifySharedReservation_OutsideShareWindow extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;
    private String tcgId2 = null;
    private Map<String, String> tcgs = new HashMap<>();

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
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId2);
        getBook().setTravelComponentId(getBook().getTravelComponentId());
        setTpId(tpId);
        setTpsId(tpsId);
        setTcgId(tcgId2);
        setTcId(tcId);
        Share share = new Share(getEnvironment());
        share.setTravelComponentGroupingId(tcgId);
        share.addSharedComponent();
        share.setLocationId(getLocationId());
        share.setRoomNumber(BaseSoapCommands.REMOVE_NODE.toString());
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing a reservation: " + share.getFaultString());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify" })
    public void testModify_Negative_ModifySharedReservation_OutsideShareWindow() {
        String errorMessage = "Checked In Accommodation cannot be modified : Accommodation Sales Modify does not support checked in reservations";
        Modify modify = new Modify(getBook());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.CANNOT_MODIFY_CHECKEDIN_ACCOMMADATION, errorMessage);

    }

    private void validateError(Modify modify, ApplicationErrorCode error, String errorMessage) {
        TestReporter.logAPI(!modify.getFaultString().trim().toLowerCase().contains(errorMessage.trim().toLowerCase()), "Validate expected error message [ " + errorMessage + " ] is returned in response [ " + modify.getFaultString() + " ]", modify);
        validateApplicationError(modify, error);
    }

    @AfterMethod
    public void cleanup() {
        cancel(tcgId);
        cancel(tcgId2);
    }
}
