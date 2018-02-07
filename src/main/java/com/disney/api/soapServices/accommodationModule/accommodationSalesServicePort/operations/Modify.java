
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import org.w3c.dom.Document;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class Modify extends AccommodationSalesServicePort {

    public Modify(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
        generateServiceContext();

        // Retrofit to use new method which is designed to use Virtual Table data warehouse
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/modify/modifyInput.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    /**
     * Map existing ReplaceAllForTravelPlanSegment request to Modify request
     *
     * @param rq
     */

    public Modify(final ReplaceAllForTravelPlanSegment rq) {
        super(rq.getEnvironment());
        buildRequestFromWSDL("modify");
        rq.setByPassFreeze("true");
        rq.setRoomDetailsOverideFreeze("true");
        // Extract XML Doc from replaceAll and make it Modify
        Document doc = XMLTools.makeXMLDocument(rq.getRequest().replaceAll("replaceAllForTravelPlanSegment", "modify"));
        setRequestDocument(doc);

        // Remove objects not used in modify if existed in replaceAll
        removeNode("/Envelope/Body/modify/request/areaPeriod");
        removeNode("/Envelope/Body/modify/request/confirmationAddressId");
        removeNode("/Envelope/Body/modify/request/confirmationDetails");
        removeNode("/Envelope/Body/modify/request/confirmationPhoneId");
        removeNode("/Envelope/Body/modify/request/cruiseDetail");
        removeNode("/Envelope/Body/modify/request/replaceAll");
        removeNode("/Envelope/Body/modify/request/sentToProperty");
        removeNode("/Envelope/Body/modify/request/resExternalReferences");
        removeNode("/Envelope/Body/modify/request/roomDetails/freezeId");

        // Be safe in updating Node Name. Modify is a single RoomDetail object, not a List
        XMLTools.updateNodeName(getRequestDocument(), "/Envelope/Body/modify/request/roomDetails", "roomDetail");

        // Regen service context and clean up doc
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    private void removeNode(String xpath) {
        try {
            setRequestNodeValueByXPath(xpath, BaseSoapCommands.REMOVE_NODE.toString());
        } catch (XPathNotFoundException e) {

        }
    }

    public void setTravelPlanId(String TP_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanId", TP_ID);
    }

    public void setTravelComponentGroupingId(String Tcg_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentGroupingId", Tcg_ID);
    }

    public void setTravelComponentId(String tc_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentId", tc_ID);
    }

    public void setTravelPlanSegmentId(String tps_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanSegmentId", tps_ID);
    }

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/packageCode");
    }

    public String getResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/resortCode");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/travelPlanId");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/travelPlanSegmentId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//modifyResponse/modifyResponse/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
    }
}
