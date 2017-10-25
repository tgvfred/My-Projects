package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class DeleteGroupTeamName extends AccommodationSalesServicePort {
    public DeleteGroupTeamName(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("deleteGroupTeamName")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // setters
    public void setgroupCode(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupCode", tcg_id);
    }

    public void setgroupTeamName(String value) {
        setRequestNodeValueByXPath("Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupTeamName", value);
    }

    public void setGroupName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupName", value);
    }

    public void setGroupTeamId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/groupTeamId", value);
    }

    public void setSelected(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/deleteGroupTeamName/request/groupTeamViewTO/selected", value);
    }

    public void setTravelPlanSegmentId(String value) {
        setRequestNodeValueByXPath("Envelope/Body/deleteGroupTeamName/request/travelPlanSegmentId", value);
    }

    // getters
    public String getTeamNameDeleted() {
        return getResponseNodeValueByXPath("/Envelope/Body/deleteGroupTeamNameResponse/response/teamNameDeleted");
    }

}