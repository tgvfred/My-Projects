package com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationInventoryRequestComponentServicePort extends BaseSoapService {

    public AccommodationInventoryRequestComponentServicePort(String environment) {
        setEnvironmentServiceURL("AccommodationInventoryRequestComponentServicePort", environment);
    }
}