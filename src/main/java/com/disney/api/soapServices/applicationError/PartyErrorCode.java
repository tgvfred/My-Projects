package com.disney.api.soapServices.applicationError;

/**
 * This class mirrors actual Party Error Codes and Message
 * @see https://github.disney.com/WDPR-Composite-Modernization/party-common/blob/CM-develop/Party/src/main/java/com/wdw/party/service/exception/PartyErrorCodeEnum.java
 * @author Justin Phlegar
 *
 */
public final class PartyErrorCode{
	
	private static final String MODULE_NAME = "LILO_PARTY";

	public static final ApplicationErrorCode CREATE_PARTY_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15000,
					"Create Party Error");

	public static final ApplicationErrorCode RETRIEVE_PARTY_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15001,
					"Retrieve Party Error");

	public static final ApplicationErrorCode CREATE_KTTW_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15002,
					"Create Key to the World Request invalid");

	public static final ApplicationErrorCode TRAVEL_PLAN_ID_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15003,
					"Travel Plan Id invalid");

	public static final ApplicationErrorCode PARTY_ID_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15004,
					"Party Id is invalid");

	public static final ApplicationErrorCode CHARGING_FLAG_REQURIED =
			new ApplicationErrorCode(
					MODULE_NAME,
					15005,
					"Charging Enabled Flag required");

	public static final ApplicationErrorCode KTTW_STATUS_FLAG_NOT_SET =
			new ApplicationErrorCode(
					MODULE_NAME,
					15006,
					"KTTW Access flag needs to be set");

	public static final ApplicationErrorCode EXTERNAL_SOURCE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15007,
					"External Reference is Invalid");

	public static final ApplicationErrorCode RETRIEVE_KTTW_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15008,
					"Reterieve Kttw request is not valid");

	public static final ApplicationErrorCode UPDATE_ENCODING_STATUS_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15009,
					"Update Encoding Status Request not valid"
					);

	public static final ApplicationErrorCode INVALID_GUEST_ENCODING_DETAIL =
			new ApplicationErrorCode(
					MODULE_NAME,
					15010,
					"Guest KTTW Encoding detail is invalid"
					);

	public static final ApplicationErrorCode INVALID_GUEST_ENCODE_KEY_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15011,
					"Guest KTTW Encode Key Error is invalid"
					);

	public static final ApplicationErrorCode INVALID_KTTW_ID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15012,
					"KTTW ID provided is invalid"
					);

	public static final ApplicationErrorCode UPDATE_KTTW_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15013,
					"Update Kttw Request not valid"
					);

	public static final ApplicationErrorCode RETRIEVE_PARTY_BY_EXTERNAL_GUEST_ID_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15014,
					"Retrieve Party by external guest id request invalid"
					);

	public static final ApplicationErrorCode SALUTATION_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15015,
					"Salutation is invalid"
					);

	public static final ApplicationErrorCode SUFFIX_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15016,
					"Suffix is invalid"
					);

	public static final ApplicationErrorCode UPDATE_ACCESS_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15017,
					"Update Access Request is invalid"
					);

	public static final ApplicationErrorCode LOCATOR_TYPE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15018,
					"Locator Type provided is invalid"
					);

	public static final ApplicationErrorCode PHONE_TECH_TYPE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15019,
					"Phone Tech Type invalid"
					);

	public static final ApplicationErrorCode PARTY_ID_LIST_NULL_EMPTY =
			new ApplicationErrorCode(
					MODULE_NAME,
					15020,
					"List of Party ids is null or empty"
					);

	public static final ApplicationErrorCode RETRIEVE_ADDRESS_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15021,
					"Retieve Address Error"
					);

	public static final ApplicationErrorCode COUNTRY_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15022,
					"Country is invalid"
					);

	public static final ApplicationErrorCode ROLE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15023,
					"Role is invalid"
					);

	public static final ApplicationErrorCode NO_INPUT =
			new ApplicationErrorCode(
					MODULE_NAME,
					15024,
					"Input request is not valid."
					);

	public static final ApplicationErrorCode LOCATOR_EXTERNAL_REFERENCE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15025,
					"Invalid input request for locator external references."
					);

	public static final ApplicationErrorCode LOCATOR_ID_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15026,
					"Invalid locator id."
					);

	public static final ApplicationErrorCode GUEST_KTTW_DETAIL_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15027,
					"Guest Kttw Detail invalid"
					);

	public static final ApplicationErrorCode EAI_ORGANIZATION_SERVICE_FAILURE =
			new ApplicationErrorCode(
					MODULE_NAME,
					15028,
					"Error Invoking  EAI Organization Service "
					);

	public static final ApplicationErrorCode LANGUAGE_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15029,
					"Language is invalid"
					);

	public static final ApplicationErrorCode UPDATE_ACTIVE_STATUS_REQUEST_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15030,
					"Update Individual Active Status Request invalid"
					);

	public static final ApplicationErrorCode ADDRESS_INFO_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15031,
					"Address Info invalid"
					);

	public static final ApplicationErrorCode PHONE_INFO_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15032,
					"Phone Info invalid"
					);

	public static final ApplicationErrorCode EMAIL_INFO_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15033,
					"Email Info invalid"
					);

	public static final ApplicationErrorCode UPDATE_PARTY_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15034,
					"Update Party Error"
					);

	public static final ApplicationErrorCode RETRIEVE_LOCATOR_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15035,
					"Retieve Locator Error"
					);

	public static final ApplicationErrorCode CREATE_TRAVEL_AGENCY_ORGANIZATION_ERROR =
			new ApplicationErrorCode(
					MODULE_NAME,
					15036,
					"Create Travel Agency Organization Error"
					);

	public static final ApplicationErrorCode PARTY_INFO_INVALID =
			new ApplicationErrorCode(
					MODULE_NAME,
					15037,
					"Party Info invalid"
					);

	public static final ApplicationErrorCode INVALID_TRAVEL_AGENCY =
			new ApplicationErrorCode(
					MODULE_NAME,
					15038,
					"Travel Agency is invalid"
					);

	public static final ApplicationErrorCode EAI_PARTY_SERVICE_FAILURE =
			new ApplicationErrorCode(
					MODULE_NAME,
					15028,
					"Error Invoking  EAI Party Service "
					);
	
	public static final ApplicationErrorCode PUBLISH_GUEST_CHANGE_EVENT_FAILURE =
			new ApplicationErrorCode(
					MODULE_NAME,
					15029,
					"Publish GuestChangeEvent Failed "
					);	

	public static final ApplicationErrorCode UPDATE_INACTIVE_DATE_REQUEST_INVALID =
		new ApplicationErrorCode(
				MODULE_NAME,
				15030,
				"Upadte Inactive Date request error"
				);

	public static final ApplicationErrorCode RETRIEVE_PARTY_BY_EXTERNAL_PARTY_ID_REQUEST_INVALID =
		new ApplicationErrorCode(
				MODULE_NAME,
				15042,
				"Retrieve party by External Party Id Error."
				);
	
	public static final ApplicationErrorCode GENDER_TYPE_INVALID =
		new ApplicationErrorCode(
				MODULE_NAME,
				15043,
				"Gender Type invalid"
				);
	
	public static final ApplicationErrorCode GOMASTER_ORGANIZATION_SERVICE_FAILURE =
		new ApplicationErrorCode(
				MODULE_NAME,
				15044,
				"Error Invoking GoMaster OrganizationV2 Service "
				);
	
	public static final ApplicationErrorCode LAST_NAME_INVALID =
                new ApplicationErrorCode(
                                MODULE_NAME,
                                15045,
                                "Last Name is invalid "
                                );
	
        public static final ApplicationErrorCode LOCATOR_VALUE_INVALID =
                new ApplicationErrorCode(
                                MODULE_NAME,
                                15046,
                                "Locator Value is invalid "
                                );	
}


