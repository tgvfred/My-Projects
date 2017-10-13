package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class LiloSystemErrorCode {

    private static final String APPLICATION = "LILO System";

    public static final ApplicationErrorCode UNEXPECTED_ERROR_OCCURRED = new ApplicationErrorCode(APPLICATION, 1001,
            "Unexpected Error occurred");

}
