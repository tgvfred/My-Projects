package com.disney.utils;

import org.testng.annotations.Test;

import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

/**
 * This class is intended to retrieve product IDs for a given facility ID
 * @author Waightstill W Avery
 *
 */
public class RetrieveComponentProductIdByFacilityId {
	private String query = Dreams.ComponentProductIds.productIdWithAgeDefFacilityIdQuery;
	private String database = "DREAMS";
	private String environment;
	private String facilityId;
	private OracleDatabase odb;
	private Recordset resultSet;
	private String[][] idsAndTypes;
	
	// Dummy constructor
	public RetrieveComponentProductIdByFacilityId(){}
	/**
	 * This constructor is used to determine the type of query to perform, execute the query, and capture all product IDs
	 * @param env - environment to query for product IDs
	 * @param facId - facility for which to query for product IDs
	 */
	public RetrieveComponentProductIdByFacilityId(String env, String facId){
		environment = env;
		facilityId = facId;
		replaceValues();
		executeQuery();
		setComponentProductIdsAndTypes();
	}
	
	/**
	 * Executes the query and ensures IDs were returned
	 */
	private void executeQuery(){
		TestReporter.log("Facility ID: " + facilityId);
		TestReporter.log("Query: " + query);
		System.out.println("QUERY: " + query);

		odb = new OracleDatabase(environment, database);
		resultSet = new Recordset(odb.getResultSet(query));		
		
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "The component product id query did not return any records for facilty id ["+facilityId+"] in ["+environment+"].");
		TestReporter.log("Record Count: " + String.valueOf(resultSet.getRowCount()));
		idsAndTypes = new String[resultSet.getRowCount()][2];
	}
	
	private void replaceValues(){query = query.replace("{FACILITY_ID}", facilityId);}
	
	private void setComponentProductIdsAndTypes(){
		resultSet.moveFirst();
		int row = 0;
		do{
			idsAndTypes[row][0] = resultSet.getValue(Dreams.ComponentProductIds.componentProductIdColumnName);
			idsAndTypes[row][1] = resultSet.getValue(Dreams.ComponentProductIds.componentProductTypeColumnName);
			row++;
			resultSet.moveNext();
		}while(resultSet.hasNext());
	}
	
	public String[][] getComponentProductIdsAndTypes(){return idsAndTypes;}
	
	@Test
	public void test(){
		RetrieveComponentProductIdByFacilityId retrieve = new RetrieveComponentProductIdByFacilityId("Grumpy", "90001369");
		System.out.println("Number of component product IDs: " + retrieve.getComponentProductIdsAndTypes().length);
	}
}
