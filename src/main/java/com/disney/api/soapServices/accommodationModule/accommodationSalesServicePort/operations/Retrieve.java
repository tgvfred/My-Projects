
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.Arrays;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Retrieve extends AccommodationSalesServicePort {

    public Retrieve(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public Retrieve(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));

        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    // getters
    public String getPartyId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/partyId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/guestId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/travelPlanSegmentId");

    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/componentId");
    }

    public String[] getTravelComponentIDs(int numberOfTcIds) {
        String[] TC_IDs = new String[numberOfTcIds];
        int loopCounter;

        for (loopCounter = 1; loopCounter <= numberOfTcIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/accommodation/componentId";
            TC_IDs[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return TC_IDs;
    }

    public String getAddressLocatorId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest/addressDetails/locatorId");
    }

    public String[] getTravelComponentGroupingIDs(int numberOfTcgIds) {
        String[] TCG_IDs = new String[numberOfTcgIds];
        int loopCounter;

        for (loopCounter = 1; loopCounter <= numberOfTcgIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/travelComponentGroupingId";
            TCG_IDs[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return TCG_IDs;
    }

    public String queryAndGetRandomTravelPlanId() {
        OracleDatabase db = new OracleDatabase("Sleepy", "Dreams");
        Recordset rs = new Recordset(db.getResultSet("Select * FROM RES_MGMT.TPS WHERE ROWNUM < 10"));

        // System.out.println("Example 1");
        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            // System.out.println(rs.getValue("TPS_ID"));
        }

        // System.out.println();
        // System.out.println("Example 2");
        rs.moveFirst();
        String report = "";
        for (int row = 0; rs.hasNext(); rs.moveNext(), row++) {
            report = "|";
            for (int column = 1; column < rs.getColumnCount(); column++) {
                report += rs.getValue(column, row) + " | ";
            }

            // System.out.println(report);

        }

        // System.out.println();
        // System.out.println();
        // rs.print();
        return "";
    }

    // setters
    public void setTravelPlanId(String TP_ID) {
        setRequestNodeValueByXPath("//request/travelPlanId", TP_ID);
    }

    public void setLocationId(String locationId) {
        setRequestNodeValueByXPath("//request/locationId", locationId);
    }

    public void setSiebelTravelPlanId(String value) {

        setRequestNodeValueByXPath("Envelope/Body/retrieve/request/siebelTravelPlanId", value);
    }

    public String[] getGuestIds(int numberOfGuests) {
        String[] guestIds = new String[numberOfGuests];

        for (int loopCounter = 1; loopCounter <= numberOfGuests; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests[" + String.valueOf(loopCounter) + "]/guest/guestId";
            guestIds[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        Arrays.sort(guestIds);

        return guestIds;
    }

    public String[] getFacilityIds(int numberOfFacilityIds) {
        String[] facilityIds = new String[numberOfFacilityIds];

        for (int loopCounter = 1; loopCounter <= numberOfFacilityIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/accommodation/facilityId";
            facilityIds[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return facilityIds;
    }

    public String getFacilityId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/facilityId");
    }

    public void setTravelPlanSegmentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request/travelPlanSegmentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request", BaseSoapCommands.ADD_NODE.commandAppend("travelPlanSegmentId"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request/travelPlanSegmentId", value);
        }
    }

    // base validation getters

}
