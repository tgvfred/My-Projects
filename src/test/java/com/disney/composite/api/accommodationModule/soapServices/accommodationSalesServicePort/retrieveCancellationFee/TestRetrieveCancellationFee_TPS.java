package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee_TPS extends AccommodationBaseTest {

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
        // locVar = environment;
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee" })
    public void testRetrieveCancellationFee_TPS() {
        String date = Randomness.generateCurrentXMLDate();
        String id = getBook().getTravelPlanSegmentId();
        String idLevel = "TravelPlanSegment";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();

        System.out.println(fee.getRequest());
        System.out.println(fee.getResponse());

        TestReporter.logAPI(!fee.getResponseStatusCode().equals("200"), "Verify that no error occurred" + fee.getFaultString(), fee);

        // Validations
        TestReporter.logStep("Validate response");
        String status = "false";
        String type = "Cancellation Fee";

        TestReporter.assertEquals(fee.getFeeName(), type, "Validate the fee name returned in the response [" + fee.getFeeName() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getWaived(), status, "Validate the waived status returned in the response [" + fee.getWaived() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getOverridden(), status, "Validate the fee name returned in the response [" + fee.getOverridden() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getClassName(), type, "Validate the class name returned in the response [" + fee.getClassName() + "] matches the expected response [" + type + "]");

        System.out.println(fee.getFeeName());
        System.out.println(fee.getWaived());
        System.out.println(fee.getOverridden());
        System.out.println(fee.getClassName());

    }
}
