package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestReinstate_dvcCash_Negative extends BookDVCCashHelper {

    Reinstate reinstate;
    Cancel cancel;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        DVCSalesBaseTest.environment = environment;
        setUseDvcResort(true);
        bookDvcReservation("testCancel_M$", 1);
        setTpId(getFirstBooking().getTravelPlanId());
        makeCCPayment(Environment.getBaseEnvironmentName(environment));

    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void Test_Reinstate_dvcCash_Negative() {

        cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getFirstBooking().getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookResponse/externalReferenceDetail/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getFirstBooking().getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookResponse/externalReferenceDetail/externalReferenceSource"));

        cancel.sendRequest();
        reinstate = new Reinstate(environment, "Main_2");
        reinstate.setTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        reinstate.setTravelPlanSegmentId(getFirstBooking().getTravelPlanSegmentId());
        reinstate.sendRequest();

        String faultstring = "This reservation is not eligible to be Re-instated. Please contact the appropriate Reservation Office, Operations Support or Manager for assistance : DVC Reservation can not be ReInstated";

        validateApplicationError(reinstate, LiloResmErrorCode.NOT_ELIGIBLE_FOR_REINSTATE);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
