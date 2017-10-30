package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieve_bookPackageSelectableTickets extends AccommodationBaseTest {

    private String locEnv;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(Environment.getBaseEnvironmentName(environment));
        locEnv = environment;

        daysOut.set(1);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(true);
        setMywPackageCode(true);
        setValues();
        // isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_bookPackageSelectableTickets() {

        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(environment);
        find.setPackageCode(getPackageCode());
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + getPackageCode() + "].");

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName(find.getTicketGroupName());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + find.getTicketGroupName() + "].");
        code = get.getCodeByTicketDescriptionAndAgeType("2 Day Base Ticket", "Adult");

        bookPackage = new BookPackageSelectableTickets(locEnv, "singleTickets");
        bookPackage.setExternalReference("01825", getExternalRefNumber());
        bookPackage.setTickets(code, getHouseHold().primaryGuest(), getLocationId(), getBook().getTravelPlanSegmentId());
        bookPackage.sendRequest();
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"), "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(), bookPackage);

    }
}
