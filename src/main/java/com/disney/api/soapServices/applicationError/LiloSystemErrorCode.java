package com.disney.api.soapServices.applicationError;


/**
 * This class contains errors returned by the LILO system
 * @author Waightstill W Avery
 *
 */
public class LiloSystemErrorCode {
			public static final ApplicationErrorCode UNEXPECTED_ERROR = new ApplicationErrorCode("LILO System", 1001,
		            "Unexpected Error occurred");
			public static final ApplicationErrorCode SQL_QUERY_ERROR = new ApplicationErrorCode("LILO System", 1011,
		            "SQL Query Error");
}