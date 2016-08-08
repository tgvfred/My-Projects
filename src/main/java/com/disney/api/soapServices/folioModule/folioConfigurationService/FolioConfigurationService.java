package com.disney.api.soapServices.folioModule.folioConfigurationService;

import com.disney.api.soapServices.core.BaseSoapService;

public class FolioConfigurationService extends BaseSoapService {

	public FolioConfigurationService(String environment) {		
		setEnvironmentServiceURL("FolioConfigurationService", environment);
	}
}
