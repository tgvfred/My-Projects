package com.disney.api.restServices.accommodationSales.updateComments.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.accommodationSales.updateComments.response.objects.CommentsInfo;

public class UpdateCommentsResponse {
	private List<CommentsInfo> commentsInfo = new ArrayList<CommentsInfo>();

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

