package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.AddAccommodationHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCancel_addAccomm_checkInOne_cancelOne_tcgOnly extends AccommodationBaseTest {
    private AddAccommodationHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        setIsADA(false);
        bookReservation();

        helper = new AddAccommodationHelper(Environment.getBaseEnvironmentName(getEnvironment()), getBook());
        helper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(), getLocationId());
        CheckInHelper checkInHelper = new CheckInHelper(Environment.getBaseEnvironmentName(getEnvironment()), helper.getWs());
        checkInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_addAccomm_checkInOne_cancelOne_tcgOnly() {
        TestReporter.logScenario("Test Cancel RO ADA");

        String faultString = " Accommodation should be in Booked status to be cancelled :";
        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));

        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getFaultString().contains(faultString), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_CANCELLED);
        // TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(),
        // cancel);
        // TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
        //
        // TestReporter.assertNotNull(cancel.getCancellationNumber(), "Verify that a cancellation number was returned.");
        // TestReporter.log("Cancellation number: " + cancel.getCancellationNumber());
        //
        // retrieveReservation();
        // TestReporter.setAssertFailed(false);
        // try {
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancelDate");
        // TestReporter.assertTrue(false, "Verify that no cancellation date was found for the TPS");
        // } catch (XPathNotFoundException e) {
        // TestReporter.assertTrue(true, "Verify that no cancellation date was found for the TPS");
        // }
        // TestReporter.softAssertEquals(getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber"),
        // "0", "Verify that the cancellation number [" +
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber") + "] is that which
        // is expected [0].");
        //
        // int index = 0;
        // int numAuditDetails;
        // try {
        // numAuditDetails = XMLTools.getNodeList(XMLTools.makeXMLDocument(getRetrieve().getResponse()),
        // "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails/status").getLength();
        // for (int i = 1; i <= numAuditDetails; i++) {
        // if (getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(i) +
        // "]/status").equals("Cancelled")) {
        // index = i;
        // break;
        // }
        // }
        // } catch (XPathNotFoundException e) {
        // throw e;
        // }
        // TestReporter.assertTrue(index > 0, "Verify that a cancellation audit details node was returned in the retrieve.");
        // TestReporter.softAssertEquals(
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/createdDate").split("T")[0],
        // DateTimeConversion.ConvertToDateYYYYMMDD("0"),
        // "Verify that the audit details cancellation created date [" +
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/createdDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        // TestReporter.softAssertEquals(
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/updatedDate").split("T")[0],
        // DateTimeConversion.ConvertToDateYYYYMMDD("0"),
        // "Verify that the audit details cancellation updated date [" +
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/updatedDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        // TestReporter.softAssertEquals(
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/status"),
        // "Cancelled",
        // "Verify that the audit details cancellation status [" +
        // getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) +
        // "]/status") + "] is that which is expected [Cancelled].");
        // TestReporter.assertAll();
        //
        // validations(cancel);
    }

    // private void validations(Cancel cancel) {
    // CancelHelper cancelHelper = new CancelHelper(removeCM(environment), getBook().getTravelPlanId());
    // cancelHelper.verifyChargeGroupsCancelled();
    // cancelHelper.verifyCancellationIsFoundInResHistory(getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(),
    // getBook().getTravelComponentId());
    // // cancelHelper.verifyCancellationComment(getRetrieve(), "Air not available CancellationNumber : " + cancel.getCancellationNumber());
    // cancelHelper.verifyNumberOfCharges(1);
    // cancelHelper.verifyInventoryReleased(getBook().getTravelComponentGroupingId());
    // cancelHelper.verifyNumberOfTpPartiesByTpId(2);
    // cancelHelper.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Cancelled");
    // cancelHelper.verifyExchangeFeeFound(false);
    // cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 1);
    // cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 2);
    // cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 1);
    // cancelHelper.verifyNumberOfChargesByStatus("UnEarned", 1);
    // // Verify the reasonID matches the reason code used for the given TCId
    // // cancelHelper.verifyProductReasonID(book.getTravelComponentId());
    // cancelHelper.verifyTPV3GuestRecordCreated(getBook().getTravelPlanId(), getHouseHold().primaryGuest());
    // cancelHelper.verifyTPV3RecordCreated(getBook().getTravelPlanId());
    // cancelHelper.verifyTPV3SalesOrderRecordCreated(getBook().getTravelPlanId());
    // TestReporter.assertAll();
    //
    // cancelHelper = new CancelHelper(removeCM(environment), helper.getTpId());
    // cancelHelper.verifyChargeGroupsCancelled();
    // cancelHelper.verifyCancellationNotFoundInResHistory(helper.getTpsId(), helper.getTcgId(), helper.getTcId());
    // cancelHelper.verifyNumberOfCharges(1);
    // cancelHelper.setInventoryReleased(false);
    // cancelHelper.verifyInventoryReleased(helper.getTcgId());
    // cancelHelper.verifyTcStatusByTcg(helper.getTcgId(), "Booked");
    // cancelHelper.verifyNumberOfTpPartiesByTpId(2);
    // cancelHelper.verifyExchangeFeeFound(false);
    // cancelHelper.verifyTPV3RecordCreated(helper.getTpId());
    // cancelHelper.verifyTPV3SalesOrderRecordCreated(helper.getTpId());
    // TestReporter.assertAll();
    // }
    //
    // public static String removeCM(String cmEnv) {
    // return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    // }
}
