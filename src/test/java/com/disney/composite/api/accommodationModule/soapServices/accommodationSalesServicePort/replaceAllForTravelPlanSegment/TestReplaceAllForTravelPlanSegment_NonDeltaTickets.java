package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.mq.sbc.MiscRes;
import com.disney.api.mq.sbc.RoomRes;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations.BookPackageSelectableTickets;
import com.disney.api.soapServices.pricingModule.enums.DistributionProductChannel;
import com.disney.api.soapServices.pricingModule.enums.PlanType;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.api.soapServices.pricingModule.packagingService.operations.helpers.PackageCodeHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestReplaceAllForTravelPlanSegment_NonDeltaTickets extends AccommodationBaseTest {

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
        // setValues(getFacilityId(),getResortCode(), getLocationId());
        setSendRequest(false);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService",
            "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_NonDeltaTickets() {
        bookReservation();
        getHouseHold().sendToApi(Environment.getBaseEnvironmentName(getEnvironment()));

        PackageCodeHelper pkg = new PackageCodeHelper(environment, Randomness.generateCurrentXMLDate(),
                PlanType.TICKET_PLUS_DELUXE_DINING_PLAN, DistributionProductChannel.WDTC_SPORTS_PACKAGES,
                getResortCode(), getRoomTypeCode(), Randomness.generateCurrentXMLDate(getDaysOut()));
        getBook().setRoomDetailsPackageCode(pkg.getPackageCode());
        getBook().sendRequest();

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName("529");
        get.setArrivalDate(getArrivalDate());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"),
                "Verify that no error occurred finding ticket products for ticket group name [517]: "
                        + get.getFaultString());
        code = get.getCodeByTicketDescriptionAndAgeType("1-Day Theme Park Ticket", "Adult");

        bookPackage = new BookPackageSelectableTickets(environment, "SingleSelectableTicket");
        bookPackage.setExternalReference("01825", getExternalRefNumber());
        bookPackage.setSelectableTicket(code, getBook().getTravelComponentGroupingId(), getExternalRefNumber(),
                getHouseHold().primaryGuest(), getLocationId(), getBook().getTravelPlanSegmentId());
        bookPackage.sendRequest();
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"),
                "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(),
                bookPackage);

        bookRetrieve = new Retrieve(environment.replace("_CM", ""), "Main");
        bookRetrieve.setTravelPlanId(getBook().getTravelPlanId());
        bookRetrieve.setLocationId(getLocationId());
        bookRetrieve.sendRequest();
        // TestReporter.logAPI(!res.isSuccess(), "Verify that no error occurred
        // retrieving a reservation: " + bookRetrieve.getResponse(), res);

        // Test validations
        TestReporter.assertTrue(
                getBook().getNumberOfResponseNodesByXPath(
                        "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/complimentaryTicketDetail/validityPeriod/startDate") == 0,
                "No Validity Period Start Date Node was found in the Response.");
        TestReporter.assertTrue(
                getBook().getNumberOfResponseNodesByXPath(
                        "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/complimentaryTicketDetail/validityPeriod/endDate") == 0,
                "No Validity Period End Date Node was found in the Response.");

    }

}
