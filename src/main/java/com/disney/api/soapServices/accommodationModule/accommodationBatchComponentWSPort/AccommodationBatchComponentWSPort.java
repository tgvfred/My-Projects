package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationBatchComponentWSPort extends BaseSoapService {
    public static String MASS_CANCEL = "MASS_CANCEL";
    public static String MASS_MODIFY = "MASS_MODIFY";
    public static String ROOMING_LIST = "ROOMING_LIST";
    public static String REMOVEGROUP = "REMOVEGROUP";
    public static String MASS_REINSTATE = "MASS_REINSTATE";

    public AccommodationBatchComponentWSPort(String environment) {
        setEnvironmentServiceURL("AccommodationBatchComponentWSPort", environment);
    }
}
