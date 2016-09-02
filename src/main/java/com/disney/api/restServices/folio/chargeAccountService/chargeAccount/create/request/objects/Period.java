package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

import com.disney.test.utils.Randomness;

public class Period {
	private String startDate = Randomness.generateCurrentXMLDatetime(10)+"-04:00";

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
}
