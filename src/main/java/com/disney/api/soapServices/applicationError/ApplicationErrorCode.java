package com.disney.api.soapServices.applicationError;

public class ApplicationErrorCode {

	private String moduleName = "";
	private String errorCode = "";
	private String desciption = "";
	
	public ApplicationErrorCode(String moduleName, int errorCode, String desciption) {
		this.moduleName = moduleName;
		this.errorCode = String.valueOf(errorCode);
		this.desciption = desciption;		
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public String getDesciption() {
		return desciption.trim();
	}
	
	@Override
	public String toString(){
		return String.format("Module: '%s' \nError Code: '%s' \nError Message: '%s'", moduleName, errorCode, desciption);
	}
}
