package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee_TpsAndTcgExtRef extends AccommodationBaseTest {

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
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        setValues(getEnvironment());
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationFee" })
    public void testRetrieveCancellationFee_TpsAndTcgExtRef() {
        String date = Randomness.generateCurrentXMLDate();
        String tpsId = getBook().getTravelPlanSegmentId();
        String tcgId = getBook().getTravelComponentGroupingId();
        String idLevel = "TravelPlanSegment";
        String idLevelTcg = "TravelComponentGrouping";
        // String refType = getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceType");
        String refNum = getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber");
        String refSource = getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource");

        RetrieveCancellationFee fee = new RetrieveCancellationFee(environment);
        fee.setCancelDate(date);
        fee.setID(tpsId);
        fee.setIdentityLevel(idLevel);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request", BaseSoapCommands.ADD_NODE.commandAppend("identityDetails"));
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails[2]", BaseSoapCommands.ADD_NODE.commandAppend("id"));
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails[2]", BaseSoapCommands.ADD_NODE.commandAppend("identityLevel"));
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails[2]/id", tcgId);
        fee.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails[2]/identityLevel", idLevelTcg);
        fee.setReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        fee.setReferenceNumber(refNum);
        fee.setReferenceSource(refSource);
        fee.sendRequest();
        TestReporter.logAPI(!fee.getResponseStatusCode().equals("200"), "Verify that no error occurred" + fee.getFaultString(), fee);

        // Validations
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
