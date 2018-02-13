package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesServicePort extends BaseSoapService {
    public AccommodationSalesServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationSalesServicePort", environment);

        setXmlRepo("xml/accommodationSalesServicePort");
    }

    @Override
    public void sendRequest() {
        if (getEnvironment().equalsIgnoreCase(Environment.getEnvironmentName("Bashful")) || getEnvironment().equalsIgnoreCase(Environment.getEnvironmentName("Latest"))) {
            setServiceURL("http://accommodationsvcbash.wdw.disney.com:8080/Accommodation/AccommodationSalesServicePort");
        }
        super.sendRequest();
    }
}