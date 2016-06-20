package com.disney.api.soapServices.accommodationServiceV2.operations;
import com.disney.api.soapServices.accommodationServiceV2.AccommodationServiceV2;
import com.disney.utils.XMLTools;


public class RetrieveServiceInfo extends AccommodationServiceV2{

	public RetrieveServiceInfo(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveServiceInfo")));
		//System.out.println(getRequest());
				
		removeComments() ;
		removeWhiteSpace();
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}
