package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations.BookPackageSelectableTickets;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestModify_ModifyGuestChildToInfant_AddTickets extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;
    private BookPackageSelectableTickets bookPackage;
    private String code;
    private Retrieve bookRetrieve = null;
    private String locEnv;

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
        setAddChildGuest(true);
        setIsWdtcBooking(true);
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
        tpPtyId = getBook().getGuestId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setTravelComponentId(tcId);
        setTpId(tpId);
        setTpsId(tpsId);
        setTcgId(tcgId);
        setTcId(tcId);
        getBook().sendRequest();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify" })
    public void testModify_ModifyGuestChildToInfant_AddTickets() {

        TestReporter.logStep("Validate that Age [3] and Age Type of [CHILD] are found before modify");
        ageTypeValidation("3", "CHILD");

        // Modify the Child to become an Infant
        Modify modify = new Modify(getBook());
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/roomReservationDetail/guestReferenceDetails[2]/age", "2");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);

        TestReporter.logStep("Validate that Age [2] and Age Type of [INFANT] are found after modify");
        ageTypeValidation("2", "INFANT");

        // Check In the reservation
        CheckInHelper check = new CheckInHelper(environment, getBook());
        check.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName("634");
        get.setArrivalDate(getArrivalDate());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [634].");
        code = get.getCodeByTicketDescriptionAndAgeType("2 Day Base Ticket", "Adult");

        bookPackage = new BookPackageSelectableTickets(environment, "SingleSelectableTicket");
        bookPackage.setExternalReference("01825", getExternalRefNumber());
        bookPackage.setSelectableTicket(code, getBook().getTravelComponentGroupingId(), getExternalRefNumber(), getHouseHold().primaryGuest(), getLocationId(), getBook().getTravelPlanSegmentId());
        bookPackage.setRequestNodeValueByXPath("/Envelope/Body/bookPackageSelectableTickets/request/selectableTickets/ticket/guestReference/guest/dob", BaseSoapCommands.REMOVE_NODE.toString());
        bookPackage.setRequestNodeValueByXPath("/Envelope/Body/bookPackageSelectableTickets/request/selectableTickets/ticket/guestReference/guest/middleName", BaseSoapCommands.REMOVE_NODE.toString());
        bookPackage.setRequestNodeValueByXPath("/Envelope/Body/bookPackageSelectableTickets/request/selectableTickets/ticket/guestReference/guest/addressDetails/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
        bookPackage.setRequestNodeValueByXPath("/Envelope/Body/bookPackageSelectableTickets/request/selectableTickets/ticket/guestReference/guest/addressDetails/regionName", BaseSoapCommands.REMOVE_NODE.toString());
        bookPackage.sendRequest();
        TestReporter.logAPI(!bookPackage.getResponseStatusCode().equals("200"), "Verify that no error occurred booking package selectable tickets: " + bookPackage.getFaultString(), bookPackage);

        bookRetrieve = new Retrieve(environment.replace("_CM", ""), "Main");
        bookRetrieve.setTravelPlanId(getBook().getTravelPlanId());
        bookRetrieve.setLocationId(getLocationId());
        int tries = 0;
        int maxTries = 10;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            bookRetrieve.sendRequest();
            if (bookRetrieve.getResponseStatusCode().equals("200")) {
                success = true;
            }
        } while (tries < maxTries && !success);
        TestReporter.logAPI(!bookRetrieve.getResponseStatusCode().equals("200"), "Verify that no error occurred retrieving a reservation: " + bookRetrieve.getResponse(), bookRetrieve);

    }

    public void ageTypeValidation(String age, String at) {
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select b.AGE_NB, c.AGE_TYP_NM "
                + "from res_mgmt.tp_pty a "
                + "join guest.TXN_IDVL_PTY b on a.TXN_PTY_ID = b.TXN_IDVL_PTY_ID "
                + "join res_mgmt.tc_gst c on b.TXN_IDVL_PTY_ID = c.TXN_IDVL_PTY_ID "
                + "where a.PRMY_PTY_IN = 'N' "
                + "and a.tp_id = '" + getBook().getTravelPlanId() + "' ";
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertEquals(rs.getValue("AGE_NB"), age, "The Age on the database [" + rs.getValue("AGE_NB") + "] is equal to the Age associated with the booking [" + age + "]");
        TestReporter.assertEquals(rs.getValue("AGE_TYP_NM"), at, "The Age Type on the database [" + rs.getValue("AGE_TYP_NM") + "] is equal to the Age Type associated with the booking [" + at + "]");
    }
}
