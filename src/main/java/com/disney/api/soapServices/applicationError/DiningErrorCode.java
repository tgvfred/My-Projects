package com.disney.api.soapServices.applicationError;


/**
 * This class mirrors actual Dining Error Codes and Message
 * @see https://github.disney.com/WDPR-Composite-Modernization/DiningParent/blob/CM-develop/dining/src/main/java/com/wdpr/bussvcs/dining/util/DiningErrorCode.java
 * @see https://github.disney.com/WDPR-Sales-SVCS/DiningParent/blob/master/dining/src/main/java/com/wdpr/bussvcs/dining/util/DiningErrorCode.java
 * @author Justin Phlegar
 *
 */
public class DiningErrorCode {

			public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new ApplicationErrorCode("Dining", 7065,
		            "RECORD NOT FOUND");
			
			public static final ApplicationErrorCode INVALID_TRAVEL_STATUS = new ApplicationErrorCode("Dining", 7110,
		            " Travel Status is invalid ");
			
			public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new ApplicationErrorCode("Dining",
		            7008, "Travel Plan Segment Not Found");
			
			public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new ApplicationErrorCode("Dining",
		            8013, "SOURCER ACCOUNTING CENTER IS REQUIRED!");
			
			public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode("Dining", 7007,
		            "TRAVEL_PLAN_NOT_FOUND");
			
			public static final ApplicationErrorCode CANNOT_CREATE_GENERIC_DINE_EVENT = new ApplicationErrorCode("Dining",
		            9117, "Unable to Create a Generic Dine Event");
			
			public static final ApplicationErrorCode APPLICATION_EXCEPTION = new ApplicationErrorCode("Dining", 7063,
		            "GENERAL ERROR");
			
			public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new ApplicationErrorCode("Dining", 7177,
		            " No Accommodation Component found.");
			
			public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode("Dining", 9042,
		            "INVALID REQUEST !");
			
			public static final ApplicationErrorCode NOT_VALID_GATHERING_DETAIL = new ApplicationErrorCode("Dining", 7156,
		            "Gathering Detail is not valid");
			
			public static final ApplicationErrorCode PROFILEID_MISSING = new ApplicationErrorCode("Dining", 8088,
		            "Service request detail has profile Id missing");

			public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_MISSING = new ApplicationErrorCode("Dining", 8084,
		            "Travel component Id is missing");
			
			public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY = new ApplicationErrorCode("Dining", 7102,
		            " Travel Agency is invalid ");
			
			public static final ApplicationErrorCode DINE_INVENTORY_REQD = new ApplicationErrorCode(
					"Dining",
					9128,
					"Reservation has Dinning package.Inventory Details Required for Dinning Package");
			
			public static final ApplicationErrorCode SHOW_INVENTORY_REQD = new ApplicationErrorCode(
				    "Dining", 9127,
				    "Reservation has Show package.Inventory Details Required for Show Package");

			public static final ApplicationErrorCode SERVICE_PERIOD_REQUIRED = new ApplicationErrorCode("Dining", 8017,
		            "ENTERPRISE SERVICE PERIOD ID IS REQUIRED.!");
			
			public static final ApplicationErrorCode PRODUCT_ID_REQUIRED = new ApplicationErrorCode("Dining", 8053,
		            "PRODUCT ID IS REQUIRED !!");
			
			public static final ApplicationErrorCode FREEZE_ID_REQUIRED = new ApplicationErrorCode("Dining", 7025,
		            "Freeze Id is required");
			
			public static final ApplicationErrorCode EXCEPTION_RULE_FIRED = new ApplicationErrorCode("Dining", 8555,
		            "RESManagement suggests to stop this reservation");
			
			public static final ApplicationErrorCode REVENUE_CLASSFICATION_IS_REQUIRED = new ApplicationErrorCode("Dining",
		            8054, "COMPONENTPRICE.REVENUE CLASSFICATION IS REQUIRED!!");
			
			public static final ApplicationErrorCode SERVICE_START_DATE_REQUIRED = new ApplicationErrorCode("Dining", 8029,
		            "INVALID  SERVICE START DATE!!");
			
			public static final ApplicationErrorCode PROCESS_NAME_NULL = new ApplicationErrorCode("Dining", 8069,
		            "Process Name cannot be null");
			
			public static final ApplicationErrorCode INVALID_INVENTORY = new ApplicationErrorCode("Dining", 8027,
		            "INVALID GOT/WANT INVENTORY!");
			
			public static final ApplicationErrorCode TABLE_NUMBERS_ASSIGNED = new ApplicationErrorCode("Dining", 8026,
		            "TABLE NUMBERS ASSIGNED !");
			
			public static final ApplicationErrorCode INVALID_PARTYMIX = new ApplicationErrorCode("Dining", 7005,
		            "Invalid PartyMix. Please send valid partymix");
			
			public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode("Dining", 7124,
		            "Search Criteria is Invalid");
			
			public static final ApplicationErrorCode INVALID_FACILITY = new ApplicationErrorCode("Dining", 8011,
		            "FACILITY ID/NAME IS REQUIRED!");
			
			public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new ApplicationErrorCode("Dining", 9095,
		            "Print Package Component Report failed");
			
			public static final ApplicationErrorCode EXCEEDS_MAXIMUM_LIMIT = new ApplicationErrorCode("Dining", 7126,
		            "Exceeds Maximum Limit");
			
			public static final ApplicationErrorCode TRAVEL_PLAN_SEARCH_NO_RESULT = new ApplicationErrorCode("Dining", 7183,
		            "No travel plan data found.");
			
			public static final ApplicationErrorCode DINNERSHOW_PKG_NOT_FOUND = new ApplicationErrorCode("Dining", 8025,
		            "DINNER SHOW PACKAGE NOT FOUND !");
			
			public static final ApplicationErrorCode TKT_PRNT_ONLY_FOR_DINNERSHOW = new ApplicationErrorCode("Dining", 8022,
		            "TICKET CAN BE PRINTED/REPRINTED ONLY FOR A DINNER SHOW !");
			
			public static final ApplicationErrorCode DINNERSHOW_TKT_PRINTED = new ApplicationErrorCode("Dining", 8023,
		            "TICKET IS ALREADY PRINTED.PLEASE USE REPRINT !");
			
			public static final ApplicationErrorCode INVALID_REASON = new ApplicationErrorCode("Dining", 8021,
		            "INVALID REASON ID!");
			
			public static final ApplicationErrorCode DINNERSHOW_TKT_NOT_PRINTED = new ApplicationErrorCode("Dining", 8024,
		            "TICKET IS NOT PRINTED.PLEASE USE PRINT !");
			
			public static final ApplicationErrorCode INVALID_PRODUCT_TYPE_NAME = new ApplicationErrorCode("Dining", 8032,
		            "INVALID PRODUCT TYPE !!");
			
			public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new ApplicationErrorCode("Dining",
		            7064, "Required parameters are missing");
			
			public static final ApplicationErrorCode INVALID_VALUE = new ApplicationErrorCode("Dining", 7082,
		            "INVALID VALUE");
			
			public static final ApplicationErrorCode INVALID_RESPONSE = new ApplicationErrorCode("Dining", 8505,
		            "Invalid Response From an External System!");
			
			public static final ApplicationErrorCode CANNOT_CREATE_TABLE_SERVICE_DINE_EVENT = new ApplicationErrorCode(
		            "Dining", 9122, "Unable to Create a Event Table Service Dine Event");

			public static final ApplicationErrorCode CANNOT_CREATE_SHOW_DINE_EVENT = new ApplicationErrorCode("Dining",
		            9121, "Unable to Create a Show Dine Event");
			
			public static final ApplicationErrorCode CANNOT_CREATE_EVENT_DINE_EVENT = new ApplicationErrorCode("Dining",
		            9120, "Unable to Create a Event Dine Event");
			
			public static final ApplicationErrorCode CANNOT_CREATE_DINETIME_EVENT = new ApplicationErrorCode("Dining",
					9125, "Unable to Create a DineTime Event");
			
			public static final ApplicationErrorCode REVENUE_TYPE_IS_REQUIRED = new ApplicationErrorCode("Dining", 8055,
		            "UNITPRICE.BASECHARGE.REVENUE TYPE IS REQUIRED!!");
			
			public static final ApplicationErrorCode CHARGE_ITEMS_NOT_FOUND = new ApplicationErrorCode("Dining", 7113,
		            " Charge Items are not found");
			
			public static final ApplicationErrorCode PRICING_SERVICE_FAILURE = new ApplicationErrorCode("Dining", 8303,
		            "Error Invoking Pricing Service ");
			
			public static final ApplicationErrorCode REVENUE_TYPE_NULL_FROM_PRICING = new ApplicationErrorCode("Dining",
		            8312, "Revenue Type from Pricing is null");

			public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode("Dining", 7026,
		            "Travel Plan Guest is required");
			
			public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new ApplicationErrorCode("Dining", 8304,
		            "Error Invoking Packaging Service ");
			
			public static final ApplicationErrorCode LILO_PARY_SERVICE_FAILURE = new ApplicationErrorCode("Dining", 8502,
		            "Error Invoking Lilo Party Service ");
			
			public static final ApplicationErrorCode NO_SERVICE_TYPE_PROFILE_FOUND = new ApplicationErrorCode("Dining",
		            8104, "Profile is not of type service, profile Id");
			
			public static final ApplicationErrorCode NO_PROFILE_FOUND = new ApplicationErrorCode("Dining", 8089,
		            "No profile found for profile Id");
			
			public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(
		            "Dining", 8314, "Error Invoking  Inventory Management Service ");
			
			public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode("Dining",
		            8307, "Error Invoking  Folio Management Service ");
			
			public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new ApplicationErrorCode("Dining", 91443,
		            "Data not found.");
			
			public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new ApplicationErrorCode("Dining", 91443,
		            "Validation Failed.");
			
			public static final ApplicationErrorCode INVALID_TAX_EXEMPT = new ApplicationErrorCode("Dining", 8012,
		            "INVALID TAX EXEMPT DETAIL!");
			
			public static final ApplicationErrorCode NO_WILL_CALL_DETAILS_PROVIDED = new ApplicationErrorCode("Dining",
		            9018, "No detials provided to fulfill will call request");
			
			public static final ApplicationErrorCode INVALID_INTERNAL_COMMENT = new ApplicationErrorCode("Dining", 8014,
		            "COMMENT TEXT AND TYPE ARE REQUIRED !");
			
			public static final ApplicationErrorCode GUEST_REQUEST_ID_REQUIRED = new ApplicationErrorCode("Dining", 8019,
		            "GUEST REQUEST ID IS REQUIRED!");
			
			public static final ApplicationErrorCode INVALID_FACILITY_ID = new ApplicationErrorCode("Dining", 8030,
		            "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!!");
			
			public static final ApplicationErrorCode BASE_CHARGE_IS_REQUIRED = new ApplicationErrorCode("Dining", 8059,
		            "UNITPRICE.BASE CHARGE IS REQUIRED!!");
			
			public static final ApplicationErrorCode UNIT_PRICES_ARE_EMPTY = new ApplicationErrorCode("Dining", 8058,
		            "COMPONENTPRICE.UNITPRICES ARE REQUIRED!!");
			
			public static final ApplicationErrorCode PRICING_PARTY_MIX_REQUIRED = new ApplicationErrorCode("Dining", 8056,
		            "PRICING PARTY MIX IS REQUIRED!!");
			
			public static final ApplicationErrorCode COMPONENT_TYPE_NAME_IS_REQUIRED = new ApplicationErrorCode("Dining",
		            8057, "COMPONENT TYPE NAME IS REQUIRED!!");
			
			public static final ApplicationErrorCode INVALID_AUTHORIZATION_CODE = new ApplicationErrorCode("Dining", 8038,
		            "INVALID AUTHORIZATION CODE !!");
			
			public static final ApplicationErrorCode INVALID_MEMBERSFIP = new ApplicationErrorCode("Dining", 8015,
		            "INVALID MEMBERSHIP/AFFILIATION !");
			
			public static final ApplicationErrorCode MORE_THAN_ONE_AFFILIATION_FOUND = new ApplicationErrorCode("Dining",
		            8020, "MORE_THAN_ONE_AFFILIATION_FOUND!");
			
			public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_REQUIRED = new ApplicationErrorCode("Dining", 7018,
		            " Travel Plan Segment is Required ");
			
			public static final ApplicationErrorCode AGE_REQUIRED = new ApplicationErrorCode("Dining", 8018,
		            "AGE IS REQUIRED!");
			
			public static final ApplicationErrorCode AGE_TYPE_REQUIRED = new ApplicationErrorCode("Dining", 7035,
		            "Age Type is required");
			
			public static final ApplicationErrorCode PRODUCT_DETAILS_CANNOT_BE_EMPTY = new ApplicationErrorCode("Dining",
		            8006, "PRODUCT DETAILS CANNOT BE EMPTY!");
			
			public static final ApplicationErrorCode INVALID_LOS_OR_DURATION = new ApplicationErrorCode("Dining", 8028,
		            "INVALID DURATION/UNIT MEASURE COUNT!");
			
			public static final ApplicationErrorCode PRODUCT_TYPE_NAME_REQUIRED = new ApplicationErrorCode("Dining", 8031,
		            "PRODUCT TYPE NAME IS REQUIRED!!");
			
			public static final ApplicationErrorCode COMPONENT_PRICE_REQUIRED = new ApplicationErrorCode("Dining", 8016,
		            "COMPONENT PRICE IS REQUIRED!");
			
			public static final ApplicationErrorCode NO_RESERVABLE_RESOURCE_ID = new ApplicationErrorCode("Dining", 8010,
		            "RESERVABLE RESOURCE ID IS REQUIRED!");
			
			public static final ApplicationErrorCode NO_PRODUCT_FOUND = new ApplicationErrorCode("Dining", 8567,
		            "NO_PRODUCT_FOUND !!");
			
			public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode("Dining",
		            7096, "communication Channel is required");
			
			public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode("Dining", 7094,
		            "Sales Channel is required");
			
			public static final ApplicationErrorCode SAC_REQUIRED= new ApplicationErrorCode("Dining",
		            91436, "SAC IS REQUIRED!");
			
			public static final ApplicationErrorCode INCONSISTENT_DATA = new ApplicationErrorCode("Dining", 7072,
		            "Inconsitent Data");
			
			public static final ApplicationErrorCode INVALID_STATUS = new ApplicationErrorCode("Dining", 9041,
		            "Invalid Status");
			
			public static final ApplicationErrorCode FULFILL_WILL_CALL_REQUEST_INVALID = new ApplicationErrorCode("Dining",
		            9049, "Fullfill Will Call Reservation request invalid");
			
			public static final ApplicationErrorCode TICKETING_SERVICE_ERROR = new ApplicationErrorCode("Dining", 9061,
		            "Ticketing Service Error");
}
