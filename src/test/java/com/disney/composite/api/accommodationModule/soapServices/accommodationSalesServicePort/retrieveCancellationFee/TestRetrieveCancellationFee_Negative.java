package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee_Negative extends AccommodationBaseTest {

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

}
