package com.disney.api.soapServices.bussvcsModule.commonDoorService;

import com.disney.api.soapServices.core.BaseSoapService;

public class CommonDoorService extends BaseSoapService {


	public CommonDoorService(String environment) {
        setEnvironmentServiceURL("CommonDoorServiceV1", environment);
	}
}
