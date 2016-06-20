package com.disney.api.soapServices.commonDoorService.operations;

import com.disney.api.soapServices.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class CreateTAG extends CommonDoorService{

	public CreateTAG(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createTAG")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}		
	
	public String getTagNumber(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='tAGIds'][1]/*[local-name(.)='value'][1]");
	}
	
	public String getKeyName(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='keyName'][1]");
	}
	
	public String getTagId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAGResponse'][1]/*[local-name(.)='createTAGResponse'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='id'][1]");
	}
	
	public String getTagType(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='tAGIds'][1]/*[local-name(.)='type'][1]");
	}

	public String getMediaType(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='mediaType'][1]");
	}
	
	public String getMediaStatus(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='tAGDetail'][1]/*[local-name(.)='status'][1]");
	}
	
	public String getScheduleRecurrence(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='timeSchedules'][1]/*[local-name(.)='recurrenceType'][1]");
	}
	
	public String getScheduleDayName(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/*[local-name(.)='timeSchedules'][1]/*[local-name(.)='dayName'][1]");
	}
	
	public String getTagStartDate(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/@*[local-name(.)='endDate'][1]");
	}
	
	public String getTagEndDate(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createTAG'][1]/*[local-name(.)='createTagRequest'][1]/@*[local-name(.)='startDate'][1]");
	}
}
