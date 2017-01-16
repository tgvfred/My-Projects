package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
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
	
	public void setParentIds(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/parentIds",value);
	}
	
	public void setCommentText(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentText",value);
	}
	
	public void setTcId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tcId",value);
	}
	
	public void setTpsId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tpsId",value);
	}
	
	public void setTpId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail/tpId", value);
	}
	
	
	
	
	
	
	
	
	
}
