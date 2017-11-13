package com.disney.api.soapServices.accommodationModule.accommodationServiceV2;

import com.disney.api.soapServices.core.BaseSoapService;


public class AccommodationServiceV2 extends BaseSoapService {
    public AccommodationServiceV2(String environment) {
        setEnvironmentServiceURL("AccommodationServiceV2", environment);
        setXmlRepo("xml/accommodationServiceV2");
    }

}