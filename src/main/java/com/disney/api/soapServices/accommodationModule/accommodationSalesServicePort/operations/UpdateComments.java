package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class UpdateComments extends AccommodationSalesServicePort{

	public UpdateComments(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateComments")));
		//System.out.println(getRequest());
		generateServiceContext();	
		
	    //setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
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

	//Setters
	public void setIsActive(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/isActive", value);}
	public void setSendToGSR(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/sendToGSR", value);}
	public void setConfidential(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/confidential", value);}
	public void setProfileId(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/profileId", value);}
	public void setProfileCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/profileCode", value);}
	public void setCommentId(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentId", value);}
	public void setCommentLevel(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentLevel", value);}
	public void setupdatedBy(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail/updatedBy", value);}
	public void setupdatedDate(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail/updatedDate", value);}
	public void setcreatedDate(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail/createdDate", value);}
	public void setcreatedby(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail/createdBy", value);}
	public void setStatus(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail/status", value);}
	public void setRoomExternalReferenceNumber(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference/externalReferenceNumber", value);}
	public void setRoomExternalReferenceSource(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference/externalReferenceSource", value);}
	public void setRoomExternalReferenceType(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference/externalReferenceType", value);}
	public void setRoomExternalReferenceCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference/externalReferenceCode", value);}
	public void setTpsExternalReferenceNumber(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference/externalReferenceNumber", value);}
	public void setTpsExternalReferenceSource(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference/externalReferenceSource", value);}
	public void setTpsExternalReferenceType(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference/externalReferenceType", value);}
	public void setTpsExternalReferenceCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference/externalReferenceCode", value);}
	
	
	//Getters
	public String getIsActive(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/isActive");}
	public String getSendToGSR(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/sendToGSR");}
	public String getConfidential(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/confidential");}
	public String getProfileId(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/profileId");}
	public String getProfileCode(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/profileCode");}
	public String getCommentId(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentId");}
	public String getCommentText(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentText");}
	public String getCommentLevel(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentLevel");}
	public String getTcId(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentOwnerDetail/tcId");}
	public String getTpsId(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentOwnerDetail/tpsId");}
	public String getTpId(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentOwnerDetail/tpId");}
	public String getupdatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/auditDetail/updatedBy");}
	public String getupdatedDate(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/auditDetail/updatedDate");}
	public String getcreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/auditDetail/createdBy");}
	public String getcreatedDate(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/auditDetail/createdDate");}
	public String getCommentType(){return getResponseNodeValueByXPath("/Envelope/Body/updateCommentsResponse/response/commentsInfo/commentType");}}
	