package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesServicePort extends BaseSoapService {
    public AccommodationSalesServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationSalesServicePort", environment);

        if (environment.equalsIgnoreCase(Environment.getEnvironmentName("Bashful")) || environment.equalsIgnoreCase(Environment.getEnvironmentName("Latest"))) {
            setServiceURL("http://accommodationsvcbash.wdw.disney.com:8080/Accommodation/AccommodationSalesServicePort");
        }

        setXmlRepo("xml/accommodationSalesServicePort");
    }
}