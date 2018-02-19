package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

/**
 * @author phlej001
 *
 */
public class Add extends AccommodationFulfillmentServicePort {

    public Add(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("add")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public void setTravelPlanId(String tp_id) {
        setRequestNodeValueByXPath("//travelPlanId", tp_id);
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("//travelPlanId");
    }

    public void setTravelPlanSegmentId(String tps_id) {
        setRequestNodeValueByXPath("//travelPlanSegmentId", tps_id);
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("//travelPlanSegmentId");
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("//travelComponentGroupingId", tcg_id);
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("//travelComponentGroupingId");
    }

    public void setTravelComponentId(String tc_id) {
        setRequestNodeValueByXPath("//travelComponentId", tc_id);
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("//travelComponentId");
    }

    public void setBookingDate(String bookingDate) {
        if (bookingDate.contains("fx:GetDate; Daysout:")) {
            setRequestNodeValueByXPath("//roomDetail/bookingDate", bookingDate);
        } else {
            setRequestNodeValueByXPath("//roomDetail/bookingDate", "fx:GetDate; Daysout:" + bookingDate);
        }
    }

    /**
     *
     * @summary Method to set the request departure date - modified to handle 2 scenarios
     *          1) a string-number is passed as the days out (EX: "0")
     *          2) a string-function is passed as the days out (EX: "fx:GetDate; Daysout:0")
     * @version Created 11/11/2014
     * @author Waightstill W Avery
     * @param deptDateDaysOut
     *            - string used to define the days out in the request; see summary for more details
     * @throws NA
     * @return NA
     *
     */
    public void setDeptDate(String deptDateDaysOut) {
        if (deptDateDaysOut.contains("fx:GetDate; Daysout:")) {
            setRequestNodeValueByXPath("//resortPeriod/endDate", deptDateDaysOut);
        } else {
            setRequestNodeValueByXPath("//resortPeriod/endDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
        }
    }

    /**
     *
     * @summary Method to set the request arrival date - modified to handle 2 scenarios
     *          1) a string-number is passed as the days out (EX: "0")
     *          2) a string-function is passed as the days out (EX: "fx:GetDate; Daysout:0")
     * @version Created 11/11/2014
     * @author Waightstill W Avery
     * @param arrivalDaysOut
     *            - string used to define the days out in the request; see summary for more details
     * @throws NA
     * @return NA
     *
     */
    public void setArrivalDate(String arrivalDaysOut) {
        if (arrivalDaysOut.contains("fx:GetDate; Daysout:")) {
            setRequestNodeValueByXPath("//resortPeriod/startDate", arrivalDaysOut);
        } else {
            setRequestNodeValueByXPath("//resortPeriod/startDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
        }
    }

    public void setPackageCode(String packageCode) {
        setRequestNodeValueByXPath("//roomDetail/packageCode", packageCode);
    }

    public void setResortCode(String resortCode) {
        setRequestNodeValueByXPath("//roomDetail/resortCode", resortCode);
    }

    public void setRoomTypeCode(String roomTypeCode) {
        setRequestNodeValueByXPath("//roomDetail/roomTypeCode", roomTypeCode);
    }

    public void setLocationID(String locationID) {
        setRequestNodeValueByXPath("//request/locationId", locationID);
    }

    public void setSalesChannel(String salesChannel) {
        setRequestNodeValueByXPath("//salesChannel", salesChannel);
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//guestId");
    }

    public String getPartyId() {
        return getResponseNodeValueByXPath("//partyId");
    }

    public void setPrimaryGuestFirstName(String name) {
        setRequestNodeValueByXPath("//guestReferenceDetails/guest/firstName", name);
        setRequestNodeValueByXPath("//travelPlanGuest/firstName", name);
    }

    public void setPrimaryGuestLastName(String name) {
        setRequestNodeValueByXPath("//guestReferenceDetails/guest/lastName", name);
        setRequestNodeValueByXPath("//travelPlanGuest/lastName", name);
    }

}
