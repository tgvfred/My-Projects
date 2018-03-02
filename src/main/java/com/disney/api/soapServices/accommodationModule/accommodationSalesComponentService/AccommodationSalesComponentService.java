package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesComponentService extends BaseSoapService {
    public AccommodationSalesComponentService(String environment) {
        setEnvironmentServiceURL("AccommodationSalesComponentServicePort", Environment.getEnvironmentName(environment).equalsIgnoreCase("Grumpy_CM") ? "Grumpy" : environment);
        setXmlRepo("xml/accommodationSalesComponentServicePort");
    }
}
