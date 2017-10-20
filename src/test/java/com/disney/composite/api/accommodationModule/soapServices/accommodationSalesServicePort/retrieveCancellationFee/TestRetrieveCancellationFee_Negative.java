package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestRetrieveCancellationFee_Negative extends AccommodationBaseTest {
    private CancelHelper cancelHelper;
    private CheckInHelper checkedInHelper;
    private String tpId;
    private String tcgId;
    private String locVar;

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        locVar = environment;
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullRequest() {
        String faultString = "Missing Required Parameters";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentityDetails() {
        String date = Randomness.generateCurrentXMLDate();
        String faultString = "Missing Required Parameters";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentifyDetailsIdAndExtRef() {
        String date = Randomness.generateCurrentXMLDate();
        String idLevel = "TravelPlanSegment";
        String faultString = "Missing Required Parameters TPS Identity Level";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setIdentityLevel(idLevel);
        fee.setID(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    // @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentifyDetailsIdentityLevel() { // NPE ISSUE ON BOARD TK-670903
        String date = Randomness.generateCurrentXMLDate();
        String id = "472911928036";
        String refType = "RESERVATION";
        String refNum = "875hhg03hg30hg";
        String refSource = "DPMSProperty";
        String faultString = "null";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setReferenceType(refType);
        fee.setReferenceNumber(refNum);
        fee.setReferenceSource(refSource);
        fee.setReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setIdentityLevel(BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    // @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentiyDetailsExtRef() { // NPE ISSUE ON BOARD Task TK-670913
        String date = Randomness.generateCurrentXMLDate();
        String refType = "RESERVATION";
        String refNum = "875hhg03hg30hg";
        String refSource = "DPMSProperty";
        String idLevel = "TravelPlanSegment";
        String faultString = "null";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setReferenceType(refType);
        fee.setReferenceNumber(refNum);
        fee.setReferenceSource(refSource);
        fee.setReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setIdentityLevel(idLevel);
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    // @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_InvalidTpsAndExtRefNumber() { // NPE ISSUE ON BOARD Task TK-671458
        String date = Randomness.generateCurrentXMLDate();
        String id = "-1";
        String refType = "RESERVATION";
        String refNum = "-1";
        String refSource = "DPMSProperty";
        String faultString = "null";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setReferenceType(refType);
        fee.setReferenceNumber(refNum);
        fee.setReferenceSource(refSource);
        fee.setReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setIdentityLevel(BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative", "debug" })
    public void TestRetrieveCancellationFee_Cancelled() {
        String date = Randomness.generateCurrentXMLDate();
        String id = "472911928036";
        String idLevel = "TravelPlanSegment";
        tcgId = getBook().getTravelComponentGroupingId();
        String faultString = "Missing Required Parameters TPS Identity Level";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);

        TestReporter.logScenario("Cancel");
        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(tcgId);
        cancel.sendRequest();

        System.out.println(cancel.getRequest());
        System.out.println(cancel.getResponse());
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        System.out.println(fee.getRequest());
        System.out.println(fee.getResponse());

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_CheckedIn() {
        String date = Randomness.generateCurrentXMLDate();
        String id = "472911928036";
        String idLevel = "TravelPlanSegment";
        tpId = getBook().getTravelPlanId();
        String faultString = "Cannot Calculate Cancellation Fee for cancelled or checked in or checked out reservation";
        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        checkedInHelper = new CheckInHelper(locVar, getBook());
        checkedInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }
}
