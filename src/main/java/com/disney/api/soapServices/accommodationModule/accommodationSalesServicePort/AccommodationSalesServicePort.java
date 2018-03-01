package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesServicePort extends BaseSoapService {
    public AccommodationSalesServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationSalesServicePort", Environment.getEnvironmentName(environment).equalsIgnoreCase("Grumpy_CM") ? "Grumpy" : environment);
        setXmlRepo("xml/accommodationSalesServicePort");
    }
}