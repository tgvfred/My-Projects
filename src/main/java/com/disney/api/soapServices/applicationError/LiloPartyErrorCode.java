package com.disney.api.soapServices.applicationError;


/**
 * This class contains errors returned by the LILO system
 * @author Waightstill W Avery
 *
 */
public class LiloPartyErrorCode {
			public static final ApplicationErrorCode INVALID_TITLE = new ApplicationErrorCode("LILO_PARTY", 15015,
		            "Salutation is invalid");
			
			public static final ApplicationErrorCode INVALID_COUNTRY = new ApplicationErrorCode("LILO_PARTY", 15000,
		            "Create Party Error");
}