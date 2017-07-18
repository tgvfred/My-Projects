package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationSalesComponentServicePort extends BaseSoapService {
    public AccommodationSalesComponentServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);
        setXmlRepo("xml/accommodationSalesComponentServicePort");
    }
}
