package com.disney.api.soapServices.core;

/**
 * @summary Creating a central location to store all Affiliation options 
 * @version Created 11/23/2015
 * @author Justin Phlegar
 */
public enum BaseSoapCommands {
	ADD_NODE("fx:addnode; Node:"),
	ADD_ATTIRBUTE("fx:addattribute; Attribute:"),
	ADD_NAMESPACE("fx:addnamespace; Namespace:"),
	GET_DATE_TIME("fx:getdatetime; DaysOut:"),
	GET_DATE("fx:getdate; DaysOut:"),
	REMOVE_NODE("fx:removenode"),
	REMOVE_ATTRIBUTE("fx:removeattribute"),
	RANDOM_NUMBER( "fx:randomnumber; node:"),
	RANDOM_STRING ("fx:randomstring; node:"),
	RANDOM_ALPHANUMBERIC("fx:randomalphanumeric; node:");
		 
	private String text = "";
	private BaseSoapCommands(String text){this.text = text;}
	public String commandAppend(String append){
		return text+append;
	}
	
	@Override
	public String toString(){
		return text;
	}
		    
}