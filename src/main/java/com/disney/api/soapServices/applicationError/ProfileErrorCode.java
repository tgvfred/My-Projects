package com.disney.api.soapServices.applicationError;

public final class ProfileErrorCode{ 
	
	public static final String MODULE_NAME = "LILO_Profile";

	public static final ApplicationErrorCode MULTIPLE_PROFILE_WITH_CODE =
			new ApplicationErrorCode(
					MODULE_NAME,
					6001,
					"Date issue; mulitiple profiles exist with code"
					);

	public static final ApplicationErrorCode PROFILE_EXISTS_WITH_CODE =
			new ApplicationErrorCode(
					MODULE_NAME,
					6002,
					"Another profile already exists with code"
					);

	public static final ApplicationErrorCode NO_PROFILE_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6003,
					"No profile found for profileId"
					);

	public static final ApplicationErrorCode PROFILE_TYPE_NOT_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6004,
					"Profile type not found in database"
					);

	public static final ApplicationErrorCode ROUTING_TYPE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6005,
					"Routing type is missing"
					);

	public static final ApplicationErrorCode PROFILE_DETAIL_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6006,
					"ProfileDetail should not be null"
					);

	public static final ApplicationErrorCode PROFILE_TYPE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6007,
					"Profile type should not be null"
					);

	public static final ApplicationErrorCode PROFILE_TYPE_INAPPROPRIATE =
			new ApplicationErrorCode(
					MODULE_NAME,
					6009,
					"Appropriate profile type is not set."
					);

	public static final ApplicationErrorCode EXT_REF_VALUE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6010,
					"Attribute value is missing for the Attribute profile"
					);

	public static final ApplicationErrorCode PROFILE_SERVICE_DETAIL_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6011,
					"ProfileServiceDetail should not be null"
					);

	public static final ApplicationErrorCode PICKUP_IND_SERVICE_TYPE_MISMATCH =
			new ApplicationErrorCode(
					MODULE_NAME,
					6012,
					"Since the schedule pickup indicator is selected, service type should be set to"
					);

	public static final ApplicationErrorCode ITEM_ROUTING_TYPE_MISMATCH =
			new ApplicationErrorCode(
					MODULE_NAME,
					6013,
					"Since an item is selected, there should be a routing of type"
					);

	public static final ApplicationErrorCode PROFILEIDS_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6014,
					"No profileIds found."
					);

	public static final ApplicationErrorCode HOUSEKEEPING_DETAIL_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6015,
					"HouseKeeping Detail is null"
					);

	public static final ApplicationErrorCode FACILITY_NOT_FOUND_FOR_RESORTCODE =
			new ApplicationErrorCode(
					MODULE_NAME,
					6016,
					"AccommodationFacilityTO not obtained from AccommodationFacilityService for resortCode"
					);

	public static final ApplicationErrorCode FACILITYID_NOT_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6017,
					"EnterpriseFacilityId obtained from AccommodationFacilityService is zero for resortCode"
					);

	public static final ApplicationErrorCode FACILITY_NOT_FOUND_FOR_FACILITYID =
			new ApplicationErrorCode(
					MODULE_NAME,
					6018,
					"AccommodationFacilityTO not obtained from AccommodationFacilityService for facilityId"
					);

	public static final ApplicationErrorCode RESORTCODE_NOT_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6019,
					"ResortCode obtained from AccommodationFacilityService is blank for facilityId"
					);

	public static final ApplicationErrorCode MAINTAIN_SERVICE_FAILED =
			new ApplicationErrorCode(
					MODULE_NAME,
					6020,
					"ResortCode obtained from AccommodationFacilityService is blank for facilityId"
					);

	public static final ApplicationErrorCode SERVICE_NOT_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6021,
					"Service not found for serviceId"
					);

	public static final ApplicationErrorCode CONVERSION_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					6022,
					"Error while converting serviceId"
					);

	public static final ApplicationErrorCode SERVICE_EXT_REF_VALUE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6023,
					"Cannot retrieve service for null profile external reference value."
					);

	public static final ApplicationErrorCode EMPTY_LIST_OF_SERVICE_IDS =
			new ApplicationErrorCode(
					MODULE_NAME,
					6024,
					"List of Service Ids is null or empty."
					);

	public static final ApplicationErrorCode NO_ACTIVE_PROFILE_FOUND =
			new ApplicationErrorCode(
					MODULE_NAME,
					6025,
					"No active profile found for profileId"
					);

	public static final ApplicationErrorCode INPUT_OBJECT_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6026,
					"Input object to method should not be null"
					);

	public static final ApplicationErrorCode SERVICE_TYPE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6027,
					"Service type should not be null"
					);

	public static final ApplicationErrorCode HOUSE_KEEPING_DETAIL_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6028,
					"Since housekeeping routing is selected, section board type should also be selected"
					);

	public static final ApplicationErrorCode PROFILE_CODE_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6029,
					"Profile code should not be null"
					);

	public static final ApplicationErrorCode DISPATCH_SYSTEM_ROUTING_ITEM_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6030,
					"Since dispatch system routing is selected, item should also be selected"
					);

	public static final ApplicationErrorCode PROFILE_ACTIVE_INDICATOR_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6031,
					"Profile active indicator should not be null"
					);

	public static final ApplicationErrorCode PROFILE_OPTION_ENUM_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6032,
					"ProfileOptionEnum should not be null"
					);

	public static final ApplicationErrorCode PROFILE_FILTER_OPTION_ENUM_MISSING =
			new ApplicationErrorCode(
					MODULE_NAME,
					6033,
					"ProfileFilterOptionEnum should not be null"
					);

	public static final ApplicationErrorCode INVALID_OPTION =
			new ApplicationErrorCode(
					MODULE_NAME,
					6034,
					"This method does not support ProfileOptionEnum"
					);

	public static final ApplicationErrorCode INVALID_FILTER_OPTION =
			new ApplicationErrorCode(
					MODULE_NAME,
					6035,
					"This method does not support ProfileOptionFilterEnum"
					);
}