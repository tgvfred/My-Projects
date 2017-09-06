package com.disney.api.soapServices.accommodationModule.roomingListServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class RoomingListServicePort extends BaseSoapService {

    public RoomingListServicePort(String environment) {
        setEnvironmentServiceURL("RoomingListServicePort", environment);
        setXmlRepo("xml/roomingListServicePort");
    }

}
