package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_RO_Shared extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        // setIsShared(true);

        bookReservation();

        Share share = new Share(environment.toLowerCase().replace("_cm", ""));
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request/roomNumber", "fx:removenode");
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request/locationId", getLocationId());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing TCG ID [" + getBook().getTravelComponentGroupingId() + "]: " + share.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_RO_Shared() {
        TestReporter.logScenario("Test Cancel RO Shared");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
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
        CancelHelper cancelHelper = new CancelHelper(removeCM(environment), getBook().getTravelPlanId());
        cancelHelper.verifyChargeGroupsCancelled();
        cancelHelper.verifyCancellationIsFoundInResHistory(getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());
        // cancelHelper.verifyCancellationComment(getRetrieve(), "Air not available CancellationNumber : " + cancel.getCancellationNumber());
        cancelHelper.verifyNumberOfCharges(1);
        cancelHelper.verifyInventoryReleased(getBook().getTravelComponentGroupingId());
        cancelHelper.verifyNumberOfTpPartiesByTpId(1);
        cancelHelper.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Cancelled");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 2);
        cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 0);
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 1);
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
