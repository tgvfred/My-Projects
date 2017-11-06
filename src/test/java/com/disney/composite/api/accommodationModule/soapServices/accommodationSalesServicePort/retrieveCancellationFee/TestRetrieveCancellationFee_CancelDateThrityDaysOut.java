package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee_CancelDateThrityDaysOut extends AccommodationBaseTest {

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
    public void testRetrieveCancellationFee_CancelDateThrityDaysOut() {
        String date = Randomness.generateCurrentXMLDatetime(30);
        String id = getBook().getTravelPlanSegmentId();
        String idLevel = "TravelPlanSegment";

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(id);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        fee.sendRequest();
        TestReporter.logAPI(!fee.getResponseStatusCode().equals("200"), "Verify that no error occurred" + fee.getFaultString(), fee);

        // Validations
        TestReporter.logStep("Validate response");
        String status = "false";
        String price = "0.0";
        String feeId = "0";

        TestReporter.assertEquals(fee.getFeeId(), feeId, "Validate the fee id returned in the response [" + fee.getFeeId() + "] matches the expected response [" + feeId + "]");
        TestReporter.assertEquals(fee.getOverridePrice(), price, "Validate the overridden price returned in the response [" + fee.getOverridePrice() + "] matches the expected response [" + price + "]");
        TestReporter.assertEquals(fee.getProductPrice(), price, "Validate the product price returned in the response [" + fee.getProductPrice() + "] matches the expected response [" + price + "]");
        TestReporter.assertEquals(fee.getSellingPrice(), price, "Validate the class name returned in the response [" + fee.getSellingPrice() + "] matches the expected response [" + price + "]");
        TestReporter.assertEquals(fee.getWaived(), status, "Validate the status of the waived node returned in the response [" + fee.getWaived() + "] matches the expected response [" + status + "]");
        TestReporter.assertEquals(fee.getOverridden(), status, "Validate the status of the overridden node returned in the response [" + fee.getOverridden() + "] matches the expected response [" + status + "]");
    }

}
