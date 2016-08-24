package com.disney.utils.dataFactory.database.sqlStorage;

public class Pricing {
	public static String getProductInfoByFacilityIdAndProdName(final String facilityId, final String productName){
		return " SELECT PROD_TYP_NM, FAC_NM, FAC.FAC_ID, FAC_PROD_TYP_NM,PROD.PROD_ID,PROD_INTRNL_NM " +
				" FROM ACCTING.FAC  " +
				" JOIN PRICING.FAC_PROD ON FAC.FAC_ID = FAC_PROD.FAC_ID " +
				" JOIN PRICING.PROD ON FAC_PROD.PROD_ID = PROD.PROD_ID " +
				" WHERE PROD_TYP_NM in ('ShowPackage', 'DiningProduct','ShowDiningProduct','CategoryDiningProduct', " +
				" 'FoodAndBeverageProduct','ActivityProduct','ViewingExperienceActivityProduct', " +
				" 'TourActivityProduct','RecreationActivityProduct','ChildActivityProduct') " +
				" AND PROD_RSRVBL_IN = 'Y' " +
				" AND FAC.FAC_ID = " + facilityId + 
				" AND PROD_INTRNL_NM = '" + productName + "' " +
				" AND FAC_PROD_TYP_NM NOT IN ('Proximity' , 'Activity Product Location','Pickup Location','Host location') " +
				" ORDER BY PROD_TYP_NM, FAC_NM, PROD_INTRNL_NM";
	}
	
	public static String getProductInfoByFacilityNameAndProdName(final String facilityName, final String productName){
		return " SELECT PROD_TYP_NM, FAC_NM, FAC.FAC_ID, FAC_PROD_TYP_NM,PROD.PROD_ID,PROD_INTRNL_NM " +
				" FROM ACCTING.FAC  " +
				" JOIN PRICING.FAC_PROD ON FAC.FAC_ID = FAC_PROD.FAC_ID " +
				" JOIN PRICING.PROD ON FAC_PROD.PROD_ID = PROD.PROD_ID " +
				" WHERE PROD_TYP_NM in ('ShowPackage', 'DiningProduct','ShowDiningProduct','CategoryDiningProduct', " +
				" 'FoodAndBeverageProduct','ActivityProduct','ViewingExperienceActivityProduct', " +
				" 'TourActivityProduct','RecreationActivityProduct','ChildActivityProduct') " +
				" AND PROD_RSRVBL_IN = 'Y' " +
				" AND FAC.FAC_NM = '" + facilityName +  "' " +
				" AND PROD_INTRNL_NM = '" + productName + "' " +
				" AND FAC_PROD_TYP_NM NOT IN ('Proximity' , 'Activity Product Location','Pickup Location','Host location') " +
				" ORDER BY PROD_TYP_NM, FAC_NM, PROD_INTRNL_NM";
	}
}
