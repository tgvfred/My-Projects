package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestRetrieveCancellationFee_Negative extends AccommodationBaseTest {
    private CheckInHelper checkedInHelper;
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
        String faultString = "Travel Plan Segment Should not be NULL";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setIdentityLevel(idLevel);
        fee.setID(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentifyDetailsIdentityLevel() { // NPE ISSUE ON BOARD TK-670903
        String date = Randomness.generateCurrentXMLDate();
        String id = "472911928036";
        String refType = "RESERVATION";
        String refNum = "875hhg03hg30hg";
        String refSource = "DPMSProperty";
        String faultString = "Travel Plan Segment Should not be NULL";

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
        validateApplicationError(fee, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_NullIdentiyDetailsExtRef() { // NPE ISSUE ON BOARD Task TK-670913
        String date = Randomness.generateCurrentXMLDate();
        String refType = "RESERVATION";
        String refNum = "875hhg03hg30hg";
        String refSource = "DPMSProperty";
        String idLevel = "TravelPlanSegment";
        String faultString = "Travel Plan Segment Should not be NULL";

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
        validateApplicationError(fee, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_InvalidTpsAndExtRefNumber() { // NPE ISSUE ON BOARD Task TK-671458
        String date = Randomness.generateCurrentXMLDate();
        String id = "-1";
        String refType = "RESERVATION";
        String refNum = "-1";
        String refSource = "DPMSProperty";
        String faultString = "Travel Plan Segment Should not be NULL";

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
        validateApplicationError(fee, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative", "debug" })
    public void TestRetrieveCancellationFee_Cancelled() {
        String date = Randomness.generateCurrentXMLDate();
        String idLevel = "TravelPlanSegment";
        String faultString = "Cannot Calculate Cancellation Fee for cancelled or checked in or checked out reservation";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        cancel(getBook().getTravelComponentGroupingId());
        fee.setCancelDate(date);
        fee.setID(getBook().getTravelPlanSegmentId());
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_CheckedIn() {
        String date = Randomness.generateCurrentXMLDate();
        String idLevel = "TravelPlanSegment";

        String faultString = "Cannot Calculate Cancellation Fee for cancelled or checked in or checked out reservation";
        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        checkedInHelper = new CheckInHelper(locVar, getBook());
        checkedInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        fee.setCancelDate(date);
        fee.setID(getBook().getTravelPlanSegmentId());
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_CheckedOut() {
        String date = Randomness.generateCurrentXMLDate();
        String idLevel = "TravelPlanSegment";
        String faultString = "Cannot Calculate Cancellation Fee for cancelled or checked in or checked out reservation";
        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        checkedInHelper = new CheckInHelper(locVar, getBook());
        checkedInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        checkedInHelper.checkOut(getLocationId());
        fee.setCancelDate(date);
        fee.setID(getBook().getTravelPlanSegmentId());
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee", "negative" })
    public void TestRetrieveCancellationFee_DiningOnly() {
        String date = Randomness.generateCurrentXMLDate();
        String idLevel = "TravelPlanSegment";
        String faultString = "Travel Plan Segment Should not be NULL";

        ScheduledEventReservation res = new ShowDiningReservation("Latest");
        res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
        // res.folio().settlement().createSettlementMethod(FolioInterfaceSettlement.defaultSettlementScenario); // Omit this line to get one without a CC
        String tpsID = res.getConfirmationNumber();

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(tpsID);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        TestReporter.assertTrue(fee.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + fee.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(fee, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }

}
