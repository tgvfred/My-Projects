package com.disney.utils.exceptions;


import com.disney.AutomationException;

public class NoDataFromVirtualTableException extends AutomationException{
	private static final long serialVersionUID = 3407361723082329697L;


	public NoDataFromVirtualTableException(String message){
		super(message );
	}
}
