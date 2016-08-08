package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class DeleteGroupTeamName extends AccommodationSalesServicePort{
	public DeleteGroupTeamName(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("deleteGroupTeamName")));
	    generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
	public void setgroupCode(String tcg_id){
	setRequestNodeValueByXPath("/Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupCode", tcg_id);
    }
	
	
	public void setgroupTeamName(String value){
		setRequestNodeValueByXPath("Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupTeamName", value);
	    }
	
	public void setTravelPlanSegmentId(String value){
		setRequestNodeValueByXPath("Envelope/Body/deleteGroupTeamName/request/travelPlanSegmentId", value);
	    }
		
	public String getTeamNameDeleted(){
		return getResponseNodeValueByXPath("/Envelope/Body/deleteGroupTeamNameResponse/response/teamNameDeleted");
	}
	
	
	
	
    
}