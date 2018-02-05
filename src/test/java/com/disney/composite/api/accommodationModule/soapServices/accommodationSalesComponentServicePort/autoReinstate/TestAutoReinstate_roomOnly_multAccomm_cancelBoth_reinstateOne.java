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

        cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "MainCancel");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(firstTCG);

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);
        Sleeper.sleep(3000);
        cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "MainCancel");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);

        numExpectedRecords = getFolioItemCount();

        auto = new AutoReinstate(environment, "Main");
        auto.setTravelComponentGroupingId(firstTCG);

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        auto.sendRequest();
        TestReporter.logAPI(!auto.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + auto.getFaultString(), auto);

        validations();

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
