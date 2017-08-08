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
    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(APP_NAME, 7107,
            " Resort Period is invalid ");
    public static final ApplicationErrorCode INVALID_PRICE = new ApplicationErrorCode(APP_NAME, 7108,
            " Invalid Price for the Date ");
    public static final ApplicationErrorCode INVALID_POINTS = new ApplicationErrorCode(APP_NAME, 7109,
            " Invalid Points for Posting ");
    public static final ApplicationErrorCode PARSING_ARRIVALDATE_DEPARTUREDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7110,
            " Error Parsing Arrival and Departure Date ");
    public static final ApplicationErrorCode PARSING_ARRIVALDATE_ERROR = new ApplicationErrorCode(APP_NAME, 7111,
            " Error Parsing Arrival Date ");
    public static final ApplicationErrorCode ERROR_FROM_DVC_PRICING_SERVICE = new ApplicationErrorCode(APP_NAME, 7112,
            " Fault Returned From DVC Pricing Service ");
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
    public static final ApplicationErrorCode REQUIRED_PARAMETERS_MISSING = new ApplicationErrorCode(MOD_NAME, 7064,
            "Required parameters are missing");
    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED = new ApplicationErrorCode(MOD_NAME, 7090,
            "External Reference Source or Code required");
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

    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(MOD_NAME, 9083,
            " Accommodation Component not found ");
    public static final ApplicationErrorCode NO_ACCOMMODATION_COMPONENT_EXCEPTION = new ApplicationErrorCode(MOD_NAME, 7177,
            " No Accommodation Component found. ");


private static final String APPLICATION = "LILO System";

public static final ApplicationErrorCode UNEXPECTED_ERROR_OCCURRED = new ApplicationErrorCode( APPLICATION, 1001,
		"Unexpected Error occurred");
}