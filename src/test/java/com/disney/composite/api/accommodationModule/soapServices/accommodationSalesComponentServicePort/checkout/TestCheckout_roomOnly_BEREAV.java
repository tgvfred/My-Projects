package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.CheckoutValidationHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCheckout_roomOnly_BEREAV extends BookDVCCashHelper {
    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setUseDvcResort(true);
        int daysOut = Integer.valueOf(Randomness.randomNumberBetween(50, 60));
        setDaysOut(daysOut);
        setArrivalDate(daysOut);
        setDepartureDate(1);
        setDaysOut(daysOut);
        setArrivalDate(daysOut);
        setDepartureDate(1);
        setValues("305669", "5A", "10068", "15");
        setUseExistingValues(true);
        setRetrieveAfterBook(false);
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "GCAL" })
    public void testCheckout_roomOnly_BEREAV() {

        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        DVCSalesBaseTest.environment = environment;
        Modify modify = new Modify(getFirstBooking());
        modify.setEnvironment(environment);
        modify.setTravelStatus("Checked In");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);
        String status = "false";
        String tcgId = getFirstBooking().getTravelComponentGroupingId();
        String locationId = getLocationId();
        String checkoutDate = Randomness.generateCurrentXMLDate();
        String earlyCheckoutReason = "BEREAV";
        String refType = "RESERVATION";
        String refNumber = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceNumber");
        String refSource = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceSource");

        Checkout checkout = new Checkout(getEnvironment(), "main");
        checkout.setEarlyCheckOutReason(earlyCheckoutReason);
        checkout.setIsBellServiceRequired(status);
        checkout.setIsSameRoomNumberAssigned(status);
        checkout.setTravelComponentGroupingId(tcgId);
        checkout.setExternalReferenceType(refType);
        checkout.setExternalReferenceNumber(refNumber);
        checkout.setExternalReferenceSource(refSource);
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setCheckoutDate(checkoutDate);
        checkout.setLocationId(locationId);
        checkout.sendRequest();

        TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking out: " + checkout.getFaultString(), checkout);

        CheckoutValidationHelper helper = new CheckoutValidationHelper(getFirstBooking());
        String assignOwnerId = helper.validateResMgmt(getFirstBooking().getTravelComponentId());
        helper.validateRIM(assignOwnerId);
        helper.additionalValidations(assignOwnerId);
    }
}
