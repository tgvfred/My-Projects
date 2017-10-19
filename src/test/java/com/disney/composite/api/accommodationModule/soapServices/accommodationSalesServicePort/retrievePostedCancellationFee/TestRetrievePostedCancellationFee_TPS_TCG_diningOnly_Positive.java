package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestRetrievePostedCancellationFee_TPS_TCG_diningOnly_Positive extends AccommodationBaseTest {

    private CheckInHelper helper;
    private ScheduledEventReservation diningRes;

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
        setHouseHold(new HouseHold(1));
        isComo.set("true");
        setIsWdtcBooking(false);
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_CancellationFees_Positive() {

        diningRes = new ShowDiningReservation(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        diningRes.setTravelPlanId(getBook().getTravelPlanId());
        diningRes.setFacilityName("Pioneer Hall");
        diningRes.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
        diningRes.setServiceStartDate(getArrivalDate());
        diningRes.book("NoComponentsNoAddons");

        String tcgId = findDiningResTcg(diningRes.getConfirmationNumber());

        diningRes.cancel();

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "Main");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

    }
}
