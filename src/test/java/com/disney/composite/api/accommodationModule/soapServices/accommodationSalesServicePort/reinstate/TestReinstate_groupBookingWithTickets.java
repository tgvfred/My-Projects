package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ReinstateHelper;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindMiscPackages;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.date.DateTimeConversion;

public class TestReinstate_groupBookingWithTickets extends AccommodationBaseTest {
    Reinstate reinstate;
    String TCG;
    private String travelStatus = "Booked";
    private String tpsCancelDate = Randomness.generateCurrentDatetime().split(" ")[0];
    private AddBundle add;
    private RetrieveDetailsByTravelPlanId details;
    private int arrivalDaysOut = 0;
    private int departureDaysOut = 4;
    String cancelNumber;

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
        setAddTickets(true);
        setIsLibgoBooking(true);
        isComo.set("true");
        // retrieveReservation();
        bookReservation();

        details = new RetrieveDetailsByTravelPlanId(Environment.getBaseEnvironmentName(environment), "Main");
        details.setTravelPlanId(getBook().getTravelPlanId());
        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());

        add = new AddBundle(Environment.getBaseEnvironmentName(environment), "Main");
        add.setGuestsGuestNameFirstName(getHouseHold().primaryGuest().getFirstName());
        add.setGuestsGuestNameLastName(getHouseHold().primaryGuest().getLastName());
        add.setGuestsGuestReferenceId(details.getGuestsId());
        add.setGuestsId(details.getGuestsId());
        add.setPackageBundleRequestsBookDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
        add.setPackageBundleRequestsContactName(getHouseHold().primaryGuest().getFirstName() + " " + getHouseHold().primaryGuest().getLastName());
        add.setPackageBundleRequestsEndDate(Randomness.generateCurrentXMLDate(departureDaysOut - 2) + "T00:00:00");
        add.setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(details.getGuestsId());
        add.setPackageBundleRequestsStartDate(Randomness.generateCurrentXMLDate(arrivalDaysOut + 1) + "T00:00:00");
        add.setTravelPlanId(getBook().getTravelPlanId());
        add.retrieveSalesOrderId(getBook().getTravelPlanId());
        add.setSalesOrderId(add.getBundleSalesOrderIds()[0]);

        FindMiscPackages find = new FindMiscPackages(Environment.getBaseEnvironmentName(environment), "MinimalInfo");
        find.setArrivalDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
        find.setBookDate(Randomness.generateCurrentXMLDate());
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred adding a bundle to TP ID [" + getBook().getTravelPlanId() + "]: " + add.getFaultString());
        add.setPackageBundleRequestsCode(find.getPackageCode());

        add.sendRequest();
        TestReporter.assertEquals(add.getResponseStatusCode(), "200", "An error occurred while adding a bundle.\nRequest:\n" + add.getRequest() + "\nResonse:\n" + add.getResponse());
        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());
        TCG = getBook().getTravelComponentGroupingId();
    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodatoinsales" })
    public void Test_Reinstate_groupBookingWithTickets() {
        int numBookedComponents_book = getNumberOfBookedComponents(getBook().getTravelComponentGroupingId());

        Sleeper.sleep(3000);
        Cancel cancel = new Cancel(environment, "MainTickets");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
        cancelNumber = cancel.getCancellationNumber();
        Sleeper.sleep(3000);
        reinstate = new Reinstate(environment, "Main_2");
        reinstate.setTravelComponentGroupingId(TCG);
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        reinstate.sendRequest();
        TestReporter.logAPI(!reinstate.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + reinstate.getFaultString(), reinstate);

        int numBookedComponents_reinstate = getNumberOfBookedComponents(getBook().getTravelComponentGroupingId());
        TestReporter.assertEquals(numBookedComponents_book, numBookedComponents_reinstate + 1, "Verify that the number of reinstated components [" + (numBookedComponents_reinstate) + "] is that which is expected [" + (numBookedComponents_book) + "].");
        Sleeper.sleep(3000);
        validations();
        // cancel and reinstate in order to clone on the old service.
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

        reinstate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        // reinstate.sendRequest();
        TestReporter.logAPI(!reinstate.getResponseStatusCode().equals("200"), "An error occurred while creating a comment: " + reinstate.getFaultString(), reinstate);
        if (Environment.isSpecialEnvironment(environment)) {
            Reinstate clone = (Reinstate) reinstate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));

            int tries = 0;
            int maxTries = 10;
            boolean success = false;
            do {
                Sleeper.sleep(1000);
                try {
                    clone.sendRequest();
                    success = true;
                } catch (Exception e) {

                }
                tries++;
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(reinstate, true), "Validating Response Comparison");
        }
    }

    private int getNumberOfBookedComponents(String tcg) {
        String sql = "select a.TRVL_STS_NM "
                + "from res_mgmt.tc a "
                + "where a.tc_grp_nb = " + tcg;
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No components found for tcg ID [ " + tcg + " ]", sql);
        }

        int numBookedComponents = 0;
        do {
            if (rs.getValue("TRVL_STS_NM").equals("Booked")) {
                numBookedComponents++;
            }
            rs.moveNext();
        } while (rs.hasNext());

        return numBookedComponents;
    }

    public void validations() {
        ReinstateHelper reinstateHelper = new ReinstateHelper(environment, getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());
        int numExpectedRecords = 3;
        reinstateHelper.validateActiveChargeGroup(numExpectedRecords);

        int numExpectedRecords14 = 12;
        reinstateHelper.validateTCReservationStatusForTCGFacId(numExpectedRecords14, getBook().getTravelComponentId(), getArrivalDate(), getDepartureDate(), getSalesChannelId(),
                "Booked", getFacilityId(), getBook().getTravelComponentGroupingId());

        int numExpectedRecords12 = 1;
        reinstateHelper.validateTPSReservationStatus(numExpectedRecords12, tpsCancelDate, travelStatus, "0", getArrivalDate(), getDepartureDate());

        int numExpectedRecords2 = 10;
        String cancelledChargeId = reinstateHelper.validateCharges(numExpectedRecords2, null, numExpectedRecords2, 0);

        int numExpectedRecords3 = 10;
        // reinstateHelper.validateChargeItem(cancelledChargeId, numExpectedRecords3);
        reinstateHelper.validateChargeItem(cancelledChargeId, numExpectedRecords3, numExpectedRecords3, 0);

        int numExpectedRecords4 = 12;
        reinstateHelper.validateFolioStatus(numExpectedRecords4);

        int numExpectedRecords5 = 11;
        reinstateHelper.validateFolioData(numExpectedRecords5);

        int numExpectedRecords6 = 12;
        reinstateHelper.validateFolioItemData(numExpectedRecords6);

        int numExpectedRecords8 = 4;
        reinstateHelper.validateReservationHistoryMultAccomm(numExpectedRecords8, getBook().getTravelComponentId());
        // reinstateHelper.validateReservationHistory(numExpectedRecords8);

        // int numExpectedRecords10 = 1;
        // reinstateHelper.validateTPV3Records(numExpectedRecords10, getArrivalDate(), Randomness.generateCurrentXMLDate(getNights() + 1));

        // int numExpectedRecords11 = 1;
        // reinstateHelper.validateTPV3SalesOrderAccomm(numExpectedRecords11, getArrivalDate(), Randomness.generateCurrentXMLDate(getNights() + 1));

        reinstateHelper.validateTCFee(false, 0);

        int numExpectedRecords9 = 1;
        reinstateHelper.validateRIM(numExpectedRecords9, getRoomTypeCode());

    }

}
