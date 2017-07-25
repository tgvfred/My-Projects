package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_RO_AdditionalAdult extends TravelPlanBaseTest {

    private Book book;
    private String packageCode;
    private static final int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        daysOut.set(Randomness.randomNumberBetween(15, 120));
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setValues("80010402", "EA", "42");
        createHouseHold();

        book = new Book(environment, "3Adults");

        int bookTries = 0;
        PackageCodes pkg = new PackageCodes();
        boolean bookSuccess = false;
        do {
            book.setArrivalDate(String.valueOf(getDaysOut()));
            book.setDeptDate(String.valueOf(getDaysOut() + getNights()));

            int tries = 0;
            pkg = new PackageCodes();
            boolean success = false;
            do {
                try {
                    packageCode = pkg.retrievePackageCode(environment, String.valueOf(getDaysOut()),
                            getLocationId(), "DRC RO", "", getResortCode(), getRoomTypeCode(), "");
                    success = true;
                } catch (AssertionError e) {
                    setValues("80010402", "EA", "42");
                }
            } while (!success && ++tries < maxTries);

            TestReporter.assertTrue(success, "Successfully found package code");
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
            TestReporter.logAPI(false, "", book);
            if (book.getResponseStatusCode().equals("200")) {
                bookSuccess = true;
            } else {
                if (fixedDates != null) {
                    if (fixedDates.get() != null) {
                        if (fixedDates.get() != true) {
                            daysOut.set(Randomness.randomNumberBetween(15, 120));
                            nights.set(1);
                            arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
                            departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
                        }
                    }
                }
                setValues("80010402", "EA", "42");
            }
        } while (!bookSuccess && ++bookTries < maxTries);
        TestReporter.assertEquals(book.getResponseStatusCode(), "200", "An error occurred booking a prereq reservations: " + book.getFaultString());
        setBook(book);
        retrieveReservation(environment);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_RO_ADA() {
        TestReporter.logScenario("Test Cancel RO ADA");

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
        cancelHelper.verifyNumberOfCharges(0);
        cancelHelper.verifyInventoryReleased(book.getTravelComponentGroupingId());
        cancelHelper.verifyNumberOfTpPartiesByTpId(3);
        cancelHelper.verifyTcStatusByTcg(book.getTravelComponentGroupingId(), "Cancelled");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 2);
        cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 0);
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 0);
        cancelHelper.verifyNumberOfChargesByStatus("UnEarned", 0);
        // Verify the reasonID matches the reason code used for the given TCId
        // cancelHelper.verifyProductReasonID(book.getTravelComponentId());
        cancelHelper.verifyTPV3GuestRecordCreated(getBook().getTravelPlanId(), getHouseHold().primaryGuest());
        cancelHelper.verifyTPV3RecordCreated(getBook().getTravelPlanId());
        cancelHelper.verifyTPV3SalesOrderRecordCreated(getBook().getTravelPlanId());
        TestReporter.assertAll();
    }

    public static String removeCM(String cmEnv) {
        return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    }

}