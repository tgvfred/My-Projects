package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RemoveComments extends AccommodationSalesServicePort{

	public RemoveComments(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("removeComments")));
		//System.out.println(getRequest());
		generateServiceContext();	
		
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setparentIds(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/removeComments/request/parentIds",value);
	}
	
	public void setcommentText(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/removeComments/request/commentsInfo/commentText",value);
	}
	
	
	
	
}
