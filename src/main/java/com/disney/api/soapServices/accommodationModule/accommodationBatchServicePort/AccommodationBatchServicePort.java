package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationBatchServicePort extends BaseSoapService {
    public static String MASS_CANCEL = "MASS_CANCEL";
    public static String MASS_MODIFY = "MASS_MODIFY";
    public static String ROOMING_LIST = "ROOMING_LIST";
    public static String REMOVEGROUP = "REMOVEGROUP";
    public static String MASS_REINSTATE = "MASS_REINSTATE";

    public AccommodationBatchServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationBatchWSPort", environment);
        setXmlRepo("xml/accommodationBatchServicePort");
    }
}
