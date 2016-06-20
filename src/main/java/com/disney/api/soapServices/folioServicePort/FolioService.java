package com.disney.api.soapServices.folioServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class FolioService extends BaseSoapService {

	public FolioService(String environment) {		
		setEnvironmentServiceURL("FolioService", environment);
	}
}
