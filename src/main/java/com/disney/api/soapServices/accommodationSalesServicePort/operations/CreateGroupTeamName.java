package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CreateGroupTeamName extends AccommodationSalesServicePort{
	
	public CreateGroupTeamName(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createGroupTeamName")));
        generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
		removeWhiteSpace();
	}
	public void setgroupcode(String code){
	setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupCode",code);
    }
    public void setgroupTeamViewTO(String code){
	setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupCode",code);
    }
    public void setgroupTeamName(String code){
    setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupTeamName",code);
    }
    

	public String getGroupCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupCode");
	}
	
	public String getGroupTeamId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupTeamId");
	}
	
	public  String getGroupTeamName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupTeamName");
	}
	
}