package com.disney.utils.exceptions;

import org.openqa.selenium.WebDriver;

import com.disney.AutomationException;

public class NoDataFromVirtualTableException extends AutomationException{
	private static final long serialVersionUID = 3407361723082329697L;


	public NoDataFromVirtualTableException(String message){
		super(message );
	}
	public NoDataFromVirtualTableException(String message, WebDriver driver){
		super(message, driver );
	}
}
