package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class Test_UpdateGuaranteeStatus_dining_Negative extends AccommodationBaseTest {

    private ScheduledEventReservation diningRes;

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            diningRes.cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_dining_Negative() {

        String faultString = " Guarantee status can not be changed  : Guarantee status can not be changed";

        ScheduledEventReservation dining = new ShowDiningReservation(getEnvironment().toLowerCase().replace("_cm", ""));
        dining.setTravelPlanId(getBook().getTravelPlanId());
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.GUARANTEE_STATUS_CANNOT_CHANGE);

    }
}
