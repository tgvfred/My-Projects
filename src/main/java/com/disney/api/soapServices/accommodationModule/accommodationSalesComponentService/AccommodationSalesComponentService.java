package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class AccommodationSalesComponentService extends BaseSoapService {
    public AccommodationSalesComponentService(String environment) {
        setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);
        setXmlRepo("xml/accommodationSalesComponentServicePort");
    }

    @Override
    public void sendRequest() {
        if (getEnvironment().equalsIgnoreCase(Environment.getEnvironmentName("Bashful")) || getEnvironment().equalsIgnoreCase(Environment.getEnvironmentName("Latest"))) {
            setServiceURL("http://accommodationsvcbash.wdw.disney.com:8080/Accommodation/AccommodationSalesComponentServicePort");
        }
        super.sendRequest();
    }
}
