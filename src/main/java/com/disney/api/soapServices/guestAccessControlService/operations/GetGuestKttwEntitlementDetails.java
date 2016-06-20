package com.disney.api.soapServices.guestAccessControlService.operations;

import com.disney.api.soapServices.guestAccessControlService.GuestAccessControlServiceV1;
import com.disney.test.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.XMLTools;

public class GetGuestKttwEntitlementDetails extends GuestAccessControlServiceV1{
	
	public GetGuestKttwEntitlementDetails(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getGuestKttwEntilementDetails")));

		generateServiceContext();	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public String getRandomSerialNumber(){
		OracleDatabase db = new OracleDatabase(getEnvironment(), "Dreams");
		db.getResultSet("SELECT SERIAL_NB_VL FROM GUEST.EXPRNC_CARD_ENTTL  Sample(1) Where SERIAL_NB_VL  Is Not Null And rownum =1 ");
		return "";
	}
}
