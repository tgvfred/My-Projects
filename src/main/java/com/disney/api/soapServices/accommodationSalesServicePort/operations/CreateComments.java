package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CreateComments extends AccommodationSalesServicePort{

	public CreateComments(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createComments")));
		//System.out.println(getRequest());
		generateServiceContext();	
		
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setparentIds(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/parentIds",value);
	}
	
	public void setcommentText(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentText",value);
	}
	
	public void settcId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tcId",value);
	}
	
	public void settpsId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tpsId",value);
	}
	
	public void settpId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tpId", value);
	}
	
	
	
	
	
	
	
	
	
}
