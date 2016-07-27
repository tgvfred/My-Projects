package com.disney.utils.dataFactory;

import com.disney.utils.dataFactory.database.Recordset;

public class ProductInfo {
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 */
	private static String mealTable = "QAAUTO_METADATA_MEAL_PRODUCTS";


	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 */
	private static String activityTable = "QAAUTO_METADATA_ACTIVITY_PRODUCTS";
	
	
	/**
	 * Common column names between Meal and Activity tables
	 * @author AutoXP
	 *
	 */
	private enum ProductColumns{
		PRODUCT_NAME{ public String toString() {return "PRODUCT_NAME";}},
		PRODUCT_ID{ public String toString() {return "PRODUCT_ID";}},
		FACILITY_NAME{ public String toString() {return "FACILITY_NAME";}},
		FACILITY_ID{ public String toString() {return "FACILITY_ID";}},
		MEAL_PERIOD_TYPE{ public String toString() {return "MEAL_PERIOD_TYPE";}},
		MEAL_EXPERIENCE_TYPE{ public String toString() {return "EXPERIENCE_TYPE";}},
		MEAL_CUISINE_TYPE{ public String toString() {return "CUISINE_TYPE";}},
		MEAL_SERVICE_STYLE{ public String toString() {return "SERVICE_STYLE";}},
		ACTIVITY_PRODUCT_TYPE{ public String toString() {return "PRODUCT_TYPE";}},
		ACTIVITY_PRODUCT_TYPE_ID{ public String toString() {return "PRODUCT_TYPE_ID";}},
		ACTIVITY_PRODUCT_SUBTYPE{ public String toString() {return "PRODUCT_SUBTYPE";}},
		ACTIVITY_PRODUCT_SUBTYPE_ID{ public String toString() {return "PRODUCT_SUBTYPE_ID";}}
	}

	/**
	 * Main method that calls Virtual Table code to get info
	 * @param table - references mealTable or activityTable for Virtual Table to look at
	 * @param using - Which column is being queried in the table
	 * @param data - data record to find in desired column
	 * @return Recordset of rows that matched query
	 */
	private static Recordset getInfo(String table, ProductColumns using, String data){
		return VirtualTable.compileJSON(table, new VirtualTable().getRows(table, using.toString(), data ));
	}
		
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param name Name of the Product to find ID for
	 * @return Product ID for desired Product Name
	 */
	public static String getMealProductIDByName(String name){
		return getInfo(mealTable, ProductColumns.PRODUCT_NAME, name).getValue(ProductColumns.PRODUCT_ID.toString());
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param id ID of the Product to find Name for
	 * @return Product Name for desired Product ID
	 */
	public static String getMealProductNameById(String id){
		return getInfo(mealTable, ProductColumns.PRODUCT_ID, id).getValue(ProductColumns.PRODUCT_NAME.toString());
	}
	
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param id ID of the Facility to find products for
	 * @return Recordset of all products for desired Facility ID
	 */
	public static Recordset getAllMealProductsByFacilityId(String id){
		return getInfo(mealTable, ProductColumns.FACILITY_ID, id);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param name Name of the Facility to find products for
	 * @return Recordset of all products for desired Facility Name
	 */
	public static Recordset getAllMealProductsByFacilityName(String name){
		return getInfo(mealTable, ProductColumns.FACILITY_NAME, name);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param type Experience Type ('Breakfast', 'Dinner', 'Lunch', 'Special', 'Brunch', 'Lounge')
	 * @return Recordset of all products for desired Period Type
	 */
	public static Recordset getAllMealProductsByMealPeriodType(String type){
		return getInfo(mealTable, ProductColumns.MEAL_PERIOD_TYPE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param  type Experience Type ('Character Dining', 'Themed Dining', 'Dining Event', 'Casual Dining','Signature Dining', 
	 * 'Special and Unique Dining','Dinner Show','Quick Service Restaurant','Lounges')
	 * @return Recordset of all products for desired Experience Type
	 */
	public static Recordset getAllMealProductsByExperinceType(String type){
		return getInfo(mealTable, ProductColumns.MEAL_EXPERIENCE_TYPE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param type Experience Type of the Product ('American', 'Norwegian', 'Pacific Northwest', 'Beverages Only', 'French', 
	 * 'Seafood', 'German', 'Barbeque', 'Cajun/Creole', 'African', 'Cuban', 'Californian', 'Steakhouse', 'Modern American', 
	 * 'Mediterranean', 'Southern and American', 'Latin', 'British', 'Italian', 'Greek', 'Mexican', 'Polynesian', 
	 * 'Chinese', 'Irish', 'Moroccan', 'Japanese')
	 * @return Recordset of all products for desired Cuisine Type
	 */
	public static Recordset getAllMealProductsByCuisineType(String type){
		return getInfo(mealTable, ProductColumns.MEAL_CUISINE_TYPE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_PRODUCTS
	 * @param type Service Type of the products ('A la Carte', 'Family-style', 'Prix Fixe Menu', 'Quick Service', 'Table Service', 
	 * 'Character Dining', 'Set Menu (all-you-care-to-eat))
	 * @return Recordset of all products for desired Service Type
	 */
	public static Recordset getAllMealProductsByServiceStyle(String type){
		return getInfo(mealTable, ProductColumns.MEAL_SERVICE_STYLE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param name Name of the Product to find ID for
	 * @return Product ID for desired Product Name
	 */
	public static String getActivityProductIDByName(String name){
		return getInfo(activityTable, ProductColumns.PRODUCT_NAME, name).getValue(ProductColumns.PRODUCT_ID.toString());
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param id ID of the Product to find Name for
	 * @return Product Name for desired Product ID
	 */
	public static String getActivityProductNameById(String id){
		return getInfo(activityTable, ProductColumns.PRODUCT_ID, id).getValue(ProductColumns.PRODUCT_NAME.toString());
	}

	
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param id ID of the Facility to find products for
	 * @return Recordset of all products for desired Facility ID
	 */
	public static Recordset getAllActivityProductsByFacilityId(String id){
		return getInfo(activityTable, ProductColumns.FACILITY_ID, id);
	}
	
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param name Name of the Facility to find products for
	 * @return Recordset of all products for desired Facility Name
	 */
	public static Recordset getAllActivityProductsByFacilityName(String name){
		return getInfo(activityTable, ProductColumns.FACILITY_NAME, name);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param type Product Type of the products ('Entertainment', 'Food & Beverage', 'MDX Overview', 'Merchandise', 
	 * 'Personal Services', 'Program', 'Recreation', 'Resort Services', 'Theme Park Service', 'Tour', 'Viewing', 'WDTC Package')
	 * @return Recordset of all products for desired Product Type
	 */
	public static Recordset getAllActivityProductsByProductType(String type){
		return getInfo(activityTable, ProductColumns.ACTIVITY_PRODUCT_TYPE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param id Product Type id of the products 
	 * @return Recordset of all products for desired Product Type ID
	 */
	public static Recordset getAllActivityProductsByProductTypeID(String id){
		return getInfo(activityTable, ProductColumns.ACTIVITY_PRODUCT_TYPE_ID, id);
	}
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param type Product Type of the products subtypes ('Animal Kingdom Tours', 'Animal Programs', 'Child', 'Epcot Tours', 
	 * 'Family', 'Fishing', 'Guest Tours', 'Interactive Experience''Magic Kingdom Tours', 'Parasailing','Personal Watercraft', 
	 * 'Product_Subtype', 'Resort Tours', 'Specialty Cruise', 'Surfing', 'Trail Rides')
	 * @return Recordset of all products for desired Product Type
	 */
	public static Recordset getAllActivityProductsByProductSubType(String type){
		return getInfo(activityTable, ProductColumns.ACTIVITY_PRODUCT_SUBTYPE, type);
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 * @param id Product Type id of the products 
	 * @return Recordset of all products for desired Product Type ID
	 */
	public static Recordset getAllActivityProductsByProductSubTypeID(String id){
		return getInfo(activityTable, ProductColumns.ACTIVITY_PRODUCT_SUBTYPE_ID, id);
	}
}
