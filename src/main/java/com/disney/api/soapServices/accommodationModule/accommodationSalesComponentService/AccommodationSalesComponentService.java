package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesComponentService extends BaseSoapService {
    public AccommodationSalesComponentService(String environment) {
        setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);
        if (environment.equalsIgnoreCase(Environment.getEnvironmentName("Bashful"))) {
            setServiceURL("http://accommodationsvcbash.wdw.disney.com:8080/Accommodation/AccommodationSalesComponentServicePort");
        }
        setXmlRepo("xml/accommodationSalesComponentServicePort");
    }
}
