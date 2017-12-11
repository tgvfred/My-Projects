package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class AccommodationErrorCode {
    private static final String APP_NAME = "Accommodation Sales";
    private static final String LILO_APP_NAME = "LILO_RESM";

    public static final ApplicationErrorCode ACCOMM_NOT_BOOKED_STATUS_SHARED = new ApplicationErrorCode(APP_NAME, 17192,
            " Accommodation should be in Booked status to be Shared");

    public static final ApplicationErrorCode GROUP_TEAM_NOT_CREATED = new ApplicationErrorCode(APP_NAME, 7179,
            "Group Team Can not be Created");

    public static final ApplicationErrorCode CANNOT_OVERRIDE_CANCELLED_RES = new ApplicationErrorCode(APP_NAME, 8509,
            " Cancelled accommodations cannot be overriden");

    public static final ApplicationErrorCode ACCOMM_NOT_BOOKED_STATUS_UNSHARED = new ApplicationErrorCode(APP_NAME, 27192,
            " Accommodation should be in Booked status to be UnShared");

    public static final ApplicationErrorCode APPLICATION_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7063,
            "GENERAL ERROR");

    public static final ApplicationErrorCode ACCOMMODATION_SALES_ERROR = new ApplicationErrorCode(APP_NAME, 100004,
            "Error in Accommodation Sales Service");

    public static final ApplicationErrorCode INVALID_FREEZE_REQUEST = new ApplicationErrorCode(APP_NAME, 7030,
            "Invalid Freeze Request. See HTTP Status Code");

    public static final ApplicationErrorCode ASYNC_CALL_FAILURE = new ApplicationErrorCode(
            "Accommodation", 8506, "Failure during Async call");

    public static final ApplicationErrorCode GROUPS_SERVICE_FAILURE = new ApplicationErrorCode(
            "Accommodation", 8305, "Error Invoking Groups Management Service ");

    public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8307,
            "Error Invoking  Folio Management Service ");

    public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            8314, "Error Invoking  Inventory Management Service ");

    public static final ApplicationErrorCode PARTY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 7135,
            "Error Invoking  PartyService Service ");

    public static final ApplicationErrorCode DATES_MUST_OVERLAP = new ApplicationErrorCode(APP_NAME, 7135,
            "Accommodation can not be shared - dates must overlap at least one night");

    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(APP_NAME, 9042,
            "INVALID REQUEST !");

    public static final ApplicationErrorCode ROOM_TYPE_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 8602,
            "Room Type not found ");

    public static final ApplicationErrorCode MISSING_ACTIVE_FLAG = new ApplicationErrorCode(APP_NAME, 8600,
            "Active Flag not found in Room Inventory Response");

    public static final ApplicationErrorCode NO_PROFILE_FOUND = new ApplicationErrorCode(APP_NAME, 8089,
            "No profile found for profile Id");

    public static final ApplicationErrorCode PROFILE_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8599,
            "Error Invoking Profile Service ");

    public static final ApplicationErrorCode CREATE_GROUP_TEAM = new ApplicationErrorCode(APP_NAME, 8079,
            "Error while trying create GroupTeam");

    public static final ApplicationErrorCode RETRIEVE_GROUP_TEAM = new ApplicationErrorCode(APP_NAME, 80079,
            "Error while trying retrieve GroupTeam");

    public static final ApplicationErrorCode OPERATION_NOT_SUPPORTED = new ApplicationErrorCode(APP_NAME, 9111,
            "This operation is not supported anymore from this version.");

    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7007,
            "TRAVEL_PLAN_NOT_FOUND");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7008,
            "Travel Plan Segment Not Found");

    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(APP_NAME, 7107,
            " Resort Period is invalid ");

    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7026,
            "Travel Plan Guest is required");

    public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY = new ApplicationErrorCode(APP_NAME, 7102,
            " Travel Agency is invalid ");

    public static final ApplicationErrorCode INVALID_VALUE = new ApplicationErrorCode(APP_NAME, 7082, "INVALID VALUE");

    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS = new ApplicationErrorCode(APP_NAME, 7110,
            " Travel Status is invalid ");

    public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(
            APP_NAME, 7216, "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");

    public static final ApplicationErrorCode ROOM_DETAIL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7228,
            "There should be at least one Room Detail");

    public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode(APP_NAME, 7106,
            " Resort Code is invalid ");

    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(APP_NAME, 7105,
            " Room Type is invalid ");

    public static final ApplicationErrorCode INVALID_PACKAGE_CODE = new ApplicationErrorCode(APP_NAME, 7104,
            " Package Code is invalid ");

    public static final ApplicationErrorCode NOT_VALID_GATHERING_DETAIL = new ApplicationErrorCode(APP_NAME, 7156,
            "Gathering Detail is not valid");

    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED = new ApplicationErrorCode(
            APP_NAME, 7090, "External Reference Source or Code required");

    public static final ApplicationErrorCode INVALID_EXTERNAL_REFERNCE_CODE = new ApplicationErrorCode(APP_NAME, 7086,
            "Invalid External Reference Code");

    public static final ApplicationErrorCode INTERNAL_RESERVATIONS_CANNOT_HAVE_EXTERNAL_REF_NUMBERS = new ApplicationErrorCode(
            APP_NAME, 7214, "External Reference Numbers not allowed For Internal Bookings !!");

    public static final ApplicationErrorCode EXTERNAL_REFERENCE_NUMBER_REQUIRED = new ApplicationErrorCode(APP_NAME,
            7088, "External Reference is required");

    public static final ApplicationErrorCode BOOKING_SOURCE_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7125,
            "Booking Source not found");

    public static final ApplicationErrorCode DELETE_TEAM_NAME = new ApplicationErrorCode(APP_NAME, 7009,
            "Invalid Delete Team Name Request");

    public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8304,
            "Error Invoking Packaging Service ");

    public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new ApplicationErrorCode(APP_NAME, 91443,
            "Validation Failed.");

    public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new ApplicationErrorCode(APP_NAME,
            91443, "Data not found.");

    public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new ApplicationErrorCode(APP_NAME, 7177,
            " No Accommodation Component found.");

    public static final ApplicationErrorCode INVALID_EXTERENAL_REFERENCE_DETAIL = new ApplicationErrorCode(APP_NAME,
            7092, "Invalid External Reference Details");

    public static final ApplicationErrorCode INVALID_GUEST_REFERENCE_DETAIL = new ApplicationErrorCode(APP_NAME, 91443,
            "Invalid External Reference Details");

    public static final ApplicationErrorCode INVALID_ORIGINAL_TXN_GUEST_ID = new ApplicationErrorCode(APP_NAME, 91444,
            "Invalid External Reference Details");

    public static final ApplicationErrorCode ROOM_OR_FACILITY_MISSING = new ApplicationErrorCode(APP_NAME, 91446,
            "Room Or Facility Is Missing");

    public static final ApplicationErrorCode SALES_CHANNEL_NOT_EXISTS_FOR_BLOCK = new ApplicationErrorCode(APP_NAME,
            7230, "Sales Channel are not available for the block");

    public static final ApplicationErrorCode INCONSISTENT_DATA = new ApplicationErrorCode(APP_NAME, 7072,
            "Inconsistent Data");

    public static final ApplicationErrorCode INVALID_PACKAGE_FOR_BLOCK = new ApplicationErrorCode(APP_NAME, 7169,
            "Package is invalid for the block");

    public static final ApplicationErrorCode PACKAGE_CANNOT_BE_SOLD = new ApplicationErrorCode(APP_NAME, 7170,
            "Package cannot be sold");

    public static final ApplicationErrorCode TRAVEL_AGENCY_NOT_VALID = new ApplicationErrorCode(APP_NAME, 7013,
            "Travel Agency is not valid");

    public static final ApplicationErrorCode GROUP_NOT_COMMISSIONABLE = new ApplicationErrorCode(APP_NAME, 7112,
            " Group is not commissionable");

    public static final ApplicationErrorCode FACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8601,
            "Error Invoking Facility Service ");

    public static final ApplicationErrorCode DATE_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9145,
            "Error Invoking Date Service ");

    public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7027,
            "Guest is Required");

    public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new ApplicationErrorCode(APP_NAME, 8013,
            "SOURCER ACCOUNTING CENTER IS REQUIRED!");

    public static final ApplicationErrorCode MISSING_CAMPUS_ID = new ApplicationErrorCode(APP_NAME, 8603,
            "Campus Id not found in Facility Response");

    public static final ApplicationErrorCode LOCATION_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9146,
            "Error Invoking Location Service ");

    public static final ApplicationErrorCode DATEFACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9147,
            "Error Invoking Date Facility Service ");

    public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode(APP_NAME, 7124,
            "Search Criteria is Invalid ");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEARCH_NO_RESULT = new ApplicationErrorCode(APP_NAME, 7183,
            "No travel plan data found.");

    public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new ApplicationErrorCode(APP_NAME, 9095,
            "Print Package Component Report failed");

    public static final ApplicationErrorCode ATS_TICKET_CODE_FROM_PRICING_NULL = new ApplicationErrorCode(APP_NAME,
            8506, "ATS Ticket Code from Pricing is null ");

    public static final ApplicationErrorCode PRICING_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8303,
            "Error Invoking Pricing Service ");

    public static final ApplicationErrorCode REVENUE_TYPE_IS_REQUIRED = new ApplicationErrorCode(APP_NAME, 8055,
            "UNITPRICE.BASECHARGE.REVENUE TYPE IS REQUIRED!!");

    public static final ApplicationErrorCode CREATE_COMMENTS = new ApplicationErrorCode(LILO_APP_NAME, 8078,
            "Create Comments Request Invalid");

    public static final ApplicationErrorCode UPDATE_COMMENTS = new ApplicationErrorCode(APP_NAME, 8078,
            "Create Comments Request Invalid");

    public static final ApplicationErrorCode SERVICE_ID_CONVERSION_ERROR = new ApplicationErrorCode(APP_NAME, 8117,
            "Error while converting serviceId");

    public static final ApplicationErrorCode LOCATION_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 9025,
            "Location Id is required");

    public static final ApplicationErrorCode ACCOMMODATIONFACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            8601, "Error Invoking Accommodation Facility Service ");

    public static final ApplicationErrorCode CHARGE_ITEMS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7113,
            " Charge Items are not found");
    public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7064,
            "Required parameters are missing");

    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_CANCELLED = new ApplicationErrorCode(
            APP_NAME, 7192, " Accommodation should be in Booked status to be cancelled");

    public static final ApplicationErrorCode CANNOT_CANCEL_ACCOMMODATIONS_WITH_TICKETS = new ApplicationErrorCode(
            APP_NAME, 7066, "Could not cancel accommodation with tickets");
    public static final ApplicationErrorCode PICKEDUP_PREARRIVAL_TICKETS = new ApplicationErrorCode(APP_NAME, 9097,
            "Pre arrival tickets have been picked up");

    public static final ApplicationErrorCode INVALID_FACILITY = new ApplicationErrorCode(APP_NAME, 8011,
            "FACILITY ID/NAME IS REQUIRED!");

    public static final ApplicationErrorCode CANNOT_CREATE_RES_CHANGE_EVENT = new ApplicationErrorCode(APP_NAME, 9118,
            "Unable to Create a Res Change Event");

    public static final ApplicationErrorCode CANNOT_CREATE_ADMISSION_EVENT = new ApplicationErrorCode(APP_NAME, 9119,
            "Unable to Create a Admission Event");
    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_CHANGE_EVENT = new ApplicationErrorCode(APP_NAME,
            9124, "Unable to Create a Travel Plan Change Event");

    public static final ApplicationErrorCode CANNOT_CREATE_TPV3_EVENT = new ApplicationErrorCode(APP_NAME, 9117,
            "Unable to Create a Generic TPv3 Event");

    public static final ApplicationErrorCode REVENUE_TYPE_NULL_FROM_PRICING = new ApplicationErrorCode(APP_NAME, 9120,
            "Revenue Type from Pricing is null");

    public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7192,
            "Accommodations not found");

    public static final ApplicationErrorCode DVC_RESERVATION = new ApplicationErrorCode(APP_NAME, 9122,
            "Cannot cancel DVC Reservation");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUPING_NOT_FOUND = new ApplicationErrorCode(APP_NAME,
            9039, "Travel Component Grouping not found");

    public static final ApplicationErrorCode REINSTATE_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 9121,
            "This reservation is not eligible to be Re-instated. "
                    + "Please contact the appropriate Reservation Office, "
                    + "Operations Support or Manager for assistance");

    public static final ApplicationErrorCode NOT_CANCELLED = new ApplicationErrorCode(APP_NAME, 9122,
            "Accommodation Not in Cancelled State");

    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7096,
            "communication Channel is required");

    public static final ApplicationErrorCode LILO_COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(LILO_APP_NAME, 7096,
            "communication Channel is required");

    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7094,
            "Sales Channel is required");

    public static final ApplicationErrorCode MULIPLE_ROOM_DETAILS = new ApplicationErrorCode(APP_NAME, 9125,
            "Multiple Room Details");

    public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE = new ApplicationErrorCode(APP_NAME, 7080,
            "cannot calculate Cancel fee");
    public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE_CAPS = new ApplicationErrorCode(APP_NAME, 7080,
            "Cannot calculate Cancel fee");

    public static final ApplicationErrorCode PACKAGE_RATE_CATEGORY_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7173,
            " Package Rate Category not found");

    public static final ApplicationErrorCode GAURANTEE_STATUS_CAN_NOT_BE_CHANGED = new ApplicationErrorCode(APP_NAME,
            7195, " Guarantee status can not be changed ");

    public static final ApplicationErrorCode LOCATION_ID_MANDATORY = new ApplicationErrorCode(APP_NAME, 8317,
            "Location Id is Mandatory");

    public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7065,
            "RECORD NOT FOUND");

    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME,
            9128, "Accommodation Component not found");

    public static final ApplicationErrorCode ROOM_READY_MESSAGE_INVALID = new ApplicationErrorCode(APP_NAME, 9129,
            "Room Ready Message Invalid");

    public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_DATA_NOT_SETUP = new ApplicationErrorCode(APP_NAME,
            9130, "Room Ready Notification Data Not SetUp");

    public static final ApplicationErrorCode SERVICE_REQUEST_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9131,
            "Error Invoking Service Request Service ");

    public static final ApplicationErrorCode ACCOMMODATION_INVALID_FOR_SHARE = new ApplicationErrorCode(APP_NAME, 7130,
            "Accommodation is invalid for Share");

    public static final ApplicationErrorCode INVALID_PARTYMIX = new ApplicationErrorCode(APP_NAME, 7005,
            "Invalid PartyMix. Please send valid partymix");

    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS_FOR_SHARE = new ApplicationErrorCode(APP_NAME, 7131,
            "Travel Status is invalid for Share");

    public static final ApplicationErrorCode ACCOMMODATION_ALREADY_ADDED_FOR_SHARE = new ApplicationErrorCode(APP_NAME,
            7132, "Accommodation has already been added for Share");

    public static final ApplicationErrorCode CANT_CHANGE_BLOCK_RESORT_PACKAGE_FOR_SHARED_ACCOMMODATION = new ApplicationErrorCode(
            APP_NAME, 7208, "Cannot change Block/Resort/Package for an shared Accommodation.");

    public static final ApplicationErrorCode INVALID_PARTY_MIX_FOR_SHARE = new ApplicationErrorCode(APP_NAME, 7137,
            "Invalid Party Mix.  Cannot Create Share With Accommodation");

    public static final ApplicationErrorCode PROPERTY_SHOULD_BE_DISNEY = new ApplicationErrorCode(APP_NAME, 7136,
            "Property should be Disney");

    public static final ApplicationErrorCode ACCOMMODATION_PERIOD_NOT_OVERLAP = new ApplicationErrorCode(APP_NAME, 7138,
            "Accommodation can not be shared - dates must overlap at least one night");

    public static final ApplicationErrorCode ACCOMMODATION_SHAREPARTYMIX_NOT_OVERLAP = new ApplicationErrorCode(APP_NAME, 7638,
            "Share Party Mix - overlap period error");

    public static final ApplicationErrorCode OVERRIDE_RATE_RACK_RATE_ERROR = new ApplicationErrorCode(APP_NAME, 7138,
            "Override rate cannot be more than rack rate.");

    public static final ApplicationErrorCode RATE_OVERRIDE_FAILURE = new ApplicationErrorCode(APP_NAME, 7140,
            "Rate override failed");

    public static final ApplicationErrorCode INVALID_CHANGE_REASON = new ApplicationErrorCode(APP_NAME, 7146,
            " Change Reason is invalid ");

    public static final ApplicationErrorCode CANNOT_OVERRIDE_CANCELLED_ACCOMMODATIONS = new ApplicationErrorCode(
            APP_NAME, 8509, " Cancelled accommodations cannot be overriden");

    public static final ApplicationErrorCode INVALID_SHARE_DATA = new ApplicationErrorCode(APP_NAME, 7140,
            "One Property Reservation cannot have multiple Accommodations which are shared");

    public static final ApplicationErrorCode INVALID_NUMBER_FORMAT = new ApplicationErrorCode(APP_NAME, 7143,
            "Invalid Number Format");

    public static final ApplicationErrorCode INVALID_VISIT_PURPOSE = new ApplicationErrorCode(APP_NAME, 7144,
            "Invalid Visit Purpose");

    public static final ApplicationErrorCode XBMS_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 7145,
            "XBMS Service Failure");

    public static final ApplicationErrorCode ENTERPRICE_FACILITY_REQUIRED = new ApplicationErrorCode(APP_NAME, 7146,
            "Enterprise Facility Id Missing");

    public static final ApplicationErrorCode RETRIEVE_COMMENTS = new ApplicationErrorCode(APP_NAME, 9001,
            "Unable to Retrieve Comments!");

    public static final ApplicationErrorCode CANNOT_MODIFY_CHECKEDIN_ACCOMMADATION = new ApplicationErrorCode(APP_NAME,
            7187, "Checked In Accommodation cannot be modified");

    public static final ApplicationErrorCode ROOM_INVENTORY_API_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            8306, "Error Invoking Room Inventory API");

    public static final ApplicationErrorCode INVALID_SHARE_ACTION = new ApplicationErrorCode(APP_NAME, 7239,
            "Action has to be Share");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_GUEST_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7147,
            "Travel Component Guest not found");

    private static final String MODULE_NAME = "Accommodation-Sales-Batch-Service";

    public static final ApplicationErrorCode INVALID_RQ = new ApplicationErrorCode(MODULE_NAME, 9020,
            "INVALID REQUEST !");

    public static final ApplicationErrorCode INVALID_UPDATE_PROCESS_STATUS_RQ = new ApplicationErrorCode(MODULE_NAME, 9015,
            "Invalid UpdateProcessStatus Request");

    /*
     * private static final String APP_NAME = "LILO FOLIO";
     *
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS = new ApplicationErrorCode(APP_NAME, 2901,
     * " Missing required fields. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_POST_REFUND_REQUEST = new ApplicationErrorCode(APP_NAME, 2902,
     * " Missing required fields - PostRefundRequest. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_REFUNDTYPE = new ApplicationErrorCode(APP_NAME, 2903,
     * " Missing required field - RefundType. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_REFUNDINFORMATION = new ApplicationErrorCode(APP_NAME, 2904,
     * " Missing required field - RefundInformation. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_PAYMENTREFERENCEVALUE = new ApplicationErrorCode(APP_NAME, 2905,
     * " Missing required field - PaymentReferenceValue. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_BASICORDERDETAIL = new ApplicationErrorCode(APP_NAME, 2906,
     * " Missing required field - BasicOrderDetail. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESERVATIONTYPE = new ApplicationErrorCode(APP_NAME, 2907,
     * " Missing required field - ReservationType. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESORTCODE = new ApplicationErrorCode(APP_NAME, 2908,
     * " Missing required field - ResortCode. ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESNUM_PAYREF_RESREF = new ApplicationErrorCode(APP_NAME, 2909,
     * " Missing required field - Reservation Number, Payment Reference Number, or Reservation Reference Number ");
     * public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESERVATIONREFERENCENO = new ApplicationErrorCode(APP_NAME, 2910,
     * " Missing required field - Reservation Reference Number ");
     * public static final ApplicationErrorCode INVALID_PRICE = new ApplicationErrorCode(APP_NAME, 7108,
     * " Invalid Price for the Date ");
     * public static final ApplicationErrorCode INVALID_POINTS = new ApplicationErrorCode(APP_NAME, 7109,
     * " Invalid Points for Posting ");
     * public static final ApplicationErrorCode PARSING_ARRIVALDATE_DEPARTUREDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7110,
     * " Error Parsing Arrival and Departure Date ");
     * public static final ApplicationErrorCode PARSING_ARRIVALDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7111,
     * " Error Parsing Arrival Date ");
     * public static final ApplicationErrorCode RES_REF_NO_MANDATORY_WHEN_CANCEL_WITHOUT_PENALTY = new ApplicationErrorCode(APP_NAME, 7113,
     * " If CancelWithoutPenalty is True, ReservationReferenceNumber is mandatory ");
     * public static final ApplicationErrorCode MISSING_ORDER_REFERENCE_VALUES = new ApplicationErrorCode(APP_NAME, 7114,
     * " Either ReservationNumber and ReservationReferenceNo or PaymentRefenceValue must be passed ");
     * public static final ApplicationErrorCode INVALID_POINT_PURCHASE_PARAM = new ApplicationErrorCode(APP_NAME, 7116,
     * " Invalid Point Purchase Parameters ");
     * public static final ApplicationErrorCode INVENTORY_TRACKING_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7025,
     * "Inventory Tracking Id is invalid");
     * public static final ApplicationErrorCode TRAVEL_PLAN_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7028,
     * "Travel Plan ID is Invalid");
     * public static final ApplicationErrorCode INVALID_POINT_PURCHASE_REQUEST = new ApplicationErrorCode(APP_NAME, 7117,
     * " Invalid Point Purchase Request ");
     * public static final ApplicationErrorCode INVALID_UPDATE_REQUEST = new ApplicationErrorCode(APP_NAME, 7118,
     * " Invalid Update Request ");
     * public static final ApplicationErrorCode INVALID_UPDATE_REQUEST_PARAM = new ApplicationErrorCode(APP_NAME, 7119,
     * " Invalid Update Request Parameters");
     * public static final ApplicationErrorCode INVALID_FULLFILL_PURCHASE_RESPONSE = new ApplicationErrorCode(APP_NAME, 7120,
     * " Invalid FullFIll Purchase Response");
     * public static final ApplicationErrorCode INVALID_PRODUCT_RESPONSE = new ApplicationErrorCode(APP_NAME, 7121,
     * " Invalide Products Response. ");
     * public static final ApplicationErrorCode RETRIEVE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 7122,
     * " Invalide Retrieve Request. ");
     * public static final ApplicationErrorCode PRODUCT_DETAILS_CANNOT_BE_EMPTY = new ApplicationErrorCode(APP_NAME, 7123,
     * " Product Details Can not be Empty . ");
     * public static final ApplicationErrorCode INVALID_MEMBERSHIP = new ApplicationErrorCode(APP_NAME, 7124,
     * " Invalide Membership Id . ");
     * public static final ApplicationErrorCode INVALID_POINT_PURCHASE_CONTRACTS = new ApplicationErrorCode(APP_NAME, 7125,
     * " Invalide Point Purchase Contracts . ");
     * public static final ApplicationErrorCode PAYMENT_NOT_MADE = new ApplicationErrorCode(APP_NAME, 7128,
     * " Payment is not made before allocating points . ");
     * public static final ApplicationErrorCode INVALID_CONTRACT_IDENTIFIER_IN_POINT_PURCHASE_REQUEST = new ApplicationErrorCode(APP_NAME, 7126,
     * "Invalide Contract Identifier in Point Purchase Request. ");
     * public static final ApplicationErrorCode INVALID_ENTITLEMENT_DETAILS = new ApplicationErrorCode(APP_NAME, 7127,
     * "Invalid Entitlement Detail for UpdateRequest In fullFillPurchaseRequest");
     * public static final ApplicationErrorCode INVALID_REFUND_OTUP_REQUEST = new ApplicationErrorCode(APP_NAME, 7130,
     * "Invalid Refund OTUP Request");
     * public static final ApplicationErrorCode NO_ENTITLEMENT_FOUND = new ApplicationErrorCode(APP_NAME, 7131,
     * "No Entitlement Found for given TP Id");
     * public static final ApplicationErrorCode NO_TP_FOUND = new ApplicationErrorCode(APP_NAME, 7132,
     * "No Travel Plan Found for given TC Id");
     * public static final ApplicationErrorCode ROLLBACK_IMPLEMENTATION_VALIDATION_EXCEPTION = new ApplicationErrorCode(APP_NAME, 2901,
     * " Rollback Implemenation Validation. ");
     *
     * private static final String MOD_NAME = "LILO_RESM";
     *
     * // LILO_RESM errors
     * public static final ApplicationErrorCode SEARCH_CRITERIA_INVALID = new ApplicationErrorCode(MOD_NAME, 7124,
     * "Search Criteria is Invalid");
     * public static final ApplicationErrorCode NO_TRAVEL_PLAN_DATA_FOUND = new ApplicationErrorCode(MOD_NAME, 7183,
     * "No travel plan data found.");
     * public static final ApplicationErrorCode RESULT_SIZE_TOO_LARGE_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 91443,
     * " Validation Failed. ");
     * public static final ApplicationErrorCode DATA_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 91443,
     * " Data not found. ");
     * public static final ApplicationErrorCode ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL = new ApplicationErrorCode(MOD_NAME, 7192,
     * " Accommodation should be in Booked status to be cancelled");
     * public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 7192,
     * "Accommodations not found");
     * public static final ApplicationErrorCode COMMENT_LEVEL_REQUIRED = new ApplicationErrorCode(MOD_NAME, 8078,
     * " Create Comments Request Invalid ");
     * public static final ApplicationErrorCode REQUEST_REQUIRED = new ApplicationErrorCode(MOD_NAME, 8078,
     * " Create Comments Request Invalid ");
     * public static final ApplicationErrorCode ACCOMMODATION_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 7192,
     * " Accommodations not found ");
     * public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(MOD_NAME, 7192,
     * " Accommodation should be in Booked status to be cancelled ");
     * public static final ApplicationErrorCode REQ_PARAM_MISSING = new ApplicationErrorCode(MOD_NAME, 7064,
     * " Required parameters are missing ");
     * public static final ApplicationErrorCode EXT_REF_SRC_REQ = new ApplicationErrorCode(MOD_NAME, 7090,
     * " External Reference Source or Code required ");
     * public static final ApplicationErrorCode CANNOT_CALC_CANCEL_FEE = new ApplicationErrorCode(MOD_NAME, 7080,
     * " Cannot calculate Cancel fee ");
     *
     * public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 9083,
     * " Accommodation Component not found ");
     * public static final ApplicationErrorCode NO_ACCOMMODATION_COMPONENT_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 7177,
     * " No Accommodation Component found. ");
     * public static final ApplicationErrorCode TRVL_PLAN_SGMT_CANNOT_BE_NULL = new ApplicationErrorCode(MOD_NAME, 7008,
     * " Travel Plan Segment Not Found");
     * public static final ApplicationErrorCode TRVL_PLAN_COMPONENT_ID_REQ = new ApplicationErrorCode(MOD_NAME, 7037,
     * "Travel Component Id is required");
     *
     * private static final String APPLICATION = "LILO System";
     *
     * public static final ApplicationErrorCode UNEXPECTED_ERROR_OCCURRED = new ApplicationErrorCode(APPLICATION, 1001,
     * "Unexpected Error occurred");
     *
     * public static final ApplicationErrorCode ACCOMMODATION_INVALID_FOR_SHARE = new ApplicationErrorCode(MOD_NAME, 7130,
     * "Accommodation is invalid for Share");
     * public static final ApplicationErrorCode TRAVEL_STATUS_INVALID_FOR_SHARE = new ApplicationErrorCode(MOD_NAME, 7131,
     * "Travel Status is invalid for Share");
     * public static final ApplicationErrorCode CANNOT_CHANGE_PACKAGE = new ApplicationErrorCode(MOD_NAME, 7208,
     * "Cannot change Block/Resort/Package for an shared Accommodation.");
     *
     * private static final String MODULE_NAME = "Accommodation Sales";
     * public static final ApplicationErrorCode PACKAGE_CODE_INVALID = new ApplicationErrorCode(MODULE_NAME, 7104,
     * "Package Code is invalid");
     * public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(MODULE_NAME, 7107,
     * " Resort Period is invalid ");
     * public static final ApplicationErrorCode UNEXPECTED_ERR_OCCURRED = new ApplicationErrorCode(MODULE_NAME, 1001,
     * "Unexpected Error occurred");
     * public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE = new ApplicationErrorCode(MODULE_NAME, 7080,
     * "cannot calculate Cancel fee");
     * public static final ApplicationErrorCode NO_TP_DATA_FOUND = new ApplicationErrorCode(MODULE_NAME, 7183,
     * "No travel plan data found.");
     * public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode(MODULE_NAME, 7028,
     * "Invalid Search Criteria ");
     * public static final ApplicationErrorCode ACCOMM_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(MODULE_NAME, 7192,
     * " Accommodation should be in Booked status to be cancelled");
     * public static final ApplicationErrorCode TPS_CANNOT_BE_NULL = new ApplicationErrorCode(MODULE_NAME, 7008,
     * "TRAVEL_PLAN_SEGMENT_NOT_FOUND");
     * public static final ApplicationErrorCode COMMENT_LVL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 8078,
     * " Create Comments Request Invalid ");
     * public static final ApplicationErrorCode NO_ACCOMM_COMPONENT_EXCEPTION = new ApplicationErrorCode(MODULE_NAME, 7177,
     * " No Accommodation Component found. ");
     * public static final ApplicationErrorCode ACCOMM_INVALID_FOR_SHARE = new ApplicationErrorCode(MODULE_NAME, 7130,
     * "Accommodation is invalid for Share");
     * public static final ApplicationErrorCode CANNOT_CHANGE_PACKAGE_ACCOM = new ApplicationErrorCode(MODULE_NAME, 7208,
     * "Cannot change Block/Resort/Package for an shared Accommodation.");
     * public static final ApplicationErrorCode INVALID_GATHERING_DETAIL = new ApplicationErrorCode(MODULE_NAME, 7156,
     * "Gathering Detail is not valid");
     * public static final ApplicationErrorCode ACCOMM_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 9121,
     * "Accommodations not found");
     * public static final ApplicationErrorCode ACCOMMODATION_INVALID_SHARE = new ApplicationErrorCode(MODULE_NAME, 7130,
     * "Accommodation is invalid for Share");
     * public static final ApplicationErrorCode GUARANTEE_STATUS_CANNOT_CHANGE = new ApplicationErrorCode(MODULE_NAME, 7195,
     * " Guarantee status can not be changed ");
     * public static final ApplicationErrorCode EXTERNAL_REFERENCE_REQUIRED = new ApplicationErrorCode(
     * MODULE_NAME, 7088, "External Reference is required");
     * public static final ApplicationErrorCode CANNOT_CANCEL_DVC_RES = new ApplicationErrorCode(
     * MODULE_NAME, 9122, "Cannot cancel DVC Reservation");
     * public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(MODULE_NAME, 7216,
     * "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");
     * public static final ApplicationErrorCode TRAVEL_STATUS_INVALID = new ApplicationErrorCode(MODULE_NAME, 7110,
     * " Travel Status is invalid ");
     * public static final ApplicationErrorCode REQUIRED_PARAM_MISSING = new ApplicationErrorCode(MODULE_NAME, 7064,
     * " Required parameters are missing ");
     * public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7090,
     * "External Reference Source or Code required");
     * public static final ApplicationErrorCode TCG_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 9120,
     * "Travel Component Grouping not found");
     * public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode(MODULE_NAME, 7106,
     * "Resort Code is invalid");
     * public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 7007,
     * "TRAVEL_PLAN_NOT_FOUND");
     * public static final ApplicationErrorCode TRAVEL_AGENCY_INVALID = new ApplicationErrorCode(MODULE_NAME, 7102,
     * "Travel Agency is invalid");
     * public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7026,
     * "Travel Plan Guest is required");
     * public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(MODULE_NAME, 7105,
     * "Room Type is invalid");
     * public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7027,
     * "Guest is Required");
     * public static final ApplicationErrorCode ROOM_DETAIL_MISSING = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     * public static final ApplicationErrorCode INVALID_MODIFY_REQUEST = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     * public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(
     * MODULE_NAME, 9042, "INVALID REQUEST");
     * public static final ApplicationErrorCode INVALID_EXT_REF_DETAILS = new ApplicationErrorCode(MODULE_NAME, 7088,
     * "External Reference is required");
     * public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     * public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     * public static final ApplicationErrorCode REQUIRED_PARAMETERS_MISSING = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     * public static final ApplicationErrorCode CANNOT_BOOK_OR_MOD_DVC = new ApplicationErrorCode(MODULE_NAME, 9042,
     * "INVALID REQUEST!");
     */
}