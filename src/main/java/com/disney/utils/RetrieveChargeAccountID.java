package com.disney.utils;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;

import com.disney.test.utils.dataFactory.database.Recordset;
import com.disney.test.utils.dataFactory.database.databaseImpl.OracleDatabase;


public class RetrieveChargeAccountID {
	private String query = "SELECT CG.CHRG_GRP_ID, SME.EXPRNC_CRD_NB "
			+ "FROM GST_CHRG_ACCT.CHRG_GRP CG "
			+ "JOIN GST_CHRG_ACCT.CHRG_GRP_SETTL_METH SCG ON CG.CHRG_GRP_ID = SCG.CHRG_GRP_ID "
			+ "JOIN GST_CHRG_ACCT.SETTL_METH_EXPR_CRD SME ON SCG.SETTL_METH_ID = SME.SETTL_METH_ID "
			+ "WHERE SME.EXPRNC_CRD_NB LIKE  '%{TP_ID}%"
			+ "' ORDER BY SME.EXPRNC_CRD_NB ASC";
	private String chargeAccountID = "";
	private String experienceCardNumber = "";
	private String[][] results;
	private String database = "SALES";
	
	
	
	public String[][] getChargeIDs(String environment, String TP_ID, int expectedNumRecords){
		query = query.replace("{TP_ID}", TP_ID);
		TestReporter.log("TP_ID: " + TP_ID);
		TestReporter.log("Query: " + query);

		StopWatch sw = new StopWatch();
		OracleDatabase odb;
		Object[][] resultSet;
		sw.start();
		boolean recordsFound = false;
		do{
			odb = new OracleDatabase(environment, database);
			resultSet = odb.getResultSet(query);
			if(resultSet.length > 1) recordsFound = true;
		}while(sw.getTime() < 30 * 1000 && !recordsFound);
		sw.stop();
//		OracleDatabase odb = new OracleDatabase(environment, database);
//		Object[][] resultSet = odb.getResultSet(query);
		printChargeAcountData(resultSet);
		
		
		TestReporter.assertTrue(resultSet.length > 1, "The Charge Account ID query did not return any records.");
		TestReporter.assertTrue(resultSet.length - 1 == expectedNumRecords,
				"The number of Charge Account IDs [" + String.valueOf(resultSet.length - 1)
						+ "] did not match the expected number of records [" + String.valueOf(expectedNumRecords)
						+ "]");

		TestReporter.log("Record Count: " + String.valueOf(resultSet.length - 1));
		
		results = new String[resultSet.length-1][resultSet[0].length];
		
		for(int row = 0; row < resultSet.length-1; row++){
			for(int column = 0; column < resultSet[0].length; column++){
				results[row][column] = resultSet[row+1][column].toString();
			}
		}
		
//		chargeAccountID = resultSet[1][0].toString();
//		experienceCardNumber = resultSet[1][1].toString();
//		results[0] = chargeAccountID;
//		results[1] = experienceCardNumber;
		return results;
	}
	
	private void printChargeAcountData(Object[][] events) {
		String[][] eventsArray = new String[events.length][events[0].length];
		for (int row = 0; row < events.length; row++) {
			for (int column = 0; column < events[0].length; column++) {
				eventsArray[row][column] = events[row][column].toString();
			}
		}
		new Recordset(eventsArray).print();
	}
}
