package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_RO_Shared extends TravelPlanBaseTest {

    private Book book;
    private HouseHold hh;
    private String packageCode = "";
    private static final int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        if (environment.toLowerCase().contains("_cm")) {
            environment = environment.toLowerCase().replace("_cm", "");
        }
        book = book();
        hh = getHouseHold();

        Share share = new Share(environment.toLowerCase().replace("_cm", ""), "Share");
        share.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request/roomNumber", "fx:removenode");
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request/locationId", getLocationId());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing TCG ID [" + book.getTravelComponentGroupingId() + "]: " + share.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_RO_Shared() {
        TestReporter.logScenario("Test Cancel RO Shared");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        TestReporter.assertNotNull(cancel.getCancellationNumber(), "Verify that a cancellation number was returned.");
        TestReporter.log("Cancellation number: " + cancel.getCancellationNumber());

        retrieveReservation();
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
        cancelHelper.verifyChargeGroupsCancelled();
        cancelHelper.verifyCancellationIsFoundInResHistory(book.getTravelPlanSegmentId(), book.getTravelComponentGroupingId(), book.getTravelComponentId());
        // cancelHelper.verifyCancellationComment(getRetrieve(), "Air not available CancellationNumber : " + cancel.getCancellationNumber());
        cancelHelper.verifyNumberOfCharges(1);
        cancelHelper.verifyInventoryReleased(book.getTravelComponentGroupingId());
        cancelHelper.verifyNumberOfTpPartiesByTpId(1);
        cancelHelper.verifyTcStatusByTcg(book.getTravelComponentGroupingId(), "Cancelled");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 2);
        cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 0);
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 1);
        cancelHelper.verifyNumberOfChargesByStatus("UnEarned", 0);
        // Verify the reasonID matches the reason code used for the given TCId
        // cancelHelper.verifyProductReasonID(book.getTravelComponentId());
        cancelHelper.verifyTPV3GuestRecordCreated(book.getTravelPlanId(), getHouseHold().primaryGuest());
        cancelHelper.verifyTPV3RecordCreated(book.getTravelPlanId());
        cancelHelper.verifyTPV3SalesOrderRecordCreated(book.getTravelPlanId());
        TestReporter.assertAll();
    }

    public static String removeCM(String cmEnv) {
        return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    }

    private Book book() {
        createHouseHold();
        Book book = new Book(environment.toLowerCase().replace("_cm", ""), "bookWithoutTickets");
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
            // String packageCode = "";
            do {
                try {
                    packageCode = pkg.retrievePackageCode(environment.toLowerCase().replace("_cm", ""), String.valueOf(locDaysOut),
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

            book.setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().getAllPhones().get(0).getNumber());
            book.setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().getAllEmails().get(0).getEmail());

            book.sendRequest();
            if (book.getResponseStatusCode().equals("200")) {
                bookSuccess = true;
            } else {
                tries++;
            }
        } while (!bookSuccess && tries < maxTries);
        TestReporter.assertTrue(book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking the prereq reservation: " + book.getFaultString());
        setTpId(book.getTravelPlanId());
        setTpsId(book.getTravelPlanSegmentId());
        setTcgId(book.getTravelComponentGroupingId());
        setTcId(book.getTravelComponentId());
        setArrivalDate(Randomness.generateCurrentXMLDate(locDaysOut));
        setDepartureDate(String.valueOf(Randomness.generateCurrentXMLDate(locDaysOut + locNights)));
        retrieveReservation(environment.toLowerCase().replace("_cm", ""));
        makeFirstNightDeposit(environment.toLowerCase().replace("_cm", ""));
        retrieveReservation(environment.toLowerCase().replace("_cm", ""));

        return book;
    }

}
