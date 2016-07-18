package com.disney.api.soapServices.applicationError;

/**
 * This class mirrors actual Activity Error Codes and Message
 * @see https://github.disney.com/WDPR-Composite-Modernization/activity/blob/CM-develop/Activity/src/main/java/com/wdpr/bussvcs/activity/service/exception/ActivityErrorCode.java
 * @author Justin Phlegar
 *
 */

public class ActivityErrorCode {
    
    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUP_MISSING = new  ApplicationErrorCode("Activity", 7073, "Travel Component Group is missing");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_MISSING = new  ApplicationErrorCode("Activity", 8084, "Travel component Id is missing");

    public static final ApplicationErrorCode PROFILEID_MISSING = new  ApplicationErrorCode("Activity", 8088, "Service request detail has profile Id missing");

    public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new  ApplicationErrorCode("Activity", 7065, "RECORD NOT FOUND");

    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS = new  ApplicationErrorCode("Activity", 7110, " Travel Status is invalid ");

    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new  ApplicationErrorCode("Activity", 7007, "TRAVEL_PLAN_NOT_FOUND");

    public static final ApplicationErrorCode CANNOT_CREATE_GENERIC_ACTIVITY_EVENT = new  ApplicationErrorCode("Activity", 9117, "Unable to Create a Generic Activity Event");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new  ApplicationErrorCode("Activity", 7008, "Travel Plan Segment Not Found");

    public static final ApplicationErrorCode SERVICE_START_DATE_REQUIRED = new  ApplicationErrorCode("Activity", 8029, "INVALID  SERVICE START DATE!!");

    public static final ApplicationErrorCode INVALID_PRODUCT_TYPE_NAME = new  ApplicationErrorCode("Activity", 8032, "INVALID PRODUCT TYPE !!");

    public static final ApplicationErrorCode REVENUE_CLASSFICATION_IS_REQUIRED = new  ApplicationErrorCode("Activity", 8054, "COMPONENTPRICE.REVENUE CLASSFICATION IS REQUIRED!!");

    public static final ApplicationErrorCode PRODUCT_ID_REQUIRED = new  ApplicationErrorCode("Activity", 8053, "PRODUCT ID IS REQUIRED !!");

    public static final ApplicationErrorCode NOT_VALID_GATHERING_DETAIL = new  ApplicationErrorCode("Activity", 7156, "Gathering Detail is not valid");

    public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY = new  ApplicationErrorCode("Activity", 7102, " Travel Agency is invalid ");

    public static final ApplicationErrorCode INVALID_REQUEST = new  ApplicationErrorCode("Activity", 9042, "INVALID REQUEST !");

    public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new  ApplicationErrorCode("Activity", 7177, " No Accommodation Component found.");

    public static final ApplicationErrorCode PRICING_SERVICE_FAILURE = new  ApplicationErrorCode("Activity", 8303, "Error Invoking Pricing Service ");

    public static final ApplicationErrorCode CHARGE_ITEMS_NOT_FOUND = new  ApplicationErrorCode("Activity", 7113, " Charge Items are not found");

    public static final ApplicationErrorCode REVENUE_TYPE_IS_REQUIRED = new  ApplicationErrorCode("Activity", 8055, "UNITPRICE.BASECHARGE.REVENUE TYPE IS REQUIRED!!");

    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new  ApplicationErrorCode("Activity", 7026, "Travel Plan Guest is required");

    public static final ApplicationErrorCode REVENUE_TYPE_NULL_FROM_PRICING = new  ApplicationErrorCode("Activity", 8312, "Revenue Type from Pricing is null");

    public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new  ApplicationErrorCode("Activity", 8304, "Error Invoking Packaging Service ");

    public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new  ApplicationErrorCode("Activity", 7064, "Required parameters are missing");

    public static final ApplicationErrorCode LILO_PARY_SERVICE_FAILURE = new  ApplicationErrorCode("Activity", 8502, "Error Invoking Lilo Party Service ");

    public static final ApplicationErrorCode APPLICATION_EXCEPTION = new  ApplicationErrorCode("Activity", 7063, "GENERAL ERROR");

    public static final ApplicationErrorCode NO_PROFILE_FOUND = new  ApplicationErrorCode("Activity", 8089, "No profile found for profile Id");

    public static final ApplicationErrorCode NO_SERVICE_TYPE_PROFILE_FOUND = new  ApplicationErrorCode("Activity", 8104, "Profile is not of type service, profile Id");

    public static final ApplicationErrorCode CANNOT_CREATE_GSR_EVENT = new  ApplicationErrorCode("Activity", 10201, "Unable to Create an GSR Event");

    public static final ApplicationErrorCode CANNOT_CREATE_RES_CHANGE_EVENT = new  ApplicationErrorCode("Activity", 9118, "Unable to Create a Res Change Event");

    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_GUEST_EVENT = new  ApplicationErrorCode("Activity", 9124, "Unable to Create a Travel Plan Guest Event");

    public static final ApplicationErrorCode PRODUCT_DETAILS_CANNOT_BE_EMPTY = new  ApplicationErrorCode("Activity", 8006, "PRODUCT DETAILS CANNOT BE EMPTY!");

    public static final ApplicationErrorCode INVALID_PARTYMIX = new  ApplicationErrorCode("Activity", 7005, "Invalid PartyMix. Please send valid partymix");

    public static final ApplicationErrorCode AGE_TYPE_REQUIRED = new  ApplicationErrorCode("Activity", 7035, "Age Type is required");

    public static final ApplicationErrorCode AGE_REQUIRED = new  ApplicationErrorCode("Activity", 8018, "AGE IS REQUIRED!");

    public static final ApplicationErrorCode MORE_THAN_ONE_AFFILIATION_FOUND = new  ApplicationErrorCode("Activity", 8020, "MORE_THAN_ONE_AFFILIATION_FOUND!");

    public static final ApplicationErrorCode INVALID_MEMBERSFIP = new  ApplicationErrorCode("Activity", 8015, "INVALID MEMBERSHIP/AFFILIATION !");

    public static final ApplicationErrorCode INVALID_AUTHORIZATION_CODE = new  ApplicationErrorCode("Activity", 8038, "INVALID AUTHORIZATION CODE !!");

    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new  ApplicationErrorCode("Activity", 7096, "communication Channel is required");

    public static final ApplicationErrorCode COMPONENT_TYPE_NAME_IS_REQUIRED = new  ApplicationErrorCode("Activity", 8057, "COMPONENT TYPE NAME IS REQUIRED!!");

    public static final ApplicationErrorCode PRICING_PARTY_MIX_REQUIRED = new  ApplicationErrorCode("Activity", 8056, "PRICING PARTY MIX IS REQUIRED!!");

    public static final ApplicationErrorCode UNIT_PRICES_ARE_EMPTY = new  ApplicationErrorCode("Activity", 8058, "COMPONENTPRICE.UNITPRICES ARE REQUIRED!!");

    public static final ApplicationErrorCode BASE_CHARGE_IS_REQUIRED = new  ApplicationErrorCode("Activity", 8059, "UNITPRICE.BASE CHARGE IS REQUIRED!!");

    public static final ApplicationErrorCode INVALID_FACILITY = new  ApplicationErrorCode("Activity", 8011, "FACILITY ID/NAME IS REQUIRED!");

    public static final ApplicationErrorCode INVALID_FACILITY_ID = new  ApplicationErrorCode("Activity", 8030, "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!!");

    public static final ApplicationErrorCode GUEST_REQUEST_ID_REQUIRED = new  ApplicationErrorCode("Activity", 8019, "GUEST REQUEST ID IS REQUIRED!");

    public static final ApplicationErrorCode INVALID_INTERNAL_COMMENT = new  ApplicationErrorCode("Activity", 8014, "COMMENT TEXT AND TYPE ARE REQUIRED !");

    public static final ApplicationErrorCode NO_PRODUCT_FOUND = new  ApplicationErrorCode("Activity", 8567, "NO_PRODUCT_FOUND !!");

    public static final ApplicationErrorCode NO_RESERVABLE_RESOURCE_ID = new  ApplicationErrorCode("Activity", 8010, "RESERVABLE RESOURCE ID IS REQUIRED!");

    public static final ApplicationErrorCode FREEZE_ID_REQUIRED = new  ApplicationErrorCode("Activity", 7025, "Freeze Id is required");

    public static final ApplicationErrorCode INVALID_VALUE = new  ApplicationErrorCode("Activity", 7082, "INVALID VALUE");

    public static final ApplicationErrorCode COMPONENT_PRICE_REQUIRED = new  ApplicationErrorCode("Activity", 8016, "COMPONENT PRICE IS REQUIRED!");

    public static final ApplicationErrorCode PRODUCT_TYPE_NAME_REQUIRED = new  ApplicationErrorCode("Activity", 8031, "PRODUCT TYPE NAME IS REQUIRED!!");

    public static final ApplicationErrorCode INVALID_LOS_OR_DURATION = new  ApplicationErrorCode("Activity", 8028, "INVALID DURATION/UNIT MEASURE COUNT!");

    public static final ApplicationErrorCode INVALID_REASON = new  ApplicationErrorCode("Activity", 8021, "INVALID REASON ID!");

    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new  ApplicationErrorCode("Activity", 7094, "Sales Channel is required");

    public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new  ApplicationErrorCode("Activity", 8013, "SOURCER ACCOUNTING CENTER IS REQUIRED!");

    public static final ApplicationErrorCode INVALID_TAX_EXEMPT = new  ApplicationErrorCode("Activity", 8012, "INVALID TAX EXEMPT DETAIL!");

    public static final ApplicationErrorCode INVALID_TRAVELPLAN_ID_RECIEVED_FROM_SIEBEL = new  ApplicationErrorCode("Activity", 7193, " Invalid Travel Plan Id received from SIEBEL");

    public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new  ApplicationErrorCode("Activity", 8314, "Error Invoking  Inventory Management Service ");

    public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new  ApplicationErrorCode("Activity", 8307, "Error Invoking  Folio Management Service ");

    public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new  ApplicationErrorCode("Activity", 91443, "Validation Failed.");

    public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new  ApplicationErrorCode("Activity", 91443, "Data not found.");

    public static final ApplicationErrorCode SAC_REQUIRED = new  ApplicationErrorCode("Activity", 91436, "SAC IS REQUIRED!");

    public static final ApplicationErrorCode INCONSISTENT_DATA = new  ApplicationErrorCode("Activity", 7072, "Inconsitent Data");

    public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new  ApplicationErrorCode("Activity", 9095, "Print Package Component Report failed");

    public static final ApplicationErrorCode EXCEPTION_RULE_FIRED = new  ApplicationErrorCode("Activity", 8555, "RESManagement suggests to stop this reservation");

    public static final ApplicationErrorCode INVALID_RESPONSE = new  ApplicationErrorCode("Activity", 8505, "Invalid Response From an External System!");
}

