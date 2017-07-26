package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.WebService;
import com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.operations.FindRoomForReservation;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckOut;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckingIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Add;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.roomInventoryModule.accommodationAssignmentServicePort.operations.AssignRoomForReservation;
import com.disney.api.soapServices.roomInventoryModule.accommodationStatusComponentService.operations.UpdateSingleRoomStatus;
import com.disney.api.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;

public class CheckInHelper {
    private String environment;
    private String[] roomRsrc;
    private String roomNumber;
    private String locationId;
    private String primaryGuestId;
    private String primaryPartyId;
    private WebService ws;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String[] getRoomRsrc() {
        return roomRsrc;
    }

    public void setRoomRsrc(String[] roomRsrc) {
        this.roomRsrc = roomRsrc;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPrimaryGuestId() {
        return primaryGuestId;
    }

    public void setPrimaryGuestId(String primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
    }

    public String getPrimaryPartyId() {
        return primaryPartyId;
    }

    public void setPrimaryPartyId(String primaryPartyId) {
        this.primaryPartyId = primaryPartyId;
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

    public CheckInHelper(String environment, WebService ws) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (ws == null) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setWs(ws);
            if (ws instanceof ReplaceAllForTravelPlanSegment) {
                setTpId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanId());
                setTpsId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanSegmentId());
                setTcgId(((ReplaceAllForTravelPlanSegment) ws).getTravelComponentGroupingId());
                setTcId(((ReplaceAllForTravelPlanSegment) ws).getTravelComponentId());
            } else if (ws instanceof ReplaceAllForTravelPlanSegment) {
                setTpId(((Add) ws).getTravelPlanId());
                setTpsId(((Add) ws).getTravelPlanSegmentId());
                setTcgId(((Add) ws).getTravelComponentGroupingId());
                setTcId(((Add) ws).getTravelComponentId());
            }
        }
        retrieveReservation();
    }

    public FindRoomForReservation findRoomForReservation() {
        FindRoomForReservation findRoom = new FindRoomForReservation(environment, "UI Booking");
        findRoom.setTravelPlanId(getTpId());
        findRoom.setNumberOfResponseRows("50");
        findRoom.sendRequest();
        TestReporter.assertTrue(findRoom.getResponseStatusCode().equals("200"), "Verify no error occurred finding a room for a reservation: " + findRoom.getFaultString());
        return findRoom;
    }

    public String[] assignRoomForReservation(FindRoomForReservation findRoom, String daysOut, String nights, String facilityId) {
        String resourceId = null;
        String roomNumber = null;
        AssignRoomForReservation assignRoom = null;
        boolean roomAdded = false;
        Map<String, String> values = findRoom.getAllRoomAndResourceIds();
        Iterator<Entry<String, String>> it = values.entrySet().iterator();
        while (!roomAdded && it.hasNext()) {
            Entry<String, String> et = it.next();
            roomNumber = et.getKey();
            resourceId = et.getValue();

            assignRoom = new AssignRoomForReservation(environment, "UI Booking");
            assignRoom.setArrivalAndDepartureDaysOut(daysOut, nights);
            assignRoom.setAssignmentOwnerNumber(findRoom.getAssignmentOwnerNumber());
            assignRoom.setFacilityId(facilityId);
            assignRoom.setRoomNumber(roomNumber);
            assignRoom.setRoomResourceNumber(resourceId);

            int tries = 0;
            int maxTries = 3;
            do {
                assignRoom.sendRequest();
                if (!assignRoom.getResponseStatusCode().equals("200")) {
                    Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
                }
                tries++;
            } while (tries < maxTries && !assignRoom.getResponseStatusCode().equals("200"));
            if (assignRoom.getResponseStatusCode().equals("200")) {
                roomAdded = true;
            }
        }
        ;
        if (assignRoom == null) {
            TestReporter.log("\n\nRQ:\n\n");
            TestReporter.logNoXmlTrim(findRoom.getRequest());
            TestReporter.log("\n\nRS:\n\n");
            TestReporter.logNoXmlTrim(findRoom.getResponse());
        }
        TestReporter.assertNotNull(assignRoom, "No rooms were found for this reservation.");
        TestReporter.assertTrue(roomAdded, "Verify no error occurred assigning a room to a reservation: " + assignRoom.getFaultString());

        setRoomRsrc(new String[2]);
        getRoomRsrc()[0] = roomNumber;
        getRoomRsrc()[1] = resourceId;
        setRoomNumber(roomNumber);
        return getRoomRsrc();
    }

    public void checkingIn(String locationId, Integer daysOut, Integer nights, String facilityId) {
        checkingIn(locationId, String.valueOf(daysOut), String.valueOf(nights), facilityId);
    }

    public void checkingIn(String locationId, String daysOut, String nights, String facilityId) {
        FindRoomForReservation findRoom = findRoomForReservation();
        setRoomRsrc(assignRoomForReservation(findRoom, daysOut, nights, facilityId));
        updateSingleRoomStatus("updateToCleanAndVacant");

        CheckingIn checkingIn = new CheckingIn(environment, "UI_Booking");
        checkingIn.setLocationId(locationId);
        checkingIn.setTravelComponentGroupingId(getTcgId());
        checkingIn.sendRequest();
        TestReporter.assertTrue(checkingIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getTpId() + "]: " + checkingIn.getFaultString());
    }

    public void checkIn(String locationId, Integer daysOut, Integer nights, String facilityId) {
        checkIn(locationId, String.valueOf(daysOut), String.valueOf(nights), facilityId);
    }

    public void checkIn(String locationId, String daysOut, String nights, String facilityId) {
        checkingIn(locationId, daysOut, nights, facilityId);
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getLocationIdByTpId(getTpId())));
        CheckIn checkIn = new CheckIn(environment, "UI_Booking");
        checkIn.setGuestId(getPrimaryGuestId());
        checkIn.setLocationId(rs.getValue("WRK_LOC_ID", 1));
        checkIn.setTravelComponentGroupingId(getTcgId());
        int maxTries = 20;
        int tries = 0;
        do {
            Sleeper.sleep(Randomness.randomNumberBetween(3, 5) * 1000);
            checkIn.sendRequest();
            if (!checkIn.getResponseStatusCode().equals("200")) {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
            }
            tries++;
        } while (!checkIn.getResponseStatusCode().equals("200") && tries < maxTries);
        TestReporter.assertTrue(checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getTpId() + "]: " + checkIn.getFaultString());
    }

    public void checkOut(String locationId) {
        CheckOut checkout = new CheckOut(environment, "Check_Out");
        checkout.setTravelComponentGroupingId(getTcgId());
        checkout.setLocationId(locationId);
        checkout.sendRequest();
        if (checkout.getFaultString().contains("Row was updated or deleted by another transaction")) {
            Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
            checkout.sendRequest();
        }
        try {
            TestReporter.assertTrue(checkout.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-out TP ID [" + getTpId() + "]: " + checkout.getFaultString());
        } catch (AssertionError e) {
        }
        ;

        updateSingleRoomStatus("updateToCleanAndVacant");
    }

    public void updateSingleRoomStatus(String scenario, String roomNumner, String resourceId) {
        UpdateSingleRoomStatus updateStatus = new UpdateSingleRoomStatus(environment, scenario);
        updateStatus.setResourceId(roomNumner);
        updateStatus.setRoomNumber(resourceId);
        updateStatus.sendRequest();
    }

    public void updateSingleRoomStatus(String scenario) {
        UpdateSingleRoomStatus updateStatus = new UpdateSingleRoomStatus(environment, scenario);
        updateStatus.setResourceId(getRoomRsrc()[1]);
        updateStatus.setRoomNumber(getRoomRsrc()[0]);
        updateStatus.sendRequest();
    }

    public void retrieveReservation() {
        Retrieve retrieve = new Retrieve(getEnvironment(), "Main");
        retrieve.setRequestNodeValueByXPath("//request/travelPlanId", getTpId());
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams.getLocationIdByTpId(getTpId())));

        do {
            retrieve.setRequestNodeValueByXPath("//request/locationId", rs.getValue("WRK_LOC_ID"));
            retrieve.sendRequest();
            if (retrieve.getResponseStatusCode().equals("200")) {
                setLocationId(rs.getValue("WRK_LOC_ID"));
                break;
            } else {
                rs.moveNext();
            }
        } while (rs.hasNext());

        TestReporter.assertTrue(retrieve.getResponseStatusCode().equals("200"), "Verify that an error did not occurred retrieving the prereq reservation: " + retrieve.getFaultString());
        setPrimaryPartyId(retrieve.getPartyId());
        setPrimaryGuestId(retrieve.getGuestId());
    }
}
