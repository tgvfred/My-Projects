package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class ValidateShareRules extends AccommodationSalesComponentService {

    public ValidateShareRules(String environment, String scenario) {
        super(environment);
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validateShareRules")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters
    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails/travelComponentGroupingId", value);
    }

    public void setTravelComponentGroupingId(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/travelComponentGroupingId", value);
    }

    public void setTravelComponentId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails/travelComponentId", value);
    }

    public void setTravelComponentId(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/travelComponentId", value);
    }

    public void setTravelStatus(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails/travelStatus", value);
    }

    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request", value);
    }

    public void setSharedRoomDetails(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails", value);
    }

    public void setRoomTypeCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails/roomTypeCode", value);
    }

    public void setRoomTypeCode(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/roomTypeCode", value);
    }

    public void setResortCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails/resortCode", value);
    }

    public void setResortCode(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/resortCode", value);
    }

    public void setEndDate(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/resortPeriod/endDate", value);
    }

    public void setStartDate(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/resortPeriod/startDate", value);
    }

    public void setLocationId(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/locationId", value);
    }

    public void setPackageCode(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/packageCode", value);
    }

    public void setBlockCode(String value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/validateShareRules/request/sharedRoomDetails[" + index + "]/blockCode", value);
    }

    // Getters
    public String getReturn() {
        return getResponseNodeValueByXPath("/Envelope/Body/validateShareRulesResponse/return");
    }

}
