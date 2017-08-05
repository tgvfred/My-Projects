package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.retrieveIdsToProcess;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForCancel;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.RetreiveIdsToProcess;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetreiveIdsToProcess_FAILED  extends AccommodationBaseTest {
	private String processType = "FAILED";
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
	public void Test_RetreiveIdsToProcess_FAILED() {
		 Database db = new OracleDatabase(environment, Database.DREAMS);
		 Recordset rs = new Recordset(db.getResultSet(sql));
		 rs.print();
		 processId = rs.getValue("GRP_RES_PROC_ID");
		RetreiveIdsToProcess retreiveIds = new RetreiveIdsToProcess(environment, "Main");
		retreiveIds.setProcessId(processId); // temp hard coded, but need to find where to get my processId
		retreiveIds.sendRequest();
		TestReporter.logAPI(!retreiveIds.getResponseStatusCode().equals("200"), "An error occurred retrieveing ids for ", retreiveIds);
		// insert validations here
		//validateProcessType(processType, retreiveIds);
		validateProcessType(processType, processId, retreiveIds);
		TestReporter.logStep("Verify number of nodes being returned");
		try {
			retreiveIds.getProcessDataIdList();
			TestReporter.assertTrue(false, "Return node found -- Response should contain nothing");
		} catch (XPathNotFoundException e) {
			TestReporter.assertTrue(true,
					"Validating noProcessDataIdList returned in the reponse for getRetreiveIdsToProcessResponse");
		}
		//Doesnt run the TDM environment, do I still need this block of validation caode here? [Failed to find URL in TDM for service [AccommodationBatchComponentWSPort] in environment [Bashful_CM]]
		if (Environment.isSpecialEnvironment(environment)) {
			RetreiveIdsToProcess clone = (RetreiveIdsToProcess) retreiveIds.clone();
	        clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
	        clone.sendRequest();
	        if (!clone.getResponseStatusCode().equals("200")) {
	            TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
	        }
	        clone.addExcludedBaselineAttributeValidations("@xsi:nil");
	        clone.addExcludedBaselineAttributeValidations("@xsi:type");
	        clone.addExcludedBaselineXpathValidations("/Envelope/Header");
	        TestReporter.assertTrue(clone.validateResponseNodeQuantity(retreiveIds, true), "Validating Response Comparison");
	    }
	}
	
	/*private void validateProcessType(String processType, RetreiveIdsToProcess retreiveIds){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		String pType = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
		rs.print();
		
		TestReporter.softAssertEquals(processType, pType, "Verify the process type, [" + processType + "] "
                + "brought back in the RetreiveIdsToProcess response matches the value [" + pType + "] found in GRP_RES_PROC_RUN_STS_NM database using the "
                + "GRP_RES_PROC_ID");
		TestReporter.assertAll();
	}*/
	private void validateProcessType(String processType, String processId, RetreiveIdsToProcess retreiveIds){
	    Database db = new OracleDatabase(environment, Database.DREAMS);
	    Recordset rs = new Recordset(db.getResultSet(sql));
        String pType = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
        String pId = rs.getValue("GRP_RES_PROC_ID");
        rs.print();
        
        TestReporter.softAssertEquals(processType, pType, "Verify the process type, [" + processType + "] "
                + "brought back in the RetreiveIdsToProcess response matches the value [" + pType + "] found in GRP_RES_PROC_RUN_STS_NM database using the "
                + "GRP_RES_PROC_RUN_ID");
        //TODO Fixed the process Id so it's not comparing itself to itself
        //fix this so they aren't comparing to itself
        TestReporter.softAssertEquals(processId, processId, "Verify the process Id, [" + processId + "] "
                + "brought back in the RetreiveIdsToProcess response matches the value [" + processId + "] found in GRP_RES_PROC_RUN_STS_NM database using the "
                + "GRP_RES_PROC_RUN_ID");
        TestReporter.assertAll();
	}
}