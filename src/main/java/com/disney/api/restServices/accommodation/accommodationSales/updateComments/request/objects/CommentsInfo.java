package com.disney.api.restServices.accommodation.accommodationSales.updateComments.request.objects;

public class CommentsInfo {
	private String profileCode = "FL02";
	private String commentLevel = "TC";
	private String commentText = "UpdateCommentTest";

	/**
	* 
	* @return
	* The profileCode
	*/
	public String getProfileCode() {
	return profileCode;
	 }

	/**
	* 
	* @param profileCode
	* The profileCode
	*/
	public void setProfileCode(String profileCode) {
	this.profileCode = profileCode;
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
}
