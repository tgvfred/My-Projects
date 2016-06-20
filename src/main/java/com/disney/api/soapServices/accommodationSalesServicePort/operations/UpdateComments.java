package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class UpdateComments extends AccommodationSalesServicePort{

	public UpdateComments(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateComments")));
		//System.out.println(getRequest());
		generateServiceContext();	
		
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setparentIds(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/parentIds",value);
	}
	
	public void setcommentText(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentText",value);
	}
	
	public void settcId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentOwnerDetail/tcId",value);
	}
	
	public void settpsId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentOwnerDetail/tpsId",value);
	}
	
	public void settpId(String value ){
		setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentOwnerDetail/tpId", value);
	}
	
	public String getcommentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentId");
	}

	public String getcommentLevel(){
		return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentLevel");
	}

	
	
	
	
	
	
	
}
