package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public final class AccommodationErrorCode {

    private AccommodationErrorCode() {
    }

    private static final String APP_NAME = "Accommodation Sales";

    /**
     * Kept the Error code in Synch with Old Accommodation Error Code
     */

    public static final ApplicationErrorCode INVALID_DISCOVERY_PREFERENCE = new ApplicationErrorCode(APP_NAME, 7078,
            "Invalid Discovery Preferences");

    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7007,
            "TRAVEL_PLAN_NOT_FOUND");

    public static final ApplicationErrorCode ACTIVE_ACCOMMODATION_REQUIRED_TOREPLACE = new ApplicationErrorCode(
            APP_NAME, 91453, "Active accommodation required");

    public static final ApplicationErrorCode GUEST_IDS_ARE_NOTIN_SAMETP = new ApplicationErrorCode(APP_NAME, 91454,
            "Guest Ids provided in Request are not in same Travel Plan!!!");

    public static final ApplicationErrorCode MET_NT_SUP = new ApplicationErrorCode(APP_NAME, 91437,
            "METHOD NOT SUPPORTED!");
    public static final ApplicationErrorCode GUESTID_REQUIRED_FORREPLACE = new ApplicationErrorCode(APP_NAME, 91452,
            "Guest ID required for replacing the guest");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7008,
            "Travel Plan Segment Not Found");

    public static final ApplicationErrorCode NOT_A_NUMERIC = new ApplicationErrorCode(APP_NAME, 8573,
            "Not a numeric value ");

    public static final ApplicationErrorCode REQUESTED_DATE_MISSING = new ApplicationErrorCode(APP_NAME, 8098,
            "Requested date not provided");

    public static final ApplicationErrorCode KTTW_TICKET_NEEDS_TO_BE_REMOVED = new ApplicationErrorCode(APP_NAME, 7198,
            "Guest Cannot be changed without cancelling the Key to the World Ticket.");

    public static final ApplicationErrorCode XBAND_ALREADY_ASSOCIATED_TO_GUEST = new ApplicationErrorCode(APP_NAME,
            9142, "XBAND is already associated to guest");

    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DETAIL_MISSING = new ApplicationErrorCode(APP_NAME,
            8086, "Bell service request detail is missing");

    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DATE_BEFORE_START = new ApplicationErrorCode(APP_NAME,
            8115, "Bell service request date should not be before travel period start date");

    public static final ApplicationErrorCode END_DATE_REQUIRED = new ApplicationErrorCode(APP_NAME, 91433,
            "End Date is required  is required");

    public static final ApplicationErrorCode START_DATE_LESSTHEN_END_DATE = new ApplicationErrorCode(APP_NAME, 91434,
            "Start date is less then EndDate is required");

    public static final ApplicationErrorCode TRAVEL_PLAN_CHANGED_REQUEST_REQUIRED = new ApplicationErrorCode(APP_NAME,
            91425, "Publish TravelPlanChangedRequest is Required");

    public static final ApplicationErrorCode FACILITY_ID_IS_REQUIRED = new ApplicationErrorCode(APP_NAME, 91431,
            "Facility id is required");
    public static final ApplicationErrorCode START_DATE_GREATER_BOOKING_DATE = new ApplicationErrorCode(APP_NAME, 91432,
            "Start date is greater then booking date is required");

    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DATE_AFTER_END = new ApplicationErrorCode(APP_NAME,
            8116, "Bell service request date should not be after 5 days of travel period end date");

    public static final ApplicationErrorCode NO_KTTW_FOUND = new ApplicationErrorCode(APP_NAME, 9029,
            "No Kttw available in Database");

    public static final ApplicationErrorCode CHARGING_PRIVILEGE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME,
            9032, "Charging Privilage Request is invalid");

    public static final ApplicationErrorCode NO_LATE_CHECK_OUT_REQUEST_FOR_ACCOMMODATION_COMPONENT = new ApplicationErrorCode(
            APP_NAME, 8417, "There is no approved late check out request associated to this accommodation component, "
                    + "travelComponentId");

    public static final ApplicationErrorCode TTPL_NOT_ENABLED = new ApplicationErrorCode(APP_NAME, 8581,
            "CONFIGURATION ERROR - TAP TO PLAY IS NOT ENABLED");

    public static final ApplicationErrorCode ADMISSION_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode(APP_NAME, 8063,
            "Admission Status is not in Booked");

    public static final ApplicationErrorCode PRE_ARIVAL_CHECK_IN_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME,
            9065, "Pre Arival Check-In request invalid");

    public static final ApplicationErrorCode AUTO_CHECKOUT_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9089,
            "Auto Checkout Request invalid");

    public static final ApplicationErrorCode ROWS_SUPPORTED = new ApplicationErrorCode(APP_NAME, 91435,
            "Rows retrieved for given dates are not supported for publish event");

    public static final ApplicationErrorCode CONVERSATION_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 91426,
            "ConversationId is Required");

    public static final ApplicationErrorCode MESSAGE_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 91427,
            "MessageId is Required");

    public static final ApplicationErrorCode USER_NAME_REQUIRED = new ApplicationErrorCode(APP_NAME, 91428,
            "UserName is Required");

    public static final ApplicationErrorCode USER_NAME_AS_PER_PROPERTIES_REQUIRED = new ApplicationErrorCode(APP_NAME,
            91429, "UserName is Required as per mentioned in Constants");
    public static final ApplicationErrorCode TRAVEL_PLAN_IDS_OR_STARTDATE_REQUIRED = new ApplicationErrorCode(APP_NAME,
            91430, "TravelPlanId's or StartDate is required");
    public static final ApplicationErrorCode NO_GUEST_ASSOCIATED = new ApplicationErrorCode(APP_NAME, 91441,
            "No guest is associated to the band");

    public static final ApplicationErrorCode MUID_NOT_PROVIDED = new ApplicationErrorCode(APP_NAME, 91440,
            "Manufacturer id of media is not provided in teh request");

    public static final ApplicationErrorCode MEDIA_IS_NOT_ACTIVE = new ApplicationErrorCode(APP_NAME, 9113,
            "Media is not Active");

    public static final ApplicationErrorCode ASSOCIATION_NOT_EXIST_IN_GL = new ApplicationErrorCode(APP_NAME, 9132,
            "Association of xband does not exist in guest link");

    public static final ApplicationErrorCode NO_ENCODABLE_ACCESSES = new ApplicationErrorCode(APP_NAME, 91423,
            "There are no NON_ENCODED/FAILED_TO_ENCODE accesses on the band");

    public static final ApplicationErrorCode XBAND_NOT_LINKED_WITH_GL = new ApplicationErrorCode(APP_NAME, 9116,
            "XBAND is not linked with guestlink");

    public static final ApplicationErrorCode INVALID_MEDIA_STATE = new ApplicationErrorCode(APP_NAME, 9141,
            "Media state is invalid");

    public static final ApplicationErrorCode MEDIA_IS_ACTIVE = new ApplicationErrorCode(APP_NAME, 9137,
            "Media is already in ACTIVE state");

    public static final ApplicationErrorCode COMMUNICATION_ERROR_TO_TIMELOX_ERROR = new ApplicationErrorCode(APP_NAME,
            91423, "Expetion occured while connecting keycutting/timelox");

    public static final ApplicationErrorCode ERROR_CODE_403_RECVD = new ApplicationErrorCode(APP_NAME, 9138,
            "Error code 403 received");

    public static final ApplicationErrorCode ERROR_CODE_404_RECVD = new ApplicationErrorCode(APP_NAME, 9139,
            "Error code 404 received");

    public static final ApplicationErrorCode ERROR_CODE_400_RECVD = new ApplicationErrorCode(APP_NAME, 9140,
            "Error code 400 received");

    public static final ApplicationErrorCode SERVICE_REQUEST_CANCEL_ERROR = new ApplicationErrorCode(APP_NAME, 8410,
            "Attempt to cancel service request which is already cancelled; travel component service request id ");

    public static final ApplicationErrorCode ERROR_PARSING_TIME = new ApplicationErrorCode(APP_NAME, 8311,
            "Error parsing time from facility");

    public static final ApplicationErrorCode NO_CURRENT_ACCOMMODATION_FOUND = new ApplicationErrorCode(APP_NAME, 8316,
            "No current accommodation found for this travel plan, travelPlanId ");

    public static final ApplicationErrorCode PRINT_PACKAGE_COMPONENT_REPORT_FAILED = new ApplicationErrorCode(APP_NAME,
            9094, "Print Package Component Report failed");

    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_OR_NOT_ARRIVED_STATUS = new ApplicationErrorCode(
            APP_NAME, 8062, "Accommodation Status is not in Booked or not arrived or checkingIn");

    public static final ApplicationErrorCode DATERANGE_INVALID = new ApplicationErrorCode(APP_NAME, 7051,
            "Date range is invalid");

    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(APP_NAME, 9042,
            "INVALID REQUEST !");

    public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7065,
            "RECORD NOT FOUND");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_NOT_CHECKED_IN = new ApplicationErrorCode(APP_NAME,
            9073, "Accommodation Should be in CheckedIn Status");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 9055,
            "UPGRADE NOT ALLOWED !");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_FOR_UPGRADE_FOR_COUNT = new ApplicationErrorCode(
            APP_NAME, 9103, "Accommodation is Upgraded For Counts. No Modification is allowed");

    public static final ApplicationErrorCode SWAP_GUESTS_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9090,
            "Swap Guests Request invalid");

    public static final ApplicationErrorCode CHANGE_ROOM_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9083,
            "Change Room Request invalid");

    public static final ApplicationErrorCode CUT_KEY_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9028,
            "GrantAccess request is invalid");

    public static final ApplicationErrorCode ACTIVATE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9114,
            "Activate Request invalid");

    public static final ApplicationErrorCode RFID_REFERENCE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9131,
            "RFIdMediaReferenceRequest invalid");

    public static final ApplicationErrorCode ASSOCIATE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9117,
            "Associate Request invalid");

    public static final ApplicationErrorCode EM_STAUS_CHANGE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9129,
            "ExperienceMediaStatusChange Request invalid");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUPING_NOT_FOUND = new ApplicationErrorCode(APP_NAME,
            9039, "Travel Component Grouping not found");

    public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7192,
            "Accommodations not found");

    public static final ApplicationErrorCode INVALID_STATUS = new ApplicationErrorCode(APP_NAME, 9041, "Invalid Status");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_FIXED_TICKETS = new ApplicationErrorCode(APP_NAME,
            9074, "Accommodation has Fixed Tickets");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_TICKET_GROUP_DIFFERENT = new ApplicationErrorCode(
            APP_NAME, 9075, "Ticket Group is Different");

    public static final ApplicationErrorCode PRINT_COLLATERAL_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9093,
            "Print Request Invalid");

    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATION_COUNT_BY_STATUS_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 9087, "Retrieve Accommodation count by status request invalid");

    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATION_SERVICES_DETAILS_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 9084, "Retrieve Accommodation Services Details Requests invalid");

    public static final ApplicationErrorCode MANAGE_ARRIVAL = new ApplicationErrorCode(APP_NAME, 9002,
            "Manage Arrival Error");

    public static final ApplicationErrorCode CHARGE_TO_REQUIRED = new ApplicationErrorCode(APP_NAME, 9070,
            "Charge to Guest or Wholesaler should be provided");

    public static final ApplicationErrorCode CHARGE_TO_WHOLESALER_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 9069,
            " Charge to Wholesaler is not allowed for a Non Wholesaler package");

    public static final ApplicationErrorCode ROOM_DETAIL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7228,
            "There should be at least one Room Detail");

    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode(APP_NAME, 7107,
            " Resort Period is invalid ");

    public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY = new ApplicationErrorCode(APP_NAME, 7102,
            " Travel Agency is invalid ");

    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS = new ApplicationErrorCode(APP_NAME, 7110,
            " Travel Status is invalid ");

    public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(
            APP_NAME, 7216, "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");

    public static final ApplicationErrorCode BYPASS_FREEZE_INACTIVE_MANDATORY_FIELDS = new ApplicationErrorCode(
            APP_NAME, 7217, "ATLEAST FPLOSID OR FREEZEID MUST BE PROVIDED");

    public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode(APP_NAME, 7106,
            " Resort Code is invalid ");

    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(APP_NAME, 7105,
            " Room Type is invalid ");

    public static final ApplicationErrorCode INVALID_PACKAGE_CODE = new ApplicationErrorCode(APP_NAME, 7104,
            " Package Code is invalid ");

    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED = new ApplicationErrorCode(
            APP_NAME, 7090, "External Reference Source or Code required");

    public static final ApplicationErrorCode APPLICATION_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7063,
            "GENERAL ERROR");

    public static final ApplicationErrorCode TRAVEL_AGENCY_NOT_VALID = new ApplicationErrorCode(APP_NAME, 7013,
            "Travel Agency is not valid");

    public static final ApplicationErrorCode INVALID_EXTERNAL_REFERNCE_CODE = new ApplicationErrorCode(APP_NAME, 7086,
            "Invalid External Reference Code");

    public static final ApplicationErrorCode INTERNAL_RESERVATIONS_CANNOT_HAVE_EXTERNAL_REF_NUMBERS = new ApplicationErrorCode(
            APP_NAME, 7214, "External Reference Numbers not allowed For Internal Bookings !!");

    public static final ApplicationErrorCode EXTERNAL_REFERENCE_NUMBER_REQUIRED = new ApplicationErrorCode(APP_NAME,
            7088, "External Reference is required");

    public static final ApplicationErrorCode SALES_CHANNEL_NOT_EXISTS_FOR_BLOCK = new ApplicationErrorCode(APP_NAME,
            7230, "Sales Channel are not available for the block");

    public static final ApplicationErrorCode INVALID_PACKAGE_FOR_BLOCK = new ApplicationErrorCode(APP_NAME, 7169,
            "Package is invalid for the block");

    public static final ApplicationErrorCode PACKAGE_CANNOT_BE_SOLD = new ApplicationErrorCode(APP_NAME, 7170,
            "Package cannot be sold");

    public static final ApplicationErrorCode GROUP_NOT_COMMISSIONABLE = new ApplicationErrorCode(APP_NAME, 7112,
            " Group is not commissionable");

    public static final ApplicationErrorCode INCONSISTENT_DATA = new ApplicationErrorCode(APP_NAME, 7072,
            "Inconsitent Data");

    public static final ApplicationErrorCode INVALID_PARTYMIX = new ApplicationErrorCode(APP_NAME, 7005,
            "Invalid PartyMix. Please send valid partymix");

    public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7064,
            "Required parameters are missing");

    public static final ApplicationErrorCode LOCATION_ID_MANDATORY = new ApplicationErrorCode(APP_NAME, 8317,
            "Location Id is Mandatory");

    public static final ApplicationErrorCode AGE_REQUIRED = new ApplicationErrorCode(APP_NAME, 8018, "AGE IS REQUIRED!");

    public static final ApplicationErrorCode QUICK_BOOK_NO_OF_ROOMS_REQUIRED = new ApplicationErrorCode(APP_NAME, 9004,
            "NUMBER OF ROOMS IS REQUIRED !");

    public static final ApplicationErrorCode PRIMARY_GUEST_CANNOT_BE_INACTIVE = new ApplicationErrorCode(APP_NAME, 5092,
            "Primary Guest cannot be inactive.");

    public static final ApplicationErrorCode RETRIEVE_COMMENTS = new ApplicationErrorCode(APP_NAME, 9001,
            "Unable to Retrieve Comments!");

    public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new ApplicationErrorCode(APP_NAME, 7177,
            " No Accommodation Component found.");

    public static final ApplicationErrorCode REINSTATE_NOT_ALLOWED_FOR_DVC = new ApplicationErrorCode(APP_NAME, 7229,
            "This reservation is not eligible to be Re-instated. "
                    + "Please contact the appropriate Reservation Office, "
                    + "Operations Support or Manager for assistance");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7032,
            "Travel Plan Segment Id is required");

    public static final ApplicationErrorCode TRAVEL_PLAN_MISMATCH = new ApplicationErrorCode(APP_NAME, 7162,
            "Travel Plan Does not match");

    public static final ApplicationErrorCode SOURCE_ACCOUNTING_CENTER_NOT_MATCHING = new ApplicationErrorCode(APP_NAME,
            8575, "Source Accounting Center Not Matching");

    public static final ApplicationErrorCode BOOKING_SOURCE_DO_NOT_MATCH = new ApplicationErrorCode(APP_NAME, 7109,
            "Booking Source does not match");

    public static final ApplicationErrorCode CREATE_GROUP_TEAM = new ApplicationErrorCode(APP_NAME, 8079,
            "Error while trying create GroupTeam");

    public static final ApplicationErrorCode DELETE_TEAM_NAME = new ApplicationErrorCode(APP_NAME, 8081,
            "Invalid Delete Team Name Request");

    public static final ApplicationErrorCode CHECKED_OUT_ATTEMPTED_ON_CANCEL_AUTOCANCELLED_PAST_VIST_ACCOM = new ApplicationErrorCode(
            APP_NAME, 7210,
            "Check out is attempted on accommodation which is Cancelled " + "or Auto Cancelled or Past Visit!!");

    public static final ApplicationErrorCode CHECKED_OUT_ATTEMPTED_ON_BOOKED_ACCOM = new ApplicationErrorCode(APP_NAME,
            7212, "Check out is attempted on accommodation which is in Booked Status!!");

    public static final ApplicationErrorCode OVERRIDE_RATE_RACK_RATE_ERROR = new ApplicationErrorCode(APP_NAME, 7138,
            "Override rate cannot be more than rack rate.");

    public static final ApplicationErrorCode DVC_GROUP_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 7158,
            "Please use DVC Wishes UI to book against a DVC Group");

    public static final ApplicationErrorCode INVALID_SHARE_ACTION = new ApplicationErrorCode(APP_NAME, 7139,
            "Action has to be Share");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_SERVICE_REQUEST_ID_MISSING = new ApplicationErrorCode(
            APP_NAME, 8093, "Travel component service request Id is missing");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_MISSING = new ApplicationErrorCode(APP_NAME, 8084,
            "Travel component Id is missing");

    public static final ApplicationErrorCode PROFILEID_MISSING = new ApplicationErrorCode(APP_NAME, 8088,
            "Service request detail has profile Id missing");

    public static final ApplicationErrorCode RETRIEVE_SERVICE_REQUESTS_BY_ROUTING_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 9085, "Retrieve Service Requests By Routing Request is invalid");

    public static final ApplicationErrorCode SERVICE_REQUEST_DETAIL_MISSING = new ApplicationErrorCode(APP_NAME, 8087,
            "Service request detail is missing");

    public static final ApplicationErrorCode RECURRENCE_START_DATE_MISSING = new ApplicationErrorCode(APP_NAME, 8403,
            "Recurrence start date is missing");

    public static final ApplicationErrorCode RECURRENCE_END_DATE_AND_COUNT_MISSING = new ApplicationErrorCode(APP_NAME,
            8404, "Both recurrence end date and recurrence count are missing; at least one of them should be entered");

    public static final ApplicationErrorCode RECURRENCE_TYPE_MISSING = new ApplicationErrorCode(APP_NAME, 8405,
            "Recurrence type is missing");

    public static final ApplicationErrorCode INTERVAL_IN_DAYS_INVALID = new ApplicationErrorCode(APP_NAME, 8406,
            "Interval in days should be at least 1; interval in days entered - ");

    public static final ApplicationErrorCode INTERVAL_IN_WEEKS_INVALID = new ApplicationErrorCode(APP_NAME, 8407,
            "Interval in weeks is either null or less than 1");

    public static final ApplicationErrorCode SERVICE_REQUEST_DATE_MISSING = new ApplicationErrorCode(APP_NAME, 8408,
            "Service request date / recurrence start date is missing");

    public static final ApplicationErrorCode SERVICE_REQUEST_DATE_INVALID = new ApplicationErrorCode(APP_NAME, 8113,
            "Service request date / recurrence start date should be current date or a future date");

    public static final ApplicationErrorCode SERVICE_REQUEST_START_DATE_INVALID = new ApplicationErrorCode(APP_NAME,
            8114,
            "Service request date / recurrence start date should not be after the scheduled travel period end date");

    public static final ApplicationErrorCode SERVICE_REQUEST_QUANTITY_INVALID = new ApplicationErrorCode(APP_NAME, 8415,
            "Since item / service is associated to a pricing product, please enter a non-zero quantity");

    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME,
            9083, "Accommodation Component not found");

    public static final ApplicationErrorCode NOT_VALID_GATHERING_DETAIL = new ApplicationErrorCode(APP_NAME, 7156,
            "Gathering Detail is not valid");

    public static final ApplicationErrorCode INVALID_KTTW_TYPE_FOR_REPLACE = new ApplicationErrorCode(APP_NAME, 8577,
            "Invalid KTTW Type for Replace");

    public static final ApplicationErrorCode REPLACE_KEY_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9056,
            "Replace Key Request invalid");

    public static final ApplicationErrorCode MODIFY_ACCESS_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9047,
            "Modify Access Request invalid");

    public static final String NO_KTTW_FOUND_FOR_KTTW_NUMBER = "No Kttw details found for given Kttw number";

    public static final ApplicationErrorCode SEARCH_KTTW_GUEST_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME,
            9026, "Search Kttw Guests Request is invalid");

    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7026,
            "Travel Plan Guest is required");

    public static final ApplicationErrorCode BOOKING_SOURCE_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7125,
            "Booking Source not found");

    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7096,
            "communication Channel is required");

    public static final ApplicationErrorCode INVALID_CHANGE_REASON = new ApplicationErrorCode(APP_NAME, 7146,
            " Change Reason is invalid ");

    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode(APP_NAME, 7094,
            "Sales Channel is required");

    public static final ApplicationErrorCode CREATE_COMMENTS = new ApplicationErrorCode(APP_NAME, 8078,
            "Create Comments Request Invalid");

    public static final ApplicationErrorCode UPDATE_COMMENTS = new ApplicationErrorCode(APP_NAME, 8078,
            "Create Comments Request Invalid");

    public static final ApplicationErrorCode TRAVEL_PLAN_REQUIRED = new ApplicationErrorCode(APP_NAME, 9024,
            "Travel Plan Required");

    public static final ApplicationErrorCode INVALID_SEARCH_GUEST_REQUEST = new ApplicationErrorCode(APP_NAME, 8127,
            "Invalid Search Guest Request. Last Name and Postal Code are required fields");

    public static final ApplicationErrorCode RESERVATION_TYPE_REQUIRED = new ApplicationErrorCode(APP_NAME, 7233,
            "Reservation Type is Required");

    public static final ApplicationErrorCode LOCATION_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 9025,
            "Location Id is required");

    public static final ApplicationErrorCode ATS_TICKET_CODE_FROM_PRICING_NULL = new ApplicationErrorCode(APP_NAME,
            8506, "ATS Ticket Code from Pricing is null ");

    public static final ApplicationErrorCode FACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8501,
            "Error Invoking Facility Service ");

    public static final ApplicationErrorCode ROOM_READY_MESSAGE_INVALID = new ApplicationErrorCode(APP_NAME, 8564,
            "Room ready message is not set up along with its delivery information");

    public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new ApplicationErrorCode(APP_NAME, 8013,
            "SOURCER ACCOUNTING CENTER IS REQUIRED!");

    public static final ApplicationErrorCode SERVICE_REQUEST_RECURRENCE_ERROR = new ApplicationErrorCode(APP_NAME, 8090,
            "No service requests created as per the recurrence set");

    public static final ApplicationErrorCode NO_CHARGE_TRAVEL_COMPONENT_FOR_TCSR = new ApplicationErrorCode(APP_NAME,
            8409, "Charge Component Travel Component(s) not found for travel component service request Id");

    public static final ApplicationErrorCode NO_TC_SERVICE_REQUEST_FOUND = new ApplicationErrorCode(APP_NAME, 8092,
            "No travel component service request found for travel component service request Id");

    public static final ApplicationErrorCode SERVICE_REQUESTS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 8416,
            "Service requests could not be found in service request domain, ");

    public static final ApplicationErrorCode SERVICE_REQUEST_MODIFY_ERROR = new ApplicationErrorCode(APP_NAME, 8091,
            "At least one service request has been fulfilled or cancelled, this service request cannot be modified");

    public static final ApplicationErrorCode INVALID_RETRIEVE_SERVICE_REQUESTS_INPUT = new ApplicationErrorCode(
            APP_NAME, 8094, "Either travel component service request Id or travel component Id should be set");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 8085,
            "Travel Component not found for travel component Id");

    public static final ApplicationErrorCode GUARANTEE_STATUS_CAN_NOT_BE_CHANGED = new ApplicationErrorCode(APP_NAME,
            7195, " Guarantee status can not be changed ");

    public static final ApplicationErrorCode PACKAGE_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7121,
            "Package Not Found");

    public static final ApplicationErrorCode FACILITY_ID_INVALID = new ApplicationErrorCode(APP_NAME, 9019,
            "Faclity id not provided or is incorrect");

    public static final ApplicationErrorCode CHECKEDIN_ACCOMMODATION_ARRIVALPERIOD_MODIFY = new ApplicationErrorCode(
            APP_NAME, 7222,
            "Arrival Date can not be changed after reservation has been placed in a Reservation Status of "
                    + "Checking In, Checked In, In-House, Deposit Forfeiture and Evacuation.");

    public static final ApplicationErrorCode CANNOT_CREATE_RES_CHANGE_EVENT = new ApplicationErrorCode(APP_NAME, 9118,
            "Unable to Create a Res Change Event");

    public static final ApplicationErrorCode CANNOT_CREATE_ONE_CLICK_CHKIN_EVENT = new ApplicationErrorCode(APP_NAME,
            9150, "Unable to Create a one click check in event");

    public static final ApplicationErrorCode CANNOT_CREATE_GSR_EVENT = new ApplicationErrorCode(APP_NAME, 10201,
            "Unable to Create an GSR Event");

    public static final ApplicationErrorCode CANNOT_ASSIGN_MULTIPLE_TICKETS_TO_SAME_PERSON = new ApplicationErrorCode(
            APP_NAME, 7157, "Mutliple Tickets cannot be assigned to the same person");

    public static final ApplicationErrorCode ALL_TICKETS_ARE_NOT_ASSIGNED_TO_GUESTS = new ApplicationErrorCode(APP_NAME,
            7223, "All Tickets are not assigned to Guests");

    public static final ApplicationErrorCode CREATE_ADMISSION_COMPONENT_FAILED = new ApplicationErrorCode(APP_NAME,
            9096, "Create Admission Component failed");

    public static final ApplicationErrorCode CANNOT_CREATE_ADMISSION_EVENT = new ApplicationErrorCode(APP_NAME, 9119,
            "Unable to Create a Admission Event");

    public static final ApplicationErrorCode TIME_OUT_FOR_ROOM_INV_DECREMENT_RESPONSE = new ApplicationErrorCode(
            APP_NAME, 7189, " Timed out for RoomInventoryDecrement response");

    public static final ApplicationErrorCode AVAILABLITY_SERVICE_EXCEPTION = new ApplicationErrorCode(APP_NAME, 7127,
            "Exception in Availability Service ");

    public static final ApplicationErrorCode FREEZE_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7025,
            "Freeze Id is required");

    public static final ApplicationErrorCode INVENTORY_TRACKING_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7025,
            "Inventory Tracking Id is invalid");

    public static final ApplicationErrorCode RESERVATION_NUMBER_REQUIRED = new ApplicationErrorCode(APP_NAME, 7025,
            "Reservation Number is invalid");

    public static final ApplicationErrorCode GROUPS_CONFIRMATION_DLVRY_METHD_NULL = new ApplicationErrorCode(APP_NAME,
            7197, "Groups Confirmation Delivery method cannot be null.");

    public static final ApplicationErrorCode INVALID_RESPONSE = new ApplicationErrorCode(APP_NAME, 8505,
            "Invalid Response From an External System!");

    public static final ApplicationErrorCode INVALID_VALUE = new ApplicationErrorCode(APP_NAME, 7082, "INVALID VALUE");

    public static final ApplicationErrorCode CANNOT_CREATE_EXPERIENCE_MEDIA_EVENT = new ApplicationErrorCode(APP_NAME,
            10195, "Unable to Create an Experience Media Event");

    public static final ApplicationErrorCode INVALID_FACILITY_ID = new ApplicationErrorCode(APP_NAME, 8030,
            "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!!");

    public static final ApplicationErrorCode CHARGE_ITEMS_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7113,
            " Charge Items are not found");

    public static final ApplicationErrorCode PRICING_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8303,
            "Error Invoking Pricing Service ");

    public static final ApplicationErrorCode REVENUE_TYPE_NULL_FROM_PRICING = new ApplicationErrorCode(APP_NAME, 8312,
            "Revenue Type from Pricing is null");

    public static final ApplicationErrorCode REVENUE_TYPE_IS_REQUIRED = new ApplicationErrorCode(APP_NAME, 8055,
            "UNITPRICE.BASECHARGE.REVENUE TYPE IS REQUIRED!!");

    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS = new ApplicationErrorCode(APP_NAME,
            7163, "Invalid status for modification");

    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_PAST_VISIT = new ApplicationErrorCode(
            APP_NAME, 7165,
            "Charge Group status cannot be changed to Booked, " + "Cancelled or Auto Cancelled from Past Visit");

    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_CHECKED_IN = new ApplicationErrorCode(
            APP_NAME, 7164,
            "Charge Group status cannot be changed to Booked, " + "Cancelled or Auto Cancelled from Checked In");

    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_CANCELLED = new ApplicationErrorCode(
            APP_NAME, 7166, "Charge Group cannot be modified from Cancelled Status");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUPING_ID_REQUIRED = new ApplicationErrorCode(APP_NAME,
            9007, "Travel Component Grouping Id Required ");

    public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8304,
            "Error Invoking Packaging Service ");

    public static final ApplicationErrorCode APP_NAME_PARY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8502,
            "Error Invoking APP_NAME Party Service ");

    public static final ApplicationErrorCode NO_PROFILE_FOUND = new ApplicationErrorCode(APP_NAME, 8089,
            "No profile found for profile Id");

    public static final ApplicationErrorCode NO_SERVICE_TYPE_PROFILE_FOUND = new ApplicationErrorCode(APP_NAME, 8104,
            "Profile is not of type service, profile Id");

    public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_DATA_NOT_SETUP = new ApplicationErrorCode(APP_NAME,
            8561, "Room ready notification data is not set up");

    public static final ApplicationErrorCode MULTIPLE_ACCOM_ERROR_SHARE = new ApplicationErrorCode(APP_NAME, 7175,
            " Cannot have multiple accommodations for a External reference for share ");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_REQUIRED = new ApplicationErrorCode(APP_NAME, 7037,
            "Travel Component Id is required");

    public static final ApplicationErrorCode INVALID_SHARE_DATA = new ApplicationErrorCode(APP_NAME, 7140,
            "One Property Reservation cannot have multiple Accommodations which are shared");

    public static final ApplicationErrorCode ACCOMMODATION_INVALID_FOR_SHARE = new ApplicationErrorCode(APP_NAME, 7130,
            "Accommodation is invalid for Share");

    public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            8314, "Error Invoking  Inventory Management Service ");

    public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_ETD_ETA = new ApplicationErrorCode(APP_NAME,
            4072, "Error Invoking  Inventory Management Service ");

    public static final ApplicationErrorCode PACKAGE_CODE_REQUIRED = new ApplicationErrorCode(APP_NAME, 7021,
            "Package Code is required");

    public static final ApplicationErrorCode CHARGE_REQUESTS_FOR_SERVICE_REQUEST_NOT_CREATED = new ApplicationErrorCode(
            APP_NAME, 8107, "Charge requests for the service request could not be created");

    public static final ApplicationErrorCode BUNDLE_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9142,
            "Error Invoking Bundle Service ");

    public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8307,
            "Error Invoking  Folio Management Service ");

    public static final ApplicationErrorCode GROUPCODE_INVALID = new ApplicationErrorCode(APP_NAME, 7041,
            "Groupcode is invalid");

    public static final ApplicationErrorCode GROUPS_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8305,
            "Error Invoking Groups Management Service ");

    public static final ApplicationErrorCode HOUSE_KEEPING_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8566,
            "Error invoking House Keeping service");

    public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new ApplicationErrorCode(APP_NAME, 91443,
            "Validation Failed.");

    public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new ApplicationErrorCode(APP_NAME,
            91443, "Data not found.");

    public static final ApplicationErrorCode NO_CONFIG_DATA_FOUND = new ApplicationErrorCode(APP_NAME, 8569,
            "Config Data Error!");

    public static final ApplicationErrorCode ADD_UPDATE_ALERT = new ApplicationErrorCode(APP_NAME, 8072,
            "Request is invalid for updateAlert");

    public static final ApplicationErrorCode FIND_TEAMS_BY_GROUP_CODE = new ApplicationErrorCode(APP_NAME, 8080,
            "Invalid Group Code");

    public static final ApplicationErrorCode ROOMING_LIST_NAME_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 5090,
            "Rooming List Name was not found, please re-enter.");

    public static final ApplicationErrorCode PROCESS_NAME_NULL = new ApplicationErrorCode(APP_NAME, 8069,
            "Process Name cannot be null");

    public static final ApplicationErrorCode NO_RESULTS_FOUND = new ApplicationErrorCode(APP_NAME, 5089,
            "No Results found, please re-enter.");

    public static final ApplicationErrorCode USER_ID_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 5088,
            "User ID was not found, please re-enter.");

    public static final ApplicationErrorCode MASS_MODIFY_NAME_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 5091,
            "Mass Modify Name was not found, please re-enter.");

    public static final ApplicationErrorCode RESVTN_IN_PROGRESS = new ApplicationErrorCode(APP_NAME, 8570,
            "You have selected reservations that are already in progress?Please wait");

    public static final ApplicationErrorCode TRAVEL_COMPONENT_GUEST_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 9057,
            "Travel Component Guest not found");

    public static final ApplicationErrorCode INVALID_ROOM_TYPE_ADA = new ApplicationErrorCode(APP_NAME, 8577,
            "Mass Option is not available when reservation contains an Accessible Room or Accessible Icon");

    public static final ApplicationErrorCode PRIMARY_TRAVEL_PARTY_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 8120,
            "Primary Travel Party could not be found for accommodation component; party id");

    public static final ApplicationErrorCode INVALID_FACILITY = new ApplicationErrorCode(APP_NAME, 8011,
            "FACILITY ID/NAME IS REQUIRED!");
    public static final ApplicationErrorCode INVALID_KTTW_ID = new ApplicationErrorCode(APP_NAME, 8578,
            "Kttw Id is not Valid");

    public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new ApplicationErrorCode(APP_NAME, 9095,
            "Print Package Component Report failed");

    public static final ApplicationErrorCode FULFILL_SERVICE_REQUEST_INPUT_MISSING = new ApplicationErrorCode(APP_NAME,
            8108, "FulfillServiceRequestInput is missing");

    public static final ApplicationErrorCode SERVICE_REQUEST_TYPE_MISSING = new ApplicationErrorCode(APP_NAME, 8109,
            "Service request type is missing");

    public static final ApplicationErrorCode SERVICE_REQUEST_TYPE_INVALID = new ApplicationErrorCode(APP_NAME, 8111,
            "Only service requests and bell service requests can be fulfilled via this method; service request type");

    public static final ApplicationErrorCode PROPERTY_FILE_NOT_LOADED = new ApplicationErrorCode(APP_NAME, 91438,
            "Error in loading property file <one-source.properties>.");

    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_CANCELLED = new ApplicationErrorCode(
            APP_NAME, 7192, " Accommodation should be in Booked status to be cancelled");

    public static final ApplicationErrorCode CANNOT_CANCEL_ACCOMMODATIONS_WITH_TICKETS = new ApplicationErrorCode(
            APP_NAME, 7066, "Could not cancel accommodation with tickets");

    public static final ApplicationErrorCode PICKEDUP_PREARRIVAL_TICKETS = new ApplicationErrorCode(APP_NAME, 9097,
            "Pre arrival tickets have been picked up");

    public static final ApplicationErrorCode ACCOMMODATION_CHECK_OUT_DATE_MISSING = new ApplicationErrorCode(APP_NAME,
            8411, "Accommodation check out date should not be null");

    public static final ApplicationErrorCode EXCEPTION_IN_TICKET_BANK_INTERNAL_SERVICE = new ApplicationErrorCode(
            APP_NAME, 8512, "Exception in invoking TicketBankService EJB ");

    public static final ApplicationErrorCode CREATE_KTTW_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9023,
            "Could not fulfill create Kttw as the request list was invalid");

    public static final ApplicationErrorCode ADMISSION_COMPONENT_NOT_VALID = new ApplicationErrorCode(APP_NAME, 9012,
            "Admission Component Id or entitlment detauls required ");

    public static final ApplicationErrorCode DEACTIVATE_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9043,
            "Deactivate Request invalid");

    public static final ApplicationErrorCode CANNOT_CREATE_GRANT_EXPERICENCE_MEDIA_ACCESS_EVENT = new ApplicationErrorCode(
            APP_NAME, 10198, "Unable to Create an Grant Expericence Media Access Event");

    public static final ApplicationErrorCode COULD_NOT_CREATE_KTTW = new ApplicationErrorCode(APP_NAME, 9058,
            "Could not create KTTW");

    public static final ApplicationErrorCode HOUSEKEEPING_SECTION_DETAILS_NOT_FOUND = new ApplicationErrorCode(APP_NAME,
            8402, "Housekeeping section details could not be found");

    public static final ApplicationErrorCode CANNOT_CREATE_MODIFY_ACCESS_EVENT = new ApplicationErrorCode(APP_NAME,
            10199, "Unable to Create an Modify Access Event");

    public static final ApplicationErrorCode REINSTATE_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 100016,
            "This reservation is not eligible to be Re-instated. "
                    + "Please contact the appropriate Reservation Office, "
                    + "Operations Support or Manager for assistance");
    public static final ApplicationErrorCode MOD_MORE_THAN_ONE_SHARE_ACCOMM = new ApplicationErrorCode(APP_NAME, 7150,
            "Cannot have more than one shared accommodation for Modification");

    public static final ApplicationErrorCode MORE_THAN_ONE_ACCOMM = new ApplicationErrorCode(APP_NAME, 7151,
            "Only one Room Type should be selected for shared room modification!!");

    public static final ApplicationErrorCode MULTIPLE_PACKAGES_SELECTED = new ApplicationErrorCode(APP_NAME, 7209,
            "Cannot select more than one package for shared room modification!!");

    public static final ApplicationErrorCode INVALID_DATES = new ApplicationErrorCode(APP_NAME, 7149,
            "Dates are invalid");

    public static final ApplicationErrorCode CANNOT_OVERRIDE_BOXED_RESTRICTIONS = new ApplicationErrorCode(APP_NAME,
            7114, " Boxed restrictions cannot be overriden");

    public static final ApplicationErrorCode NO_MIX_OF_NONDISNEY_AND_DISNEY_RESORTS = new ApplicationErrorCode(APP_NAME,
            7141, "Disney and Non Disney Resorts cannot be mixed");

    public static final ApplicationErrorCode NO_MULTIPLE_NON_DISNEY_RESORTS_FOR_ROOMS = new ApplicationErrorCode(
            APP_NAME, 7133, "No multiple Non Disney Resorts for rooms");

    public static final ApplicationErrorCode CANNOT_OVERRIDE_CANCELLED_ACCOMMODATIONS = new ApplicationErrorCode(
            APP_NAME, 8509, " Cancelled accommodations cannot be overriden");

    public static final ApplicationErrorCode RATE_OVERRIDE_FAILURE = new ApplicationErrorCode(APP_NAME, 7048,
            "Rate override failed");

    public static final ApplicationErrorCode NO_ROOM_PRICES_FROM_PRICING = new ApplicationErrorCode(APP_NAME, 7188,
            "No Room Prices returned from Pricing");

    public static final ApplicationErrorCode LOCATION_ID_IS_MANDATORY = new ApplicationErrorCode(APP_NAME, 8317,
            "Location Id is Mandatory");

    public static final ApplicationErrorCode RECORD_NOT_FOUND_EXC = new ApplicationErrorCode(APP_NAME, 9127,
            "Record Not Found");

    public static final ApplicationErrorCode SERVICE_REQUEST_NOT_PRICED = new ApplicationErrorCode(APP_NAME, 8105,
            "The service request could not be priced successfully");

    public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode(APP_NAME, 7027,
            "Guest is Required");

    public static final ApplicationErrorCode ROOM_OR_FACILITY_MISSING = new ApplicationErrorCode(APP_NAME, 8132,
            "RoomTypeTO or AccommodationFacilityTO is null; cannot convert to PricingRoomTypeTO");

    public static final ApplicationErrorCode PACKAGE_RATE_CATEGORY_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 7173,
            " Package Rate Category not found");

    public static final ApplicationErrorCode INVALID_TC_ID = new ApplicationErrorCode(APP_NAME, 7176,
            " Invalid Travel Component Id.");

    public static final ApplicationErrorCode GAURANTEE_STATUS_CAN_NOT_BE_CHANGED = new ApplicationErrorCode(APP_NAME,
            100015, " Guarantee status can not be changed ");

    public static final ApplicationErrorCode SERVICE_REQUEST_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 9131,
            "Error Invoking Service Request Service ");

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

    public static final ApplicationErrorCode ACCOMMODATION_PERIOD_NOT_OVERLAP = new ApplicationErrorCode(APP_NAME, 7135,
            "Accommodation can not be shared - dates must overlap at least one night");

    public static final ApplicationErrorCode ACCOMMODATION_SHAREPARTYMIX_NOT_OVERLAP = new ApplicationErrorCode(APP_NAME, 7638,
            "Share Party Mix - overlap period error");

    public static final ApplicationErrorCode INVALID_NUMBER_FORMAT = new ApplicationErrorCode(APP_NAME, 7143,
            "Invalid Number Format");

    public static final ApplicationErrorCode INVALID_VISIT_PURPOSE = new ApplicationErrorCode(APP_NAME, 7144,
            "Invalid Visit Purpose");

    public static final ApplicationErrorCode XBMS_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 7145,
            "XBMS Service Failure");

    public static final ApplicationErrorCode ENTERPRICE_FACILITY_REQUIRED = new ApplicationErrorCode(APP_NAME, 7146,
            "Enterprise Facility Id Missing");

    public static final ApplicationErrorCode CANNOT_MODIFY_CHECKEDIN_ACCOMMADATION = new ApplicationErrorCode(APP_NAME,
            7187, "Checked In Accommodation cannot be modified");

    public static final ApplicationErrorCode INVALID_RES_MODIFY_REQUEST = new ApplicationErrorCode(APP_NAME, 100029,
            "Invalid Modify Request");

    public static final ApplicationErrorCode INVALID_EXTERENAL_REFERENCE_DETAIL = new ApplicationErrorCode(APP_NAME,
            7092, "Invalid External Reference Details");

    public static final ApplicationErrorCode INVALID_ORIGINAL_TXN_GUEST_ID = new ApplicationErrorCode(APP_NAME, 91444,
            "Invalid External Reference Details");

    public static final ApplicationErrorCode INVALID_GUEST_REFERENCE_DETAIL = new ApplicationErrorCode(APP_NAME, 91443,
            "Invalid External Reference Details");

    public static final ApplicationErrorCode INVALID_ENTITLEMENT_IN_REQUEST = new ApplicationErrorCode(APP_NAME, 8588,
            "Either serial number or mag strip code should be present for entitlementDetail in request");

    public static final ApplicationErrorCode INVALIDMEDIA_FOR_REPLACE = new ApplicationErrorCode(APP_NAME, 8579,
            "Rfid Media can not be replaced");

    public static final ApplicationErrorCode LATE_CHECK_OUT_REQUESTED_TOO_SOON = new ApplicationErrorCode(APP_NAME,
            8401, "Late checkout can be requested only a set number days prior to guest arrival - ");

    public static final ApplicationErrorCode DAYS_UPFRONT_TO_LATE_CHECK_OUT_NOT_SET_UP_IN_RIM = new ApplicationErrorCode(
            APP_NAME, 8400, "The number of days prior to guest arrival that a late check out can be requested, "
                    + "has not be configured in Room Inventory; facilityId - ");

    public static final ApplicationErrorCode LATE_CHECK_OUT_DATE_INVALID = new ApplicationErrorCode(APP_NAME, 8097,
            "Requested late check out date should not be after the scheduled travel period end date");

    public static final ApplicationErrorCode NOT_A_LATE_CHECK_OUT = new ApplicationErrorCode(APP_NAME, 8096,
            "Requested late check out date is before the scheduled travel period end date; "
                    + "this is not a late check out");

    public static final ApplicationErrorCode LATE_CHECK_OUT_ALREADY_REQUESTED = new ApplicationErrorCode(APP_NAME, 8018,
            "Late check out has already been requested for this accommodation component");

    public static final ApplicationErrorCode LATE_CHECK_OUT_REQUEST_DETAIL_MISSING = new ApplicationErrorCode(APP_NAME,
            8095, "Late check out request detail is missing");

    public static final ApplicationErrorCode LATE_CHECK_OUT_DATE_MISSING = new ApplicationErrorCode(APP_NAME, 8418,
            "Late check out request date/time should not be null");

    public static final ApplicationErrorCode CANNOT_CREATE_TICKET_BANK_EVENT = new ApplicationErrorCode(APP_NAME, 10197,
            "Unable to Create an Ticket Bank Event");

    public static final ApplicationErrorCode CANNOT_CREATE_KEY_CUTTING_EVENT = new ApplicationErrorCode(APP_NAME, 10196,
            "Unable to Create an Key Cutting Event");

    public static final ApplicationErrorCode COULD_NOT_CUT_KEY = new ApplicationErrorCode(APP_NAME, 9062,
            "Key Cutting Service could not cut keys");

    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_GUEST_EVENT = new ApplicationErrorCode(APP_NAME,
            9124, "Unable to Create a Travel Plan Guest Event");

    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATIONS_FOR_AUTO_CHECKOUT_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 9089, "Retrieve Accommodation for auto checkout request invalid");

    public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE = new ApplicationErrorCode(APP_NAME, 7080,
            "cannot calculate Cancel fee");

    public static final ApplicationErrorCode RETRIEVE_BUNDLE_FAILURE = new ApplicationErrorCode(APP_NAME, 9143,
            "Bundle details not returned by the Bundle Service ");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEARCH_NO_RESULT = new ApplicationErrorCode(APP_NAME, 7183,
            "No travel plan data found.");

    public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode(APP_NAME, 7124,
            "Search Criteria is Invalid");

    public static final ApplicationErrorCode RETRIEVE_SERVICE_REQUESTS_FOR_FULFILLMENT_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 8412, "RetrieveServiceRequestsForFulfillmentRequest is either null or invalid");

    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_REQUIRED = new ApplicationErrorCode(APP_NAME, 7018,
            " Travel Plan Segment is Required ");

    public static final ApplicationErrorCode EXCEEDS_MAXIMUM_LIMIT = new ApplicationErrorCode(APP_NAME, 7126,
            "Exceeds Maximum Limit");

    public static final ApplicationErrorCode INVALID_TRAVELPLAN_ID_RECIEVED_FROM_SIEBEL = new ApplicationErrorCode(
            APP_NAME, 7193, " Invalid Travel Plan Id received from SIEBEL");

    public static final ApplicationErrorCode TICKETING_SERVICE_ERROR = new ApplicationErrorCode(APP_NAME, 9061,
            "Ticketing Service Error");

    public static final ApplicationErrorCode DME_TRANSIT_TIME_NOT_SET_UP_IN_RIM = new ApplicationErrorCode(APP_NAME,
            8510, "The DME transit time has not be configured in Room Inventory; facilityId - ");

    public static final ApplicationErrorCode LATE_CHECK_OUT_NEW_STATUS_MISSING = new ApplicationErrorCode(APP_NAME,
            8414, "New late check out status is missing");

    public static final ApplicationErrorCode UPDATE_LATE_CHECK_OUT_REQUEST_LIST_MISSING = new ApplicationErrorCode(
            APP_NAME, 8099, "No late check out request list provided for status update");

    public static final ApplicationErrorCode LATE_CHECK_OUT_STATUS_CHANGE_DETAIL_MISSING = new ApplicationErrorCode(
            APP_NAME, 8413, "LateCheckOutStatusChangeDetail object is missing");

    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_DENIED_TO_CANCELLED_NOT_ALLOWED = new ApplicationErrorCode(
            APP_NAME, 8103, "The status of a late check out request can not be changed from Denied to Cancelled; "
                    + "travelComponentServiceRequestId");

    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_APPROVED_TO_DENIED_NOT_ALLOWED = new ApplicationErrorCode(
            APP_NAME, 8102, "The status of a late check out request can not be changed from Approved to Denied;");

    public static final ApplicationErrorCode CANCELLED_REQUEST_STATUS_CHANGE_NOT_ALLOWED = new ApplicationErrorCode(
            APP_NAME, 8100, "This late check out request has been cancelled, its status cannot be changed now; "
                    + "travelComponentServiceRequestId ");

    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_PENDING_NOT_ALLOWED = new ApplicationErrorCode(
            APP_NAME, 8101,
            "The status of a late check out request can be set to Pending by the system and not by an user; "
                    + "travelComponentServiceRequestId");

    public static final ApplicationErrorCode STATUS_CHANGE_TO_FULFILLED_NOT_SUPPORTED = new ApplicationErrorCode(
            APP_NAME, 8099,
            "New status is set to fulfilled; "
                    + "fulfillment of late check out service request is not supported by this method; "
                    + "travelComponentServiceRequestId");

    public static final ApplicationErrorCode DVC_RESERVATION_CANNOT_BE_UPGRADED = new ApplicationErrorCode(APP_NAME,
            8135, "DVC Reservation Cannot be Upgraded!");

    public static final ApplicationErrorCode UPGRADE_CHARGETOGUEST_WHOLESALER = new ApplicationErrorCode(APP_NAME, 9099,
            "UpgradePartyMix as charge to guest not allowed for wholesale reservation");

    public static final ApplicationErrorCode GUEST_REFERENCE_REQUIRED = new ApplicationErrorCode(APP_NAME, 9082,
            "Guest Reference Should be Provided for upgrading Party Mix");

    public static final ApplicationErrorCode TICKET_CODE_REQUIRED_ERROR = new ApplicationErrorCode(APP_NAME, 91445,
            "Ticket Code is required");

    public static final ApplicationErrorCode INVALID_GUEST_ID = new ApplicationErrorCode(APP_NAME, 9081,
            "Invalid GuestId for Deleting or changing Age Type of the Guest");

    public static final ApplicationErrorCode AGR_TYPE_OR_AGE_REQUIRED = new ApplicationErrorCode(APP_NAME, 9080,
            "Age Type or Age for the Guest Should be provided");

    public static final ApplicationErrorCode ADD_GUEST_NOT_ALLOWED_FOR_DCL = new ApplicationErrorCode(APP_NAME, 7227,
            "Adding Guest is not allowed for DCL Reservation ");

    public static final ApplicationErrorCode AGE_TYPE_CHANGE_NOT_ALLOWED_FOR_DCL = new ApplicationErrorCode(APP_NAME,
            7225, "Age Type change is not allowed for DCL Reservation ");

    public static final ApplicationErrorCode TICKET_ALREADY_USED_ERROR_CODE = new ApplicationErrorCode(APP_NAME, 91444,
            "Ticket is already used by guest.");

    public static final ApplicationErrorCode PRICING_DETAILS_NOT_AVAIL_FOR_TICKET_CODE = new ApplicationErrorCode(
            APP_NAME, 91443, "No Pricing Detail found For ticket Code.");

    public static final ApplicationErrorCode NO_CHARGE_NOT_ALLOWED = new ApplicationErrorCode(APP_NAME, 9101,
            "No Charge Not Allowed!");

    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_END_DATE_SAME = new ApplicationErrorCode(APP_NAME,
            9078, "End date requested is same as Accommodations End Date");

    public static final ApplicationErrorCode ACCOMMODATION_END_DATE_REQUIRED = new ApplicationErrorCode(APP_NAME, 9077,
            "Accommodation End Date is Required");

    public static final ApplicationErrorCode START_DATE_CHANGE = new ApplicationErrorCode(APP_NAME, 9076,
            "Start Date for the Accommodation can not be Changed");

    public static final ApplicationErrorCode RESORT_OR_ROOM_TYPE_REQUIRED = new ApplicationErrorCode(APP_NAME, 9060,
            "Resort and Room Type Required");

    public static final ApplicationErrorCode RETRIEVE_GUEST_SUMMARY = new ApplicationErrorCode(APP_NAME, 9003,
            "RETRIEVE GUEST SUMMARY");

    public static final ApplicationErrorCode RFID_MEDIA_NOT_AVAILABLE = new ApplicationErrorCode(APP_NAME, 91442,
            "Rfid Media not available for any of the Travel Plan Guest.");

    public static final ApplicationErrorCode TICKET_BANK_EMPTY = new ApplicationErrorCode("APP_NAME_RESM", 9104,
            "No Tickets in ticket bank");

    public static final ApplicationErrorCode SHARE_ACCOMMODATION_SEARCH_NO_RESULT = new ApplicationErrorCode(
            "APP_NAME_RESM", 7184, "No accommodation data found.");

    public static final ApplicationErrorCode TRANSFER_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 9135,
            "TransferRequest invalid");

    public static final ApplicationErrorCode GET_NEXT_SECONDARY_STATE_REQUEST_INVALID = new ApplicationErrorCode(
            APP_NAME, 9136, "Get Next Secondary State Request is Invalid ");

    public static final ApplicationErrorCode RESORT_CACHE_NOT_LOADED = new ApplicationErrorCode(APP_NAME, 29136,
            " Unable to fetch data from Resort Cache ");

    public static final ApplicationErrorCode RESORT_TYPE_NOT_FOUND_FOR_RESORTCODE = new ApplicationErrorCode(APP_NAME,
            29137, " Resort Type Not Found for resortCode :  ");

    public static final ApplicationErrorCode TRANSFER_ROOM_REQUEST_INVALID = new ApplicationErrorCode(APP_NAME, 4001,
            "Transfer Room Request invalid");
    public static final ApplicationErrorCode PAYMENTV2IF_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 8307,
            "Error Invoking  PaymentV2Service ");

    /**
     * New Error Code Added during Uplift
     */

    public static final ApplicationErrorCode ACCOMMODATION_SALES_ERROR = new ApplicationErrorCode(APP_NAME, 100001,
            "Error in Accommodation Sales Service");

    public static final ApplicationErrorCode ASYNC_CALL_FAILURE = new ApplicationErrorCode(
            APP_NAME, 100002, "Failure during Async call");

    public static final ApplicationErrorCode PARTY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100003,
            "Error Invoking  PartyService Service ");

    public static final ApplicationErrorCode ROOM_TYPE_NOT_FOUND = new ApplicationErrorCode(APP_NAME, 100004,
            "Room Type not found ");

    public static final ApplicationErrorCode PROFILE_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100005,
            "Error Invoking Profile Service ");

    public static final ApplicationErrorCode RETRIEVE_GROUP_TEAM = new ApplicationErrorCode(APP_NAME, 100006,
            "Error while trying retrieve GroupTeam");

    public static final ApplicationErrorCode MISSING_CAMPUS_ID = new ApplicationErrorCode(APP_NAME, 100007,
            "Campus Id not found in Facility Response");

    public static final ApplicationErrorCode LOCATION_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100008,
            "Error Invoking Location Service ");

    public static final ApplicationErrorCode DATEFACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100009,
            "Error Invoking Date Facility Service ");

    public static final ApplicationErrorCode SERVICE_ID_CONVERSION_ERROR = new ApplicationErrorCode(APP_NAME, 100010,
            "Error while converting serviceId");

    public static final ApplicationErrorCode ACCOMMODATIONFACILITY_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            100011, "Error Invoking Accommodation Facility Service ");

    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED = new ApplicationErrorCode(
            APP_NAME, 100012, " Accommodation should be in Booked status to be Shared");

    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_UNSHARED = new ApplicationErrorCode(
            APP_NAME, 100013, " Accommodation should be in Booked status to be UnShared");

    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_CHANGE_EVENT = new ApplicationErrorCode(APP_NAME,
            100014, "Unable to Create a Travel Plan Change Event");

    public static final ApplicationErrorCode CANNOT_CREATE_TPV3_EVENT = new ApplicationErrorCode(APP_NAME, 9117,
            "Unable to Create a Generic TPv3 Event");

    public static final ApplicationErrorCode DVC_RESERVATION = new ApplicationErrorCode(APP_NAME, 100015,
            "Cannot cancel DVC Reservation");

    public static final ApplicationErrorCode MULIPLE_ROOM_DETAILS = new ApplicationErrorCode(APP_NAME, 100017,
            "Multiple Room Details");

    public static final ApplicationErrorCode ROOM_INVENTORY_API_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME,
            100018, "Error Invoking Room Inventory API");

    public static final ApplicationErrorCode GUEST_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100019,
            "Error Invoking Guest Service V2");

    public static final ApplicationErrorCode CANNOT_CREATE_GUEST_GOMASTER = new ApplicationErrorCode(APP_NAME, 100020,
            "Unable to Create a Guest in GoMaster");

    public static final ApplicationErrorCode CANNOT_CREATE_GROUPTEAM = new ApplicationErrorCode(APP_NAME, 100022,
            "Group Team Can not be Created");

    public static final ApplicationErrorCode DVC_MODIFY_RESERVATION = new ApplicationErrorCode(APP_NAME, 100023,
            "Cannot modify DVC Reservation");

    public static final ApplicationErrorCode RETRIEVE_DVC_RESERVATION = new ApplicationErrorCode(APP_NAME, 100024,
            "Cannot retrieve DVC Reservation");

    public static final ApplicationErrorCode TPV3_SERVICE_FAILURE = new ApplicationErrorCode(APP_NAME, 100025,
            "Error Invoking  TravelPlanV3 Service ");

    public static final ApplicationErrorCode INVALID_COMMENT_ID = new ApplicationErrorCode(APP_NAME, 100026,
            "No Comments exists for Comment Id");

    public static final ApplicationErrorCode FREEZE_ID_NOT_OBTAINED = new ApplicationErrorCode(APP_NAME, 100027,
            "The Inventory Requested for this reservation is not available. Please contact your FSA or Manager for assistance");

    public static final ApplicationErrorCode INVALID_FREEZE_REQUEST = new ApplicationErrorCode(APP_NAME, 100028,
            "Invalid Freeze Request. See HTTP Status Code");

    public static final ApplicationErrorCode INVALID_FACILITY_CHECKOUT = new ApplicationErrorCode(APP_NAME, 100030,
            "Reservation of other than Disney's Grand Californian facility , can not be checked out");

}