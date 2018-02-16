package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationSalesServicePort extends BaseSoapService {
    public AccommodationSalesServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationSalesServicePort", environment);

        setXmlRepo("xml/accommodationSalesServicePort");
    }

    @Override
    public void sendRequest() {
        super.sendRequest();
    }
}