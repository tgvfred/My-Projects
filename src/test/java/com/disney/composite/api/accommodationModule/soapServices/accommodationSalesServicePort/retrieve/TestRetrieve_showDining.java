package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve_showDining extends AccommodationBaseTest {

    private Book book;
    private HouseHold hh = new HouseHold(1);

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        book = new Book(Environment.getBaseEnvironmentName(environment), ScheduledEventReservation.ONECOMPONENTSNOADDONS);
        book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));
        book.setParty(hh);

        book.sendRequest();

        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
        System.out.println(book.getRequest());
        System.out.println(book.getResponse());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_showDining() {
        String fault = "No accommodation components found in this travel plan,";

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(book.getTravelPlanId());
        retrieve.setLocationId("51");
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.NO_ACCOMMODATION_FOUND);

    }
}