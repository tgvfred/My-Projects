package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.operations.FindRoomForReservation;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckOut;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckingIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
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
    private Book book;
    private String environment;
    private String[] roomRsrc;
    private String roomNumber;
    private String locationId;
    private String primaryGuestId;
    private String primaryPartyId;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

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

    public CheckInHelper(String environment, Book book) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (book == null) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setBook(book);
        }
        retrieveReservation();
    }

    public FindRoomForReservation findRoomForReservation() {
        FindRoomForReservation findRoom = new FindRoomForReservation(environment, "UI Booking");
        findRoom.setTravelPlanId(getBook().getTravelPlanId());
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
        checkingIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkingIn.sendRequest();
        TestReporter.assertTrue(checkingIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getBook().getTravelPlanId() + "]: " + checkingIn.getFaultString());
    }

    public void checkIn(String locationId, Integer daysOut, Integer nights, String facilityId) {
        checkIn(locationId, String.valueOf(daysOut), String.valueOf(nights), facilityId);
    }

    public void checkIn(String locationId, String daysOut, String nights, String facilityId) {
        checkingIn(locationId, daysOut, nights, facilityId);
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getLocationIdByTpId(getBook().getTravelPlanId())));
        CheckIn checkIn = new CheckIn(environment, "UI_Booking");
        checkIn.setGuestId(getPrimaryGuestId());
        checkIn.setLocationId(rs.getValue("WRK_LOC_ID", 1));
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
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
        TestReporter.assertTrue(checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getBook().getTravelPlanId() + "]: " + checkIn.getFaultString());
    }

    public void checkOut(String locationId) {
        CheckOut checkout = new CheckOut(environment, "Check_Out");
        checkout.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkout.setLocationId(locationId);
        checkout.sendRequest();
        if (checkout.getFaultString().contains("Row was updated or deleted by another transaction")) {
            Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
            checkout.sendRequest();
        }
        try {
            TestReporter.assertTrue(checkout.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-out TP ID [" + getBook().getTravelPlanId() + "]: " + checkout.getFaultString());
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
        retrieve.setRequestNodeValueByXPath("//request/travelPlanId", getBook().getTravelPlanId());
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams.getLocationIdByTpId(getBook().getTravelPlanId())));

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
