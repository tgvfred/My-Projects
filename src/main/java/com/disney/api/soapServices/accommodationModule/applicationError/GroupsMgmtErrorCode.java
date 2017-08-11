package com.disney.api.soapServices.accommodationModule.applicationError;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;

public class GroupsMgmtErrorCode {
    private static final String APP_NAME = "DPMSGroups";

    public static final ApplicationErrorCode INVALID_ROOM_TYPE = new ApplicationErrorCode(APP_NAME, 4605,
            "Group Profile does not exist with the given code");
}
