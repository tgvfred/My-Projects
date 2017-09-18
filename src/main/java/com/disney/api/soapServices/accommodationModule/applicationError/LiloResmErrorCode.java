/**
 *
 */
/**
 * @author MALIC012
 *
 */
package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class LiloResmErrorCode {
    private static final String APP_NAME = "LILO_RESM";

    public static final ApplicationErrorCode EXTERNAL_REFERENCE_REQUIRED = new ApplicationErrorCode(
            APP_NAME, 7088, "External Reference is required");
    public static final ApplicationErrorCode CANNOT_MODIFY_CHECKEDIN_ACCOMMODATION = new ApplicationErrorCode(
            APP_NAME, 7187, "Checked In Accommodation cannot be modified");
    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(
            APP_NAME, 9042, "INVALID REQUEST");
    public static final ApplicationErrorCode SEARCH_CRITERIA_INVALID = new ApplicationErrorCode(APP_NAME, 7124,
            "Search Criteria is Invalid");
    public static final ApplicationErrorCode NO_TRAVEL_PLAN_DATA_FOUND = new ApplicationErrorCode(APP_NAME, 7183,
            "No travel plan data found.");
    public static final ApplicationErrorCode RESULT_SIZE_TOO_LARGE_EXCEPTION = new ApplicationErrorCode(APP_NAME, 91443,
            " Validation Failed. ");
    public static final ApplicationErrorCode DATA_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(APP_NAME, 91443,
            " Data not found. ");
    public static final ApplicationErrorCode REQUIRED_PARAMETERS_MISSING = new ApplicationErrorCode(APP_NAME, 7064,
            "Required parameters are missing");
    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED = new ApplicationErrorCode(APP_NAME, 7090,
            "External Reference Source or Code required");
    public static final ApplicationErrorCode ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL = new ApplicationErrorCode(APP_NAME, 7192,
            " Accommodation should be in Booked status to be cancelled");
    public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7192,
            "Accommodations not found");
    public static final ApplicationErrorCode COMMENT_LEVEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 8078,
            " Create Comments Request Invalid ");
    public static final ApplicationErrorCode REQUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 8078,
            " Create Comments Request Invalid ");
    public static final ApplicationErrorCode ACCOMMODATION_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7192,
            " Accommodations not found ");
    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(APP_NAME, 7192,
            " Accommodation should be in Booked status to be cancelled ");
    public static final ApplicationErrorCode REQ_PARAM_MISSING = new ApplicationErrorCode(APP_NAME, 7064,
            " Required parameters are missing ");
    public static final ApplicationErrorCode EXT_REF_SRC_REQ = new ApplicationErrorCode(APP_NAME, 7090,
            " External Reference Source or Code required ");
    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 9083,
            " Accommodation Component not found ");
    public static final ApplicationErrorCode NO_ACCOMMODATION_COMPONENT_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7177,
            " No Accommodation Component found. ");
    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7096,
            "communication Channel is required");
    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7094,
            "Sales Channel is required");
    public static final ApplicationErrorCode PACKAGE_CODE_INVALID = new ApplicationErrorCode(APP_NAME, 7104,
            "Package Code is invalid");
    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(APP_NAME, 7105,
            "Room Type is invalid");
    public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode(APP_NAME, 7106,
            "Resort Code is invalid");
    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7007,
            "TRAVEL_PLAN_NOT_FOUND");
    public static final ApplicationErrorCode ROOM_DETAIL_MISSING = new ApplicationErrorCode(APP_NAME, 7228,
            "There should be at least one Room Detail");
    public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7027,
            "Guest is Required");
    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7026,
            "Travel Plan Guest is required");
    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(APP_NAME, 7107,
            " Resort Period is invalid ");
    public static final ApplicationErrorCode TRAVEL_STATUS_INVALID = new ApplicationErrorCode(APP_NAME, 7110,
            " Travel Status is invalid ");
    public static final ApplicationErrorCode INVALID_EXT_REF_DETAILS = new ApplicationErrorCode(APP_NAME, 7092,
            "Invalid External Reference Details");
    public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(APP_NAME, 7216,
            "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");
    public static final ApplicationErrorCode INVALID_GATHERING_DETAIL = new ApplicationErrorCode(APP_NAME, 7156,
            "Gathering Detail is not valid");
    public static final ApplicationErrorCode INVALID_MODIFY_REQUEST = new ApplicationErrorCode(APP_NAME, 9105,
            "Invalid Modify Request");
    public static final ApplicationErrorCode TRAVEL_AGENCY_INVALID = new ApplicationErrorCode(APP_NAME, 7102,
            "Travel Agency is invalid");
    public static final ApplicationErrorCode TRVL_PLAN_COMPONENT_ID_REQ = new ApplicationErrorCode(APP_NAME, 7037,
            "Travel Component Id is required");
    public static final ApplicationErrorCode NOT_ELIGIBLE_FOR_REINSTATE = new ApplicationErrorCode(APP_NAME, 7229,
            "This reservation is not eligible to be Re-instated. Please contact the appropriate Reservation Office, Operations Support or Manager for assistance");
}