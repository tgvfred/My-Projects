package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.retrieveIdsToProcess;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.RetreiveIdsToProcess;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetreiveIdsToProcess_ENCODED extends AccommodationBaseTest {
	private String processType = "ENCODED";
	private String processId;
	private String sql = " select c.GRP_RES_PROC_ID, c.GRP_RES_PROC_TYP_NM, d.GRP_RES_PROC_RUN_ID, d.GRP_RES_PROC_RUN_STS_NM " + 
			" from res_mgmt.GRP_RES_PROC c " +
			" join res_mgmt.GRP_RES_PROC_RUN d on c.GRP_RES_PROC_ID = d.GRP_RES_PROC_ID " +
			" where rownum = 1 " +
			" and c.GRP_RES_PROC_ID = ( " +
			" select * " +
			" from (select unique(a.GRP_RES_PROC_ID) " +
			" from res_mgmt.GRP_RES_PROC a " +
			" join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID " +
			" where b.GRP_RES_PROC_RUN_STS_NM = '" + processType + "'"+
			" and b.UPDT_DTS <=  to_date('2017-07-18 09:25:05')" +
			" order by dbms_random.value)" +
			" where rownum = 1) ";
	
	@Test(groups = { "api", "regression", "retreiveIdsToProcess", "accommodation", "accommodationBatchComponentWSPort" })
	public void Test_RetreiveIdsToProcess_ENCODED() {
		 Database db = new OracleDatabase(environment, Database.DREAMS);
		 Recordset rs = new Recordset(db.getResultSet(sql));
		 rs.print();
		 processId = rs.getValue("GRP_RES_PROC_ID");
		RetreiveIdsToProcess retreiveIds = new RetreiveIdsToProcess(environment, "Main");
		retreiveIds.setProcessId(processId); // temp hard coded, but need to find where to get my processId
		retreiveIds.sendRequest();
		TestReporter.logAPI(!retreiveIds.getResponseStatusCode().equals("200"), "An error occurred retrieveing ids for ", retreiveIds);
		// insert validations here
		validateProcessType(processType, retreiveIds);
		TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(retreiveIds.getRetreiveIdsToProcessResponse("1") != null, "One retreiveIdsToProcessResponse node is present");
        
        if (retreiveIds.getRetreiveIdsToProcessResponse("1") !=null ){
        	TestReporter.log("One retreiveIdsToProcessResponse node is present");
        }
	}
	private void validateProcessType(String processType, RetreiveIdsToProcess retreiveIds){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		String pType = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
		rs.print();
		
		TestReporter.softAssertEquals(processType, pType, "Verify the process type, [" + processType + "] "
                + "brought back in the RetreiveIdsToProcess response matches the value [" + pType + "] found in GRP_RES_PROC_RUN_STS_NM database using the "
                + "GRP_RES_PROC_ID");
		TestReporter.assertAll();
	}
}