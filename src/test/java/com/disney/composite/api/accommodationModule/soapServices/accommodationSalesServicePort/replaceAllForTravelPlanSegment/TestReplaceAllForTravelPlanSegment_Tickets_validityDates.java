package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.mq.sbc.MiscRes;
import com.disney.api.mq.sbc.RoomRes;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.helpers.BookPackageSelectableTicketsHelper;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations.BookPackageSelectableTickets;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestReplaceAllForTravelPlanSegment_Tickets_validityDates extends AccommodationBaseTest {

    private RoomRes room = null;
    private HouseHold guest = null;
    private String code = null;
    private MiscRes misc = null;
    private BookPackageSelectableTickets bookPackage = null;
    private Retrieve bookRetrieve = null;
    private String getTicketStartDate = null;
    private String getTicketEndDate = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setIsWdtcBooking(true);
        setValues("80010401", "SA", "44");
        setAddTickets(true);
        bookReservation();
        getBook().setRoomDetailsPackageCode("PH4F1");
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/partOfPackage", "true");
        getBook().sendRequest();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService",
            "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_Tickets_validityDates() {

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName("517");
        get.setArrivalDate(getArrivalDate());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"),
                "Verify that no error occurred finding ticket products for ticket group name [517]: "
                        + get.getFaultString());
        code = get.getCodeByTicketDescriptionAndAgeType("3 Day Base Ticket", "Adult");

        bookPackage = new BookPackageSelectableTickets(environment, "SingleSelectableTicket");
        bookPackage.setExternalReference("01825", getExternalRefNumber());
        bookPackage.setSelectableTicket(code, getBook().getTravelComponentGroupingId(), getExternalRefNumber(),
                getHouseHold().primaryGuest(), getLocationId(), getBook().getTravelPlanSegmentId());
        bookPackage.sendRequest();
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"),
                "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(),
                bookPackage);

        getBook().setCommunicationChannel("Internet");
        getBook().setTravelPlanId(getBook().getTravelPlanId());
        getBook().setTravelPlanSegementId(getBook().getTravelPlanSegmentId());
        getBook().setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        getBook().setTravelComponentId(getBook().getTravelComponentId());
        getBook().setReplaceAll("true");
        getBook().sendRequest();

        bookRetrieve = new Retrieve(environment.replace("_CM", ""), "Main");
        bookRetrieve.setTravelPlanId(getBook().getTravelPlanId());
        bookRetrieve.setLocationId(getLocationId());
        bookRetrieve.sendRequest();

        // TestReporter.logAPI(!res.isSuccess(), "Verify that no error occurred
        // retrieving a reservation: " + bookRetrieve.getResponse(), res);

        validations();
        assertResponseValidations(getBook());
    }

    public void validations() {
        BookPackageSelectableTicketsHelper bookHelper = new BookPackageSelectableTicketsHelper(environment,
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(),
                getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());

        // Verifying successful response
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"),
                "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(),
                bookPackage);

        // Capturing ticket validity dates for later validations
        getTicketStartDate = bookPackage.getTicketValidity_startDate().replace("T", " ") + ".0";
        getTicketEndDate = bookPackage.getTicketValidity_endDate().replace("T", " ") + ".0";

        // Verifying ticket validity dates
        String ticketStartDate = getTicketStartDate;
        String ticketEndDate = getTicketEndDate;
        bookHelper.validateTicketValidityDates(ticketStartDate, ticketEndDate);

        TestReporter.assertAll();
    }

    private void assertResponseValidations(ReplaceAllForTravelPlanSegment rq) {
        TestReporter.setAssertFailed(false);

        // Capturing ticket validity dates for later validations
        getTicketStartDate = rq.getTicketValidity_startDate().replace("T", " ") + ".0";
        getTicketEndDate = rq.getTicketValidity_endDate().replace("T", " ") + ".0";

        // Verifying ticket validity dates
        rq.validateTicketValidityDates(getTicketStartDate, getTicketEndDate);
        TestReporter.assertAll();
    }

}