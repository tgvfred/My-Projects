package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchWSPort.getStagedRecordsForReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassReinstateTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForReinstate_twoReservations extends AccommodationBaseTest {

    private GetStagedRecordsForReinstate stageReinstate;
    private StageMassReinstateTransactional stage;
    private String firstResTCG;
    private String firstResTP;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);

        // grab the TCG and TPS from the first reservation.
        firstResTCG = getBook().getTravelComponentGroupingId();
        firstResTP = getBook().getTravelPlanSegmentId();

        daysOut.set(Randomness.randomNumberBetween(1, 12));
        nights.set(Randomness.randomNumberBetween(1, 3));
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "getStagedRecordsForReinstate", "accommodation" })
    public void Test_GetStagedRecordsForReinstate_twoReservations() {

        // stage reinstate transactions.
        stage = new StageMassReinstateTransactional(environment, "Main_TwoReservations");
        stage.setTcg(firstResTCG);
        stage.setTpId(firstResTP);
        stage.setSecondTcg(getBook().getTravelComponentGroupingId());
        stage.setSecondTpId(getBook().getTravelPlanId());
        stage.sendRequest();
        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Verify that no error occurred staging records for reinstate: " + stage.getFaultString(), stage);

        // pull the group reservation process run id from the database.
        TestReporter.logStep("Pull process reservation run Id from database");
        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM from res_mgmt.GRP_RES_PROC a join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID where a.GRP_RES_PROC_ID in '" + stage.getResponseProcessId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // get staged records for reinstate.
        stageReinstate = new GetStagedRecordsForReinstate(environment, "Main");
        stageReinstate.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
        stageReinstate.sendRequest();
        TestReporter.logAPI(!stageReinstate.getResponseStatusCode().equals("200"), "Verify that no error occurred getting staged records for reinstate: " + stageReinstate.getFaultString(), stageReinstate);
        validateResponseReturnNode();
    }

    public void validateResponseReturnNode() {
        TestReporter.logStep("Validate the response node has one 'return' node.");
        int numExpectedNodes = 1;
        int returnNodes = stage.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");
        TestReporter.softAssertEquals(returnNodes, numExpectedNodes, "Verify that the response returns the number of 'return' nodes [" + returnNodes + "] is that which is expected [" + numExpectedNodes + "].");

    }

    {

}
