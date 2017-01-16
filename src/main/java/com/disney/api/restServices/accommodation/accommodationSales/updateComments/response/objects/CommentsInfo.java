package com.disney.api.restServices.accommodation.accommodationSales.updateComments.response.objects;

public class CommentsInfo {
	private Boolean isActive;
	private Object sendToGSR;
	private Object confidential;
	private Object profileId;
	private Object profileCode;
	private Long commentId;
	private String commentText;
	private String commentLevel;
	private Object commentOwnerDetail;
	private AuditDetail auditDetail;
	private Object commentType;

	/**
	* 
	* @return
	* The isActive
	*/
	public Boolean getIsActive() {
	return isActive;
	 }

	/**
	* 
	* @param isActive
	* The isActive
	*/
	public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
	 }

	/**
	* 
	* @return
	* The sendToGSR
	*/
	public Object getSendToGSR() {
	return sendToGSR;
	 }

	/**
	* 
	* @param sendToGSR
	* The sendToGSR
	*/
	public void setSendToGSR(Object sendToGSR) {
	this.sendToGSR = sendToGSR;
	 }

	/**
	* 
	* @return
	* The confidential
	*/
	public Object getConfidential() {
	return confidential;
	 }

	/**
	* 
	* @param confidential
	* The confidential
	*/
	public void setConfidential(Object confidential) {
	this.confidential = confidential;
	 }

	/**
	* 
	* @return
	* The profileId
	*/
	public Object getProfileId() {
	return profileId;
	 }

	/**
	* 
	* @param profileId
	* The profileId
	*/
	public void setProfileId(Object profileId) {
	this.profileId = profileId;
	 }

	/**
	* 
	* @return
	* The profileCode
	*/
	public Object getProfileCode() {
	return profileCode;
	 }

	/**
	* 
	* @param profileCode
	* The profileCode
	*/
	public void setProfileCode(Object profileCode) {
	this.profileCode = profileCode;
	 }

	/**
	* 
	* @return
	* The commentId
	*/
	public Long getCommentId() {
	return commentId;
	 }

	/**
	* 
	* @param commentId
	* The commentId
	*/
	public void setCommentId(Long commentId) {
	this.commentId = commentId;
	 }

	/**
	* 
	* @return
	* The commentText
	*/
	public String getCommentText() {
	return commentText;
	 }

	/**
	* 
	* @param commentText
	* The commentText
	*/
	public void setCommentText(String commentText) {
	this.commentText = commentText;
	 }

	/**
	* 
	* @return
	* The commentLevel
	*/
	public String getCommentLevel() {
	return commentLevel;
	 }

	/**
	* 
	* @param commentLevel
	* The commentLevel
	*/
	public void setCommentLevel(String commentLevel) {
	this.commentLevel = commentLevel;
	 }

	/**
	* 
	* @return
	* The commentOwnerDetail
	*/
	public Object getCommentOwnerDetail() {
	return commentOwnerDetail;
	 }

	/**
	* 
	* @param commentOwnerDetail
	* The commentOwnerDetail
	*/
	public void setCommentOwnerDetail(Object commentOwnerDetail) {
	this.commentOwnerDetail = commentOwnerDetail;
	 }

	/**
	* 
	* @return
	* The auditDetail
	*/
	public AuditDetail getAuditDetail() {
	return auditDetail;
	 }

	/**
	* 
	* @param auditDetail
	* The auditDetail
	*/
	public void setAuditDetail(AuditDetail auditDetail) {
	this.auditDetail = auditDetail;
	 }

	/**
	* 
	* @return
	* The commentType
	*/
	public Object getCommentType() {
	return commentType;
	 }

	/**
	* 
	* @param commentType
	* The commentType
	*/
	public void setCommentType(Object commentType) {
	this.commentType = commentType;
	 }
}
