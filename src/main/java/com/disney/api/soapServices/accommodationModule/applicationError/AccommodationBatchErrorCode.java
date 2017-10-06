package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class AccommodationBatchErrorCode {
    private static final String APP_NAME = "Accommodation-Sales-Batch-Service";

    public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(APP_NAME, 7000,
            "Invalid Request");
}