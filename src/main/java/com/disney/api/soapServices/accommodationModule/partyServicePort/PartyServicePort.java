package com.disney.api.soapServices.accommodationModule.partyServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class PartyServicePort extends BaseSoapService {
    public PartyServicePort(String environment) {
        setEnvironmentServiceURL("PartyServicePort", environment);
        setXmlRepo("xml/partyServicePort");

    }

}
