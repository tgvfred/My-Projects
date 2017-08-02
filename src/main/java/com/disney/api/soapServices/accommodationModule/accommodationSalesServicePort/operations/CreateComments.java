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
		
	    //setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	//Setters
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

	public void setIsActive(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/isActive", value);}
	public void setSendToGSR(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/sendToGSR", value);}
	public void setConfidential(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/confidential", value);}
	public void setProfileId(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/profileId", value);}
	public void setProfileCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/profileCode", value);}
	public void setCommentId(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentId", value);}
	public void setCommentLevel(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentLevel", value);}
	public void setCreatedBy(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/auditDetail/createdBy", value);}
	public void setCreatedDate(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/auditDetail/createdDate", value);}
	public void setUpdatedBy(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/auditDetail/updatedBy", value);}
	public void setUpdatedDate(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/auditDetail/updatedDate", value);}
	public void setStatus(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/auditDetail/status", value);}
	public void setRoomExternalReferenceNumber(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference/externalReferenceNumber", value);}
	public void setRoomExternalReferenceSource(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference/externalReferenceSource", value);}
	public void setRoomExternalReferenceType(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference/externalReferenceType", value);}
	public void setRoomExternalReferenceCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference/externalReferenceCode", value);}
	public void setTpsExternalReferenceNumber(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference/externalReferenceNumber", value);}
	public void setTpsExternalReferenceSource(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference/externalReferenceSource", value);}
	public void setTpsExternalReferenceType(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference/externalReferenceType", value);}
	public void setTpsExternalReferenceCode(String value ){setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference/externalReferenceCode", value);}
	
	
	//Getters
	public String getIsActive(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/isActive");}
	public String getSendToGSR(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/sendToGSR");}
	public String getConfidential(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/confidential");}
	public String getProfileId(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/profileId");}
	public String getProfileCode(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/profileCode");}
	public String getCommentId(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentId");}
	public String getCommentText(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentText");}
	public String getCommentLevel(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentLevel");}
	public String getTcId(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentOwnerDetail/tcId");}
	public String getTpsId(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentOwnerDetail/tpsId");}
	public String getTpId(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentOwnerDetail/tpId");}
	public String getCreatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/auditDetail/createdBy");}
	public String getCreatedDate(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/auditDetail/createdDate");}
	public String getUpdatedBy(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/auditDetail/udatedBy");}
	public String getUpdatedDate(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/auditDetail/updatedDate");}
	public String getCommentType(){return getResponseNodeValueByXPath("/Envelope/Body/createCommentsResponse/response/commentsInfo/commentType");}
	
}
