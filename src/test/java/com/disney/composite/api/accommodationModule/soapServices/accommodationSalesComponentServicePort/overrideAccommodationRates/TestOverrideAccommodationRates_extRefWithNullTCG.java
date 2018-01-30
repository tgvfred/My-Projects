package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOverrideAccommodationRates_extRefWithNullTCG extends AccommodationBaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();
    Map<String, String> allPairs2 = new HashMap<String, String>();
    Map<String, String> allPairs3 = new HashMap<String, String>();
    Map<String, String> allPairs4 = new HashMap<String, String>();
    Map<String, String> allPairs5 = new HashMap<String, String>();
    Map<String, String> allPairs6 = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        /// get room details
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates" })
    public void testOverrideAccommodationRates_extRefWithNullTCG() {

        String tp_id1 = getBook().getTravelPlanId();

        // Book room only booking (1 night, 1 adult)
        // Capture charge ids, charge amounts, charge items ids, charge item amounts, folio item ids, folio item amounts (to be used in a later validation)
        // Override the rate for the one night

        String sql1 = " select a.EXTNL_REF_VAL, a.EXTNL_SRC_NM, b.CHRG_GRP_ID, d.*, e.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ("
                + " (select to_char(b.tc_grp_nb)"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " where a.tp_id = '" + tp_id1 + "')  )";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));

        // SQL1
        int numberOfCharges = rs.getRowCount();
        int numberOfChargeItems = rs.getRowCount();
        String locationId = rs.getValue("WRK_LOC_ID", 4);
        TestReporter.assertTrue(numberOfCharges >= 1, "The number of charges is " + numberOfCharges);

        //
        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "externalRefDetail");
        oar.setTcgId(BaseSoapCommands.REMOVE_NODE.toString());
        oar.setTpsID(getBook().getTravelPlanSegmentId());
        oar.setBasePrice("5");
        oar.setExternalReferenceNumber(getExternalRefNumber());
        oar.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        oar.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        oar.setExternalReferenceSource("Accovia");
        oar.setRackRateRate("9.0");
        oar.setDate(getArrivalDate());
        oar.setRackRateDate(getArrivalDate());
        oar.setOverridden("true");
        oar.setOverrideReason("RTPRTSIZE");
        oar.setLocationId(locationId);

        oar.sendRequest();

        TestReporter.logAPI(!oar.getResponseStatusCode().equals("200"), "An error occurred getting override Accomodation Rates: " + oar.getFaultString(), oar);
        TestReporter.assertEquals(oar.getTcgID(), getBook().getTravelComponentGroupingId(), "The response Travel Component Grouping id [" + oar.getTcgID() + "] matches Travel Component Grouping id in the request [" + getBook().getTravelComponentGroupingId() + "].");
        TestReporter.assertEquals(oar.getTcID(), getBook().getTravelComponentId(), "The response Travel Plan Segment id [" + oar.getTcID() + "] matches Travel Plan Segment id in the request [" + getBook().getTravelComponentId() + "].");

        Recordset rs5 = new Recordset(db.getResultSet(sql1));

        // sql1
        // captures the number of charge items, charge amount, charge id and charge item amount
        TestReporter.assertTrue((numberOfChargeItems - 1) == rs5.getRowCount(), "The number of charge items before the request is [" + numberOfChargeItems + "] and after the request is [" + rs5.getRowCount() + "].");

        // old vs. new

        if (Environment.isSpecialEnvironment(getEnvironment())) {

            OverrideAccommodationRatesRequest clone = (OverrideAccommodationRatesRequest) oar.clone();
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
                if (oar.getResponseStatusCode().equals("200")) {
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

            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences");

            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences/externalReferenceNumber");
            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences/externalReferenceSource[text()='Accovia'");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(oar, true), "Validating Response Comparison");

        }
    }

}
