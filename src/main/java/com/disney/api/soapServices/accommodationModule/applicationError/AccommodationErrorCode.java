package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class AccommodationErrorCode {
    private static final String APP_NAME = "LILO FOLIO";

    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS = new ApplicationErrorCode(APP_NAME, 2901,
            " Missing required fields. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_POST_REFUND_REQUEST = new ApplicationErrorCode(APP_NAME, 2902,
            " Missing required fields - PostRefundRequest. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_REFUNDTYPE = new ApplicationErrorCode(APP_NAME, 2903,
            " Missing required field - RefundType. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_REFUNDINFORMATION = new ApplicationErrorCode(APP_NAME, 2904,
            " Missing required field - RefundInformation. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_PAYMENTREFERENCEVALUE = new ApplicationErrorCode(APP_NAME, 2905,
            " Missing required field - PaymentReferenceValue. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_BASICORDERDETAIL = new ApplicationErrorCode(APP_NAME, 2906,
            " Missing required field - BasicOrderDetail. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESERVATIONTYPE = new ApplicationErrorCode(APP_NAME, 2907,
            " Missing required field - ReservationType. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESORTCODE = new ApplicationErrorCode(APP_NAME, 2908,
            " Missing required field - ResortCode. ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESNUM_PAYREF_RESREF = new ApplicationErrorCode(APP_NAME, 2909,
            " Missing required field - Reservation Number, Payment Reference Number, or Reservation Reference Number ");
    public static final ApplicationErrorCode RESERVATION_MISSING_REQ_FIELDS_RESERVATIONREFERENCENO = new ApplicationErrorCode(APP_NAME, 2910,
            " Missing required field - Reservation Reference Number ");
    public static final ApplicationErrorCode INVALID_PRICE = new ApplicationErrorCode(APP_NAME, 7108,
            " Invalid Price for the Date ");
    public static final ApplicationErrorCode INVALID_POINTS = new ApplicationErrorCode(APP_NAME, 7109,
            " Invalid Points for Posting ");
    public static final ApplicationErrorCode PARSING_ARRIVALDATE_DEPARTUREDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7110,
            " Error Parsing Arrival and Departure Date ");
    public static final ApplicationErrorCode PARSING_ARRIVALDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7111,
            " Error Parsing Arrival Date ");
    public static final ApplicationErrorCode RES_REF_NO_MANDATORY_WHEN_CANCEL_WITHOUT_PENALTY = new ApplicationErrorCode(APP_NAME, 7113,
            " If CancelWithoutPenalty is True, ReservationReferenceNumber is mandatory ");
    public static final ApplicationErrorCode MISSING_ORDER_REFERENCE_VALUES = new ApplicationErrorCode(APP_NAME, 7114,
            " Either ReservationNumber and ReservationReferenceNo or PaymentRefenceValue must be passed ");
    public static final ApplicationErrorCode INVALID_POINT_PURCHASE_PARAM = new ApplicationErrorCode(APP_NAME, 7116,
            " Invalid Point Purchase Parameters ");
    public static final ApplicationErrorCode INVENTORY_TRACKING_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7025,
            "Inventory Tracking Id is invalid");
    public static final ApplicationErrorCode TRAVEL_PLAN_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7028,
            "Travel Plan ID is Invalid");
    public static final ApplicationErrorCode INVALID_POINT_PURCHASE_REQUEST = new ApplicationErrorCode(APP_NAME, 7117,
            " Invalid Point Purchase Request ");
    public static final ApplicationErrorCode INVALID_UPDATE_REQUEST = new ApplicationErrorCode(APP_NAME, 7118,
            " Invalid Update Request ");
    public static final ApplicationErrorCode INVALID_UPDATE_REQUEST_PARAM = new ApplicationErrorCode(APP_NAME, 7119,
            " Invalid Update Request Parameters");
    public static final ApplicationErrorCode INVALID_FULLFILL_PURCHASE_RESPONSE = new ApplicationErrorCode(APP_NAME, 7120,
            " Invalid FullFIll Purchase Response");
    public static final ApplicationErrorCode INVALID_PRODUCT_RESPONSE = new ApplicationErrorCode(APP_NAME, 7121,
            " Invalide Products Response. ");
    public static final ApplicationErrorCode RETRIEVE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 7122,
            " Invalide Retrieve Request. ");
    public static final ApplicationErrorCode PRODUCT_DETAILS_CANNOT_BE_EMPTY = new ApplicationErrorCode(APP_NAME, 7123,
            " Product Details Can not be Empty . ");
    public static final ApplicationErrorCode INVALID_MEMBERSHIP = new ApplicationErrorCode(APP_NAME, 7124,
            " Invalide Membership Id . ");
    public static final ApplicationErrorCode INVALID_POINT_PURCHASE_CONTRACTS = new ApplicationErrorCode(APP_NAME, 7125,
            " Invalide Point Purchase Contracts . ");
    public static final ApplicationErrorCode PAYMENT_NOT_MADE = new ApplicationErrorCode(APP_NAME, 7128,
            " Payment is not made before allocating points . ");
    public static final ApplicationErrorCode INVALID_CONTRACT_IDENTIFIER_IN_POINT_PURCHASE_REQUEST = new ApplicationErrorCode(APP_NAME, 7126,
            "Invalide Contract Identifier in Point Purchase Request. ");
    public static final ApplicationErrorCode INVALID_ENTITLEMENT_DETAILS = new ApplicationErrorCode(APP_NAME, 7127,
            "Invalid Entitlement Detail for UpdateRequest In fullFillPurchaseRequest");
    public static final ApplicationErrorCode INVALID_REFUND_OTUP_REQUEST = new ApplicationErrorCode(APP_NAME, 7130,
            "Invalid Refund OTUP Request");
    public static final ApplicationErrorCode NO_ENTITLEMENT_FOUND = new ApplicationErrorCode(APP_NAME, 7131,
            "No Entitlement Found for given TP Id");
    public static final ApplicationErrorCode NO_TP_FOUND = new ApplicationErrorCode(APP_NAME, 7132,
            "No Travel Plan Found for given TC Id");
    public static final ApplicationErrorCode ROLLBACK_IMPLEMENTATION_VALIDATION_EXCEPTION = new ApplicationErrorCode(APP_NAME, 2901,
            " Rollback Implemenation Validation. ");

    private static final String MOD_NAME = "LILO_RESM";

    // LILO_RESM errors
    public static final ApplicationErrorCode SEARCH_CRITERIA_INVALID = new ApplicationErrorCode(MOD_NAME, 7124,
            "Search Criteria is Invalid");
    public static final ApplicationErrorCode NO_TRAVEL_PLAN_DATA_FOUND = new ApplicationErrorCode(MOD_NAME, 7183,
            "No travel plan data found.");
    public static final ApplicationErrorCode RESULT_SIZE_TOO_LARGE_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 91443,
            " Validation Failed. ");
    public static final ApplicationErrorCode DATA_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 91443,
            " Data not found. ");
    public static final ApplicationErrorCode ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL = new ApplicationErrorCode(MOD_NAME, 7192,
            " Accommodation should be in Booked status to be cancelled");
    public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 7192,
            "Accommodations not found");
    public static final ApplicationErrorCode COMMENT_LEVEL_REQUIRED = new ApplicationErrorCode(MOD_NAME, 8078,
            " Create Comments Request Invalid ");
    public static final ApplicationErrorCode REQUEST_REQUIRED = new ApplicationErrorCode(MOD_NAME, 8078,
            " Create Comments Request Invalid ");
    public static final ApplicationErrorCode ACCOMMODATION_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 7192,
            " Accommodations not found ");
    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(MOD_NAME, 7192,
            " Accommodation should be in Booked status to be cancelled ");
    public static final ApplicationErrorCode REQ_PARAM_MISSING = new ApplicationErrorCode(MOD_NAME, 7064,
            " Required parameters are missing ");
    public static final ApplicationErrorCode EXT_REF_SRC_REQ = new ApplicationErrorCode(MOD_NAME, 7090,
            " External Reference Source or Code required ");
    public static final ApplicationErrorCode CANNOT_CALC_CANCEL_FEE = new ApplicationErrorCode(MOD_NAME, 7080,
            " Cannot calculate Cancel fee ");

    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 9083,
            " Accommodation Component not found ");
    public static final ApplicationErrorCode NO_ACCOMMODATION_COMPONENT_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 7177,
            " No Accommodation Component found. ");
    public static final ApplicationErrorCode TRVL_PLAN_SGMT_CANNOT_BE_NULL = new ApplicationErrorCode(MOD_NAME, 7008,
            " Travel Plan Segment Not Found");

    private static final String APPLICATION = "LILO System";

    public static final ApplicationErrorCode UNEXPECTED_ERROR_OCCURRED = new ApplicationErrorCode(APPLICATION, 1001,
            "Unexpected Error occurred");

    public static final ApplicationErrorCode ACCOMMODATION_INVALID_FOR_SHARE = new ApplicationErrorCode(MOD_NAME, 7130,
            "Accommodation is invalid for Share");
    public static final ApplicationErrorCode TRAVEL_STATUS_INVALID_FOR_SHARE = new ApplicationErrorCode(MOD_NAME, 7131,
            "Travel Status is invalid for Share");
    public static final ApplicationErrorCode CANNOT_CHANGE_PACKAGE = new ApplicationErrorCode(MOD_NAME, 7208,
            "Cannot change Block/Resort/Package for an shared Accommodation.");

    private static final String MODULE_NAME = "Accommodation Sales";
    public static final ApplicationErrorCode PACKAGE_CODE_INVALID = new ApplicationErrorCode(MODULE_NAME, 7104,
            "Package Code is invalid");
    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(MODULE_NAME, 7107,
            " Resort Period is invalid ");
    public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE = new ApplicationErrorCode(MODULE_NAME, 7080,
            "cannot calculate Cancel fee");
    public static final ApplicationErrorCode NO_TP_DATA_FOUND = new ApplicationErrorCode(MODULE_NAME, 7183,
            "No travel plan data found.");
    public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode(MODULE_NAME, 7028,
            "Invalid Search Criteria ");
    public static final ApplicationErrorCode ACCOMM_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(MODULE_NAME, 7192,
            " Accommodation should be in Booked status to be cancelled");
    public static final ApplicationErrorCode TPS_CANNOT_BE_NULL = new ApplicationErrorCode(MODULE_NAME, 7008,
            "TRAVEL_PLAN_SEGMENT_NOT_FOUND");
    public static final ApplicationErrorCode COMMENT_LVL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 8078,
            " Create Comments Request Invalid ");
    public static final ApplicationErrorCode NO_ACCOMM_COMPONENT_EXCEPTION = new ApplicationErrorCode(MODULE_NAME, 7177,
            " No Accommodation Component found. ");
    public static final ApplicationErrorCode ACCOMM_INVALID_FOR_SHARE = new ApplicationErrorCode(MODULE_NAME, 7130,
            "Accommodation is invalid for Share");
    public static final ApplicationErrorCode CANNOT_CHANGE_PACKAGE_ACCOM = new ApplicationErrorCode(MODULE_NAME, 7208,
            "Cannot change Block/Resort/Package for an shared Accommodation.");
    public static final ApplicationErrorCode INVALID_GATHERING_DETAIL = new ApplicationErrorCode(MODULE_NAME, 7156,
            "Gathering Detail is not valid");
    public static final ApplicationErrorCode ACCOMM_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 9121,
            "Accommodations not found");
    public static final ApplicationErrorCode ACCOMMODATION_INVALID_SHARE = new ApplicationErrorCode(MODULE_NAME, 7130,
            "Accommodation is invalid for Share");
    public static final ApplicationErrorCode GUARANTEE_STATUS_CANNOT_CHANGE = new ApplicationErrorCode(MODULE_NAME, 7195,
            " Guarantee status can not be changed ");
    public static final ApplicationErrorCode EXTERNAL_REFERENCE_REQUIRED = new ApplicationErrorCode(
            MODULE_NAME, 7088, "External Reference is required");
    public static final ApplicationErrorCode CANNOT_CANCEL_DVC_RES = new ApplicationErrorCode(
            MODULE_NAME, 9122, "Cannot cancel DVC Reservation");
    public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(MODULE_NAME, 7216,
            "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");
    public static final ApplicationErrorCode TRAVEL_STATUS_INVALID = new ApplicationErrorCode(MODULE_NAME, 7110,
            " Travel Status is invalid ");
    public static final ApplicationErrorCode REQUIRED_PARAM_MISSING = new ApplicationErrorCode(MODULE_NAME, 7064,
            " Required parameters are missing ");
    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7090,
            "External Reference Source or Code required");
    public static final ApplicationErrorCode TCG_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 9120,
            "Travel Component Grouping not found");
    public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode(MODULE_NAME, 7106,
            "Resort Code is invalid");
    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(MODULE_NAME, 7007,
            "TRAVEL_PLAN_NOT_FOUND");
    public static final ApplicationErrorCode TRAVEL_AGENCY_INVALID = new ApplicationErrorCode(MODULE_NAME, 7102,
            "Travel Agency is invalid");
    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7026,
            "Travel Plan Guest is required");
    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(MODULE_NAME, 7105,
            "Room Type is invalid");
    public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 7027,
            "Guest is Required");
    public static final ApplicationErrorCode ROOM_DETAIL_MISSING = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
    public static final ApplicationErrorCode INVALID_MODIFY_REQUEST = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(
            MODULE_NAME, 9042, "INVALID REQUEST");
    public static final ApplicationErrorCode INVALID_EXT_REF_DETAILS = new ApplicationErrorCode(MODULE_NAME, 7088,
            "External Reference is required");
    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
    public static final ApplicationErrorCode REQUIRED_PARAMETERS_MISSING = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
    public static final ApplicationErrorCode CANNOT_BOOK_OR_MOD_DVC = new ApplicationErrorCode(MODULE_NAME, 9042,
            "INVALID REQUEST!");
}