package com.disney.utils.dataFactory.database.sqlStorage;

/**
 * This class contains queries that are designed to be used in the Dreams databases in the various environments.
 * @author Waightstill W Avery
 *
 */
public class Dreams {
	final static String LILOMANAGERROLE = "Manager";
	final static String LILONOTILLROLE = "null";
	
	public static String getReservationInfoByTpsId(final String tps){
		return  " SELECT tp.TP_ID, tps.TPS_ID, tps.TRVL_STS_NM TPS_TRAVEL_STATUS, tps.SRC_ACCT_CTR_ID, tps.TPS_ARVL_DT, tps.TPS_DPRT_DT, tc_grp.TC_GRP_NB, tc_grp.TC_GRP_TYP_NM,tc.TC_ID, tc.TC_TYP_NM, tc.PROD_ID, tc.TC_STRT_DTS , tc.TC_END_DTS, tc.PROD_TYP_NM, tc.FAC_ID, tc.TRVL_STS_NM TC_TRVL_STS_NM, ASGN_OWN_ID TC_ASGN_OWN_ID " +
				" FROM RES_MGMT.TP, RES_MGMT.TPS, RES_MGMT.TC_GRP, RES_MGMT.TC " +
				" WHERE tp.TP_ID = tps.TP_ID " +
				" AND tps.TPS_ID = TC_GRP.TPS_ID " +
				" AND tc_grp.TC_GRP_NB = tc.TC_GRP_NB " +
				" AND tps.tps_id ='" + tps + "'";
	}
	
	public static String getTpPartyId(final String tpId){
		return "SELECT TXN_PTY_ID, PRMY_PTY_IN " +
				" FROM RES_MGMT.TP_PTY " + 
				" WHERE TP_ID = " + tpId;
	}
	
	public static String getTcPartyId(final String tcId){
		return "SELECT TXN_IDVL_PTY_ID " +
				" FROM RES_MGMT.TC_GST " + 
				" WHERE TC_ID = " + tcId;
	}
	
	public static String getGuestExternalReferenceInfoByType(String type){
		return "Select ext.TXN_PTY_ID, "
				+ "    ext.TXN_PTY_EXTNL_REF_VAL "
				+ " from GUEST.TXN_PTY_EXTNL_REF ext "
				+ " where  ext.PTY_EXTNL_SRC_NM= '" + type + "' "
				+ "    and ROWNUM = 1 ";
	}
	
	public static String getTpsIDFromExternalReference(String externalRefVal){
		return "SELECT TPS_ID FROM RES_MGMT.TPS_EXTNL_REF WHERE TPS_EXTNL_REF_VL = '" + externalRefVal + "'";
	}

	public static String getTcReservableResourceID(String tcId){
		return "SELECT RSRC_INVTRY_TYP_CD FROM RES_MGMT.TC_RSRVBL_RSRC WHERE TC_ID = '" + tcId + "'";
	}
	
	// Query to retrieve a lilo user
	public static String getLiloUserAndBankAccountCenterNameByRole= "select a.BANK_IN_USER_ID, b.acct_ctr_nm "
			+ "from folio.bank_in a "
			+ "join accting.acct_ctr b on a.bank_acct_ctr_id = b.acct_ctr_id "
			+ "where a.till_typ_nm = '{ROLE}' "
			+ "and rownum = 1 "
			+ "and a.bank_in_id Not In ("
			+ "select a.bank_in_id "
			+ "from folio.bank_in a "
			+ "join folio.bank_out b on a.bank_in_id = b.bank_in_id "
			+ "where a.till_typ_nm = '{ROLE}')";
	
	// Query to retrieve lilo user's banking accounting center name
	public static String getBankAccountCenterNameByUserName = "select b.acct_ctr_nm "
			+ "from folio.bank_in a "
			+ "join accting.acct_ctr b on a.bank_acct_ctr_id = b.acct_ctr_id "
			+ "where a.till_typ_nm = 'Manager' "
			+ "and a.BANK_IN_USER_ID like '%{USER}%' "
			+ "and a.bank_in_id Not In ("
			+ "select a.bank_in_id "
			+ "from folio.bank_in a "
			+ "join folio.bank_out b on a.bank_in_id = b.bank_in_id "
			+ "where a.till_typ_nm = 'Manager')";
	
	public static String getChargeInfo(String id){
		return " select * from folio.chrg where CHRG_ID = " + id;
	}
	
	public static String getTcgTypeByTcg(String tcg){
		return "select TC_GRP_TYP_NM from res_mgmt.tc_grp a where a.tc_grp_nb = '"+tcg+"'";
	}
	
	public static String getTpsByTcg(String tcg){
		return "select a.TPS_ID from res_mgmt.tc_grp a where a.tc_grp_nb = '"+tcg+"'";
	}
	
	/**
	 * This subclass of queries is intended to query the Dreams database for product IDs for a given facility ID.  
	 * The genesis of this class occurred to resolve the issue of scheduled events (i.e. ALC) reservation product IDs not being consistent across environments.
	 * There are 3 parameters that were used to derive the following queries:
	 * **	ageDef	-	assumed to mean that a product ID contains an age definition; required for some reservations to be retrieved after having been booked/modified.
	 * **	reservable	-	assumed to mean that a product ID can be booked/reserved; required for some reservations to be booked/modified.
	 * **	product type	-	the type of product that is desired for a particular facility ID; multiple product IDs can be affiliated with a given facility ID
	 * @author Waightstill W Avery
	 *
	 */
	public static class ProductIds{
		public static final String productIdColumnName = "PROD_ID";
		
		private static final String baseProductIdQuery = "select unique a.prod_id as "+productIdColumnName+" from pricing.fac_prod a ";
		private static final String joinForReservableAndProdTypeName = "join pricing.prod b on a.prod_id = b.prod_id ";
		private static final String joinForAgeDef = "join pricing.cmpnt_prod_age_def d on a.prod_id = d.prod_id ";
		private static final String facilityPart = "where a.fac_id = {FACILITY_ID} ";
		private static final String endDatePart = "and a.fac_prod_end_dt is null ";
		private static final String reservablePart = "and b.prod_rsrvbl_in = 'Y' ";
		private static final String productTypeNamePart = "and b.prod_typ_nm = '{PRODUCT_TYPE}'";
		
		private static final String noFilterQuery = baseProductIdQuery + facilityPart + endDatePart;	
		private static final String ageDefProductTypeReservableQuery = baseProductIdQuery + joinForReservableAndProdTypeName + joinForAgeDef + facilityPart + endDatePart + reservablePart + productTypeNamePart;
		private static final String ageDefProductTypeQuery = baseProductIdQuery + joinForAgeDef + joinForReservableAndProdTypeName + facilityPart + endDatePart + productTypeNamePart;
		private static final String ageDefReservableQuery = baseProductIdQuery + joinForReservableAndProdTypeName + joinForAgeDef + facilityPart + endDatePart + reservablePart;
		private static final String ageDefQuery = baseProductIdQuery + joinForReservableAndProdTypeName + joinForAgeDef + facilityPart + endDatePart;	
		private static final String productTypeReservableQuery = baseProductIdQuery + joinForReservableAndProdTypeName + facilityPart + endDatePart + reservablePart + productTypeNamePart;
		private static final String productTypeQuery = baseProductIdQuery + joinForReservableAndProdTypeName + facilityPart + endDatePart + productTypeNamePart;
		private static final String reservableQuery = baseProductIdQuery + joinForReservableAndProdTypeName + facilityPart + endDatePart + reservablePart;
		
		public static String getNoFilterQuery(){return noFilterQuery;}
		public static String getAgeDefProductTypeReservableQuery(){return ageDefProductTypeReservableQuery;}
		public static String getAgeDefProductTypeQuery(){return ageDefProductTypeQuery;}
		public static String getAgeDefReservableQuery(){return ageDefReservableQuery;}
		public static String getAgeDefQuery(){return ageDefQuery;}
		public static String getProductTypeReservableQuery(){return productTypeReservableQuery;}
		public static String getProductTypeQuery(){return productTypeQuery;}
		public static String getReservableQuery(){return reservableQuery;}
	}
	
	public static class ComponentProductIds{
		public static final String componentProductIdColumnName = "COMPONENT_ID";
		public static final String componentProductTypeColumnName = "COMPONENT_TYPE";
		
		private static final String baseComponentProductIdQuery = "select unique a.prod_id as "+componentProductIdColumnName+", c.prod_typ_nm as "+componentProductTypeColumnName+" from pricing.cmpnt_prod a ";
		private static final String joinForAgeDef = "join pricing.cmpnt_prod_age_def b on a.prod_id = b.prod_id ";
		private static final String joinForComponentType = "join pricing.prod c on a.prod_id = c.prod_id ";
		private static final String joinForFacilityId = "join pricing.fac_prod d on a.prod_id = d.prod_id ";
		private static final String facilityIdQuery = "where d.fac_id = '{FACILITY_ID}'";
		
		public static String productIdWithAgeDefFacilityIdQuery = baseComponentProductIdQuery + joinForAgeDef + joinForComponentType + joinForFacilityId + facilityIdQuery;
	}

	public static String getLiloManager_BankAccountCenterName(){ return getLiloUserAndBankAccountCenterNameByRole.replace("{ROLE}", LILOMANAGERROLE);}
	public static String getLiloNoTill_BankAccountCenterName(){ return getLiloUserAndBankAccountCenterNameByRole.replace("{ROLE}", LILONOTILLROLE);}
	public static String getBankAccountcEnterNameByUserName(String user){ return getBankAccountCenterNameByUserName.replace("{USER}", user);}
}