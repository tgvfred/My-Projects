package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AutoReinstateHelper;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations.BookPackageSelectableTickets;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_wdtcWithTickets_minimalInfo extends AccommodationBaseTest {

    AutoReinstate auto;
    Cancel cancel;
    private String locEnv;
    private BookPackageSelectableTickets bookPackage;
    private String code;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        locEnv = environment;

        daysOut.set(Randomness.randomNumberBetween(1, 12));
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(true);
        setMywPackageCode(true);
        setValues();
        // isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate" })
    public void Test_AutoReinstate_wdtcWithTickets_minimalInfo() {
        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(environment);
        find.setPackageCode(getPackageCode());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + getPackageCode() + "].");

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName(find.getTicketGroupName());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + find.getTicketGroupName() + "].");
        code = get.getCodeByTicketDescriptionAndAgeType("2 Day Base Ticket", "Adult");

        bookPackage = new BookPackageSelectableTickets(locEnv, "singleTickets");
        bookPackage.setExternalReference("01825", getExternalRefNumber());
        bookPackage.setTickets(code, getHouseHold().primaryGuest(), getLocationId(), getBook().getTravelPlanSegmentId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        bookPackage.sendRequest();
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"), "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(), bookPackage);

        Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);
        Sleeper.sleep(3000);
        auto = new AutoReinstate(environment, "Main");
        auto.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        auto.sendRequest();
        TestReporter.logAPI(!auto.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + auto.getFaultString(), auto);

        validations();
    }

    public void validations() {
        AutoReinstateHelper helper = new AutoReinstateHelper(environment, getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());

        helper.validateReservationBookedStatus();
        helper.validateComponentsBooked();
        helper.validateAdmissionComponent();
        helper.validateCancellationNumber();
        helper.validateReinstateRecord();
        helper.validateRIMInventory();
        helper.validateChargeGroups();
        helper.validateChargeItems();

        int numExpectedRecords = 12;
        helper.validateFolioItems(numExpectedRecords);

    }

}
