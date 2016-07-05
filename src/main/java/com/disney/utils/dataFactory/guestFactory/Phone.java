package com.disney.utils.dataFactory.guestFactory;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import com.disney.test.utils.Randomness;

/**
* @summary Container to store random or pre-defined Guest Phone Information
* @author Justin Phlegar
* @version 11/28/2015 Justin Phlegar - original
* @see com.disney.utils.dataFactory.guestFactory.Guest
*/
public class Phone{
	private boolean isPrimary = false;
	private String locatorId = "0";
	private String country = "";
	private String type = "";
	private String number = "";
	
	Phone(){
		this.country = "United States";
		this.type = "Home";
		this.number = "336" + Randomness.randomNumber(7);
	}
	
	public boolean isPrimary() {return isPrimary;}
	public void setPrimary(boolean isPrimary) {this.isPrimary = isPrimary;}
	
	public String getLocatorId() {return locatorId;}
	public void setLocatorId(String locatorId) {this.locatorId= locatorId;}

	public String getCountry() {return country;}
	public void setCountry(String country) {this.country = country;}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public String getNumber() {return number;}
	public void setNumber(String number) {this.number = number;}
	
	public String getFormattedNumber(){return getFormattedNumber("###-###-####");}
	public String getFormattedNumber(String phoneMask) {
		
		try {
			MaskFormatter maskFormatter= new MaskFormatter(phoneMask);
			maskFormatter.setValueContainsLiteralCharacters(false);
		
		
			return maskFormatter.valueToString(number) ;
		} catch (ParseException e) {
		}
		return number;
	}
}
