package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations.BookPackageSelectableTickets;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_bookPackageSelectableTickets extends AccommodationBaseTest {

    private String locEnv;
    private BookPackageSelectableTickets bookPackage;
    private String code;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        locEnv = environment;
        daysOut.set(1);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(true);
        setMywPackageCode(true);
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_bookPackageSelectableTickets() {

        bookPackage();

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.setValidateProfile(false);
        helper.baseValidation(getBook(), retrieve);
        helper.sqlAdmissionComponentDetails(environment, getBook().getTravelComponentGroupingId());

        String ticketComponentIdString = helper.findTicketComponentId(getBook().getTravelComponentGroupingId(), Environment.getBaseEnvironmentName(environment));

        TestReporter.softAssertEquals(retrieve.getTicketStatus(), "Booked", "Verify the ticket status is Booked as expected: [" + retrieve.getTicketStatus() + "]");
        TestReporter.softAssertEquals(retrieve.getTicketGuestId(), getBook().getGuestId(), "Verify the ticket guest id in the response: [" + retrieve.getTicketGuestId() + "] "
                + "matches the guest id from the reservation: [" + getBook().getGuestId() + "]");
        TestReporter.softAssertEquals(retrieve.getTicketAgeType(), "ADULT", "Verify the ticket age type is ADULT as expected: [" + retrieve.getTicketAgeType() + "]");
        TestReporter.softAssertEquals(retrieve.getTicketComponentId(), ticketComponentIdString, "Verify the ticket component id in the response: [" + retrieve.getTicketComponentId() + "] "
                + "matches the component id from the reservation: [" + ticketComponentIdString + "]");
        TestReporter.assertAll();

        // Old vs New
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (retrieve.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk[text()='false']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation[text()='false']");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }
    }

    private void bookPackage() {

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
