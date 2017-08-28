package com.disney.api.soapServices.accommodationModule.helpers;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.WebService;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Add;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.utils.TestReporter;

public class AddAccommodationHelper {
    private String environment;
    private WebService ws;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;
    private String daysOut;
    private String nights;
    private String locationId;
    private String resortCode;
    private String roomTypeCode;
    private String packageCode;
    private Add add;

    public Add getAdd() {
        return add;
    }

    public void setAdd(Add add) {
        this.add = add;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public WebService getWs() {
        return ws;
    }

    public void setWs(WebService ws) {
        this.ws = ws;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getDaysOut() {
        return daysOut;
    }

    public void setDaysOut(String daysOut) {
        this.daysOut = daysOut;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getResortCode() {
        return resortCode;
    }

    public void setResortCode(String resortCode) {
        this.resortCode = resortCode;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getTcgId() {
        return tcgId;
    }

    public void setTcgId(String tcgId) {
        this.tcgId = tcgId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public AddAccommodationHelper(String environment, WebService ws) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment cannot be empty or null.");
        } else {
            setEnvironment(environment);
        }
        if (ws == null) {
            throw new AutomationException("The WebService object cannot be null.");
        } else {
            setWs(ws);
        }

        if (ws instanceof Book) {
            setTpId(((Book) ws).getTravelPlanId());
            setTpsId(((Book) ws).getTravelPlanSegmentId());
        } else if (ws instanceof ReplaceAllForTravelPlanSegment) {
            setTpId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanId());
            setTpsId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanSegmentId());
        } else {
            throw new AutomationException("The WebService object was not of a type supported by this class.");
        }
    }

    public Add addAccommodation(String resortCode, String roomTypeCode, String packageCode, int daysOut, int nights, String locationId) {
        return addAccommodation(resortCode, roomTypeCode, packageCode, String.valueOf(daysOut), String.valueOf(nights), locationId);
    }

    public Add addAccommodation(String resortCode, String roomTypeCode, String packageCode, String daysOut, String nights, String locationId) {
        setResortCode(resortCode);
        setRoomTypeCode(roomTypeCode);
        setPackageCode(packageCode);
        setDaysOut(daysOut);
        setNights(nights);
        setLocationId(locationId);

        Add addAccommodation = new Add(getEnvironment(), "Main");
        addAccommodation.setTravelPlanId(getTpId());
        addAccommodation.setTravelPlanSegmentId(getTpsId());
        addAccommodation.setPackageCode(getPackageCode());
        addAccommodation.setResortCode(getResortCode());
        addAccommodation.setRoomTypeCode(getRoomTypeCode());
        addAccommodation.setBookingDate("0");
        addAccommodation.setArrivalDate(getDaysOut());
        addAccommodation.setDeptDate(String.valueOf(Integer.parseInt(getDaysOut()) + Integer.parseInt(getNights())));
        addAccommodation.setLocationID(getLocationId());
        addAccommodation.sendRequest();
        if (!addAccommodation.getResponseStatusCode().equals("200")) {
            System.out.println(((Book) getWs()).getRequest());
            System.out.println(((Book) getWs()).getResponse());
        }
        TestReporter.assertTrue(addAccommodation.getResponseStatusCode().equals("200"), "Verify that no error occurred adding an accommodation: " + addAccommodation.getFaultString());
        setTcgId(addAccommodation.getTravelComponentGroupingId());
        setTcId(addAccommodation.getTravelComponentId());
        return addAccommodation;
    }
}