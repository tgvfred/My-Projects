package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee_TpsWithMultipleTcg extends AccommodationBaseTest {

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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee" })
    public void testRetrieveCancellationFee_TpsWithMultipleTcg() {
        String date = Randomness.generateCurrentXMLDate();
        String id = getBook().getTravelPlanSegmentId();
        String idLevel = "TravelPlanSegment";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();
        TestReporter.logAPI(!fee.getResponseStatusCode().equals("200"), "Verify that no error occurred" + fee.getFaultString(), fee);

        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.sendRequest();
        TestReporter.logAPI(!fee.getResponseStatusCode().equals("200"), "Verify that no error occurred" + fee.getFaultString(), fee);

        TestReporter.logStep("Validate response");
        String status = "false";
        String type = "Cancellation Fee";
        double productPrice = Double.parseDouble(fee.getProductPrice());

        TestReporter.assertGreaterThanZero(productPrice);
        TestReporter.assertEquals(fee.getFeeName(), type, "Validate the fee name returned in the response [" + fee.getFeeName() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getWaived(), status, "Validate the waived status returned in the response [" + fee.getWaived() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getOverridden(), status, "Validate the fee name returned in the response [" + fee.getOverridden() + "] matches the expected response [" + type + "]");
        TestReporter.assertEquals(fee.getClassName(), type, "Validate the class name returned in the response [" + fee.getClassName() + "] matches the expected response [" + type + "]");

    }

}
