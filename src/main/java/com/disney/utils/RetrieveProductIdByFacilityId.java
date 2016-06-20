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
public class RetrieveProductIdByFacilityId {
	private String query = Dreams.ProductIds.getNoFilterQuery();
	private String environment;
	private String facilityId;
	private String[] productIds;
	private String database = "DREAMS";
	private OracleDatabase odb;
	private Recordset resultSet;
	private boolean bookable;
	private boolean ageDef;
	private String productType;
	boolean useProductType = false;
	boolean useAgeDef = false;
	boolean useBookable = false;
	
	// Dummy constructor
	public RetrieveProductIdByFacilityId(){}
	/**
	 * This constructor is used to determine the type of query to perform, execute the query, and capture all product IDs
	 * @param env - environment to query for product IDs
	 * @param facId - facility for which to query for product IDs
	 * @param prodTyp - the type of product that is desired for a particular facility ID; multiple product IDs can be affiliated with a given facility ID
	 * @param book - assumed to mean that a product ID can be booked/reserved; required for some reservations to be booked/modified.
	 * @param age - assumed to mean that a product ID contains an age definition; required for some reservations to be retrieved after having been booked/modified.
	 */
	public RetrieveProductIdByFacilityId(String env, String facId, String prodTyp, boolean book, boolean age){
		environment = env;
		facilityId = facId;
		productType = prodTyp;
		bookable = book;
		ageDef = age;
		
		if(!productType.isEmpty()) useProductType = true;
		if(ageDef) useAgeDef = true;
		if(bookable) useBookable = true;
		
		determineQuery();
		executeQuery();
	}
	
	/**
	 * Uses predefined values to determine the proper query to use
	 */
	private void determineQuery(){
		if(useProductType && useAgeDef && useBookable)query = Dreams.ProductIds.getAgeDefProductTypeReservableQuery();
		else if(useProductType && useAgeDef)query = Dreams.ProductIds.getAgeDefProductTypeQuery();
		else if(useProductType && useBookable)query = Dreams.ProductIds.getProductTypeReservableQuery();
		else if(useProductType)query = Dreams.ProductIds.getProductTypeQuery();
		else if(useAgeDef && useBookable)query = Dreams.ProductIds.getAgeDefReservableQuery();
		else if(useAgeDef)query = Dreams.ProductIds.getAgeDefQuery();
		else if(useBookable)query = Dreams.ProductIds.getReservableQuery();
		
		if(useProductType) replaceProductType();
		query = query.replace("{FACILITY_ID}", facilityId);
	}
	
	/**
	 * Replaces a placeholder in the query with the product type
	 */
	private void replaceProductType(){query = query.replace("{PRODUCT_TYPE}", productType);}
	
	/**
	 * Executes the query, ensures IDs were returned, and captures the IDs for later use
	 */
	private void executeQuery(){
		TestReporter.log("Facility ID: " + facilityId);
		TestReporter.log("Query: " + query);
		System.out.println("QUERY: " + query);

		odb = new OracleDatabase(environment, database);
		resultSet = new Recordset(odb.getResultSet(query));		
		
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "The product id query did not return any records for facilty id ["+facilityId+"] in ["+environment+"].");
		TestReporter.log("Record Count: " + String.valueOf(resultSet.getRowCount()));
		setProductIds();
	}
	
	/**
	 * Iterates through all returned IDs and stores them in an array
	 */
	private void setProductIds(){
		productIds = new String[resultSet.getRowCount()];
		int prod = 0;
		while(resultSet.hasNext()){
			productIds[prod] = resultSet.getValue(Dreams.ProductIds.productIdColumnName);
			prod++;
			resultSet.moveNext();
		}
		resultSet.moveFirst();
	}
	
	/**
	 * Returns the first recordset
	 * @return - the first recordset as a string array
	 */
	public String[] getFirstRecordSet(){
		String[] firstRecordSet = {"PRODUCT ID", productIds[0]};
		return firstRecordSet;
	}
	
	/**
	 * Returns the first product ID
	 * @return - first product ID as a string
	 */
	public String getFirstProductId(){return productIds[0];}
	/**
	 * Returns all product IDs
	 * @return all product IDs as a Recordset
	 */
	public Recordset getAllProductIds(){return resultSet;}
	
	@Test
	public void test(){
		RetrieveProductIdByFacilityId retrieve = new RetrieveProductIdByFacilityId("Grumpy", "80008077", "", true, false);
		
		System.out.println("Number of product IDs: " + retrieve.getAllProductIds().getRowCount());
		System.out.println("First product ID: " + retrieve.getFirstProductId());
		System.out.println();
		
		Recordset set = retrieve.getAllProductIds();
		int i = 0;
		while(set.hasNext()){
			System.out.println("Product ID ["+String.valueOf(i+1)+"]: " + set.getValue(Dreams.ProductIds.productIdColumnName));
			i++;
			set.moveNext();
		}
	}
}
