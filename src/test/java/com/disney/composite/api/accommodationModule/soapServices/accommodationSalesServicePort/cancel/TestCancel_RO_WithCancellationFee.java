package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.operations.FindRoomForReservation;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.roomInventoryModule.accommodationAssignmentServicePort.operations.AssignRoomForReservation;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_RO_WithCancellationFee extends TravelPlanBaseTest {

    private Book book;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        if (environment.toLowerCase().contains("_cm")) {
            environment = environment.toLowerCase().replace("_cm", "");
        }
        createHouseHold();
        book = new Book(environment, "bookWithoutTickets");
        int locDaysOut = 0;
        book.setArrivalDate(String.valueOf(locDaysOut));
        int locNights = 1;
        book.setDeptDate(String.valueOf(String.valueOf(locDaysOut + locNights)));

        boolean bookSuccess = false;
        int tries = 0;
        int maxTries = 10;
        PackageCodes pkg = new PackageCodes();
        do {
            setValues("80010402", "EP", "42");

            pkg = new PackageCodes();
            boolean success = false;
            String packageCode = "";
            do {
                try {
                    packageCode = pkg.retrievePackageCode(environment, String.valueOf(locDaysOut),
                            getLocationId(), "DRC RO", "", getResortCode(), getRoomTypeCode(), "");
                    success = true;
                } catch (AssertionError e) {
                    if (!getNoPackageCodes().containsKey(getResortCode() + ":" + getLocationId() + ":" + getRoomTypeCode())) {
                        String message = "No package code found for resort[" + getResortCode() + "], locationId[" + getLocationId() + "], and roomType[" + getRoomTypeCode() + "]:";
                        addToNoPackageCodes(getResortCode() + ":" + getLocationId() + ":" + getRoomTypeCode(), message);
                    }
                    setValues("80010402", "EP", "43");
                }
            } while (!success);

            book.setPackageCode(packageCode);
            book.setResortCode(getResortCode());
            book.setRoomTypeCode(getRoomTypeCode());
            book.setLocationID(getLocationId());
            book.setPrimaryGuestFirstName(getHouseHold().primaryGuest().getFirstName());
            book.setPrimaryGuestFirstNameGuestRefDetails(getHouseHold().primaryGuest().getFirstName());
            book.setPrimaryGuestFirstNameTravelPlan(getHouseHold().primaryGuest().getFirstName());
            book.setPrimaryGuestLastName(getHouseHold().primaryGuest().getLastName());
            book.setPrimaryGuestLastNameGuestRefDetails(getHouseHold().primaryGuest().getLastName());
            book.setPrimaryGuestLastNameTravelPlan(getHouseHold().primaryGuest().getLastName());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName", getHouseHold().primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/regionName", getHouseHold().primaryGuest().primaryAddress().getState());
            book.setPhoneNumber(getHouseHold().primaryGuest().primaryPhone().getNumber());
            book.setEmail(getHouseHold().primaryGuest().primaryEmail().getEmail());
            book.sendRequest();
            if (book.getResponseStatusCode().equals("200")) {
                bookSuccess = true;
            } else {
                tries++;
            }
        } while (!bookSuccess && tries < maxTries);
        TestReporter.assertTrue(book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking the prereq reservation: " + book.getFaultString());
        setBook(book);

        FindRoomForReservation findRoom = new FindRoomForReservation(environment, "UI Booking");
        findRoom.setTravelPlanId(book.getTravelPlanId());
        findRoom.setNumberOfResponseRows("50");
        findRoom.sendRequest();
        TestReporter.assertTrue(findRoom.getResponseStatusCode().equals("200"), "Verify no error occurred finding a room for a reservation: " + findRoom.getFaultString());

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
            assignRoom.setArrivalAndDepartureDaysOut(String.valueOf(locDaysOut), String.valueOf(locNights));
            assignRoom.setAssignmentOwnerNumber(findRoom.getAssignmentOwnerNumber());
            assignRoom.setFacilityId(getFacilityId());
            assignRoom.setRoomNumber(roomNumber);
            assignRoom.setRoomResourceNumber(resourceId);
            assignRoom.sendRequest();
            if (assignRoom.getFaultString().contains("LOCK ASSIGNMENT ERROR")) {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
                assignRoom.sendRequest();
            }
            if (assignRoom.getResponseStatusCode().equals("200")) {
                roomAdded = true;
            }
        }
        ;
        TestReporter.assertTrue(roomAdded, "Verify no error occurred assigning a room to a reservation: " + assignRoom.getFaultString());

        retrieveReservation(environment);
        // makeFirstNightDeposit(environment);
        retrieveReservation(environment);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_RO_WithCancellationFee() {
        TestReporter.logScenario("Test Cancel RO With Cancellation Fee");

        Cancel cancel = new Cancel(environment, "Main_WithFee");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());

        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/overridden", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/waived", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/overriddenCancelFee", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        TestReporter.assertNotNull(cancel.getCancellationNumber(), "Verify that a cancellation number was returned.");
        TestReporter.log("Cancellation number: " + cancel.getCancellationNumber());

        retrieveReservation(environment);
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancelDate").split("T")[0], DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the cancel date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancelDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber"),
                cancel.getCancellationNumber(),
                "Verify that the cancellation number [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber") + "] is that which is expected [" + cancel.getCancellationNumber() + "].");
        int index = 0;
        int numAuditDetails;
        try {
            numAuditDetails = XMLTools.getNodeList(XMLTools.makeXMLDocument(getRetrieve().getResponse()), "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails/status").getLength();
            for (int i = 1; i <= numAuditDetails; i++) {
                if (getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(i) + "]/status").equals("Cancelled")) {
                    index = i;
                    break;
                }
            }
        } catch (XPathNotFoundException e) {
            throw e;
        }
        TestReporter.assertTrue(index > 0, "Verify that a cancellation audit details node was returned in the retrieve.");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/createdDate").split("T")[0],
                DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the audit details cancellation created date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/createdDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/updatedDate").split("T")[0],
                DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the audit details cancellation updated date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/updatedDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/status"),
                "Cancelled",
                "Verify that the audit details cancellation status [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/status") + "] is that which is expected [Cancelled].");
        TestReporter.assertAll();

        validations(cancel);
    }

    private void validations(Cancel cancel) {
        CancelHelper cancelHelper = new CancelHelper(removeCM(environment), book.getTravelPlanId());
        // Checks root and node charge group status is set to cancelled
        cancelHelper.verifyChargeGroupsCancelled();
        // Checks for canclled status is reservation history using tp, tps, tc, tcg
        cancelHelper.verifyCancellationIsFoundInResHistory(book.getTravelPlanSegmentId(), book.getTravelComponentGroupingId(), book.getTravelComponentId());
        // Verify number of charges -- One left -- fee from initial deposit
        cancelHelper.verifyNumberOfCharges(1);
        // Verify cancellation fee was created in Folio
        cancelHelper.verifyCancellationFee();
        // Checks for RIM inventory release
        cancelHelper.verifyInventoryReleased(book.getTravelComponentGroupingId());
        // Validate number of parties for the tpID
        cancelHelper.verifyNumberOfTpPartiesByTpId(1);
        // Checks for cancelled TC status for first tcgID
        cancelHelper.verifyTcStatusByTcg(book.getTravelComponentGroupingId(), "Cancelled");
        // Looks for exchange fee if set
        cancelHelper.verifyExchangeFeeFound(false);
        // Verify 2 cancelled charge group status
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 2);
        // Check for 1 charge in cancelled status
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 1);
        cancelHelper.verifyTPV3GuestRecordCreated(getBook().getTravelPlanId(), getHouseHold().primaryGuest());
        cancelHelper.verifyTPV3RecordCreated(getBook().getTravelPlanId());
        cancelHelper.verifyTPV3SalesOrderRecordCreated(getBook().getTravelPlanId());
        cancelHelper.verifyTcFeeByTcg(getBook().getTravelComponentGroupingId(), true);
        TestReporter.assertAll();
    }

    public static String removeCM(String cmEnv) {
        return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    }

}
