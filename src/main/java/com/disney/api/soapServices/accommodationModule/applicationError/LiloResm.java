/**
 * 
 */
/**
 * @author MALIC012
 *
 */
package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class LiloResm {
    public static final ApplicationErrorCode EXTERNAL_REFERENCE_REQUIRED = new ApplicationErrorCode("LILO_RESM", 7088, "External Reference is required");

    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(
            "LILO_RESM", 9042, "INVALID REQUEST");
}