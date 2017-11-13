package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AutoReinstateHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestAutoReinstate_roomOnly_multAccomm_cancelBoth_reinstateOne extends AccommodationBaseTest {
    AutoReinstate auto;
    Cancel cancel;
    String firstTCG;
    String firstTPS;
    String firstTC;
    String firstTP;
    int numExpectedRecords;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate" })
    public void Test_AutoReinstate_roomOnly_multAccomm_cancelBoth_reinstateOne() {
        firstTCG = getBook().getTravelComponentGroupingId();
        firstTPS = getBook().getTravelPlanSegmentId();
        firstTC = getBook().getTravelComponentId();
        firstTP = getBook().getTravelPlanId();

        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(firstTP);
        getBook().setTravelPlanSegementId(firstTPS);
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a new TPS with an existing TP: " + getBook().getFaultString(), getBook());

        cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(firstTCG);
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

        cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

        numExpectedRecords = getFolioItemCount();

        auto = new AutoReinstate(environment, "Main");
        auto.setTravelComponentGroupingId(firstTCG);
        auto.sendRequest();
        TestReporter.logAPI(!auto.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + auto.getFaultString(), auto);

        validations();

        // cancel the reinstated booking in order to clone
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(firstTCG);
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

        auto.setTravelComponentGroupingId(firstTCG);
        auto.sendRequest();
        TestReporter.logAPI(!auto.getResponseStatusCode().equals("200"), "An error occurred while auto reinstating: " + auto.getFaultString(), auto);
        if (Environment.isSpecialEnvironment(environment)) {
            cancel.sendRequest();
            TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

            AutoReinstate clone = (AutoReinstate) auto.clone();
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
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(auto, true), "Validating Response Comparison");
        }
    }

    public void validations() {
        AutoReinstateHelper helper = new AutoReinstateHelper(environment, getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());

        helper.validateReservationBookedBundleStatus();
        helper.validateTravelComponent();
        helper.validateCancellationNumberFirstTPS(firstTPS);
        helper.validateBookedRecordTwoTCG(firstTCG, firstTP);
        helper.validateRIMInventoryReinstatedTCG(firstTCG, firstTC);
        helper.validateTwoBookedChargeGroups(firstTP, firstTCG, firstTC);
        helper.validateChargeItemsTwoTCG(firstTCG);

        // int numExpectedRecords = 8;
        helper.validateFolioItems(numExpectedRecords);

    }

    private int getFolioItemCount() {
        Sleeper.sleep(5000);
        String sql = "select h.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID "
                + "left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID "
                + "left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID "
                + "where a.EXTNL_REF_VAL in '" + getBook().getTravelPlanId() + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;
        rs = new Recordset(db.getResultSet(sql));
        return rs.getRowCount();
    }

}
