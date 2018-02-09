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
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.AddBundleHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCheckout_bundle extends BookDVCCashHelper {
    // private CheckInHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setUseDvcResort(true);
        setValues("305669", "5A", "10068", "15");
        setUseExistingValues(true);
        setRetrieveAfterBook(false);
        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        AddBundleHelper bundleHelper = new AddBundleHelper(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        bundleHelper.addBundle(getFirstBooking().getTravelPlanId(), getDaysOut());
        DVCSalesBaseTest.environment = environment;
        Modify modify = new Modify(getFirstBooking());
        modify.setEnvironment(environment);
        modify.setTravelStatus("Checked In");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);

    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "debug", "GCAL" })
    public void testCheckout_bundle() {

        String status = "false";
        String tcgId = getFirstBooking().getTravelComponentGroupingId();
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

        CheckoutValidationHelper helper = new CheckoutValidationHelper(getFirstBooking());
        String assignOwnerId = helper.validateResMgmt(getFirstBooking().getTravelComponentId());
        helper.validateRIM(assignOwnerId);
        helper.additionalValidations_Bundle(assignOwnerId);

    }

}
