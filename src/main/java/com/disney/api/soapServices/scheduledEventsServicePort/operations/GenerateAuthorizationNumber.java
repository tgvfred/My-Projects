package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class GenerateAuthorizationNumber extends ScheduledEventsServicePort{
	public GenerateAuthorizationNumber(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("generateAuthorizationNumber")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public String getAuthorizationNumber(){
		String number = "";
		try{
			number = getResponseNodeValueByXPath("/Envelope/Body/generateAuthorizationNumberResponse/authorizationNumber");		
		}catch(XPathNotFoundException throwAway){}
		return number;
	}
}