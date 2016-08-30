package com.disney.api.soapServices.applicationError;

public class CoreResErrorCode {
	
	// 8900 to 9000 - This range will be used by Reservation batch

	/**
	 * @author GOWDY001
	 * ===========================================================
	 * ========== Referenced in these Services Products ==========
	 * ===========================================================
	 * TravelPlanSegment:: TravelPlanSegmentServiceController, AutoArrivedHelper, TravelPlanSegmentMapper
	 *                     TravelPlanSegmentServiceValidationController, PricingMapper
	 * CoreRes 	        :: TravelComponentGuestControl, TravelComponentService, TravelPlanEntityMapper
	 *                     TravelPlanSegmentController, ConfiguratioConstants, CoreResEntityMapper,
	 *                     CoreResMapper, CoreResPropertyReader, CoreResUtils, CoreResValudationController
	 *
	 */


	    /******************************************************************************
	     * * START - PMSR/LILO CHANGES *
	     ****************************************************************************** */
	    public static final ApplicationErrorCode CANNOT_CREATE_GENERIC_DINE_EVENT = new ApplicationErrorCode("LILO_RESM",
	            9117, "Unable to Create a Generic Dine Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_RES_CHANGE_EVENT = new ApplicationErrorCode("LILO_RESM",
	            9118, "Unable to Create a Res Change Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_ADMISSION_EVENT = new ApplicationErrorCode("LILO_RESM",
	            9119, "Unable to Create a Admission Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_EVENT_DINE_EVENT = new ApplicationErrorCode("LILO_RESM",
	            9120, "Unable to Create a Event Dine Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_SHOW_DINE_EVENT = new ApplicationErrorCode("LILO_RESM",
	            9121, "Unable to Create a Show Dine Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_TABLE_SERVICE_DINE_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 9122, "Unable to Create a Event Table Service Dine Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_SEGMENT_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 9123, "Unable to Create a TPS Event");

	    public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_GUEST_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 9124, "Unable to Create a Travel Plan Guest Event");

	    public static final ApplicationErrorCode RESORT_ID_OR_FACILITY_ID_IS_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 9125, "Resort id or facility id is required");

	    public static final ApplicationErrorCode PARTY_ID_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9126,
	            "Party id is required");

	    public static final ApplicationErrorCode RETRIEVE_COMMENTS = new ApplicationErrorCode("LILO_RESM", 9001,
	            "Unable to Retrieve Comments!");

	    public static final ApplicationErrorCode MANAGE_ARRIVAL = new ApplicationErrorCode("LILO_RESM", 9002,
	            "Manage Arrival Error");

	    public static final ApplicationErrorCode RETRIEVE_GUEST_SUMMARY = new ApplicationErrorCode("LILO_RESM", 9003,
	            "RETRIEVE GUEST SUMMARY");

	    public static final ApplicationErrorCode QUICK_BOOK_NO_OF_ROOMS_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            9004, "NUMBER OF ROOMS IS REQUIRED !");

	    public static final ApplicationErrorCode TICKET_DETAIL_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9006,
	            "Ticket Detail sent is invalid");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUPING_ID_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 9007, "Travel Component Grouping Id Required ");

	    public static final ApplicationErrorCode ADMISSION_COMPONENT_NOT_VALID = new ApplicationErrorCode("LILO_RESM",
	            9012, "Admission Component Id or entitlment detauls required ");

	    public static final ApplicationErrorCode TICKET_BANK_LIST_NOT_VALID = new ApplicationErrorCode("LILO_RESM", 9016,
	            "Ticket Bank list not valid");

	    public static final ApplicationErrorCode SEARCH_WILL_CALL_RES_NOT_VALID = new ApplicationErrorCode("LILO_RESM",
	            9017, "Either Confirmation Number or Last name must be provided");

	    public static final ApplicationErrorCode NO_WILL_CALL_DETAILS_PROVIDED = new ApplicationErrorCode("LILO_RESM",
	            9018, "No detials provided to fulfill will call request");

	    public static final ApplicationErrorCode FACILITY_ID_INVALID = new ApplicationErrorCode("LILO_RESM", 9019,
	            "Faclity id not provided or is incorrect");

	    public static final ApplicationErrorCode VOID_COMP_TICKETS_ONLY = new ApplicationErrorCode("LILO_RESM", 9020,
	            "Only Complimentary tickets can be voided");

	    public static final ApplicationErrorCode TICKET_SERVICE_ENTITLEMENT_REQUEST_FAILED = new ApplicationErrorCode(
	            "LILO_RESM", 9022, "Could not get entitlements from Ticket Service");

	    public static final ApplicationErrorCode CREATE_KTTW_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9023,
	            "Could not fulfill create Kttw as the request list was invalid");

	    public static final ApplicationErrorCode TRAVEL_PLAN_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9024,
	            "Travel Plan Required");

	    public static final ApplicationErrorCode LOCATION_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9025,
	            "Location Id is required");

	    public static final ApplicationErrorCode SEARCH_KTTW_GUEST_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9026, "Search Kttw Guests Request is invalid");

	    public static final ApplicationErrorCode RETRIEVE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9027,
	            "Retrieve Request is invalid");

	    public static final ApplicationErrorCode CUT_KEY_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9028,
	            "GrantAccess request is invalid");

	    public static final ApplicationErrorCode NO_KTTW_FOUND = new ApplicationErrorCode("LILO_RESM", 9029,
	            "No Kttw available in Database");

	    public static final ApplicationErrorCode MODIFY_KTTW_ACCESS_RQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9031, "Modify Kttw Access Request is invalid");

	    public static final ApplicationErrorCode CHARGING_PRIVILEGE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9032, "Charging Privilage Request is invalid");

	    public static final ApplicationErrorCode MODIFY_CHARGING_PRIVILAGE_RQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9032, "Updating Privilage Request is invalid");

	    public static final ApplicationErrorCode UPGRADE_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9034, "Upgrade Ticket Request is invalid");

	    public static final ApplicationErrorCode TICKET_CODE_INVALID = new ApplicationErrorCode("LILO_RESM", 9035,
	            "Ticket Code Invalid");

	    public static final ApplicationErrorCode PARTY_ID_NOT_INVALID = new ApplicationErrorCode("LILO_RESM", 9036,
	            "Party Id invalid");

	    public static final ApplicationErrorCode ADD_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9036,
	            "Add ticket request invalid");

	    public static final ApplicationErrorCode LOCATION_ID_INVALID = new ApplicationErrorCode("LILO_RESM", 9037,
	            "Location Id invalid");

	    public static final ApplicationErrorCode BOOK_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9038,
	            "Book ticket request invalid");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_GROUPING_NOT_FOUND = new ApplicationErrorCode(
	            "LILO_RESM", 9039, "Travel Component Grouping not found");

	    public static final ApplicationErrorCode RETRIEVE_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9040, "Retrieve Ticket Detail Request invalid");

	    public static final ApplicationErrorCode INVALID_STATUS = new ApplicationErrorCode("LILO_RESM", 9041,
	            "Invalid Status");

	    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode("LILO_RESM", 9042,
	            "INVALID REQUEST !");

	    public static final ApplicationErrorCode DEACTIVATE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9043,
	            "Deactivate Request invalid");

	    public static final ApplicationErrorCode VOID_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9044,
	            "Void Ticket Request invalid");

	    public static final ApplicationErrorCode VOID_TICKET_RESPONSE_INVALID = new ApplicationErrorCode("LILO_RESM", 9045,
	            "Invalid Response Recieved from Ticket Service - Void Ticket");

	    public static final ApplicationErrorCode MODIFY_CHARGING_PRIVILAGE_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9046, "Modify Charging Privilage Request invalid");

	    public static final ApplicationErrorCode MODIFY_ACCESS_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9047, "Modify Access Request invalid");

	    public static final ApplicationErrorCode REFUND_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9048, "Refund Ticket Request Invalid");

	    public static final ApplicationErrorCode FULFILL_WILL_CALL_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9049, "Fullfill Will Call Reservation request invalid");

	    public static final ApplicationErrorCode SEARCH_TICKET_DETAILS_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9051, "Search Ticket Details request invalid");

	    public static final ApplicationErrorCode TRANSFER_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9053, "Transfer ticket request invalid");

	    public static final ApplicationErrorCode UPDATE_KTTW_TICKET_BANK_ENTITLEMENT_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9054, "Update KTTW Ticket Bank Entitlement request invalid"
	            );

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED = new ApplicationErrorCode("LILO_RESM", 9055,
	            "UPGRADE NOT ALLOWED !");

	    public static final ApplicationErrorCode REPLACE_KEY_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9056,
	            "Replace Key Request invalid");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_GUEST_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
	            9057, "Travel Component Guest not found");

	    public static final ApplicationErrorCode COULD_NOT_CREATE_KTTW = new ApplicationErrorCode("LILO_RESM", 9058,
	            "Could not create KTTW");

	    public static final ApplicationErrorCode SEARCH_BY_ENTITLEMENT_NUMBER_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9059, "Search by Entitlement Number Request invalid");

	    public static final ApplicationErrorCode RESORT_OR_ROOM_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9060,
	            "Resort and Room Type Required");

	    public static final ApplicationErrorCode TICKETING_SERVICE_ERROR = new ApplicationErrorCode("LILO_RESM", 9061,
	            "Ticketing Service Error");

	    public static final ApplicationErrorCode COULD_NOT_CUT_KEY = new ApplicationErrorCode("LILO_RESM", 9062,
	            "Key Cutting Service could not cut keys");

	    public static final ApplicationErrorCode COULD_NOT_REPLACE_TICKET_ON_TICKET_SERVICE = new ApplicationErrorCode(
	            "LILO_RESM", 9063, "Replace Ticket Failed");

	    public static final ApplicationErrorCode COMPLIMENTARY_TICKET_DETAIL_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9064, "Complimentary Ticket Detail invalid");

	    public static final ApplicationErrorCode PRE_ARIVAL_CHECK_IN_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9065, "Pre Arival Check-In request invalid");

	    public static final ApplicationErrorCode CANCEL_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9066, "Cancel Ticket request invalid");

	    public static final ApplicationErrorCode PHONE_NUMBER_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9068,
	            " Phone Number is required");

	    public static final ApplicationErrorCode CHARGE_TO_WHOLESALER_NOT_ALLOWED = new ApplicationErrorCode("LILO_RESM",
	            9069, " Charge to Wholesaler is not allowed for a Non Wholesaler package"
	            );

	    public static final ApplicationErrorCode CHARGE_TO_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9070,
	            "Charge to Guest or Wholesaler should be provided");

	    public static final ApplicationErrorCode UPGRADE_TO_WHOLESALER_NOT_ALLOWED_AFTER_GUEST = new ApplicationErrorCode(
	            "LILO_RESM", 9071, "Accommodation is Already upgraded to Charge To Guest. "
	                    + "Upgrade to Wholesaler is Not allowed after Upgrade to Guest");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_FOR_DVC = new ApplicationErrorCode("LILO_RESM", 9072,
	            "Upgrade Not Allowed for DVC Reservations");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_NOT_CHECKED_IN = new ApplicationErrorCode("LILO_RESM",
	            9073, "Accommodation Should be in CheckedIn Status");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_FIXED_TICKETS = new ApplicationErrorCode("LILO_RESM",
	            9074, "Accommodation has Fixed Tickets");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_TICKET_GROUP_DIFFERENT = new ApplicationErrorCode(
	            "LILO_RESM", 9075, "Ticket Group is Different");

	    public static final ApplicationErrorCode START_DATE_CHANGE = new ApplicationErrorCode("LILO_RESM", 9076,
	            "Start Date for the Accommodation can not be Changed");

	    public static final ApplicationErrorCode ACCOMMODATION_END_DATE_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            9077, "Accommodation End Date is Required");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_END_DATE_SAME = new ApplicationErrorCode("LILO_RESM",
	            9078, "End date requested is same as Accommodations End Date");

	    public static final ApplicationErrorCode AGR_TYPE_OR_AGE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9080,
	            "Age Type or Age for the Guest Should be provided");

	    public static final ApplicationErrorCode INVALID_GUEST_ID = new ApplicationErrorCode("LILO_RESM", 9081,
	            "Invalid GuestId for Deleting or changing Age Type of the Guest");

	    public static final ApplicationErrorCode GUEST_REFERENCE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9082,
	            "Guest Reference Should be Provided for upgrading Party Mix");

	    public static final ApplicationErrorCode CHANGE_ROOM_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9083,
	            "Change Room Request invalid");

	    public static final ApplicationErrorCode ACCOMMODATION_COMPONENT_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
	            9083, "Accommodation Component not found");

	    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATION_SERVICES_DETAILS_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9084, "Retrieve Accommodation Services Details Requests invalid"
	            );

	    public static final ApplicationErrorCode RETRIEVE_SERVICE_REQUESTS_BY_ROUTING_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9085, "Retrieve Service Requests By Routing Request is invalid"
	            );

	    public static final ApplicationErrorCode EMAIL_ADDRESS_REQUIRED = new ApplicationErrorCode("LILO_RESM", 9086,
	            "Email Address Required");

	    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATION_COUNT_BY_STATUS_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9087, "Retrieve Accommodation count by status request invalid"
	            );

	    public static final ApplicationErrorCode ADD_GUEST_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9088,
	            "Add Guest request invalid");

	    public static final ApplicationErrorCode RETRIEVE_ACCOMMODATIONS_FOR_AUTO_CHECKOUT_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9089, "Retrieve Accommodation for auto checkout request invalid"
	            );

	    public static final ApplicationErrorCode AUTO_CHECKOUT_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9089, "Auto Checkout Request invalid");

	    public static final ApplicationErrorCode SWAP_GUESTS_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9090,
	            "Swap Guests Request invalid");

	    // DO NOT CHANGE THE ERROR CODE FOR THIS ENTRY. UI is relying on this error
	    // code

	    public static final ApplicationErrorCode SEARCH_BY_ENTITLEMENT_NUMBER_FAILED = new ApplicationErrorCode(
	            "LILO_RESM", 9091, "Search Ticket by Entitlement Failed");

	    public static final ApplicationErrorCode PRINT_DAY_GUEST_RECEIPT_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 9092, "Print Day Guest Receipt Request Invalid");

	    public static final ApplicationErrorCode PRINT_COLLATERAL_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9093, "Print Request Invalid");

	    public static final ApplicationErrorCode PRINT_PACKAGE_COMPONENT_REPORT_FAILED = new ApplicationErrorCode(
	            "LILO_RESM", 9094, "Print Package Component Report failed");

	    public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 9095,
	            "Print Package Component Report failed");

	    public static final ApplicationErrorCode CREATE_ADMISSION_COMPONENT_FAILED = new ApplicationErrorCode("LILO_RESM",
	            9096, "Create Admission Component failed");

	    public static final ApplicationErrorCode PICKEDUP_PREARRIVAL_TICKETS = new ApplicationErrorCode("LILO_RESM", 9097,
	            "Pre arrival tickets have been picked up");

	    public static final ApplicationErrorCode TRAVELPLANSEGMENT_NOT_IN_TRAVELPLAN = new ApplicationErrorCode(
	            "LILO_RESM", 9098, "Travel plan segment does not belong to the travel plan in the request"
	            );

	    public static final ApplicationErrorCode UPGRADE_CHARGETOGUEST_WHOLESALER = new ApplicationErrorCode("LILO_RESM",
	            9099, "UpgradePartyMix as charge to guest not allowed for wholesale reservation"
	            );

	    public static final ApplicationErrorCode RSR_NOT_SET_FOR_BLOCK = new ApplicationErrorCode("LILO_RESM", 9100,
	            "The groups does not have the RSR Flag as true for the block");

	    public static final ApplicationErrorCode NO_CHARGE_NOT_ALLOWED = new ApplicationErrorCode("LILO_RESM", 9101,
	            "No Charge Not Allowed!");

	    public static final ApplicationErrorCode DELETE_TEMPLATE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9102, "Delete Template Request Invalid");

	    public static final ApplicationErrorCode UPGRADE_NOT_ALLOWED_FOR_UPGRADE_FOR_COUNT = new ApplicationErrorCode(
	            "LILO_RESM", 9103, "Accommodation is Upgraded For Counts. No Modification is allowed"
	            );

	    public static final ApplicationErrorCode TRAVEL_PLAN_OR_TRAVEL_COMPONENT_GUEST_NOT_FOUND = new ApplicationErrorCode(
	            "LILO_RESM", 9103, "No Travel Plan Party or Travel Component Guest found"
	            );

	    public static final ApplicationErrorCode TICKET_BANK_EMPTY = new ApplicationErrorCode("LILO_RESM", 9104,
	            "No Tickets in ticket bank");

	    public static final ApplicationErrorCode INVALID_RES_MODIFY_REQUEST = new ApplicationErrorCode("LILO_RESM", 9105,
	            "Invalid Modify Request");

	    /******************************************************************************
	     * * END - PMSR/LILO CHANGES *
	     ****************************************************************************** */

	    public static final ApplicationErrorCode UNABLE_TO_FREEZE = new ApplicationErrorCode("LILO_RESM", 7003,
	            " Unable to Freeze ");

	    public static final ApplicationErrorCode UNABLE_TO_THAW = new ApplicationErrorCode("LILO_RESM", 7004,
	            "Unable to Thaw Offer");

	    public static final ApplicationErrorCode INVALID_PARTYMIX = new ApplicationErrorCode("LILO_RESM", 7005,
	            "Invalid PartyMix. Please send valid partymix");

	    public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 7007,
	            "TRAVEL_PLAN_NOT_FOUND");

	    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
	            7008, "Travel Plan Segment Not Found");

	    public static final ApplicationErrorCode TRAVEL_AGENCY_NOT_VALID = new ApplicationErrorCode("LILO_RESM", 7013,
	            "Travel Agency is not valid");

	    public static final ApplicationErrorCode INVALID_TRAVEL_DATES = new ApplicationErrorCode("LILO_RESM", 7015,
	            "Travel Dates are invalid");

	    public static final ApplicationErrorCode TRAVEL_PLAN_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7017,
	            " Travel Plan ID is required ");

	    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7018,
	            " Travel Plan Segment is Required ");

	    public static final ApplicationErrorCode RESORT_PERIOD_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7020,
	            "Resort period is required");

	    public static final ApplicationErrorCode PACKAGE_CODE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7021,
	            "Package Code is required");

	    public static final ApplicationErrorCode ROOM_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7023,
	            "Room Type is required");

	    public static final ApplicationErrorCode FREEZE_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7025,
	            "Freeze Id is required");

	    public static final ApplicationErrorCode TRAVEL_PLAN_GUEST_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7026,
	            "Travel Plan Guest is required");

	    public static final ApplicationErrorCode GUEST_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7027,
	            "Guest is Required");

	    public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            7032, "Travel Plan Segment Id is required");

	    public static final ApplicationErrorCode AGE_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7035,
	            "Age Type is required");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7037,
	            "Travel Component Id is required");

	    public static final ApplicationErrorCode INAVLID_SELECTION = new ApplicationErrorCode("LILO_RESM", 7038,
	            "Invalid Selection is made");

	    public static final ApplicationErrorCode GROUPCODE_INVALID = new ApplicationErrorCode("LILO_RESM", 7041,
	            "Groupcode is invalid");

	    public static final ApplicationErrorCode RATE_OVERRIDE_FAILURE = new ApplicationErrorCode("LILO_RESM", 7048,
	            "Rate override failed");

	    public static final ApplicationErrorCode DATERANGE_INVALID = new ApplicationErrorCode("LILO_RESM", 7051,
	            "Date range is invalid");

	    public static final ApplicationErrorCode APPLICATION_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 7063,
	            "GENERAL ERROR");

	    public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new ApplicationErrorCode("LILO_RESM",
	            7064, "Required parameters are missing");

	    public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 7065,
	            "RECORD NOT FOUND");

	    public static final ApplicationErrorCode CANNOT_CANCEL_ACCOMMODATIONS_WITH_TICKETS = new ApplicationErrorCode(
	            "LILO_RESM", 7066, "Could not cancel accommodation with tickets");

	    public static final ApplicationErrorCode ALL_TICKETS_ARE_NOT_ASSIGNED = new ApplicationErrorCode("LILO_RESM", 7070,
	            "All tickets are not assigned");

	    public static final ApplicationErrorCode INCONSISTENT_DATA = new ApplicationErrorCode("LILO_RESM", 7072,
	            "Inconsitent Data");

	    public static final ApplicationErrorCode PRIMARY_GUEST_ID_INVALID = new ApplicationErrorCode("LILO_RESM", 7073,
	            "Primary Guest Id is invalid");

	    public static final ApplicationErrorCode INVALID_DISCOVERY_PREFERENCE = new ApplicationErrorCode("LILO_RESM", 7078,
	            "Invalid Discovery Preferences");

	    public static final ApplicationErrorCode CANNOT_CALCULATE_CANCEL_FEE = new ApplicationErrorCode("LILO_RESM", 7080,
	            "cannot calculate Cancel fee");

	    public static final ApplicationErrorCode INVALID_VALUE = new ApplicationErrorCode("LILO_RESM", 7082,
	            "INVALID VALUE");

	    public static final ApplicationErrorCode INVALID_EXTERNAL_REFERNCE_CODE = new ApplicationErrorCode("LILO_RESM",
	            7086, "Invalid External Reference Code");

	    public static final ApplicationErrorCode EXTERNAL_REFERENCE_NUMBER_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            7088, "External Reference is required");

	    public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 7090, "External Reference Source or Code required");

	    public static final ApplicationErrorCode INVALID_EXTERENAL_REFERENCE_DETAIL = new ApplicationErrorCode("LILO_RESM",
	            7092, "Invalid External Reference Details");

	    public static final ApplicationErrorCode SALES_CHANNEL_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7094,
	            "Sales Channel is required");

	    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            7096, "communication Channel is required");

	    public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY = new ApplicationErrorCode("LILO_RESM", 7102,
	            " Travel Agency is invalid ");

	    public static final ApplicationErrorCode INVALID_PACKAGE_CODE = new ApplicationErrorCode("LILO_RESM", 7104,
	            " Package Code is invalid ");

	    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode("LILO_RESM", 7105,
	            " Room Type is invalid ");

	    public static final ApplicationErrorCode INVALID_RESORT_CODE = new ApplicationErrorCode("LILO_RESM", 7106,
	            " Resort Code is invalid ");

	    public static final ApplicationErrorCode INVALID_RESORT_PERIOD = new ApplicationErrorCode("LILO_RESM", 7107,
	            " Resort Period is invalid ");

	    public static final ApplicationErrorCode BOOKING_SOURCE_DO_NOT_MATCH = new ApplicationErrorCode("LILO_RESM", 7109,
	            "Booking Source does not match");

	    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS = new ApplicationErrorCode("LILO_RESM", 7110,
	            " Travel Status is invalid ");

	    public static final ApplicationErrorCode CONFIRMATION_ROLE_NOTFOUND = new ApplicationErrorCode("LILO_RESM", 7111,
	            " Confirmation Role not found ");

	    public static final ApplicationErrorCode GROUP_NOT_COMMISSIONABLE = new ApplicationErrorCode("LILO_RESM", 7112,
	            " Group is not commissionable");

	    public static final ApplicationErrorCode CHARGE_ITEMS_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 7113,
	            " Charge Items are not found");

	    public static final ApplicationErrorCode CANNOT_OVERRIDE_BOXED_RESTRICTIONS = new ApplicationErrorCode("LILO_RESM",
	            7114, " Boxed restrictions cannot be overriden");

	    public static final ApplicationErrorCode INVALID_MAGIC_YOURWAY_TICKET_REQUEST = new ApplicationErrorCode(
	            "LILO_RESM", 7117, "Invalid Magic Your way ticket request");

	    public static final ApplicationErrorCode INVALID_TICKET_CODE = new ApplicationErrorCode("LILO_RESM", 7118,
	            "Ticket Code is invalid");

	    public static final ApplicationErrorCode INVALID_TICKET_GUEST_AGETYPE = new ApplicationErrorCode("LILO_RESM", 7119,
	            "Guest Age Type is invalid");

	    public static final ApplicationErrorCode INVALID_CODED_REMARKS_CODE = new ApplicationErrorCode("LILO_RESM", 7120,
	            "Coded Remarks Code is invalid");

	    public static final ApplicationErrorCode PACKAGE_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 7121,
	            "Package Not Found");

	    public static final ApplicationErrorCode INVALID_SEARCH_CRITERIA = new ApplicationErrorCode("LILO_RESM", 7124,
	            "Search Criteria is Invalid");

	    public static final ApplicationErrorCode BOOKING_SOURCE_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 7125,
	            "Booking Source not found");

	    public static final ApplicationErrorCode EXCEEDS_MAXIMUM_LIMIT = new ApplicationErrorCode("LILO_RESM", 7126,
	            "Exceeds Maximum Limit");

	    public static final ApplicationErrorCode AVAILABLITY_SERVICE_EXCEPTION = new ApplicationErrorCode("LILO_RESM",
	            7127, "Exception in Availability Service ");

	    public static final ApplicationErrorCode ACCOMMODATION_INVALID_FOR_SHARE = new ApplicationErrorCode("LILO_RESM",
	            7130, "Accommodation is invalid for Share");

	    public static final ApplicationErrorCode INVALID_TRAVEL_STATUS_FOR_SHARE = new ApplicationErrorCode("LILO_RESM",
	            7131, "Travel Status is invalid for Share");

	    public static final ApplicationErrorCode ACCOMMODATION_ALREADY_ADDED_FOR_SHARE = new ApplicationErrorCode(
	            "LILO_RESM", 7132, "Accommodation has already been added for Share");

	    public static final ApplicationErrorCode NO_MULTIPLE_NON_DISNEY_RESORTS_FOR_ROOMS = new ApplicationErrorCode(
	            "LILO_RESM", 7133, "No multiple Non Disney Resorts for rooms");

	    public static final ApplicationErrorCode NO_KTTW_WITH_NON_DISNEY = new ApplicationErrorCode("LILO_RESM", 7134,
	            "No KTTW with Non Disney");

	    // Changing the error message as per the QC Defect : 5155 & 4384

	    public static final ApplicationErrorCode ACCOMMODATION_PERIOD_NOT_OVERLAP = new ApplicationErrorCode("LILO_RESM",
	            7135, "Accommodation can not be shared - dates must overlap at least one night"
	            );

	    public static final ApplicationErrorCode PROPERTY_SHOULD_BE_DISNEY = new ApplicationErrorCode("LILO_RESM", 7136,
	            "Property should be Disney");

	    // Changing the error message as per the QC Defect : 4692

	    public static final ApplicationErrorCode INVALID_PARTY_MIX_FOR_SHARE = new ApplicationErrorCode("LILO_RESM", 7137,
	            "Invalid Party Mix.  Cannot Create Share With Accommodation");

	    public static final ApplicationErrorCode OVERRIDE_RATE_RACK_RATE_ERROR = new ApplicationErrorCode("LILO_RESM",
	            7138, "Override rate cannot be more than rack rate.");

	    public static final ApplicationErrorCode INVALID_SHARE_ACTION = new ApplicationErrorCode("LILO_RESM", 7139,
	            "Action has to be Share");

	    public static final ApplicationErrorCode INVALID_SHARE_DATA = new ApplicationErrorCode("LILO_RESM", 7140,
	            "One Property Reservation cannot have multiple Accommodations which are shared"
	            );

	    public static final ApplicationErrorCode NO_MIX_OF_NONDISNEY_AND_DISNEY_RESORTS = new ApplicationErrorCode(
	            "LILO_RESM", 7141, "Disney and Non Disney Resorts cannot be mixed");

	    public static final ApplicationErrorCode TRAVEL_AGENCY_NOT_ACTIVE = new ApplicationErrorCode("LILO_RESM", 7142,
	            " Travel Agency is not active ");

	    public static final ApplicationErrorCode INVALID_CHANGE_REASON = new ApplicationErrorCode("LILO_RESM", 7146,
	            " Change Reason is invalid ");

	    public static final ApplicationErrorCode INVALID_DATES = new ApplicationErrorCode("LILO_RESM", 7149,
	            "Dates are invalid");

	    public static final ApplicationErrorCode MOD_MORE_THAN_ONE_SHARE_ACCOMM = new ApplicationErrorCode("LILO_RESM",
	            7150, "Cannot have more than one shared accommodation for Modification");

	    public static final ApplicationErrorCode MORE_THAN_ONE_ACCOMM = new ApplicationErrorCode("LILO_RESM", 7151,
	            "Only one Room Type should be selected for shared room modification!!");

	    public static final ApplicationErrorCode TICKETS_GUEST_MISMATCH = new ApplicationErrorCode("LILO_RESM", 7152,
	            "Ticket Guests do not match ");

	    public static final ApplicationErrorCode TICKETS_CANT_ASSIGNED_INFANTS = new ApplicationErrorCode("LILO_RESM",
	            7155, "Infants cannot be assigned tickets");

	    public static final ApplicationErrorCode NOT_VALID_GATHERING_DETAIL = new ApplicationErrorCode("LILO_RESM", 7156,
	            "Gathering Detail is not valid");

	    public static final ApplicationErrorCode CANNOT_ASSIGN_MULTIPLE_TICKETS_TO_SAME_PERSON = new ApplicationErrorCode(
	            "LILO_RESM", 7157, "Mutliple Tickets cannot be assigned to the same person"
	            );

	    public static final ApplicationErrorCode DVC_GROUP_NOT_ALLOWED = new ApplicationErrorCode("LILO_RESM", 7158,
	            "Please use DVC Wishes UI to book against a DVC Group");

	    public static final ApplicationErrorCode TRAVEL_PLAN_MISMATCH = new ApplicationErrorCode("LILO_RESM", 7162,
	            "Travel Plan Does not match");

	    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS = new ApplicationErrorCode("LILO_RESM",
	            7163, "Invalid status for modification");

	    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_CHECKED_IN = new ApplicationErrorCode(
	            "LILO_RESM", 7164, "Charge Group status cannot be changed to Booked, "
	                    + "Cancelled or Auto Cancelled from Checked In");

	    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_PAST_VISIT = new ApplicationErrorCode(
	            "LILO_RESM", 7165, "Charge Group status cannot be changed to Booked, "
	                    + "Cancelled or Auto Cancelled from Past Visit");

	    public static final ApplicationErrorCode INVALID_MODIFICATION_TRAVEL_STATUS_CANCELLED = new ApplicationErrorCode(
	            "LILO_RESM", 7166, "Charge Group cannot be modified from Cancelled Status"
	            );

	    public static final ApplicationErrorCode NO_PACKAGE_FOUND_FOR_GROUP = new ApplicationErrorCode("LILO_RESM", 7168,
	            "No Packages found for group code entered");

	    public static final ApplicationErrorCode INVALID_PACKAGE_FOR_BLOCK = new ApplicationErrorCode("LILO_RESM", 7169,
	            "Package is invalid for the block");

	    public static final ApplicationErrorCode PACKAGE_CANNOT_BE_SOLD = new ApplicationErrorCode("LILO_RESM", 7170,
	            "Package cannot be sold");

	    public static final ApplicationErrorCode PACKAGE_RATE_CATEGORY_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
	            7173, " Package Rate Category not found");

	    public static final ApplicationErrorCode MULTIPLE_ACCOM_ERROR_SHARE = new ApplicationErrorCode("LILO_RESM", 7175,
	            " Cannot have multiple accommodations for a External reference for share "
	            );

	    public static final ApplicationErrorCode INVALID_TC_ID = new ApplicationErrorCode("LILO_RESM", 7176,
	            " Invalid Travel Component Id.");

	    public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new ApplicationErrorCode("LILO_RESM", 7177,
	            " No Accommodation Component found.");

	    public static final ApplicationErrorCode GUEST_LIST_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM", 7182,
	            "Guest list cannot be empty.");

	    public static final ApplicationErrorCode TRAVEL_PLAN_SEARCH_NO_RESULT = new ApplicationErrorCode("LILO_RESM", 7183,
	            "No travel plan data found.");

	    public static final ApplicationErrorCode SHARE_ACCOMMODATION_SEARCH_NO_RESULT = new ApplicationErrorCode(
	            "LILO_RESM", 7184, "No accommodation data found.");

	    public static final ApplicationErrorCode CANNOT_MODIFY_CHECKEDIN_ACCOMMADATION = new ApplicationErrorCode(
	            "LILO_RESM", 7187, "Checked In Accommodation cannot be modified");

	    public static final ApplicationErrorCode NO_ROOM_PRICES_FROM_PRICING = new ApplicationErrorCode("LILO_RESM", 7188,
	            "No Room Prices returned from Pricing");

	    public static final ApplicationErrorCode TIME_OUT_FOR_ROOM_INV_DECREMENT_RESPONSE = new ApplicationErrorCode(
	            "LILO_RESM", 7189, " Timed out for RoomInventoryDecrement response");

	    public static final ApplicationErrorCode VALIDATE_FREEZE_FAILED = new ApplicationErrorCode("LILO_RESM", 7190,
	            " Validate freeze failed");

	    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_CANCELLED = new ApplicationErrorCode(
	            "LILO_RESM", 7192, " Accommodation should be in Booked status to be cancelled"
	            );

	    public static final ApplicationErrorCode ACCOMMODATIONS_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 7192,
	            "Accommodations not found");

	    public static final ApplicationErrorCode INVALID_TRAVELPLAN_ID_RECIEVED_FROM_SIEBEL = new ApplicationErrorCode(
	            "LILO_RESM", 7193, " Invalid Travel Plan Id received from SIEBEL");

	    public static final ApplicationErrorCode GUARANTEE_STATUS_CAN_NOT_BE_CHANGED = new ApplicationErrorCode(
	            "LILO_RESM", 7195, " Guarantee status can not be changed ");

	    public static final ApplicationErrorCode GROUPS_CONFIRMATION_DLVRY_METHD_NULL = new ApplicationErrorCode(
	            "LILO_RESM", 7197, "Groups Confirmation Delivery method cannot be null."
	            );

	    public static final ApplicationErrorCode KTTW_TICKET_NEEDS_TO_BE_REMOVED = new ApplicationErrorCode("LILO_RESM",
	            7198, "Guest Cannot be changed without cancelling the Key to the World Ticket."
	            );

	    public static final ApplicationErrorCode INVENTORY_REQUEST_IS_NULL = new ApplicationErrorCode("LILO_RESM", 7201,
	            "inventory request null.");

	    public static final ApplicationErrorCode CANT_CHANGE_BLOCK_RESORT_PACKAGE_FOR_SHARED_ACCOMMODATION = new ApplicationErrorCode(
	            "LILO_RESM", 7208, "Cannot change Block/Resort/Package for an shared Accommodation."
	            );

	    public static final ApplicationErrorCode MULTIPLE_PACKAGES_SELECTED = new ApplicationErrorCode("LILO_RESM", 7209,
	            "Cannot select more than one package for shared room modification!!");

	    public static final ApplicationErrorCode CHECKED_OUT_ATTEMPTED_ON_CANCEL_AUTOCANCELLED_PAST_VIST_ACCOM = new ApplicationErrorCode(
	            "LILO_RESM", 7210, "Check out is attempted on accommodation which is Cancelled "
	                    + "or Auto Cancelled or Past Visit!!");

	    public static final ApplicationErrorCode CONFIRMATION_DEST_CANT_BE_NULL = new ApplicationErrorCode("LILO_RESM",
	            7211, "Confirmation Destination has to be present on the Group!!");

	    public static final ApplicationErrorCode CHECKED_OUT_ATTEMPTED_ON_BOOKED_ACCOM = new ApplicationErrorCode(
	            "LILO_RESM", 7212, "Check out is attempted on accommodation which is in Booked Status!!"
	            );

	    public static final ApplicationErrorCode INTERNAL_RESERVATIONS_CANNOT_HAVE_EXTERNAL_REF_NUMBERS = new ApplicationErrorCode(
	            "LILO_RESM", 7214, "External Reference Numbers not allowed For Internal Bookings !!"
	            );

	    public static final ApplicationErrorCode DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST = new ApplicationErrorCode(
	            "LILO_RESM", 7216, "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!");

	    public static final ApplicationErrorCode LINK_RESERVATIONS_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            7218, "Link reservation request invalid");

	    public static final ApplicationErrorCode LINK_RESERVATIONS_TRAVELPERIOD_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 7220, "Travel plan periods must be continuous for linking reservations"
	            );

	    // Error code introduced for QC defect fix : 3355

	    public static final ApplicationErrorCode CHECKEDIN_ACCOMMODATION_ARRIVALPERIOD_MODIFY = new ApplicationErrorCode(
	            "LILO_RESM", 7222,
	            "Arrival Date can not be changed after reservation has been placed in a Reservation Status of "
	                    + "Checking In, Checked In, In-House, Deposit Forfeiture and Evacuation."
	            );

	    public static final ApplicationErrorCode ALL_TICKETS_ARE_NOT_ASSIGNED_TO_GUESTS = new ApplicationErrorCode(
	            "LILO_RESM", 7223, "All Tickets are not assigned to Guests");

	    // Defect 5611 : RSR reservations can not be reinstated.

	    public static final ApplicationErrorCode REINSTATE_NOT_ALLOWED_FOR_RSR_PACKAGE = new ApplicationErrorCode(
	            "LILO_RESM", 7224, "This reservation is not eligible to be Re-instated. "
	                    + "Please contact the appropriate Reservation Office, " + "Operations Support "
	                    + "or Manager for assistance");

	    // Defect 7518 : post check in for DCL reservation Age type of the guest can
	    // not be changed

	    public static final ApplicationErrorCode AGE_TYPE_CHANGE_NOT_ALLOWED_FOR_DCL = new ApplicationErrorCode(
	            "LILO_RESM", 7225, "Age Type change is not allowed for DCL Reservation "
	            );

	    public static final ApplicationErrorCode MODIFY_NOT_ALLOWED_STATUS_CHECKIN = new ApplicationErrorCode("LILO_RESM",
	            7226, "Reseravtion can not be Modified if Travel Plan is in checked in status "
	            );

	    // Defect 7356 : post check in for DCL reservation Guest Can not be added

	    public static final ApplicationErrorCode ADD_GUEST_NOT_ALLOWED_FOR_DCL = new ApplicationErrorCode("LILO_RESM",
	            7227, "Adding Guest is not allowed for DCL Reservation ");

	    public static final ApplicationErrorCode ROOM_DETAIL_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7228,
	            "There should be at least one Room Detail");

	    // Defect 8107 : DVC reservations can not be reinstated.

	    public static final ApplicationErrorCode REINSTATE_NOT_ALLOWED_FOR_DVC = new ApplicationErrorCode("LILO_RESM",
	            7229, "This reservation is not eligible to be Re-instated. "
	                    + "Please contact the appropriate Reservation Office, "
	                    + "Operations Support or Manager for assistance");

	    // Defect 12227 : Add validation for Sales Channel

	    public static final ApplicationErrorCode SALES_CHANNEL_NOT_EXISTS_FOR_BLOCK = new ApplicationErrorCode("LILO_RESM",
	            7230, "Sales Channel are not available for the block");

	    // Defect 15617

	    public static final ApplicationErrorCode CANT_CHANGE_ROOM_TYPE_FOR_SHARED_ACCOMMODATION = new ApplicationErrorCode(
	            "LILO_RESM", 7231, "Cannot change Room type for an shared Accommodation."
	            );
	    // Defect 15617

	    public static final ApplicationErrorCode REINSTATE_VALIDATION_FOR_DATE = new ApplicationErrorCode("LILO_RESM",
	            7232, "The arrival date should be current system " + " date or future date up to 7 days"
	            );

	    public static final ApplicationErrorCode RESERVATION_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7233,
	            "Reservation Type is Required");

	    /**
	     * *************************************************************************
	     * **** * START - SE CHANGES *
	     * **********************************************
	     * ********************************
	     */
	    public static final ApplicationErrorCode PRODUCT_DETAILS_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM",
	            8006, "PRODUCT DETAILS CANNOT BE EMPTY!");

	    public static final ApplicationErrorCode NO_RESERVABLE_RESOURCE_ID = new ApplicationErrorCode("LILO_RESM", 8010,
	            "RESERVABLE RESOURCE ID IS REQUIRED!");

	    public static final ApplicationErrorCode INVALID_FACILITY = new ApplicationErrorCode("LILO_RESM", 8011,
	            "FACILITY ID/NAME IS REQUIRED!");

	    public static final ApplicationErrorCode INVALID_TAX_EXEMPT = new ApplicationErrorCode("LILO_RESM", 8012,
	            "INVALID TAX EXEMPT DETAIL!");

	    public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            8013, "SOURCER ACCOUNTING CENTER IS REQUIRED!");

	    public static final ApplicationErrorCode INVALID_INTERNAL_COMMENT = new ApplicationErrorCode("LILO_RESM", 8014,
	            "COMMENT TEXT AND TYPE ARE REQUIRED !");

	    public static final ApplicationErrorCode INVALID_MEMBERSFIP = new ApplicationErrorCode("LILO_RESM", 8015,
	            "INVALID MEMBERSHIP/AFFILIATION !");

	    public static final ApplicationErrorCode COMPONENT_PRICE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8016,
	            "COMPONENT PRICE IS REQUIRED!");

	    public static final ApplicationErrorCode SERVICE_PERIOD_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8017,
	            "ENTERPRISE SERVICE PERIOD ID IS REQUIRED.!");

	    public static final ApplicationErrorCode AGE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8018,
	            "AGE IS REQUIRED!");

	    public static final ApplicationErrorCode GUEST_REQUEST_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8019,
	            "GUEST REQUEST ID IS REQUIRED!");

	    public static final ApplicationErrorCode MORE_THAN_ONE_AFFILIATION_FOUND = new ApplicationErrorCode("LILO_RESM",
	            8020, "MORE_THAN_ONE_AFFILIATION_FOUND!");

	    public static final ApplicationErrorCode INVALID_REASON = new ApplicationErrorCode("LILO_RESM", 8021,
	            "INVALID REASON ID!");

	    public static final ApplicationErrorCode TKT_PRNT_ONLY_FOR_DINNERSHOW = new ApplicationErrorCode("LILO_RESM", 8022,
	            "TICKET CAN BE PRINTED/REPRINTED ONLY FOR A DINNER SHOW !");

	    public static final ApplicationErrorCode DINNERSHOW_TKT_PRINTED = new ApplicationErrorCode("LILO_RESM", 8023,
	            "TICKET IS ALREADY PRINTED.PLEASE USE REPRINT !");

	    public static final ApplicationErrorCode DINNERSHOW_TKT_NOT_PRINTED = new ApplicationErrorCode("LILO_RESM", 8024,
	            "TICKET IS NOT PRINTED.PLEASE USE PRINT !");

	    public static final ApplicationErrorCode DINNERSHOW_PKG_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 8025,
	            "DINNER SHOW PACKAGE NOT FOUND !");

	    public static final ApplicationErrorCode TABLE_NUMBERS_ASSIGNED = new ApplicationErrorCode("LILO_RESM", 8026,
	            "TABLE NUMBERS ASSIGNED !");

	    public static final ApplicationErrorCode INVALID_INVENTORY = new ApplicationErrorCode("LILO_RESM", 8027,
	            "INVALID GOT/WANT INVENTORY!");

	    public static final ApplicationErrorCode INVALID_LOS_OR_DURATION = new ApplicationErrorCode("LILO_RESM", 8028,
	            "INVALID DURATION/UNIT MEASURE COUNT!");

	    public static final ApplicationErrorCode SERVICE_START_DATE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8029,
	            "INVALID  SERVICE START DATE!!");

	    public static final ApplicationErrorCode INVALID_FACILITY_ID = new ApplicationErrorCode("LILO_RESM", 8030,
	            "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!!");

	    public static final ApplicationErrorCode PRODUCT_TYPE_NAME_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8031,
	            "PRODUCT TYPE NAME IS REQUIRED!!");

	    public static final ApplicationErrorCode INVALID_PRODUCT_TYPE_NAME = new ApplicationErrorCode("LILO_RESM", 8032,
	            "INVALID PRODUCT TYPE !!");

	    public static final ApplicationErrorCode PAYMENT_DETAILS_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM",
	            8034, "PAYMENT DETAILS CANNOT BE EMPTY!");

	    public static final ApplicationErrorCode SIZE_OF_PAYMENT_DETAILS_EXCEEDED = new ApplicationErrorCode("LILO_RESM",
	            8035, "NO MORE THAN THREE PAYMENTS CAN BE ACCEPTED!");

	    public static final ApplicationErrorCode ADMISSION_PRODUCTS_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM",
	            8036, "ADMISSION PRODUCTS CANNOT BE EMPTY!");

	    public static final ApplicationErrorCode INVALID_AUTHORIZATION_CODE = new ApplicationErrorCode("LILO_RESM", 8038,
	            "INVALID AUTHORIZATION CODE !!");

	    public static final ApplicationErrorCode WORK_RULES_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM", 8040,
	            "WORK RULES CANNOT BE EMPTY !!");

	    public static final ApplicationErrorCode HEADER_DATA_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM", 8041,
	            "HEADER DATA CANNOT BE NULL OR EMPTY !!");

	    public static final ApplicationErrorCode REFERENCE_NUMBER_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8042,
	            "REFERENCE NUMBER IS REQUIRED !!");

	    public static final ApplicationErrorCode REQUEST_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8043,
	            "REQUEST TYPE IS REQUIRED !!");

	    public static final ApplicationErrorCode REQUEST_SUB_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8044,
	            "REQUEST SUBTYPE IS REQUIRED !!");

	    public static final ApplicationErrorCode OPERATING_AREA_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8045,
	            "OPERATING AREA IS REQUIRED !!");

	    public static final ApplicationErrorCode USERID_PASSWORD_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8046,
	            "USERID AND PASSWORD ARE REQUIRED  !!");

	    public static final ApplicationErrorCode COMMAND_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8047,
	            "COMMAND TYPE REQUIRED  !!");

	    public static final ApplicationErrorCode CLIENT_TYPE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8048,
	            "CLIENT TYPE REQUIRED  !!");

	    public static final ApplicationErrorCode SHOWS_CANNOT_BE_EMPTY = new ApplicationErrorCode("LILO_RESM", 8049,
	            "SHOWS CANNOT BE EMPTY !!");

	    public static final ApplicationErrorCode GROUP_CODE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8050,
	            "SHOWS CANNOT BE EMPTY !!");

	    public static final ApplicationErrorCode PERFORMANCE_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8051,
	            "PERFORMANCE ID IN SHOW IS REQUIRED !!");

	    public static final ApplicationErrorCode SHOWDATE_SHOWCODE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8052,
	            "SHOW DATE AND SHOW CODE IN SHOW ARE REQUIRED !!");

	    public static final ApplicationErrorCode PRODUCT_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8053,
	            "PRODUCT ID IS REQUIRED !!");

	    public static final ApplicationErrorCode REVENUE_CLASSFICATION_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            8054, "COMPONENTPRICE.REVENUE CLASSFICATION IS REQUIRED!!");

	    public static final ApplicationErrorCode REVENUE_TYPE_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8055,
	            "UNITPRICE.BASECHARGE.REVENUE TYPE IS REQUIRED!!");

	    public static final ApplicationErrorCode PRICING_PARTY_MIX_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8056,
	            "PRICING PARTY MIX IS REQUIRED!!");

	    public static final ApplicationErrorCode COMPONENT_TYPE_NAME_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            8057, "COMPONENT TYPE NAME IS REQUIRED!!");

	    public static final ApplicationErrorCode UNIT_PRICES_ARE_EMPTY = new ApplicationErrorCode("LILO_RESM", 8058,
	            "COMPONENTPRICE.UNITPRICES ARE REQUIRED!!");

	    public static final ApplicationErrorCode BASE_CHARGE_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8059,
	            "UNITPRICE.BASE CHARGE IS REQUIRED!!");

	    public static final ApplicationErrorCode SALES_TYPE_INVALID = new ApplicationErrorCode("LILO_RESM", 8060,
	            "SALES TYPE IN RESERVATION  DATA  IS INVALID !!");

	    public static final ApplicationErrorCode ACCOMMODATION_NOT_IN_BOOKED_OR_NOT_ARRIVED_STATUS = new ApplicationErrorCode(
	            "LILO_RESM", 8062, "Accommodation Status is not in Booked or not arrived or checkingIn"
	            );

	    public static final ApplicationErrorCode ADMISSION_NOT_IN_BOOKED_STATUS = new ApplicationErrorCode("LILO_RESM",
	            8063, "Admission Status is not in Booked");

	    public static final ApplicationErrorCode TRAVEL_PLAN_GROUP_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8064,
	            "Travel Plan Group is required");

	    public static final ApplicationErrorCode CANCEL_TICKET_TO_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8065,
	            "Cancel Ticket TO is required");

	    /**
	     * Start - Error Codes for Group Enhancement from 8066 -- 8100
	     */
	    public static final ApplicationErrorCode BILL_CODE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8066,
	            "Bill Code is required");

	    public static final ApplicationErrorCode BILL_CODE_START_DATE_REQUIRED = new ApplicationErrorCode("LILO_RESM",
	            8067, "Bill Code start Date is required");

	    public static final ApplicationErrorCode BILL_CODE_END_DATE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8068,
	            "Bill Code emd Date is required");

	    public static final ApplicationErrorCode BILL_CODE_END_DATE_BEFORE_START = new ApplicationErrorCode("LILO_RESM",
	            8068, "Bill Code end date is before start Date");

	    public static final ApplicationErrorCode PROCESS_NAME_NULL = new ApplicationErrorCode("LILO_RESM", 8069,
	            "Process Name cannot be null");
	    /*
	     * End Error Codes for Group Enhancement
	     */

	    public static final ApplicationErrorCode DELIVER_GUEST_MESSAGE = new ApplicationErrorCode("LILO_RESM", 8070,
	            "The request is invalid! No or null message Ids found!");

	    public static final ApplicationErrorCode ADD_UPDATE_FEATURE = new ApplicationErrorCode("LILO_RESM", 8071,
	            "Error in Creating Features. Travel Plan Not Found");

	    public static final ApplicationErrorCode ADD_UPDATE_ALERT = new ApplicationErrorCode("LILO_RESM", 8072,
	            "Request is invalid for updateAlert");

	    public static final ApplicationErrorCode UPDATE_GUEST_MESSAGES = new ApplicationErrorCode("LILO_RESM", 8074,
	            "The updateGuestMessage request is invalid");

	    public static final ApplicationErrorCode REMOVE_GUEST_MESSAGES = new ApplicationErrorCode("LILO_RESM", 8075,
	            "The Remove GuestMessage request is invalid");

	    public static final ApplicationErrorCode RETRIEVE_GUEST_HISTORY = new ApplicationErrorCode("LILO_RESM", 8076,
	            "Invalid request for Retrieve Guest History");

	    public static final ApplicationErrorCode ROOM_NUMBER_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8077,
	            "Room Number is Required");

	    public static final ApplicationErrorCode CREATE_COMMENTS = new ApplicationErrorCode("LILO_RESM", 8078,
	            "Create Comments Request Invalid");

	    public static final ApplicationErrorCode CREATE_GROUP_TEAM = new ApplicationErrorCode("LILO_RESM", 8079,
	            "Error while trying create GroupTeam");

	    public static final ApplicationErrorCode FIND_TEAMS_BY_GROUP_CODE = new ApplicationErrorCode("LILO_RESM", 8080,
	            "Invalid Group Code");

	    public static final ApplicationErrorCode DELETE_TEAM_NAME = new ApplicationErrorCode("LILO_RESM", 8081,
	            "Invalid Delete Team Name Request");

	    public static final ApplicationErrorCode GET_BOOKING_HISTORY = new ApplicationErrorCode("LILO_RESM", 8082,
	            "Invalid Retrieve Booking History Request");

	    /******************************************************************************
	     * * START - SERVICE REQUEST CHANGES *
	     ****************************************************************************** */
	    public static final ApplicationErrorCode TRAVEL_COMPONENT_ID_MISSING = new ApplicationErrorCode("LILO_RESM", 8084,
	            "Travel component Id is missing");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 8085,
	            "Travel Component not found for travel component Id");

	    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DETAIL_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8086, "Bell service request detail is missing");

	    public static final ApplicationErrorCode SERVICE_REQUEST_DETAIL_MISSING = new ApplicationErrorCode("LILO_RESM",
	            8087, "Service request detail is missing");

	    public static final ApplicationErrorCode PROFILEID_MISSING = new ApplicationErrorCode("LILO_RESM", 8088,
	            "Service request detail has profile Id missing");

	    public static final ApplicationErrorCode NO_PROFILE_FOUND = new ApplicationErrorCode("LILO_RESM", 8089,
	            "No profile found for profile Id");

	    public static final ApplicationErrorCode SERVICE_REQUEST_RECURRENCE_ERROR = new ApplicationErrorCode("LILO_RESM",
	            8090, "No service requests created as per the recurrence set");

	    public static final ApplicationErrorCode SERVICE_REQUEST_MODIFY_ERROR = new ApplicationErrorCode("LILO_RESM", 8091,
	            "At least one service request has been fulfilled or cancelled, this service request cannot be modified"
	            );

	    public static final ApplicationErrorCode NO_TC_SERVICE_REQUEST_FOUND = new ApplicationErrorCode("LILO_RESM", 8092,
	            "No travel component service request found for travel component service request Id"	            );

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_SERVICE_REQUEST_ID_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8093, "Travel component service request Id is missing");

	    public static final ApplicationErrorCode INVALID_RETRIEVE_SERVICE_REQUESTS_INPUT = new ApplicationErrorCode(
	            "LILO_RESM", 8094, "Either travel component service request Id or travel component Id should be set"
	            );

	    public static final ApplicationErrorCode LATE_CHECK_OUT_REQUEST_DETAIL_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8095, "Late check out request detail is missing");

	    public static final ApplicationErrorCode NOT_A_LATE_CHECK_OUT = new ApplicationErrorCode("LILO_RESM", 8096,
	            "Requested late check out date is before the scheduled travel period end date; "
	                    + "this is not a late check out");

	    public static final ApplicationErrorCode LATE_CHECK_OUT_DATE_INVALID = new ApplicationErrorCode("LILO_RESM", 8097,
	            "Requested late check out date should not be after the scheduled travel period end date"
	            );

	    public static final ApplicationErrorCode REQUESTED_DATE_MISSING = new ApplicationErrorCode("LILO_RESM", 8098,
	            "Requested date not provided");

	    public static final ApplicationErrorCode UPDATE_LATE_CHECK_OUT_REQUEST_LIST_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8099, "No late check out request list provided for status update"
	            );

	    public static final ApplicationErrorCode STATUS_CHANGE_TO_FULFILLED_NOT_SUPPORTED = new ApplicationErrorCode(
	            "LILO_RESM", 8099, "New status is set to fulfilled; "	                    + "fulfillment of late check out service request is not supported by this method; "
	                    + "travelComponentServiceRequestId");

	    public static final ApplicationErrorCode CANCELLED_REQUEST_STATUS_CHANGE_NOT_ALLOWED = new ApplicationErrorCode(
	            "LILO_RESM", 8100, "This late check out request has been cancelled, its status cannot be changed now; "
	                    + "travelComponentServiceRequestId ");

	    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_PENDING_NOT_ALLOWED = new ApplicationErrorCode(
	            "LILO_RESM", 8101,
	            "The status of a late check out request can be set to Pending by the system and not by an user; "
	                    + "travelComponentServiceRequestId");

	    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_APPROVED_TO_DENIED_NOT_ALLOWED = new ApplicationErrorCode(	            "LILO_RESM", 8102, "The status of a late check out request can not be changed from Approved to Denied;"
	            );

	    public static final ApplicationErrorCode REQUEST_STATUS_CHANGE_DENIED_TO_CANCELLED_NOT_ALLOWED = new ApplicationErrorCode(
	            "LILO_RESM", 8103, "The status of a late check out request can not be changed from Denied to Cancelled; "
	                    + "travelComponentServiceRequestId");

	    public static final ApplicationErrorCode NO_SERVICE_TYPE_PROFILE_FOUND = new ApplicationErrorCode("LILO_RESM",
	            8104, "Profile is not of type service, profile Id");

	    public static final ApplicationErrorCode SERVICE_REQUEST_NOT_PRICED = new ApplicationErrorCode("LILO_RESM", 8105,
	            "The service request could not be priced successfully");

	    public static final ApplicationErrorCode TRAVEL_COMPONENT_FOR_CHARGING_SERVICE_REQUEST_NOT_CREATED = new ApplicationErrorCode(
	            "LILO_RESM", 8106, "Travel component for service request could not be created"
	            );

	    public static final ApplicationErrorCode CHARGE_REQUESTS_FOR_SERVICE_REQUEST_NOT_CREATED = new ApplicationErrorCode(
	            "LILO_RESM", 8107, "Charge requests for the service request could not be created"
	            );

	    public static final ApplicationErrorCode FULFILL_SERVICE_REQUEST_INPUT_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8108, "FulfillServiceRequestInput is missing");

	    public static final ApplicationErrorCode SERVICE_REQUEST_TYPE_MISSING = new ApplicationErrorCode("LILO_RESM", 8109,
	            "Service request type is missing");

	    public static final ApplicationErrorCode SERVICE_REQUEST_TYPE_INVALID = new ApplicationErrorCode("LILO_RESM", 8111,
	            "Only service requests and bell service requests can be fulfilled via this method; service request type"
	            );

	    public static final ApplicationErrorCode NO_SERVICE_REQUEST_FOUND = new ApplicationErrorCode("LILO_RESM", 8112,
	            "No service request found for service request Id");

	    public static final ApplicationErrorCode SERVICE_REQUEST_DATE_INVALID = new ApplicationErrorCode("LILO_RESM", 8113,
	            "Service request date / recurrence start date should be current date or a future date"
	            );

	    public static final ApplicationErrorCode SERVICE_REQUEST_START_DATE_INVALID = new ApplicationErrorCode("LILO_RESM",
	            8114,
	            "Service request date / recurrence start date should not be after the scheduled travel period end date"
	            );

	    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DATE_BEFORE_START = new ApplicationErrorCode(
	            "LILO_RESM", 8115, "Bell service request date should not be before travel period start date"
	            );

	    public static final ApplicationErrorCode BELL_SERVICE_REQUEST_DATE_AFTER_END = new ApplicationErrorCode(
	            "LILO_RESM", 8116, "Bell service request date should not be after 5 days of travel period end date"
	            );

	    public static final ApplicationErrorCode SERVICE_ID_CONVERSION_ERROR = new ApplicationErrorCode("LILO_RESM", 8117,
	            "Error while converting serviceId");

	    public static final ApplicationErrorCode LATE_CHECK_OUT_ALREADY_REQUESTED = new ApplicationErrorCode("LILO_RESM",
	            8018, "Late check out has already been requested for this accommodation component"
	            );

	    public static final ApplicationErrorCode DAYS_UPFRONT_TO_LATE_CHECK_OUT_NOT_SET_UP_IN_RIM = new ApplicationErrorCode(
	            "LILO_RESM", 8400, "The number of days prior to guest arrival that a late check out can be requested, "
	                    + "has not be configured in Room Inventory; facilityId - ");

	    public static final ApplicationErrorCode LATE_CHECK_OUT_REQUESTED_TOO_SOON = new ApplicationErrorCode("LILO_RESM",
	            8401, "Late checkout can be requested only a set number days prior to guest arrival - "
	            );

	    public static final ApplicationErrorCode HOUSEKEEPING_SECTION_DETAILS_NOT_FOUND = new ApplicationErrorCode(
	            "LILO_RESM", 8402, "Housekeeping section details could not be found");

	    public static final ApplicationErrorCode RECURRENCE_START_DATE_MISSING = new ApplicationErrorCode("LILO_RESM",
	            8403, "Recurrence start date is missing");

	    public static final ApplicationErrorCode RECURRENCE_END_DATE_AND_COUNT_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8404,
	            "Both recurrence end date and recurrence count are missing; at least one of them should be entered"
	            );
	    public static final ApplicationErrorCode RECURRENCE_TYPE_MISSING = new ApplicationErrorCode("LILO_RESM", 8405,
	            "Recurrence type is missing");

	    public static final ApplicationErrorCode INTERVAL_IN_DAYS_INVALID = new ApplicationErrorCode("LILO_RESM", 8406,
	            "Interval in days should be at least 1; interval in days entered - ");

	    public static final ApplicationErrorCode INTERVAL_IN_WEEKS_INVALID = new ApplicationErrorCode("LILO_RESM", 8407,
	            "Interval in weeks is either null or less than 1");

	    public static final ApplicationErrorCode SERVICE_REQUEST_DATE_MISSING = new ApplicationErrorCode("LILO_RESM", 8408,
	            "Service request date / recurrence start date is missing");

	    public static final ApplicationErrorCode NO_CHARGE_TRAVEL_COMPONENT_FOR_TCSR = new ApplicationErrorCode(
	            "LILO_RESM", 8409,
	            "Charge Component Travel Component(s) not found for travel component service request Id"
	            );

	    public static final ApplicationErrorCode SERVICE_REQUEST_CANCEL_ERROR = new ApplicationErrorCode("LILO_RESM", 8410,
	            "Attempt to cancel service request which is already cancelled; travel component service request id "
	            );

	    public static final ApplicationErrorCode ACCOMMODATION_CHECK_OUT_DATE_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8411, "Accommodation check out date should not be null");

	    public static final ApplicationErrorCode RETRIEVE_SERVICE_REQUESTS_FOR_FULFILLMENT_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 8412, "RetrieveServiceRequestsForFulfillmentRequest is either null or invalid"
	            );

	    public static final ApplicationErrorCode LATE_CHECK_OUT_STATUS_CHANGE_DETAIL_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8413, "LateCheckOutStatusChangeDetail object is missing");

	    public static final ApplicationErrorCode LATE_CHECK_OUT_NEW_STATUS_MISSING = new ApplicationErrorCode("LILO_RESM",
	            8414, "New late check out status is missing");

	    public static final ApplicationErrorCode SERVICE_REQUEST_QUANTITY_INVALID = new ApplicationErrorCode("LILO_RESM",
	            8415, "Since item / service is associated to a pricing product, please enter a non-zero quantity"
	            );

	    public static final ApplicationErrorCode SERVICE_REQUESTS_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 8416,
	            "Service requests could not be found in service request domain, ");

	    public static final ApplicationErrorCode NO_LATE_CHECK_OUT_REQUEST_FOR_ACCOMMODATION_COMPONENT = new ApplicationErrorCode(
	            "LILO_RESM", 8417,
	            "There is no approved late check out request associated to this accommodation component, "
	                    + "travelComponentId");

	    public static final ApplicationErrorCode LATE_CHECK_OUT_DATE_MISSING = new ApplicationErrorCode("LILO_RESM", 8418,
	            "Late check out request date/time should not be null");

	    /******************************************************************************
	     * * END - SERVICE REQUEST CHANGES *
	     ****************************************************************************** */

	    public static final ApplicationErrorCode LOCATE_SERVICE_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 8119,
	            "Error locating an external service!");

	    public static final ApplicationErrorCode PRIMARY_TRAVEL_PARTY_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
	            8120, "Primary Travel Party could not be found for accommodation component; party id"
	            );

	    public static final ApplicationErrorCode EXCEPTION_IN_FOLIO_SERVICE = new ApplicationErrorCode("LILO_RESM", 8122,
	            "Error in Folio Service");

	    public static final ApplicationErrorCode INVALID_UPDATE_GUEST_HISTORY_REQUEST = new ApplicationErrorCode(
	            "LILO_RESM", 8123, "Invalid Update Guest History Request. Travel Plan id cannot be null or zero"
	            );

	    public static final ApplicationErrorCode INVALID_UPDATE_FAVORITE_CHARACTER_FOR_GUEST = new ApplicationErrorCode(
	            "LILO_RESM", 8124, "Invalid Update Favorite Character For Guest Request. Guest ID cannot be null or zero"
	            );

	    public static final ApplicationErrorCode INVALID_UPDATE_EXTERNAL_TRAVEL_PLAN_ID = new ApplicationErrorCode(
	            "LILO_RESM", 8125, "Invalid Update External Travel PlanId Request. Travel Plan id cannot be null or zero"
	            );

	    public static final ApplicationErrorCode INVALID_SEARCH_GUEST_REQUEST = new ApplicationErrorCode("LILO_RESM", 8127,
	            "Invalid Search Guest Request. Last Name and Postal Code are required fields"
	            );

	    public static final ApplicationErrorCode INVALID_GET_TRAVEL_COMPONENTS_REQUEST = new ApplicationErrorCode(
	            "LILO_RESM", 8128, "Invalid Get Travel Components Request. Travel Plan Id is required field"
	            );

	    public static final ApplicationErrorCode INVALID_CREATE_TPS_COMMENTS_REQUEST = new ApplicationErrorCode(
	            "LILO_RESM", 8131, "Invalid Create TPS Comments Request");

	    public static final ApplicationErrorCode ROOM_OR_FACILITY_MISSING = new ApplicationErrorCode("LILO_RESM", 8132,
	            "RoomTypeTO or AccommodationFacilityTO is null; cannot convert to PricingRoomTypeTO"
	            );

	    public static final ApplicationErrorCode DVC_RESERVATION_CANNOT_BE_UPGRADED = new ApplicationErrorCode("LILO_RESM",
	            8135, "DVC Reservation Cannot be Upgraded!");

	    public static final ApplicationErrorCode PRICING_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8303,
	            "Error Invoking Pricing Service ");

	    public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8304,
	            "Error Invoking Packaging Service ");

	    public static final ApplicationErrorCode GROUPS_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8305,
	            "Error Invoking Groups Management Service ");

	    public static final ApplicationErrorCode COMMON_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8306,
	            "Error Invoking  Common Service ");

	    public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM",
	            8307, "Error Invoking  Folio Management Service ");

	    public static final ApplicationErrorCode INVALID_NUMBER_OF_ARGUMENTS = new ApplicationErrorCode("LILO_RESM", 8308,
	            "Invalid Number of Arguments ");

	    public static final ApplicationErrorCode ERROR_READING_FILES = new ApplicationErrorCode("LILO_RESM", 8309,
	            "Error reading files");

	    public static final ApplicationErrorCode ERROR_PARSING_DATE_FROM_DATABASE = new ApplicationErrorCode("LILO_RESM",
	            8310, "Error parsing date from database");

	    public static final ApplicationErrorCode ERROR_PARSING_TIME = new ApplicationErrorCode("LILO_RESM", 8311,
	            "Error parsing time from facility");

	    public static final ApplicationErrorCode REVENUE_TYPE_NULL_FROM_PRICING = new ApplicationErrorCode("LILO_RESM",
	            8312, "Revenue Type from Pricing is null");

	    public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(
	            "LILO_RESM", 8314, "Error Invoking  Inventory Management Service ");

	    public static final ApplicationErrorCode PROFILE_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8315,
	            "Error Invoking  Profile Service ");

	    public static final ApplicationErrorCode NO_CURRENT_ACCOMMODATION_FOUND = new ApplicationErrorCode("LILO_RESM",
	            8316, "No current accommodation found for this travel plan, travelPlanId "
	            );

	    public static final ApplicationErrorCode LOCATION_ID_MANDATORY = new ApplicationErrorCode("LILO_RESM", 8317,
	            "Location Id is Mandatory");

	    public static final ApplicationErrorCode FACILITY_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8501,
	            "Error Invoking Facility Service ");

	    public static final ApplicationErrorCode LILO_PARY_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8502,
	            "Error Invoking Lilo Party Service ");

	    public static final ApplicationErrorCode DPMS_PARY_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8503,
	            "Error Invoking EAI Party Service ");

	    public static final ApplicationErrorCode SIGN_IN_LOCATION_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8504,
	            "SIGN IN LOCATION IS REQUIRED ");

	    public static final ApplicationErrorCode INVALID_RESPONSE = new ApplicationErrorCode("LILO_RESM", 8505,
	            "Invalid Response From an External System!");

	    public static final ApplicationErrorCode ATS_TICKET_CODE_FROM_PRICING_NULL = new ApplicationErrorCode("LILO_RESM",
	            8506, "ATS Ticket Code from Pricing is null ");

	    public static final ApplicationErrorCode SIEBEL_TRAVELPLAN_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM",
	            8508, "Error Invoking Siebel Travel Plan Service ");

	    public static final ApplicationErrorCode CANNOT_OVERRIDE_CANCELLED_ACCOMMODATIONS = new ApplicationErrorCode(
	            "LILO_RESM", 8509, " Cancelled accommodations cannot be overriden");

	    public static final ApplicationErrorCode ROOMING_LIST_NAME_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 5090,
	            "Rooming List Name was not found, please re-enter.");

	    public static final ApplicationErrorCode MASS_MODIFY_NAME_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 5091,
	            "Mass Modify Name was not found, please re-enter.");

	    public static final ApplicationErrorCode PRIMARY_GUEST_CANNOT_BE_INACTIVE = new ApplicationErrorCode("LILO_RESM",
	            5092, "Primary Guest cannot be inactive.");

	    public static final ApplicationErrorCode USER_ID_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 5088,
	            "User ID was not found, please re-enter.");

	    public static final ApplicationErrorCode NO_RESULTS_FOUND = new ApplicationErrorCode("LILO_RESM", 5089,
	            "No Results found, please re-enter.");

	    public static final ApplicationErrorCode DME_TRANSIT_TIME_NOT_SET_UP_IN_RIM = new ApplicationErrorCode("LILO_RESM",
	            8510, "The DME transit time has not be configured in Room Inventory; facilityId - "
	            );

	    public static final ApplicationErrorCode DVC_RESERVATION_SHOULD_NOT_ADD_IATA_NUMBER = new ApplicationErrorCode(
	            "LILO_RESM", 8511, "IATA number should not be added to a DVC reservation"	            );

	    public static final ApplicationErrorCode EXCEPTION_IN_TICKET_BANK_INTERNAL_SERVICE = new ApplicationErrorCode(
	            "LILO_RESM", 8512, "Exception in invoking TicketBankService EJB ");

	    public static final ApplicationErrorCode EXCEPTION_RMI_INVOKING = new ApplicationErrorCode("LILO_RESM", 8513,
	            "Exception in invoking rmi server, Please contact the server administrator"
	            );

	    public static final ApplicationErrorCode EXCEPTION_RULE_FIRED = new ApplicationErrorCode("LILO_RESM", 8555,
	            "RESManagement suggests to stop this reservation");

	    public static final String NO_KTTW_FOUND_FOR_KTTW_NUMBER = "No Kttw details found for given Kttw number";

	    public static final String ROOM_AVAILABILITY_REQUEST_CREATION_ERROR = "While creating the "
	            + "RoomAvailabilityRequest, an exception occurred.";

	    public static final ApplicationErrorCode SALES_CHANNEL_UNKNOWN = new ApplicationErrorCode("LILO_RESM", 8557,
	            "Sales Channel cannot be UNKNOWN");

	    public static final ApplicationErrorCode COMMUNICATION_CHANNEL_UNKNOWN = new ApplicationErrorCode("LILO_RESM",
	            8558, "communication cannot be UNKNOWN");

	    public static final ApplicationErrorCode PARTY_MIX_REQUIRED = new ApplicationErrorCode("LILO_RESM", 8559,
	            "Party Mix is Required");

	    public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_INFORMATION_MISSING = new ApplicationErrorCode(
	            "LILO_RESM", 8560, "Room ready notification information is missing");

	    public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_DATA_NOT_SETUP = new ApplicationErrorCode(
	            "LILO_RESM", 8561, "Room ready notification data is not set up");

	    public static final ApplicationErrorCode INVALID_TIME_FOR_ROOM_READY_NOTIFICATION = new ApplicationErrorCode(
	            "LILO_RESM", 8562, "Room ready notification cannot be requested after 5 PM"
	            );

	    public static final ApplicationErrorCode INVALID_STATUS_FOR_ROOM_READY_NOTIFICATION = new ApplicationErrorCode(
	            "LILO_RESM", 8563, "Room ready notification can be requested only if reservation is in Checking In status "
	                    + "or if the room is being changed");

	    public static final ApplicationErrorCode ROOM_READY_MESSAGE_INVALID = new ApplicationErrorCode("LILO_RESM", 8564,
	            "Room ready message is not set up along with its delivery information");

	    public static final ApplicationErrorCode ROOM_READY_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 8565,
	            "Error invoking Room Ready service");

	    public static final ApplicationErrorCode HOUSE_KEEPING_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM",
	            8566, "Error invoking House Keeping service");

	    public static final ApplicationErrorCode NO_PRODUCT_FOUND = new ApplicationErrorCode("LILO_RESM", 8567,
	            "NO_PRODUCT_FOUND !!");

	    public static final ApplicationErrorCode METHOD_NOT_IMPLEMENTED = new ApplicationErrorCode("LILO_RESM", 8568,
	            "Method Not Implemented!");

	    public static final ApplicationErrorCode NO_CONFIG_DATA_FOUND = new ApplicationErrorCode("LILO_RESM", 8569,
	            "Config Data Error!");

	    public static final ApplicationErrorCode RESVTN_IN_PROGRESS = new ApplicationErrorCode("LILO_RESM", 8570,
	            "You have selected reservations that are already in progress"
	            );

	    public static final ApplicationErrorCode CARD_AUTHORIZATION_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM",
	            8571, "Error Invoking  Card Authorization Service ");

	    public static final ApplicationErrorCode CARD_AUTHORIZATION_MERCHANT_INFO_NOT_FOUND = new ApplicationErrorCode(
	            "LILO_RESM", 8572, "Could not find card authorization merchant info in core-reservation.properties file"
	            );

	    public static final ApplicationErrorCode NOT_A_NUMERIC = new ApplicationErrorCode("LILO_RESM", 8573,
	            "Not a numeric value ");

	    public static final ApplicationErrorCode WHOLESALER_RESERVATION_SHOULD_NOT_ADD_IATA_NUMBER = new ApplicationErrorCode(
	            "LILO_RESM", 8574, "IATA number should not be added to a Wholesaler reservation"
	            );

	    public static final ApplicationErrorCode SOURCE_ACCOUNTING_CENTER_NOT_MATCHING = new ApplicationErrorCode(
	            "LILO_RESM", 8575, "Source Accounting Center Not Matching");

	    public static final ApplicationErrorCode INVALID_CONFIRMATION_TYPE = new ApplicationErrorCode("LILO_RESM", 8576,
	            "Confirmation type is not valid");

	    public static final ApplicationErrorCode INVALID_KTTW_TYPE_FOR_REPLACE = new ApplicationErrorCode("LILO_RESM",
	            8577, "Invalid KTTW Type for Replace");

	    public static final ApplicationErrorCode INVALID_KTTW_ID = new ApplicationErrorCode("LILO_RESM", 8578,
	            "Kttw Id is not Valid");

	    public static final ApplicationErrorCode INVALIDMEDIA_FOR_REPLACE = new ApplicationErrorCode("LILO_RESM", 8579,
	            "Rfid Media can not be replaced");

	    public static final ApplicationErrorCode NO_TRAVELPLAN_ENDDATE = new ApplicationErrorCode("LILO_RESM", 8580,
	            "TravelPlanEndDate is null");

	    public static final ApplicationErrorCode INVALID_ROOM_TYPE_ADA = new ApplicationErrorCode("LILO_RESM", 8577,
	            "Mass Option is not available when reservation contains an Accessible Room or Accessible Icon"	            );

	    public static final ApplicationErrorCode TTPL_NOT_ENABLED = new ApplicationErrorCode("LILO_RESM", 8581,
	            "CONFIGURATION ERROR - TAP TO PLAY IS NOT ENABLED");

	    public static final ApplicationErrorCode TRANSFER_ELECTRONIC_TICKET_INVALID = new ApplicationErrorCode("LILO_RESM",
	            8582, "TRANSFER OF ELECTRONIC TICKET IS  NOT POSSIBLE");

	    public static final ApplicationErrorCode TICKERATE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 8583,
	            "Tickerate request is Invalid");

	    public static final ApplicationErrorCode SEARCH_ADMISSION_FOR_GUEST_REQUEST_INVALID = new ApplicationErrorCode(
	            "LILO_RESM", 8584, "Search Admission For Guest Request Are Invalid");

	    public static final ApplicationErrorCode TRAVELPLANGROUPING_NOTPRESENT = new ApplicationErrorCode("LILO_RESM",
	            8585, "Travel plan grouping not present");

	    public static final ApplicationErrorCode FACILITY_ID_NOT_PRESENT_FOR_FIRST_ADMISSION_COMPONENT = new ApplicationErrorCode(
	            "LILO_RESM", 8586, "Facility id is not present for first admission component"
	            );

	    public static final ApplicationErrorCode RESORT_NOT_NGE_ENABLED = new ApplicationErrorCode("LILO_RESM", 8587,
	            "Resort is not NGE enabled");

	    public static final ApplicationErrorCode INVALID_ENTITLEMENT_IN_REQUEST = new ApplicationErrorCode("LILO_RESM",
	            8588, "Either serial number or mag strip code should be present for entitlementDetail in request"
	            );

	    public static final ApplicationErrorCode MODIFY_TICKET_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9106, "Modify ticket request invalid");

	    public static final ApplicationErrorCode REFUND_TICKET_NOT_EXIST_ATS = new ApplicationErrorCode("LILO_RESM", 9107,
	            "Ticket not found in ticketing service (ATS) ");
	    public static final ApplicationErrorCode ADMISSION_CMPT_NOT_FOUND = new ApplicationErrorCode("LILO_RESM", 9108,
	            "Admission component not exist/created");

	    public static final ApplicationErrorCode ACTIVE_ENTIL_DOES_NOT_EXIST = new ApplicationErrorCode("LILO_RESM", 9109,
	            "Active Entitlments not exist ");

	    public static final ApplicationErrorCode UPGRADE_TKT_FAILED = new ApplicationErrorCode("LILO_RESM", 9110,
	            "Upgrade Ticket Failed ");
	    // Tap To Search Changes
	    public static final ApplicationErrorCode RFIDREFERENCE_VALUE_NOT_EXIST = new ApplicationErrorCode("LILO_RESM",
	            9111, "RFIDReference value not exist");
	    public static final ApplicationErrorCode MEDIA_NOT_ASSOCIATED_TO_ANY_GUEST = new ApplicationErrorCode("LILO_RESM",
	            9112, "Media is not associated to Any guest");
	    public static final ApplicationErrorCode MEDIA_IS_NOT_ACTIVE = new ApplicationErrorCode("LILO_RESM", 9113,
	            "Media is not Active");

	    public static final ApplicationErrorCode REFUND_TICKET_FAILED = new ApplicationErrorCode("LILO_RESM", 9114,
	            "REFUND TICKET FAILED");

	    public static final ApplicationErrorCode SEARCH_TICKET_DETAILS_FAILED = new ApplicationErrorCode("LILO_RESM", 9114,
	            "SEARCH TICKET DETAILS FAILED");

	    // Tap To Search Changes
	    public static final ApplicationErrorCode ACTIVATE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9114,
	            "Activate Request invalid");

	    public static final ApplicationErrorCode XBAND_ALREADY_EXIST = new ApplicationErrorCode("LILO_RESM", 9115,
	            "Activate Request invalid");

	    public static final ApplicationErrorCode XBAND_NOT_LINKED_WITH_GL = new ApplicationErrorCode("LILO_RESM", 9116,
	            "XBAND is not linked with guestlink");

	    public static final ApplicationErrorCode ASSOCIATE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM", 9117,
	            "Associate Request invalid");

	    // ALC reversal related error codes - START
	    public static final ApplicationErrorCode SHOW_INVENTORY_REQD = new ApplicationErrorCode(

	    "LILO_RESM", 9127,

	    "Reservation has Show package.Inventory Details Required for Show Package");

	    public static final ApplicationErrorCode DINE_INVENTORY_REQD = new ApplicationErrorCode(

	    "LILO_RESM", 9128,

	    "Reservation has Dinning package.Inventory Details Required for Dinning Package"
	            );
	    // ALC reversal related error codes - END

	    public static final ApplicationErrorCode EM_STAUS_CHANGE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9129, "ExperienceMediaStatusChange Request invalid");

	    public static final ApplicationErrorCode CHARGE_ACCOUNT_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM",
	            9130, "Error Invoking  Charge Account Service ");

	    public static final ApplicationErrorCode RFID_REFERENCE_REQUEST_INVALID = new ApplicationErrorCode("LILO_RESM",
	            9131, "RFIdMediaReferenceRequest invalid");

	    public static final ApplicationErrorCode ASSOCIATION_NOT_EXIST_IN_GL = new ApplicationErrorCode("LILO_RESM", 9132,
	            "Association of xband does not exist in guest link");

	    // Level N Changes - Start
	    public static final ApplicationErrorCode CANNOT_CREATE_BUNDLE_EVENT = new ApplicationErrorCode("LILO_RESM", 9133,
	            "Unable to Create a Bundle Event");

	    public static final ApplicationErrorCode BUNDLE_SERVICE_FAILURE = new ApplicationErrorCode("LILO_RESM", 9142,
	            "Error Invoking Bundle Service ");

	    public static final ApplicationErrorCode RETRIEVE_BUNDLE_FAILURE = new ApplicationErrorCode("LILO_RESM", 9143,
	            "Bundle details not returned by the Bundle Service ");
	    // Level N Changes - End

	    public static final ApplicationErrorCode MEDIA_IS_ACTIVE = new ApplicationErrorCode("LILO_RESM", 9137,
	            "Media is already in ACTIVE state");

	    public static final ApplicationErrorCode ERROR_CODE_403_RECVD = new ApplicationErrorCode("LILO_RESM", 9138,
	            "Error code 403 received");

	    public static final ApplicationErrorCode ERROR_CODE_404_RECVD = new ApplicationErrorCode("LILO_RESM", 9139,
	            "Error code 404 received");

	    public static final ApplicationErrorCode ERROR_CODE_400_RECVD = new ApplicationErrorCode("LILO_RESM", 9140,
	            "Error code 400 received");

	    // One view changes - Start
	    public static final ApplicationErrorCode INVALID_ONEVIEW_REQUEST = new ApplicationErrorCode("LILO_RESM", 9134,
	            "guestIdType and guestIdValue are mandatory");
	    // One view changes - End

	    public static final ApplicationErrorCode INVALID_MEDIA_STATE = new ApplicationErrorCode("LILO_RESM", 9141,
	            "Media state is invalid");

	    public static final ApplicationErrorCode XBAND_ALREADY_ASSOCIATED_TO_GUEST = new ApplicationErrorCode("LILO_RESM",
	            9142, "XBAND is already associated to guest");

	    public static final ApplicationErrorCode COMMUNICATION_ERROR_TO_TIMELOX_ERROR = new ApplicationErrorCode(
	            "LILO_RESM", 91423, "Expetion occured while connecting keycutting/timelox"
	            );

	    public static final ApplicationErrorCode NO_ENCODABLE_ACCESSES = new ApplicationErrorCode("LILO_RESM", 91423,
	            "There are no NON_ENCODED/FAILED_TO_ENCODE accesses on the band");

	    public static final ApplicationErrorCode INVALID_MODIFY_TICKET_REQUEST = new ApplicationErrorCode("LILO_RESM",
	            91424, "Modifying ticket code is not allowed");

	    // Start CR-185
	    public static final ApplicationErrorCode TRAVEL_PLAN_CHANGED_REQUEST_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 91425, "Publish TravelPlanChangedRequest is Required");

	    public static final ApplicationErrorCode CONVERSATION_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 91426,
	            "ConversationId is Required");

	    public static final ApplicationErrorCode MESSAGE_ID_REQUIRED = new ApplicationErrorCode("LILO_RESM", 91427,
	            "MessageId is Required");

	    public static final ApplicationErrorCode USER_NAME_REQUIRED = new ApplicationErrorCode("LILO_RESM", 91428,
	            "UserName is Required");

	    public static final ApplicationErrorCode USER_NAME_AS_PER_PROPERTIES_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 91429, "UserName is Required as per mentioned in Constants"
	            );
	    public static final ApplicationErrorCode TRAVEL_PLAN_IDS_OR_STARTDATE_REQUIRED = new ApplicationErrorCode(
	            "LILO_RESM", 91430, "TravelPlanId's or StartDate is required");

	    public static final ApplicationErrorCode FACILITY_ID_IS_REQUIRED = new ApplicationErrorCode("LILO_RESM", 91431,
	            "Facility id is required");

	    public static final ApplicationErrorCode START_DATE_GREATER_BOOKING_DATE = new ApplicationErrorCode("LILO_RESM",
	            91432, "Start date is greater then booking date is required");

	    public static final ApplicationErrorCode END_DATE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 91433,
	            "End Date is required  is required");

	    public static final ApplicationErrorCode START_DATE_LESSTHEN_END_DATE = new ApplicationErrorCode("LILO_RESM",
	            91434, "Start date is less then EndDate is required");
	    
	    public static final ApplicationErrorCode ROWS_SUPPORTED = new ApplicationErrorCode("LILO_RESM",
	            91435, "Rows retrieved for given dates are not supported for publish event");
	    
	    // End CR-185
	    
	    /****3.1 Changes**/
	    public static final ApplicationErrorCode SAC_REQUIRED= new ApplicationErrorCode("LILO_RESM",
	            91436, "SAC IS REQUIRED!");
	    
	    public static final ApplicationErrorCode MET_NT_SUP= new ApplicationErrorCode("LILO_RESM",
	            91437, "METHOD NOT SUPPORTED!");
	    
	    public static final ApplicationErrorCode PROPERTY_FILE_NOT_LOADED= new ApplicationErrorCode("LILO_RESM",
	            91438, "Error in loading property file <one-source.properties>.");
	    
	    public static final ApplicationErrorCode CANNOT_CREATE_CONTACT_UPDATE_EVENT = new ApplicationErrorCode("LILO_RESM",
	            91439, "Unable to Create a Contact Update Event");
	    
	    public static final ApplicationErrorCode MUID_NOT_PROVIDED = new ApplicationErrorCode(
				"LILO_RESM", 91440,
				"Manufacturer id of media is not provided in teh request"
				);

		public static final ApplicationErrorCode NO_GUEST_ASSOCIATED = new ApplicationErrorCode(
				"LILO_RESM", 91441,
				"No guest is associated to the band"
				);
				
		public static final ApplicationErrorCode CANNOT_CREATE_EXPERIENCE_MEDIA_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 10195, "Unable to Create an Experience Media Event");
	    public static final ApplicationErrorCode CANNOT_CREATE_KEY_CUTTING_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 10196, "Unable to Create an Key Cutting Event");
	    public static final ApplicationErrorCode CANNOT_CREATE_TICKET_BANK_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 10197, "Unable to Create an Ticket Bank Event");
	    public static final ApplicationErrorCode CANNOT_CREATE_GRANT_EXPERICENCE_MEDIA_ACCESS_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 10198, "Unable to Create an Grant Expericence Media Access Event");
	    public static final ApplicationErrorCode CANNOT_CREATE_MODIFY_ACCESS_EVENT = new ApplicationErrorCode(
	            "LILO_RESM", 10199, "Unable to Create an Modify Access Event");

		public static final ApplicationErrorCode CANNOT_CREATE_COMMENT_CHNAGE_EVENT = new ApplicationErrorCode(
				"LILO_RESM", 10200, "Unable to Create an Comment Change Event"
				);

		public static final ApplicationErrorCode CANNOT_CREATE_GSR_EVENT = new ApplicationErrorCode(
				"LILO_RESM", 10201, "Unable to Create an GSR Event"
				);

		public static final ApplicationErrorCode CANNOT_CREATE_SET_STATUS_EVENT = new ApplicationErrorCode(
				"LILO_RESM", 10201, "Unable to Create an Accommodation Status Event"
				);

		public static final ApplicationErrorCode RFID_MEDIA_NOT_AVAILABLE = new ApplicationErrorCode("LILO_RESM", 91442,
	            "Rfid Media not available for any of the Travel Plan Guest.");
		
		public static final ApplicationErrorCode PRICING_DETAILS_NOT_AVAIL_FOR_TICKET_CODE = new ApplicationErrorCode("LILO_RESM", 91443,
	            "No Pricing Detail found For ticket Code.");
		
		public static final ApplicationErrorCode TICKET_ALREADY_USED_ERROR_CODE = new ApplicationErrorCode("LILO_RESM", 91444,
	            "Ticket is already used by guest.");
		
		public static final ApplicationErrorCode TICKET_CODE_REQUIRED_ERROR = new ApplicationErrorCode("LILO_RESM", 91445,
	            "Ticket Code is required");
		
		public static final ApplicationErrorCode INVALID_GUEST_REFERENCE_DETAIL = new ApplicationErrorCode("LILO_RESM",
				91443, "Invalid External Reference Details");
		public static final ApplicationErrorCode INVALID_ORIGINAL_TXN_GUEST_ID = new ApplicationErrorCode("LILO_RESM",
				91444, "Invalid External Reference Details");
		
		public static final ApplicationErrorCode INVALID_ADMISSION_ENTITLEMENT_ID = new ApplicationErrorCode("LILO_RESM", 91450,
	            "Admission Entitlement Id is Null");
		public static final ApplicationErrorCode INVALID_ADMISSION_COMPONENT_ID = new ApplicationErrorCode("LILO_RESM", 91451,
	            "Admission Component Id is Null");
		
		//CR162 - TNIM changes START
		public static final ApplicationErrorCode SERIAL_NUMBER_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
				91448, "Entitlement serial number is null or empty");
		
		public static final ApplicationErrorCode GUEST_IDENTIFER_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
				91449, "Guest identifer list is null or empty");
		
		public static final ApplicationErrorCode GUEST_ID_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
				91450, "Transaction guest id is null");
		
		public static final ApplicationErrorCode NEW_MEDIA_NOT_FOUND = new ApplicationErrorCode("LILO_RESM",
				91451, "Media information is null");
		
		//CR162 - TNIM changes END
		
		public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 91443,
	            "Validation Failed.");
		
		public static final ApplicationErrorCode SERVICE_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 91443,
	            "Service Failed.");
		
		public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new ApplicationErrorCode("LILO_RESM", 91443,
	            "Data not found.");
		
		public static final ApplicationErrorCode GUESTID_REQUIRED_FORREPLACE = new ApplicationErrorCode(
	            "LILO_RESM", 91452, "Guest ID required for replacing the guest");
		
		public static final ApplicationErrorCode ACTIVE_ACCOMMODATION_REQUIRED_TOREPLACE = new ApplicationErrorCode(
	            "LILO_RESM", 91453, "Active accommodation required");
		
		public static final ApplicationErrorCode GUEST_IDS_ARE_NOTIN_SAMETP = new ApplicationErrorCode(
	            "LILO_RESM", 91454, "Guest Ids provided in Request are not in same Travel Plan!!!");
		
		public static final ApplicationErrorCode INVALID_ONEVIEW_RESPONSE = new ApplicationErrorCode("LILO_RESM", 91455,
	            "OneView call Failed for LevelN");
	


}
