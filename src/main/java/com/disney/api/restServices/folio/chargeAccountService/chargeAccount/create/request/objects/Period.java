package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class Period {
	private String startDate;
	private String endDate;

	/**
	* 
	* @return
	* The startDate
	*/
	public String getStartDate() {
	return startDate;
	 }

	/**
	* 
	* @param startDate
	* The startDate
	*/
	public void setStartDate(String startDate) {
	this.startDate = startDate;
	 }
	
	public String getEndDate() {
		return endDate;
		 }

		/**
		* 
		* @param startDate
		* The startDate
		*/
		public void setEndDate(String endDate) {
		this.startDate = endDate;
		 }
}
