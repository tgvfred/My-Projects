package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveComments extends AccommodationSalesServicePort{

	public RetrieveComments(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveComments")));
		//System.out.println(getRequest());
		generateServiceContext();	
		
	    //setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	//Setters
	public void setParentIds(String value ){setRequestNodeValueByXPath("/Envelope/Body/retrieveComments/request/parentId",value);}
	
	//Getters
	public String getIsActive(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/isActive");}
	public String getSendToGSR(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/sendToGSR");}
	public String getConfidential(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/confidential");}
	public String getProfileId(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/profileId");}
	public String getCommentId(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/commentId");}
	public String getCommentText(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/commentText");}
	public String getCommentLevel(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/commentLevel");}
	public String getTcId(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/commentOwnerDetail/tcId");}
	public String getCreatedBy(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/auditDetail/createdBy");}
	public String getCreatedDate(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/auditDetail/createdDate");}
	public String getUpdatedBy(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/auditDetail/updatedBy");}
	public String getUpdatedDate(){return getRequestNodeValueByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo/auditDetail/udatedDate");}
	
}
