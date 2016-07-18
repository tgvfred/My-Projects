package com.disney.api.soapServices.applicationError;

/**
 * This class mirrors actual Travel Plan Error Codes and Message
 * @see https://github.disney.com/WDPR-Release/TravelPlanParent/blob/master/TravelPlan/src/main/java/com/wdpr/bussvcs/travelplan/util/TravelPlanConfigurationConstants.java
 * @author Justin Phlegar
 *
 */
public class TravelPlanErrorCode {

	private static final String moduleName = "Travelplan";

	public static final ApplicationErrorCode INVALID_VALUE = new ApplicationErrorCode(moduleName, 7082,
			"INVALID VALUE");

	public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(moduleName, 9042,
			"INVALID REQUEST !");

	public static final ApplicationErrorCode NO_RESULTS_FOUND = new ApplicationErrorCode(moduleName, 5089,
			"No Results found, please re-enter.");

	public static final ApplicationErrorCode LOCATION_ID_REQUIRED = new ApplicationErrorCode(moduleName, 9025,
			"Location Id is required");

	public static final ApplicationErrorCode FACILITY_SERVICE_FAILURE = new ApplicationErrorCode(moduleName, 8501,
			"Error Invoking Facility Service ");


	public static final ApplicationErrorCode INVALID_UPDATE_GUEST_HISTORY_REQUEST = new ApplicationErrorCode(
			moduleName, 8123, "Invalid Update Guest History Request. Travel Plan id cannot be null or zero");

	public static final ApplicationErrorCode DELIVER_GUEST_MESSAGE = new ApplicationErrorCode(moduleName, 8070,
			"The request is invalid! No or null message Ids found!");

	public static final ApplicationErrorCode NO_ACCOMMODATION_FOUND = new ApplicationErrorCode(moduleName, 7177,
			" No Accommodation Component found.");

	public static final ApplicationErrorCode CANNOT_CREATE_CONTACT_UPDATE_EVENT = new ApplicationErrorCode(moduleName,
			91439, "Unable to Create a Contact Update Event");

	public static final ApplicationErrorCode INVENTORY_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(
			moduleName, 8314, "Error Invoking  Inventory Management Service ");

	public static final ApplicationErrorCode RETRIEVE_COMMENTS = new ApplicationErrorCode(moduleName, 9001,
			"Unable to Retrieve Comments!");

	public static final ApplicationErrorCode NUMBER_FORMAT_EXCEPTION = new ApplicationErrorCode(moduleName, 9095,
			"Print Package Component Report failed");

	public static final ApplicationErrorCode APPLICATION_EXCEPTION = new ApplicationErrorCode(moduleName, 7063,
			"GENERAL ERROR");

	public static final ApplicationErrorCode INVALID_UPDATE_EXTERNAL_TRAVEL_PLAN_ID = new ApplicationErrorCode(
			moduleName, 8125, "Invalid Update External Travel PlanId Request. Travel Plan id cannot be null or zero"); 

	public static final ApplicationErrorCode RETRIEVE_GUEST_HISTORY = new ApplicationErrorCode(moduleName, 8076,
			"Invalid request for Retrieve Guest History");

	public static final ApplicationErrorCode BOOKING_SOURCE_NOT_FOUND = new ApplicationErrorCode(moduleName, 7125,
			"Booking Source not found");

	public static final ApplicationErrorCode INVALID_EXTERNAL_REFERNCE_CODE = new ApplicationErrorCode(moduleName,
			7086, "Invalid External Reference Code");

	public static final ApplicationErrorCode EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED = new ApplicationErrorCode(
			moduleName, 7090, "External Reference Source or Code required");

	public static final ApplicationErrorCode EXTERNAL_REFERENCE_NUMBER_REQUIRED = new ApplicationErrorCode(moduleName,
			7088, "External Reference is required");

	public static final ApplicationErrorCode NO_TRAVELPLAN_ENDDATE = new ApplicationErrorCode(moduleName, 8580,
			"TravelPlanEndDate is null");

	public static final ApplicationErrorCode INAVLID_SELECTION = new ApplicationErrorCode(moduleName, 7038,
			"Invalid Selection is made");

	public static final ApplicationErrorCode TRAVEL_PLAN_REQUIRED = new ApplicationErrorCode(moduleName, 9024,
			"Travel Plan Required");

	public static final ApplicationErrorCode CREATE_COMMENTS = new ApplicationErrorCode(moduleName, 8078,
			"Create Comments Request Invalid");

	public static final ApplicationErrorCode INVALID_RESPONSE = new ApplicationErrorCode(moduleName, 8505,
			"Invalid Response From an External System!");

	public static final ApplicationErrorCode MISSING_REQUIRED_PARAM_EXCEPTION = new ApplicationErrorCode(moduleName,
			7064, "Required parameters are missing");

	public static final ApplicationErrorCode TRAVEL_PLAN_NOT_FOUND = new ApplicationErrorCode(moduleName, 7007,
			"TRAVEL_PLAN_NOT_FOUND");

	public static final ApplicationErrorCode PACKAGING_SERVICE_FAILURE = new ApplicationErrorCode(moduleName, 8304,
			"Error Invoking Packaging Service ");

	public static final ApplicationErrorCode LILO_PARY_SERVICE_FAILURE = new ApplicationErrorCode(moduleName, 8502,
			"Error Invoking Lilo Party Service ");

	public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_DATA_NOT_SETUP = new ApplicationErrorCode(
			moduleName, 8561, "Room ready notification data is not set up");

	public static final ApplicationErrorCode CANNOT_CREATE_GSR_EVENT = new ApplicationErrorCode(
			moduleName, 10201, "Unable to Create an GSR Event");

	public static final ApplicationErrorCode CANNOT_CREATE_SET_STATUS_EVENT = new ApplicationErrorCode(
			moduleName, 10201, "Unable to Create an Accommodation Status Event");

	public static final ApplicationErrorCode CANNOT_CREATE_RES_CHANGE_EVENT = new ApplicationErrorCode(moduleName,
			9118, "Unable to Create a Res Change Ecent");

	public static final ApplicationErrorCode FOLIO_MANAGEMENT_SERVICE_FAILURE = new ApplicationErrorCode(moduleName,
			8307, "Error Invoking  Folio Management Service ");

	public static final ApplicationErrorCode GROUPS_SERVICE_FAILURE = new ApplicationErrorCode(moduleName, 8305,
			"Error Invoking Groups Management Service ");

	public static final ApplicationErrorCode GROUPCODE_INVALID = new ApplicationErrorCode(moduleName, 7041,
			"Groupcode is invalid");

	public static final ApplicationErrorCode VALIDATION_SERVICE_EXCEPTION = new ApplicationErrorCode(moduleName, 91443,
			"Validation Failed.");

	public static final ApplicationErrorCode DATA_NOT_FOUND_SERVICE_EXCEPTION = new ApplicationErrorCode(moduleName, 91443,
			"Data not found.");

	public static final ApplicationErrorCode ADD_GUEST_REQUEST_INVALID = new ApplicationErrorCode(moduleName, 9088,
			"Add Guest request invalid");

	public static final ApplicationErrorCode UPDATE_GUEST_MESSAGES = new ApplicationErrorCode(moduleName, 8074,
			"The updateGuestMessage request is invalid");


	public static final ApplicationErrorCode CREATE_KTTW_REQUEST_INVALID = new ApplicationErrorCode(moduleName, 9023,
			"Could not fulfill create Kttw as the request list was invalid");

	public static final ApplicationErrorCode COULD_NOT_CREATE_KTTW = new ApplicationErrorCode(moduleName, 9058,
			"Could not create KTTW");

	public static final ApplicationErrorCode TRAVEL_PLAN_OR_TRAVEL_COMPONENT_GUEST_NOT_FOUND = new ApplicationErrorCode(
			moduleName, 9103, "No Travel Plan Party or Travel Component Guest found");

	public static final ApplicationErrorCode INVALID_STATUS_FOR_ROOM_READY_NOTIFICATION = new ApplicationErrorCode(
			moduleName, 8563, "Room ready notification can be requested only if reservation is in Checking In status "
					+ "or if the room is being changed");

	public static final ApplicationErrorCode INCONSISTENT_DATA = new ApplicationErrorCode(moduleName, 7072,
			"Inconsitent Data");

	public static final ApplicationErrorCode REMOVE_GUEST_MESSAGES = new ApplicationErrorCode(moduleName, 8075,
			"The Remove GuestMessage request is invalid");

	public static final ApplicationErrorCode ROOM_READY_NOTIFICATION_INFORMATION_MISSING = new ApplicationErrorCode(
			moduleName, 8560, "Room ready notification information is missing");

	public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_COMMENT_EVENT =	
			new ApplicationErrorCode(moduleName, 9135, 
					"Unable to Create a Travel Plan Comment Event");

	public static final ApplicationErrorCode PRIMARY_TRAVEL_PARTY_NOT_FOUND = new ApplicationErrorCode(moduleName,
			8120, "Primary Travel Party could not be found for accommodation component; party id");

	public static final ApplicationErrorCode SRC_ACCOUNTING_CENTER_REQUIRED = new ApplicationErrorCode(moduleName,
			8013, "SOURCER ACCOUNTING CENTER IS REQUIRED!");

	public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_NOT_FOUND = new ApplicationErrorCode(moduleName,
			7008, "Travel Plan Segment Not Found");

	public static final ApplicationErrorCode RETRIEVE_BUNDLE_FAILURE = new ApplicationErrorCode(moduleName,
			9143, "Bundle details not returned by the Bundle Service ");

	public static final ApplicationErrorCode BUNDLE_SERVICE_FAILURE = new ApplicationErrorCode(moduleName, 
			9142, "Error Invoking Bundle Service ");

	public static final ApplicationErrorCode TRAVEL_PLAN_SEGMENT_REQUIRED = new ApplicationErrorCode(moduleName, 
			7018, " Travel Plan Segment is Required ");

	public static final ApplicationErrorCode INVALID_GET_TRAVEL_COMPONENTS_REQUEST = new ApplicationErrorCode(
			moduleName, 8128, "Invalid Get Travel Components Request. Travel Plan Id is required field");

	public static final ApplicationErrorCode CANNOT_CREATE_TRAVEL_PLAN_GUEST_EVENT = new ApplicationErrorCode(
			moduleName, 9124, "Unable to Create a Travel Plan Guest Event");

	public static final ApplicationErrorCode ROOM_OR_FACILITY_MISSING = new ApplicationErrorCode(moduleName, 8132,
			"RoomTypeTO or AccommodationFacilityTO is null; cannot convert to PricingRoomTypeTO");

	public static final ApplicationErrorCode RECORD_NOT_FOUND_EXCEPTION = new ApplicationErrorCode(moduleName, 7065,
			"RECORD NOT FOUND");

	public static final ApplicationErrorCode INVALID_UPDATE_FAVORITE_CHARACTER_FOR_GUEST = new ApplicationErrorCode(
			moduleName, 8124, "Invalid Update Favorite Character For Guest Request. Guest ID cannot be null or zero"	);


}

