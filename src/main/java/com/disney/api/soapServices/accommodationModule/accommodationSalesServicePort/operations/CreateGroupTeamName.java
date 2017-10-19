package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CreateGroupTeamName extends AccommodationSalesServicePort {

    public CreateGroupTeamName(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createGroupTeamName")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // old setters
    public void setgroupcode(String code) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupCode", code);
    }

    public void setgroupTeamViewTO(String code) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupCode", code);
    }

    public void setgroupTeamName(String code) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupTeamName", code);
    }

    // setters
    public void setGroupCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupCode", value);
    }

    public void setGroupTeamViewTOGroupCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupCode", value);
    }

    public void setGroupName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupName", value);
    }

    public void setGroupTeamId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupTeamId", value);
    }

    public void setGroupTeamName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/groupTeamName", value);
    }

    public void setSelected(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/createGroupTeamName/request/groupTeamViewTO/selected", value);
    }

    // getters
    public String getGroupCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupCode");
    }

    public String getGroupTeamId() {
        return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupTeamId");
    }

    public String getGroupTeamName() {
        return getResponseNodeValueByXPath("/Envelope/Body/createGroupTeamNameResponse/response/groupTeamName");
    }

}