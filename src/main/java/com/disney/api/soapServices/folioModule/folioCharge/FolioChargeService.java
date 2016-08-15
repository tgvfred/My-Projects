package com.disney.api.soapServices.folioModule.folioCharge;

import com.disney.api.soapServices.core.BaseSoapService;

public class FolioChargeService  extends BaseSoapService {

	public FolioChargeService(String environment) {		
		setEnvironmentServiceURL("FolioCharge", environment);
	}
}
