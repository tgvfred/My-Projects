package com.disney.api.soapServices.folioModule.folioServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class FolioService extends BaseSoapService {

	public FolioService(String environment) {		
		setEnvironmentServiceURL("FolioService", environment);
	}
}
