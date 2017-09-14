package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ReinstateHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestReinstate_tpsTcgOnly extends AccommodationBaseTest {

    private Cancel cancel;
    Reinstate reinstate;
    String TCG;
    private String travelStatus = "Booked";
    private String tpsCancelDate = Randomness.generateCurrentDatetime().split(" ")[0];

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
        isComo.set("true");
        bookReservation();

        TCG = getBook().getTravelComponentGroupingId();
    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodatoinsales" })
    public void Test_Reinstate_tpsTcgOnly() {
        int numBookedComponents_book = getNumberOfBookedComponents(getBook().getTravelComponentGroupingId());

        cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred cancelling a reservation: " + cancel.getFaultString());

        reinstate = new Reinstate(environment, "TCG_TPS_Only");
        reinstate.setTravelComponentGroupingId(TCG);
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        reinstate.sendRequest();
        TestReporter.logAPI(!reinstate.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + reinstate.getFaultString(), reinstate);

        int numBookedComponents_reinstate = getNumberOfBookedComponents(getBook().getTravelComponentGroupingId());
        TestReporter.assertEquals(numBookedComponents_book, numBookedComponents_reinstate, "Verify that the number of booked components [" + numBookedComponents_reinstate + "] is that which is expected [" + numBookedComponents_book + "].");

        validations();
        // cancel and reinstate in order to clone on the old service.
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred cancelling a reservation: " + cancel.getFaultString());
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

        int numExpectedRecords14 = 2;
        reinstateHelper.validateTCReservationStatus(numExpectedRecords14, getBook().getTravelComponentId(), getArrivalDate(), getDepartureDate(), "1",
                "Booked", getFacilityId(), getBook().getTravelComponentGroupingId());

        int numExpectedRecords12 = 1;
        reinstateHelper.validateTPSReservationStatus(numExpectedRecords12, tpsCancelDate, travelStatus, cancel.getCancellationNumber(), getArrivalDate(), getDepartureDate());

        int numExpectedRecords2 = 5;
        // String cancelledChargeId = reinstateHelper.validateCharges(numExpectedRecords2, workLocation);
        String cancelledChargeId = reinstateHelper.validateCharges(numExpectedRecords2, null, 4, 0);

        int numExpectedRecords3 = 5;
        // reinstateHelper.validateChargeItem(cancelledChargeId, numExpectedRecords3);
        reinstateHelper.validateChargeItem(cancelledChargeId, numExpectedRecords3, 4, 0);

        int numExpectedRecords4 = 5;
        reinstateHelper.validateFolioStatus(numExpectedRecords4);

        int numExpectedRecords5 = 5;
        reinstateHelper.validateFolioData(numExpectedRecords5);

        int numExpectedRecords6 = 5;
        reinstateHelper.validateFolioItemData(numExpectedRecords6);

        int numExpectedRecords8 = 3;
        reinstateHelper.validateReservationHistory(numExpectedRecords8);

        int numExpectedRecords9 = 1;
        reinstateHelper.validateRIM(numExpectedRecords9, getRoomTypeCode());

        int numExpectedRecords10 = 1;
        reinstateHelper.validateTPV3Records(numExpectedRecords10, getArrivalDate(), getDepartureDate());

        int numExpectedRecords11 = 1;
        reinstateHelper.validateTPV3SalesOrder(numExpectedRecords11, getArrivalDate(), getDepartureDate());

        int numExpectedRecords13 = 1;
        reinstateHelper.validateTCGReservationStatus(numExpectedRecords13, getBook().getTravelComponentGroupingId());

        reinstateHelper.validateTCFee(true, 1);
    }

}
