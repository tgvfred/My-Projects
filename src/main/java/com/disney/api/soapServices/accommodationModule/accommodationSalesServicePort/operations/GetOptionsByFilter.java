package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class GetOptionsByFilter extends AccommodationSalesServicePort{
	public GetOptionsByFilter(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionsByFilter")));
		generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setOptionEnum(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/accommodationSalesOptionsEnum", value);}
	public void setKey(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/optionFilters/key", value);}
	public void setValue(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/optionFilters/value", value);}
	public String getoptionKey(){return getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response[1]/optionKey");}
	public String getoptionValue(){return getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response[1]/optionValue");}
	
	public Map<String, String> getKeyValuePairs(){
		Map<String, String> keyValuePairs = new HashMap<String, String>();
		int numberOfKeyValuePairs = getNumberOfKeyValuePairs();
		for(int i = 0; i < numberOfKeyValuePairs; i++){
			keyValuePairs.put(getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response["+String.valueOf(i+1)+"]/optionKey"), getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response["+String.valueOf(i+1)+"]/optionValue"));
		}
		
		return keyValuePairs;
	}
	
	public int getNumberOfKeyValuePairs(){
		int numberOfKeyValuePairs = 0;
		try{return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/getOptionsByFilterResponse/response").getLength();}
		catch(XPathNotFoundException e){}
		return numberOfKeyValuePairs;
	}
}
