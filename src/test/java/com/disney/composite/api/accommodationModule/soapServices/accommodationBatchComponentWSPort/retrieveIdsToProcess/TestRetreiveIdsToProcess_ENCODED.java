package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.retrieveIdsToProcess;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.RetreiveIdsToProcess;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetreiveIdsToProcess_ENCODED extends AccommodationBaseTest {
    private String processType = "ENCODED";
    private String processId;
    private String date = Randomness.generateCurrentXMLDatetime().replace("T", "");

    private String sql = " select c.GRP_RES_PROC_ID, c.GRP_RES_PROC_TYP_NM, d.GRP_RES_PROC_RUN_ID, d.GRP_RES_PROC_RUN_STS_NM " +
            " from res_mgmt.GRP_RES_PROC c " +
            " join res_mgmt.GRP_RES_PROC_RUN d on c.GRP_RES_PROC_ID = d.GRP_RES_PROC_ID " +
            " where rownum = 1 " +
            " and c.GRP_RES_PROC_ID = ( " +
            " select * " +
            " from (select unique(a.GRP_RES_PROC_ID) " +
            " from res_mgmt.GRP_RES_PROC a " +
            " join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID " +
            " where b.GRP_RES_PROC_RUN_STS_NM = '" + processType + "'" +
            " and b.UPDT_DTS <=  to_date('" + date + "')" +
            " order by dbms_random.value)" +
            " where rownum = 1) ";
    private Recordset rs;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
    }

    @Test(groups = { "api", "regression", "retreiveIdsToProcess", "accommodation", "accommodationBatchComponentWSPort" })
    public void Test_RetreiveIdsToProcess_ENCODED() {
        Database db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        rs.print();
        if (processId == null) {
            TestReporter.assertTrue(true, "No process ID was found in the DB for process [" + processType + "].");
        } else {
            processId = rs.getValue("GRP_RES_PROC_ID");

            RetreiveIdsToProcess retreiveIds = new RetreiveIdsToProcess(environment, "Main");
            retreiveIds.setProcessId(processId); // temp hard coded, but need to find where to get my processId
            retreiveIds.sendRequest();
            TestReporter.logAPI(!retreiveIds.getResponseStatusCode().equals("200"), "An error occurred retrieveing ids for ", retreiveIds);

            // insert validations here
            validateProcessType(processType, retreiveIds);
            TestReporter.logStep("Verify number of nodes being returned");
            TestReporter.assertTrue(retreiveIds.getRetreiveIdsToProcessResponse("1") != null, "One retreiveIdsToProcessResponse node is present");

            if (retreiveIds.getRetreiveIdsToProcessResponse("1") != null) {
                TestReporter.log("One retreiveIdsToProcessResponse node is present");
            }
        }
    }

    private void validateProcessType(String processType, RetreiveIdsToProcess retreiveIds) {
        String pType;
        try {
            pType = retreiveIds.getProcessType();
        } catch (XPathNotFoundException e) {
            pType = null;
        }

        if (pType != null) {
            TestReporter.assertTrue(pType.equals(rs.getValue("GRP_RES_PROC_TYP_NM")), "Verify that the status name [" + pType + "] is that which is expected [" + rs.getValue("GRP_RES_PROC_TYP_NM") + "].");
        } else {
            TestReporter.assertTrue(true, "No process IDs were returned for process ID [" + processId + "], process type [" + rs.getValue("GRP_RES_PROC_TYP_NM") + "] and status [" + processType + "].");
        }
    }
}