package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.BookReservations;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_GroupWithTickets extends TravelPlanBaseTest {

    private BookReservations book;
    private String ticketGroupName;
    private String locEnv;
    private static final int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        if (environment.toLowerCase().contains("_cm")) {
            locEnv = environment.toLowerCase().replace("_cm", "");
        } else {
            locEnv = environment;
        }
        setValues();

        int tries = 0;
        book = new BookReservations(locEnv, "WDTC_1Adult_Tickets");
        setArrivalDate(45);
        setDepartureDate(1);
        PackageCodes pkg = new PackageCodes();
        String packageCode = null;
        boolean success = false;
        do {
            try {
                packageCode = pkg.retrievePackageCode(locEnv, String.valueOf(getDaysOut()),
                        getLocationId(), "WDW PKG", "*WDTC", getResortCode(), getRoomTypeCode(), "R MYW Pkg + Deluxe Dining");
                success = true;
            } catch (AssertionError e) {
                setValues();
            }
        } while (!success && ++tries < maxTries);

        TestReporter.assertTrue(success, "Successfully found package code");
        book.setPackageCode(packageCode);
        book.setResortCode(getResortCode());
        book.setRoomTypeCode(getRoomTypeCode());
        book.setAreaPeriodStartDate(getArrivalDate());
        book.setAreaPeriodEndDate(getDepartureDate());
        book.setResortAreaStartDate(getArrivalDate());
        book.setResortAreaEndDate(getDepartureDate());

        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(locEnv);
        find.setPackageCode(packageCode);
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + packageCode + "].");
        book.setTicketGroup(find.getTicketGroupName());
        ticketGroupName = find.getTicketGroupName();

        GetTicketProducts get = new GetTicketProducts(locEnv, "Main");
        get.setTicketGroupName(ticketGroupName);
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + ticketGroupName + "].");
        String admissionProductId = get.getAdmissionProductIdByTicketDescription("2 Day Base Ticket");
        book.setTicketDetailsBaseAdmissionProductId(admissionProductId);
        book.setTicketDetailsCode(admissionProductId);

        createHouseHold();
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        try {
            book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelAgent", BaseSoapCommands.REMOVE_NODE.toString());
        } catch (XPathNotFoundException e) {

        }

        book.sendRequest();
        if (book.getResponse().contains("Error Invoking Pricing Service")) {
            System.out.println();
        }
        TestReporter.assertTrue(book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a prereq reservation: " + book.getFaultString());
        setTpId(book.getTravelPlanId());
        retrieveReservation(locEnv);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_GroupBundle_CancelRoom() {
        TestReporter.logScenario("Test Cancel");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));

        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request", BaseSoapCommands.ADD_NODE.commandAppend("cancelReasonCode"));
        cancel.setRequestNodeValueByXPath("//cancelReasonCode", "AIR");
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
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
        cancelHelper.verifyCancellationComment(getRetrieve(), "Air not available CancellationNumber : " + cancel.getCancellationNumber());
        cancelHelper.verifyNumberOfCharges(0);
        cancelHelper.verifyInventoryReleased(book.getTravelComponentGroupingId());
        cancelHelper.verifyNumberOfTpPartiesByTpId(2);
        cancelHelper.verifyTcStatusByTcg(book.getTravelComponentGroupingId(), "Cancelled");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 2);
        cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 0);
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 0);
        cancelHelper.verifyNumberOfChargesByStatus("UnEarned", 0);
        // Verify the reasonID matches the reason code used for the given TCId
        cancelHelper.verifyProductReasonID(book.getTravelComponentId());
        cancelHelper.verifyTPV3GuestRecordCreated(book.getTravelPlanId(), getHouseHold().primaryGuest());
        cancelHelper.verifyTPV3RecordCreated(book.getTravelPlanId());
        cancelHelper.verifyTPV3SalesOrderRecordCreated(book.getTravelPlanId());
        TestReporter.assertAll();
    }

    public static String removeCM(String cmEnv) {
        return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    }
}
