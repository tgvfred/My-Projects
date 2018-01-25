package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.CheckoutValidationHelper;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.AddBundleHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;

public class TestCheckout_bundle extends AccommodationBaseTest {
    private ThreadLocal<CheckInHelper> helper = new ThreadLocal<>();

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        bookReservation();
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            helper.get().checkOut(getLocationId());
            helper.get().updateSingleRoomStatus("updateToCleanAndVacant");
        } catch (Exception e) {

        }

        try {
            helper.get().updateSingleRoomStatus("updateToCleanAndVacant");
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "debug" })
    public void TestCheckout_bundle() {
        AddBundleHelper bundleHelper = new AddBundleHelper(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        bundleHelper.addBundle(getBook().getTravelPlanId(), getDaysOut());

        int tries = 0;
        int maxTries = 5;
        boolean success = false;
        do {
            try {

                helper.set(new CheckInHelper(getEnvironment(), getBook()));
                helper.get().checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
                success = true;
            } catch (Exception e) {
                cancel();
                setValues();
                bookReservation();
            }
        } while (tries < maxTries && !success);
        // helper.get().checkOut(getLocationId());
        String status = "false";
        String tcgId = getBook().getTravelComponentGroupingId();
        String locationId = getLocationId();
        String checkoutDate = Randomness.generateCurrentXMLDate();
        String earlyCheckoutReason = "BEREAV";
        String refType = "RESERVATION";
        String refNumber = getExternalRefNumber();
        String refSource = getExternalRefSource();

        Checkout checkout = new Checkout(getEnvironment(), "main");
        checkout.setEarlyCheckOutReason(earlyCheckoutReason);
        checkout.setIsBellServiceRequired(status);
        checkout.setIsSameRoomNumberAssigned(status);
        checkout.setTravelComponentGroupingId(tcgId);
        checkout.setExternalReferenceType(refType);
        checkout.setExternalReferenceNumber(refNumber);
        checkout.setExternalReferenceSource("Accovia");
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setCheckoutDate(checkoutDate);
        checkout.setLocationId(locationId);
        checkout.sendRequest();

        CheckoutValidationHelper helper = new CheckoutValidationHelper(getBook());
        String assignOwnerId = helper.validateResMgmt(getBook().getTravelComponentId());
        helper.validateRIM(assignOwnerId);
        helper.additionalValidations_Bundle(assignOwnerId);

    }

}
