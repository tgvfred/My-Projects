package com.disney.api.soapServices.experienceMediaServiceV1.operations;

import com.disney.api.soapServices.experienceMediaServiceV1.ExperienceMediaServiceV1;
import com.disney.utils.XMLTools;

public class FindExperienceMediaAccessStatus extends ExperienceMediaServiceV1{
	public FindExperienceMediaAccessStatus(String environment, String scenario){
		super(environment);
		//UI Booking
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("findExperienceMediaAccessStatus")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
}