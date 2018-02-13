package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCPointsHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.travelPlanSegment.MergeToTravelPlanHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieve_dvc_linked extends BookDVCPointsHelper {

    private MergeToTravelPlanHelper merge;
    private String primary;
    private String secondary;
    private RetrieveHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        DVCSalesBaseTest.environment = removeCM(environment);
        setUseDvcResort(true);
        setUseNonZeroPoints(true);
        bookDvcReservation("TestMergeToTravelPlan_MCash", 1);
        makeCCPayment(removeCM(environment));
        setUseExistingValues(true);
        bookDvcReservation("TestMergeToTravelPlan_MCash", 2);
    }

    @AfterMethod(alwaysRun = true)
    public void afterClass() {
        merge.cancelReservations();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_dvc_linked() {

        merge = new MergeToTravelPlanHelper(getFirstBooking(), getSecondBooking(), removeCM(environment));
        merge.merge();
        helper = new RetrieveHelper();
        findPrimary(getFirstBooking().getTravelPlanId());

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(primary);
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        helper.dvcMembershipValidations(retrieve, getFirstMember());
        helper.dvcMembershipValidations(retrieve, getSecondMember());

    }

    public void findPrimary(String tpID) {
        Sleeper.sleep(5000);
        String sql = "SELECT TP_ID FROM RES_MGMT.TPS WHERE TP_ID = '" + tpID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() > 0) {
            primary = getFirstBooking().getTravelPlanId();
            secondary = getSecondBooking().getTravelPlanId();
            helper.setSecond(true);
        } else {
            primary = getSecondBooking().getTravelPlanId();
            secondary = getFirstBooking().getTravelPlanId();
            helper.setFirst(true);
        }

    }

}
