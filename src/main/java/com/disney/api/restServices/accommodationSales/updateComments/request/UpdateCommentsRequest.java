package com.disney.api.restServices.accommodationSales.updateComments.request;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.accommodationSales.updateComments.request.objects.CommentsInfo;

public class UpdateCommentsRequest {
	private List<String> parentIds = new ArrayList<String>();
	private List<CommentsInfo> commentsInfo = new ArrayList<CommentsInfo>();

	
	public UpdateCommentsRequest(){
		commentsInfo.add(new CommentsInfo());
	}
	
	public void addCommentsInfo(){
		commentsInfo.add(new CommentsInfo());
	}
	/**
	* 
	* @return
	* The parentIds
	*/
	public List<String> getParentIds() {
	return parentIds;
	 }

	/**
	* 
	* @param parentIds
	* The parentIds
	*/
	public void setParentIds(List<String> parentIds) {
	this.parentIds = parentIds;
	 }

	/**
	* 
	* @return
	* The commentsInfo
	*/
	public List<CommentsInfo> getCommentsInfo() {
	return commentsInfo;
	 }

	/**
	* 
	* @param commentsInfo
	* The commentsInfo
	*/
	public void setCommentsInfo(List<CommentsInfo> commentsInfo) {
	this.commentsInfo = commentsInfo;
	 }
}
